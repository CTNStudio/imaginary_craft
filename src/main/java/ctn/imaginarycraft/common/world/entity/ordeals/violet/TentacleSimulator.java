package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * 服务端触手运动模拟器
 * 在服务器端运行简化的触手运动计算，仅包含位置和旋转数据
 */
public class TentacleSimulator {
  private final TentacleSegment[] segments;
  private final float swingSpeed;
  private final float swingAmplitude;

  /**
   * @param segmentCount   触手节数量
   * @param segmentLengths 每节长度数组
   * @param maxPitch       最大俯仰角度（弧度）
   * @param minPitch       最小俯仰角度（弧度）
   * @param maxYaw         最大偏航角度（弧度）
   * @param minYaw         最小偏航角度（弧度）
   * @param swingSpeed     摆动速度
   * @param swingAmplitude 摆动幅度
   */
  public TentacleSimulator(int segmentCount, float[] segmentLengths, float maxPitch, float minPitch, float maxYaw, float minYaw, float swingSpeed, float swingAmplitude) {
    this.swingSpeed = swingSpeed;
    this.swingAmplitude = swingAmplitude;
    this.segments = new TentacleSegment[segmentCount];

    // 初始化触手节（从根到末节）
    for (int i = 0; i < segmentCount; i++) {
      TentacleSegment parent = (i == 0) ? null : segments[i - 1];
      float length = (i < segmentLengths.length) ? segmentLengths[i] : segmentLengths[segmentLengths.length - 1];
      segments[i] = new TentacleSegment(length, maxPitch, minPitch, maxYaw, minYaw, parent);
    }
  }

  /**
   * 更新触手状态
   *
   * @param deltaTime 帧间隔时间（秒）
   */
  public void update(float deltaTime) {
    applyNaturalSwing(deltaTime);
    calculateWorldPositions();
  }

  /**
   * 应用自然摆动效果
   */
  private void applyNaturalSwing(float deltaTime) {
    float time = System.nanoTime() / 1e9f * swingSpeed;
    for (int i = 0; i < segments.length; i++) {
      float segmentAmplitude = swingAmplitude * (1 + i * 0.2f);
      float deltaPitch = (float) (Math.sin(time + i * 0.5f) * segmentAmplitude * deltaTime);
      float deltaYaw = (float) (Math.cos(time + i * 0.8f) * segmentAmplitude * deltaTime);
      deltaPitch += (float) (Math.random() - 0.5) * 0.01f;
      deltaYaw += (float) (Math.random() - 0.5) * 0.01f;

      segments[i].addSwingAngle(deltaPitch, deltaYaw);
    }
  }

  /**
   * 计算所有节的世界坐标
   */
  private void calculateWorldPositions() {
    for (TentacleSegment segment : segments) {
      segment.calculateWorldPosition();
    }
  }

  /**
   * 获取所有触手节的数据（用于网络同步）
   */
  public TentacleData[] getTentacleData() {
    TentacleData[] data = new TentacleData[segments.length];
    for (int i = 0; i < segments.length; i++) {
      Vector3f pos = segments[i].getWorldPosition();
      Quaternionf rot = segments[i].getRotation();

      // 从四元数提取欧拉角
      float x = (float) Math.asin(-2.0f * (rot.y * rot.w - rot.x * rot.z));
      float y = (float) Math.atan2(2.0f * (rot.x * rot.w + rot.y * rot.z), 1.0f - 2.0f * (rot.x * rot.x + rot.y * rot.y));
      float z = (float) Math.atan2(2.0f * (rot.y * rot.z + rot.x * rot.w), 1.0f - 2.0f * (rot.y * rot.y + rot.z * rot.z));

      data[i] = new TentacleData(pos, x, y, z);
    }
    return data;
  }

  /**
   * 设置触手某一节的目标位置（使用 IK）
   */
  public void moveSegmentTo(int segmentIndex, Vector3f targetPosition, int iterations) {
    if (segmentIndex < 0 || segmentIndex >= segments.length) {
      return;
    }

    // 使用 CCD（循环坐标下降）算法进行逆向运动学
    for (int iter = 0; iter < iterations; iter++) {
      for (int i = segmentIndex; i > 0; i--) {
        TentacleSegment current = segments[i];
        TentacleSegment parent = segments[i - 1];

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
      }

      calculateWorldPositions();
    }
  }

  /**
   * 单个触手节
   */
  public static class TentacleSegment {
    private final float length;
    private final float maxPitch;
    private final float minPitch;
    private final float maxYaw;
    private final float minYaw;
    private final TentacleSegment parent;
    private float currentPitch;
    private float currentYaw;
    private final Vector3f worldPosition;
    private final Quaternionf rotation;

    public TentacleSegment(float length, float maxPitch, float minPitch, float maxYaw, float minYaw, TentacleSegment parent) {
      this.length = length;
      this.maxPitch = maxPitch;
      this.minPitch = minPitch;
      this.maxYaw = maxYaw;
      this.minYaw = minYaw;
      this.parent = parent;
      this.worldPosition = new Vector3f();
      this.rotation = new Quaternionf();
      this.currentPitch = 0;
      this.currentYaw = 0;
    }

    public void addSwingAngle(float deltaPitch, float deltaYaw) {
      currentPitch = clampAngle(currentPitch + deltaPitch, minPitch, maxPitch);
      currentYaw = clampAngle(currentYaw + deltaYaw, minYaw, maxYaw);
    }

    private float clampAngle(float angle, float min, float max) {
      return Math.max(min, Math.min(max, angle));
    }

    public void calculateWorldPosition() {
      if (parent == null) {
        worldPosition.set(0, 0, 0);
        rotation.identity();
      } else {
        Vector3f direction = new Vector3f(0, length, 0);
        rotateVector(direction, currentPitch, currentYaw);
        parent.rotation.transform(direction);
        worldPosition.set(parent.worldPosition).add(direction);
        Quaternionf localRot = getRotationQuat(currentPitch, currentYaw);
        rotation.set(parent.rotation).mul(localRot);
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

    public Quaternionf getRotation() {
      return rotation;
    }
  }
}
