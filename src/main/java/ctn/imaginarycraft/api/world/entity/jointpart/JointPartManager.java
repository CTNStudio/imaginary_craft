package ctn.imaginarycraft.api.world.entity.jointpart;

import ctn.imaginarycraft.core.ImaginaryCraft;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 关节部件管理器
 * <p>
 * 负责管理多关节实体的子部件实体，提供增删改查、数据同步和持久化功能。
 *
 * @param <T> 关节部件实体类型，必须实现 {@link IJointPartEntity} 接口
 * @author ImaginaryCraft Team
 */
public class JointPartManager<T extends LivingEntity & IJointPartEntity<?>> {
	/**
	 * 数据变更监听器接口
	 */
	@FunctionalInterface
	public interface DataChangeListener {
		void onDataChanged();
	}

	private final ArrayList<T> jointParts;
	private final Map<String, T> jointPartsNameMap;
	private final Map<UUID, T> jointPartsUUIDMap;
	private final Int2ObjectMap<Map.Entry<Integer, UUID>> jointPartSyncDataMap;
	private final Map<T, List<String>> jointNamesCache;

	private final int maxCapacity;
	private final boolean shiftOnRemove;
	private DataChangeListener dataChangeListener;

	/**
	 * 创建新的关节部件管理器（无限容量，移除时移位）
	 */
	public JointPartManager() {
		this(-1, true);
	}

	/**
	 * 创建新的关节部件管理器
	 *
	 * @param maxCapacity   最大容量，-1 表示无限制
	 * @param shiftOnRemove 移除时是否移位（true=移位，false=保留null占位）
	 */
	public JointPartManager(int maxCapacity, boolean shiftOnRemove) {
		this(new ArrayList<>(), maxCapacity, shiftOnRemove);
	}

	public JointPartManager(@NotNull ArrayList<T> initialParts, int maxCapacity, boolean shiftOnRemove) {
		this.jointParts = new ArrayList<>(maxCapacity);
		this.jointPartsNameMap = new HashMap<>();
		this.jointPartsUUIDMap = new HashMap<>();
		this.jointPartSyncDataMap = new Int2ObjectOpenHashMap<>();
		this.jointNamesCache = new HashMap<>();
		this.maxCapacity = maxCapacity;
		this.shiftOnRemove = shiftOnRemove;

		// 初始化现有部件的映射和缓存
		for (T part : initialParts) {
			if (part != null) {
				jointPartsNameMap.put(part.getPartName(), part);
				jointPartsUUIDMap.put(part.getUUID(), part);
				cacheJointNames(part);
			}
		}
	}

	/**
	 * 设置数据变更监听器
	 *
	 * @param listener 数据变更监听器
	 */
	public void setDataChangeListener(DataChangeListener listener) {
		this.dataChangeListener = listener;
	}

	private void notifyDataChanged() {
		if (dataChangeListener != null) {
			dataChangeListener.onDataChanged();
		}
	}

	// ==================== 核心操作方法 ====================

	/**
	 * 添加新的关节部件到管理器
	 *
	 * @param jointPart 要添加的关节部件实体
	 * @return 如果添加成功返回 true，如果已存在或达到容量上限返回 false
	 */
	public boolean addJointPart(@NotNull T jointPart) {
		if (maxCapacity > 0 && jointParts.size() >= maxCapacity) {
			return false;
		}

		if (jointPartsNameMap.containsKey(jointPart.getPartName()) ||
			jointPartsUUIDMap.containsKey(jointPart.getUUID())) {
			return false;
		}

		int index = jointParts.size();
		boolean result = jointParts.add(jointPart);
		if (result) {
			jointPartsNameMap.put(jointPart.getPartName(), jointPart);
			jointPartsUUIDMap.put(jointPart.getUUID(), jointPart);
			cacheJointNames(jointPart);
			updateSyncData(index, jointPart);
			notifyDataChanged();
		}
		return result;
	}

	/**
	 * 替换指定索引位置的关节部件
	 *
	 * @param index     要替换的关节部件索引位置
	 * @param jointPart 新的关节部件实体
	 * @return 被替换的旧关节部件实体
	 * @throws IndexOutOfBoundsException 如果索引超出有效范围 [0, size())
	 */
	public T replaceJointPart(int index, @NotNull T jointPart) {
		if (index < 0 || index >= jointParts.size()) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + jointParts.size());
		}

		T oldPart = jointParts.get(index);
		if (oldPart != null) {
			jointPartsNameMap.remove(oldPart.getPartName());
			jointPartsUUIDMap.remove(oldPart.getUUID());
			removeJointNamesCache(oldPart);
		}

		updateSyncData(index, jointPart);
		jointPartsNameMap.put(jointPart.getPartName(), jointPart);
		jointPartsUUIDMap.put(jointPart.getUUID(), jointPart);
		cacheJointNames(jointPart);

		T result = jointParts.set(index, jointPart);
		notifyDataChanged();
		return result;
	}

	/**
	 * 移除指定索引位置的关节部件
	 *
	 * @param index 要移除的关节部件索引位置
	 * @return 被移除的关节部件实体，可能为 null
	 * @throws IndexOutOfBoundsException 如果索引超出有效范围
	 */
	public T removeJointPart(int index) {
		if (index < 0 || index >= jointParts.size()) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + jointParts.size());
		}

		T part;
		if (shiftOnRemove) {
			part = jointParts.remove(index);
			rebuildSyncData();
		} else {
			part = jointParts.set(index, null);
			removeSyncData(index);
		}

		if (part != null) {
			jointPartsNameMap.remove(part.getPartName());
			jointPartsUUIDMap.remove(part.getUUID());
			removeJointNamesCache(part);
		}

		notifyDataChanged();
		return part;
	}

	/**
	 * 获取指定索引位置的关节部件
	 *
	 * @param index 关节部件的索引位置
	 * @return 指定位置的关节部件实体，可能为 null
	 * @throws IndexOutOfBoundsException 如果索引超出有效范围
	 */
	public T getJointPart(int index) {
		if (index < 0 || index >= jointParts.size()) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + jointParts.size());
		}
		return jointParts.get(index);
	}

	/**
	 * 根据部件名称获取关节部件
	 *
	 * @param partName 部件名称
	 * @return 对应的关节部件实体，如果不存在返回 null
	 */
	@Nullable
	public T getJointPartByName(@NotNull String partName) {
		return jointPartsNameMap.get(partName);
	}

	/**
	 * 根据实体ID获取关节部件
	 *
	 * @param entityId 实体UUID
	 * @return 对应的关节部件实体，如果不存在返回 null
	 */
	@Nullable
	public T getJointPartById(@NotNull UUID entityId) {
		return jointPartsUUIDMap.get(entityId);
	}

	/**
	 * 获取所有关节部件的不可变列表视图
	 *
	 * @return 包含所有关节部件的不可变列表
	 */
	@NotNull
	public List<T> getJointParts() {
		return Collections.unmodifiableList(jointParts);
	}

	/**
	 * 获取所有关节部件的不可变映射视图（按名称）
	 *
	 * @return 键为部件名称、值为实体的不可变映射
	 */
	@NotNull
	public Map<String, T> getJointPartNameMap() {
		return Collections.unmodifiableMap(jointPartsNameMap);
	}

	/**
	 * 获取所有关节部件的不可变映射视图（按ID）
	 *
	 * @return 键为实体ID、值为实体的不可变映射
	 */
	@NotNull
	public Map<UUID, T> getJointPartUUIDMap() {
		return Collections.unmodifiableMap(jointPartsUUIDMap);
	}

	public int size() {
		return jointParts.size();
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public boolean hasRemainingCapacity() {
		return maxCapacity < 0 || jointParts.size() < maxCapacity;
	}

	public boolean contains(@NotNull T jointPart) {
		return jointParts.contains(jointPart);
	}

	public boolean containsName(@NotNull String partName) {
		return jointPartsNameMap.containsKey(partName);
	}

	public boolean containsUUID(@NotNull UUID entityId) {
		return jointPartsUUIDMap.containsKey(entityId);
	}

	/**
	 * 获取指定部件的骨骼名称列表（带缓存）
	 *
	 * @param jointPart 关节部件实体
	 * @return 骨骼名称列表，如果部件为 null 返回空列表
	 */
	@NotNull
	public List<String> getJointNamesForPart(@NotNull T jointPart) {
		List<String> cached = jointNamesCache.get(jointPart);
		if (cached != null) {
			return cached;
		}

		List<String> names = jointPart.getJointsNames();
		jointNamesCache.put(jointPart, names);
		return names;
	}

	@NotNull
	public List<String> getAllJointNames() {
		List<String> names = new ArrayList<>();
		for (T part : jointParts) {
			if (part != null) {
				names.addAll(getJointNamesForPart(part));
			}
		}
		return names;
	}

	public void clear() {
		jointParts.clear();
		jointPartsNameMap.clear();
		jointPartsUUIDMap.clear();
		jointPartSyncDataMap.clear();
		jointNamesCache.clear();
		notifyDataChanged();
	}

	// ==================== 数据同步与持久化 ====================

	public void restoreFromSyncData(@NotNull Level level, @NotNull Int2ObjectMap<Map.Entry<Integer, UUID>> syncDataMap) {
		if (syncDataMap.isEmpty()) {
			if (!jointParts.isEmpty()) {
				clear();
			}
			return;
		}

		if (isSyncDataValid(syncDataMap)) {
			return;
		}

		clear();

		int maxIndex = findMaxIndex(syncDataMap);
		if (maxIndex >= 0) {
			preallocateCapacity(maxIndex);
		}

		for (Int2ObjectMap.Entry<Map.Entry<Integer, UUID>> entry : syncDataMap.int2ObjectEntrySet()) {
			int index = entry.getIntKey();
			var value = entry.getValue();
			UUID uuid = value.getValue();
			var partEntity = (T) level.getEntity(value.getKey());
			if (index < 0 || index >= jointParts.size()) {
				continue;
			}

			jointParts.set(index, partEntity);
			if (partEntity == null) {
				continue;
			}

			jointPartsNameMap.put(partEntity.getPartName(), partEntity);
			jointPartsUUIDMap.put(uuid, partEntity);
			cacheJointNames(partEntity);
		}

		jointPartSyncDataMap.putAll(syncDataMap);
	}

	@NotNull
	public Int2ObjectMap<Map.Entry<Integer, UUID>> getSyncDataMap() {
		return jointPartSyncDataMap;
	}

	// ==================== NBT持久化 ====================

	/**
	 * 将关节部件数据写入NBT标签
	 *
	 * @param compound 目标NBT标签
	 */
	public void writeToNbt(@NotNull CompoundTag compound) {
		ListTag listtag = new ListTag();
		for (T jointPart : jointParts) {
			if (jointPart != null) {
				listtag.add(NbtUtils.createUUID(jointPart.getParent().getUUID()));
			}
		}
		compound.put(ImaginaryCraft.modRlText("JointParts"), listtag);
	}

	/**
	 * 从NBT标签读取关节部件数据
	 *
	 * @param compound    源NBT标签
	 * @param serverLevel 服务端等级对象
	 */
	public void readFromNbt(@NotNull CompoundTag compound, @NotNull ServerLevel serverLevel) {
		clear();
		if (compound.get(ImaginaryCraft.modRlText("JointParts")) instanceof ListTag list) {
			for (Tag tag : list) {
				UUID parentUuid = NbtUtils.loadUUID(tag);
				var entity = serverLevel.getEntity(parentUuid);
				if (entity instanceof LivingEntity) {
					addJointPart((T) entity);
				}
			}
		}
	}

	// ==================== 私有辅助方法 ====================

	private boolean isSyncDataValid(Int2ObjectMap<Map.Entry<Integer, UUID>> syncDataMap) {
		if (jointParts.size() != syncDataMap.size()) {
			return false;
		}

		for (Int2ObjectMap.Entry<Map.Entry<Integer, UUID>> entry : syncDataMap.int2ObjectEntrySet()) {
			int index = entry.getIntKey();
			if (index >= jointParts.size() || jointParts.get(index) == null) {
				return false;
			}
		}
		return true;
	}

	private int findMaxIndex(Int2ObjectMap<Map.Entry<Integer, UUID>> syncDataMap) {
		int maxIndex = -1;
		for (int key : syncDataMap.keySet()) {
			if (key > maxIndex) {
				maxIndex = key;
			}
		}
		return maxIndex;
	}

	private void preallocateCapacity(int maxIndex) {
		if (maxIndex >= 0) {
			jointParts.ensureCapacity(maxIndex + 1);
			while (jointParts.size() <= maxIndex) {
				jointParts.add(null);
			}
		}
	}

	private void updateSyncData(int index, T part) {
		jointPartSyncDataMap.put(index, Map.entry(part.getId(), part.getUUID()));
	}

	private void removeSyncData(int index) {
		jointPartSyncDataMap.remove(index);
	}

	private void rebuildSyncData() {
		jointPartSyncDataMap.clear();
		for (int i = 0; i < jointParts.size(); i++) {
			T part = jointParts.get(i);
			if (part != null) {
				jointPartSyncDataMap.put(i, Map.entry(part.getId(), part.getUUID()));
			}
		}
	}

	private void cacheJointNames(T part) {
		if (part != null) {
			jointNamesCache.put(part, part.getJointsNames());
		}
	}

	private void removeJointNamesCache(T part) {
		if (part != null) {
			jointNamesCache.remove(part);
		}
	}
}
