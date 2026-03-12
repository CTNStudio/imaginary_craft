package ctn.imaginarycraft.client.renderer.item;

import ctn.imaginarycraft.client.ModRenderTypes;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

/**
 * 魔弹法阵渲染
 */
public class MagicBulletMagicCircleRenderer {
  public static final RenderType MAGIC_CIRCLE_16X = ModRenderTypes.magicBulletMagicCircle(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle16x.png"));
  public static final RenderType MAGIC_CIRCLE_32X = ModRenderTypes.magicBulletMagicCircle(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle32x.png"));
  public static final RenderType MAGIC_CIRCLE_128X = ModRenderTypes.magicBulletMagicCircle(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle128x.png"));

  public static void magicBulletMagicCircle(AbstractClientPlayer entity, float partialTick, MultiBufferSource multiBufferSource) {
  }
}
