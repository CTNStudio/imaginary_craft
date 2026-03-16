package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;

import java.util.List;

/**
 * 基于 GeoBone 的 3D 多节触手运动算法（客户端渲染版本）
 * 核心：正向运动学 + 角度限制 + 自然摆动插值
 * 每节触手对应一个 GeoBone，直接操作骨骼旋转实现运动
 * <p>
 * 链式结构：每个 TentacleSegment 包含下一个 segment 的引用
 */
public class ClientTentacle {
  private final GeoBone[] bones;
  private final TentacleSegment[] segments;
  private final float swingSpeed;
  private final float swingAmplitude;

  /**
   * @param rootBone       根骨骼
   * @param maxPitch       最大俯仰角度（弧度）
   * @param minPitch       最小俯仰角度（弧度）
   * @param maxYaw         最大偏航角度（弧度）
   * @param minYaw         最小偏航角度（弧度）
   * @param swingSpeed     摆动速度
   * @param swingAmplitude 摆动幅度
   */
  public ClientTentacle(
    GeoBone rootBone,
    float maxPitch,
    float minPitch,
    float maxYaw,
    float minYaw,
    float swingSpeed,
    float swingAmplitude
  ) {
    this.swingSpeed = swingSpeed;
    this.swingAmplitude = swingAmplitude;

    // 获取所有子骨骼作为触手节
    List<GeoBone> childBones = rootBone.getChildBones();
    int segmentCount = childBones.size();
    this.bones = new GeoBone[segmentCount];
    this.segments = new TentacleSegment[segmentCount];

    // 计算每节长度
    float[] segmentLengths = new float[segmentCount];
    GeoBone previousBone = rootBone;
    for (int i = 0; i < segmentCount; i++) {
      GeoBone currentBone = childBones.get(i);
      bones[i] = currentBone;
      segmentLengths[i] = Math.max(0.0f, distance(previousBone, currentBone));
      previousBone = currentBone;
    }

    // 初始化触手节（从根到末节，链式连接）
    TentacleSegment firstSegment = null;
    TentacleSegment prevSegment = null;

    for (int i = 0; i < segmentCount; i++) {
      TentacleSegment segment = new TentacleSegment(segmentLengths[i], maxPitch, minPitch, maxYaw, minYaw);

      if (firstSegment == null) {
        firstSegment = segment;
      }

      if (prevSegment != null) {
        prevSegment.next = segment;
      }

      prevSegment = segment;
      segments[i] = segment;
    }
  }

  private static float distance(GeoBone first, GeoBone bone) {
    return new Vector3f(first.getPivotX(), first.getPivotY(), first.getPivotZ())
      .distance(new Vector3f(bone.getPivotX(), bone.getPivotY(), bone.getPivotZ()));
  }

  /**
   * 计算并应用自然摆动效果
   *
   * @param deltaTime 帧间隔时间
   */
  private void applyNaturalSwing(float deltaTime) {
    float time = System.nanoTime() / 1e9f * swingSpeed;

    // 使用链式遍历
    TentacleSegment segment = getFirstSegment();
    int index = 0;

    while (segment != null) {
      float segmentAmplitude = swingAmplitude * (1 + index * 0.2f);
      float deltaPitch = (float) (Math.sin(time + index * 0.5f) * segmentAmplitude * deltaTime);
      float deltaYaw = (float) (Math.cos(time + index * 0.8f) * segmentAmplitude * deltaTime);
      deltaPitch += (float) (Math.random() - 0.5) * 0.01f;
      deltaYaw += (float) (Math.random() - 0.5) * 0.01f;

      segment.addSwingAngle(deltaPitch, deltaYaw);

      segment = segment.next;
      index++;
    }
  }

  /**
   * 移动触手的某一节到目标位置（逆向运动学）
   *
   * @param segmentIndex   要移动的节索引（0 为根节点）
   * @param targetPosition 目标世界坐标
   * @param iterations     IK 迭代次数
   */
  public void moveSegmentTo(int segmentIndex, Vector3f targetPosition, int iterations) {
    if (segmentIndex < 0 || segmentIndex >= segments.length) {
      return;
    }

    for (int iter = 0; iter < iterations; iter++) {
      // 使用链式反向遍历
      TentacleSegment current = getSegmentByIndex(segmentIndex);

      for (int i = segmentIndex; i > 0; i--) {
        TentacleSegment parent = current.prev;

        if (parent == null) break;

        Vector3f toCurrent = new Vector3f(current.getWorldPosition()).sub(parent.getWorldPosition());
        toCurrent.normalize();

        Vector3f toTarget = new Vector3f(targetPosition).sub(parent.getWorldPosition());
        toTarget.normalize();

        float angle = toCurrent.angle(toTarget);
        if (angle > 0.001f) {
          Vector3f axis = new Vector3f(toCurrent).cross(toTarget).normalize();

          Quaternionf parentRotInv = new Quaternionf(parent.rotation).invert();
          Vector3f localAxis = parentRotInv.transform(new Vector3f(axis));

          float deltaPitch = (float) Math.asin(localAxis.y * Math.sin(angle));
          float deltaYaw = (float) Math.atan2(localAxis.x, localAxis.z);

          parent.addSwingAngle(deltaPitch * 0.1f, deltaYaw * 0.1f);
        }

        current = parent;
      }

      calculateWorldPositions();
    }
  }

  private TentacleSegment getSegmentByIndex(int index) {
    if (index < 0 || index >= segments.length) {
      return null;
    }

    TentacleSegment segment = getFirstSegment();
    for (int i = 0; i < index && segment != null; i++) {
      segment = segment.next;
    }
    return segment;
  }

  /**
   * 更新触手状态并应用到 GeoBone（每一帧调用）
   *
   * @param deltaTime 帧间隔时间
   */
  public void update(float deltaTime) {
    applyNaturalSwing(deltaTime);
    calculateWorldPositions();
    applyToBones();
  }

  /**
   * 获取第一个触手节
   */
  public TentacleSegment getFirstSegment() {
    return segments.length > 0 ? segments[0] : null;
  }

  /**
   * 将计算结果应用到 GeoBone 旋转
   */
  private void applyToBones() {
    for (int i = 0; i < bones.length && i < segments.length; i++) {
      GeoBone bone = bones[i];
      TentacleSegment segment = segments[i];

      // 从四元数提取欧拉角（XYZ 顺序）
      Quaternionf rotation = segment.getRotation();

      // 使用 JOML 的方法获取旋转角度
      float x = (float) Math.asin(-2.0f * (rotation.y * rotation.w - rotation.x * rotation.z));
      float y = (float) Math.atan2(2.0f * (rotation.x * rotation.w + rotation.y * rotation.z),
        1.0f - 2.0f * (rotation.x * rotation.x + rotation.y * rotation.y));
      float z = (float) Math.atan2(2.0f * (rotation.y * rotation.z + rotation.x * rotation.w),
        1.0f - 2.0f * (rotation.y * rotation.y + rotation.z * rotation.z));

      // 应用旋转到骨骼（Geckolib 使用弧度）
      bone.setRotX(x);
      bone.setRotY(y);
      bone.setRotZ(z);
    }
  }

  /**
   * 计算所有节的世界坐标（正向运动学）
   */
  private void calculateWorldPositions() {
    // 使用链式遍历
    TentacleSegment segment = getFirstSegment();

    while (segment != null) {
      segment.calculateWorldPosition();
      segment = segment.next;
    }
  }

  public Vector3f[] getSegmentPositions() {
    Vector3f[] positions = new Vector3f[segments.length];

    TentacleSegment segment = getFirstSegment();
    int index = 0;

    while (segment != null) {
      positions[index++] = segment.getWorldPosition();
      segment = segment.next;
    }

    return positions;
  }

  public GeoBone[] getBones() {
    return bones;
  }

  /**
   * 获取所有节的数据（用于服务端同步）
   */
  public TentacleData[] getSegmentData() {
    TentacleData[] data = new TentacleData[segments.length];

    TentacleSegment segment = getFirstSegment();
    int index = 0;

    while (segment != null) {
      Vector3f pos = segment.getWorldPosition();
      Quaternionf rot = segment.getRotation();

      // 从四元数提取欧拉角
      float x = (float) Math.asin(-2.0f * (rot.y * rot.w - rot.x * rot.z));
      float y = (float) Math.atan2(2.0f * (rot.x * rot.w + rot.y * rot.z),
        1.0f - 2.0f * (rot.x * rot.x + rot.y * rot.y));
      float z = (float) Math.atan2(2.0f * (rot.y * rot.z + rot.x * rot.w),
        1.0f - 2.0f * (rot.y * rot.y + rot.z * rot.z));

      data[index++] = new TentacleData(pos, x, y, z);
      segment = segment.next;
    }

    return data;
  }

  /**
   * 触手节（链式结构）
   * 每个 segment 包含 next 引用指向下一节
   */
  public static class TentacleSegment {
    private final float length;
    private final float maxPitch;
    private final float minPitch;
    private final float maxYaw;
    private final float minYaw;

    // 链式结构引用
    TentacleSegment prev;  // 前一节（包级可见，便于内部访问）
    TentacleSegment next;  // 下一节（包级可见，便于内部访问）

    private float currentPitch;
    private float currentYaw;
    private final Vector3f worldPosition;
    private final Quaternionf rotation;

    public TentacleSegment(float length, float maxPitch, float minPitch,
                           float maxYaw, float minYaw) {
      this.length = length;
      this.maxPitch = maxPitch;
      this.minPitch = minPitch;
      this.maxYaw = maxYaw;
      this.minYaw = minYaw;
      this.prev = null;
      this.next = null;
      this.worldPosition = new Vector3f();
      this.rotation = new Quaternionf();
      this.currentPitch = 0;
      this.currentYaw = 0;
    }

    public Quaternionf getRotation() {
      return rotation;
    }

    /**
     * 添加摆动角度增量
     *
     * @param deltaPitch 俯仰角增量
     * @param deltaYaw   偏航角增量
     */
    public void addSwingAngle(float deltaPitch, float deltaYaw) {
      currentPitch = clampAngle(currentPitch + deltaPitch, minPitch, maxPitch);
      currentYaw = clampAngle(currentYaw + deltaYaw, minYaw, maxYaw);
    }

    private float clampAngle(float angle, float min, float max) {
      return Math.max(min, Math.min(max, angle));
    }

    public void calculateWorldPosition() {
      if (prev == null) {
        // 根节点
        worldPosition.set(0, 0, 0);
        rotation.identity();
      } else {
        // 基于前一节计算
        Vector3f direction = new Vector3f(0, length, 0);
        rotateVector(direction, currentPitch, currentYaw);
        prev.rotation.transform(direction);
        worldPosition.set(prev.worldPosition).add(direction);
        Quaternionf localRot = getRotationQuat(currentPitch, currentYaw);
        rotation.set(prev.rotation).mul(localRot);
      }
    }

    private void rotateVector(Vector3f vec, float pitch, float yaw) {
      new Quaternionf().rotateX(pitch).transform(vec);
      new Quaternionf().rotateY(yaw).transform(vec);
    }

    private Quaternionf getRotationQuat(float pitch, float yaw) {
      Quaternionf pitchRot = new Quaternionf().rotateX(pitch);
      Quaternionf yawRot = new Quaternionf().rotateY(yaw);
      return new Quaternionf(pitchRot).mul(yawRot);
    }

    public Vector3f getWorldPosition() {
      return worldPosition;
    }
  }
}
