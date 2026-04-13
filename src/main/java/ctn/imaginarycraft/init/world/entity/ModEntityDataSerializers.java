package ctn.imaginarycraft.init.world.entity;

import ctn.imaginarycraft.api.ModByteBufCodecs;
import ctn.imaginarycraft.core.ImaginaryCraft;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ModEntityDataSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = ImaginaryCraft.modRegister(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS);

	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Int2ObjectMap<Map.Entry<Integer, UUID>>>> ENTITY_ID = register(
		"entity_id", EntityDataSerializer.forValueType(ByteBufCodecs.map(Int2ObjectOpenHashMap::new,
			ByteBufCodecs.INT, ModByteBufCodecs.<ByteBuf, Map.Entry<Integer, UUID>, Integer, UUID>entry(Map::entry, ByteBufCodecs.INT, UUIDUtil.STREAM_CODEC))));
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<List<String>>> JOINT_NAMES = register(
		"joint_names", EntityDataSerializer.forValueType(ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())));

	private static <T> DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<T>> register(String name, EntityDataSerializer<T> serializer) {
		return ModEntityDataSerializers.REGISTRY.register(name, () -> serializer);
	}
}
