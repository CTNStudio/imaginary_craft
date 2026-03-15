//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ctn.imaginarycraft.client.particle.text;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.imaginarycraft.init.ModParticleTypes;
import net.mehvahdjukaar.dummmmmmy.configs.ClientConfigs;
import net.mehvahdjukaar.dummmmmmy.configs.CritMode;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DamageNumberParticle extends Particle {
  private static final List<Float> POSITIONS = new ArrayList<>(Arrays.asList(0.0F, -0.25F, 0.12F, -0.12F, 0.25F));
  private final Font fontRenderer;
  private final Component text;
  private final int color;
  private final int darkColor;
  private float fadeout;
  private float prevFadeout;
  private float visualDY;
  private float prevVisualDY;
  private float visualDX;
  private float prevVisualDX;

  public DamageNumberParticle(ClientLevel clientLevel, double x, double y, double z, double amount, double dColor, double dz, Component text) {
    super(clientLevel, x, y, z);
    this.fontRenderer = Minecraft.getInstance().font;
    this.fadeout = -1.0F;
    this.prevFadeout = -1.0F;
    this.visualDY = 0.0F;
    this.prevVisualDY = 0.0F;
    this.visualDX = 0.0F;
    this.prevVisualDX = 0.0F;
    this.lifetime = 35;
    this.color = amount < (double) 0.0F ? -16711936 : (int) dColor;
    this.darkColor = ARGB32.color(255, (int) (this.rCol * 0.25F), (int) (this.rCol * 0.25F), (int) ((double) this.rCol * (double) 0.25F));
    double number = Math.abs(ClientConfigs.SHOW_HEARTHS.get() ? amount / (double) 2.0F : amount);
    boolean bold = ClientConfigs.CRIT_BOLD.get();
    this.yd = 1.0F;
    int index = CritMode.extractIntegerPart(dz);
    float critMult = CritMode.extractFloatPart(dz);
    this.text = text;
//    if (critMult == 0.0F) {
//      this.text = Component.literal((amount < (double) 0.0F ? "+" : "") + Dummmmmmy.DF2.format(number));
//    } else {
//      switch ((CritMode) ClientConfigs.CRIT_MODE.get()) {
//        case COLOR ->
//          this.text = Component.literal((amount < (double) 0.0F ? "+" : "") + Dummmmmmy.DF2.format(number)).setStyle(bold ? Style.EMPTY.withBold(bold) : Style.EMPTY);
//        case COLOR_AND_MULTIPLIER ->
//          this.text = Component.translatable("message.dummmmmmy.crit", new Object[]{Dummmmmmy.DF1.format(number), Dummmmmmy.DF1.format((double) critMult)}).setStyle(bold ? Style.EMPTY.withBold(bold) : Style.EMPTY);
//        default ->
//          this.text = Component.literal((amount < (double) 0.0F ? "+" : "") + Dummmmmmy.DF2.format(number));
//      }
//    }

    this.xd = (double) POSITIONS.get(Math.floorMod(index, POSITIONS.size()));
  }

  public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
    Vec3 cameraPos = camera.getPosition();
    float particleX = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPos.x());
    float particleY = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPos.y());
    float particleZ = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPos.z());
    int light = ClientConfigs.LIT_UP_PARTICLES.get() ? 15728880 : this.getLightColor(partialTicks);
    PoseStack poseStack = new PoseStack();
    poseStack.pushPose();
    poseStack.translate(particleX, particleY, particleZ);
    double distanceFromCam = (new Vec3(particleX, particleY, particleZ)).length();
    double inc = Mth.clamp(distanceFromCam / (double) 32.0F, 0.0F, 5.0F);
    poseStack.translate(0.0F, ((double) 1.0F + inc / (double) 4.0F) * (double) Mth.lerp(partialTicks, this.prevVisualDY, this.visualDY), 0.0F);
    float fadeout = Mth.lerp(partialTicks, this.prevFadeout, this.fadeout);
    float defScale = 0.006F;
    float scale = (float) ((double) defScale * distanceFromCam);
    poseStack.mulPose(camera.rotation());
    poseStack.translate(((double) 1.0F + inc) * (double) Mth.lerp(partialTicks, this.prevVisualDX, this.visualDX), 0.0F, 0.0F);
    poseStack.scale(scale, -scale, -scale);
    poseStack.translate(0.0F, (double) 4.0F * (double) (1.0F - fadeout), 0.0F);
    poseStack.scale(fadeout, fadeout, fadeout);
    poseStack.translate(0.0F, -distanceFromCam / (double) 10.0F, 0.0F);
    MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
//    RenderSystem.enableDepthTest();
    RenderSystem.depthMask(false);
    RenderSystem.disableDepthTest();
    RenderSystem.enableBlend();
    RenderSystem.blendFuncSeparate(770, 771, 1, 0);
    float x1 = 0.5F - (float) this.fontRenderer.width(this.text) / 2.0F;
    this.fontRenderer.drawInBatch(this.text, x1, 0.0F, this.color, false, poseStack.last().pose(), buffer, DisplayMode.SEE_THROUGH, 0, light);
    poseStack.translate(1.0F, 1.0F, 0.8);
    this.fontRenderer.drawInBatch(this.text, x1, 0.0F, this.darkColor, false, poseStack.last().pose(), buffer, DisplayMode.SEE_THROUGH, 0, light);
    buffer.endBatch();
    RenderSystem.depthMask(true);
    RenderSystem.enableDepthTest();
    poseStack.popPose();
  }

  public void tick() {
    this.xo = this.x;
    this.yo = this.y;
    this.zo = this.z;
    if (this.age++ >= this.lifetime) {
      this.remove();
    } else {
      float length = 6.0F;
      this.prevFadeout = this.fadeout;
      this.fadeout = (float) this.age > (float) this.lifetime - length ? ((float) this.lifetime - (float) this.age) / length : 1.0F;
      this.prevVisualDY = this.visualDY;
      this.visualDY = (float) ((double) this.visualDY + this.yd);
      this.prevVisualDX = this.visualDX;
      this.visualDX = (float) ((double) this.visualDX + this.xd);
      if (Math.sqrt(Mth.square((double) this.visualDX * (double) 1.5F) + (double) Mth.square(this.visualDY - 1.0F)) < 0.8999999999999999) {
        this.yd /= 2.0F;
      } else {
        this.yd = 0.0F;
        this.xd = 0.0F;
      }
    }

  }

  public ParticleRenderType getRenderType() {
    return ParticleRenderType.CUSTOM;
  }

  public static class Provider implements ParticleProvider<Options> {

    public Particle createParticle(Options typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return new DamageNumberParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.component);
    }
  }

  public record Options(Component component) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      ComponentSerialization.CODEC.fieldOf("component").forGetter(Options::component)
    ).apply(instance, Options::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
      ComponentSerialization.STREAM_CODEC, Options::component,
      Options::new);

    @Override
    public @NotNull ParticleType<?> getType() {
      return ModParticleTypes.DAMAGE_NUMBER_PARTICLE.get();
    }
  }
}
