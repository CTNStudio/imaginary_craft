package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import net.minecraft.network.FriendlyByteBuf;
import org.joml.Vector3f;

/**
 * 服务端触手数据 - 仅包含位置和旋转信息
 * 用于网络同步到客户端
 */
public record TentacleData(Vector3f position, float rotX, float rotY, float rotZ) {

  /**
   * 从字节缓冲区读取触手数据
   */
  public static TentacleData fromNetwork(FriendlyByteBuf buf) {
    Vector3f pos = new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
    float rotX = buf.readFloat();
    float rotY = buf.readFloat();
    float rotZ = buf.readFloat();
    return new TentacleData(pos, rotX, rotY, rotZ);
  }

  /**
   * 将触手数据写入字节缓冲区
   */
  public void toNetwork(FriendlyByteBuf buf) {
    buf.writeFloat(position.x);
    buf.writeFloat(position.y);
    buf.writeFloat(position.z);
    buf.writeFloat(rotX);
    buf.writeFloat(rotY);
    buf.writeFloat(rotZ);
  }

  /**
   * 从渲染器的 Tentacle3D 创建服务端数据
   */
  public static TentacleData[] fromTentacle3D(Object tentacle3D) {
    // 这个类将在服务端使用，通过反射或直接调用获取数据
    // 实际实现会根据具体架构调整
    return new TentacleData[0];
  }
}
