package ctn.imaginarycraft.client.particle.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class DamageNumberParticle1 extends Particle {
  private final double isCrit;
  private final int numberLifetime = 30;
  private final int damageNumberId;
  private int phase = 0;
  private int phaseTimer = 0;
  private float startX, startY, startZ;
  private float targetX, targetY, targetZ;

  public DamageNumberParticle1(ClientLevel clientLevel, double x, double y, double z,
                               double amount, double damageTypeID, double isCrit) {
    super(clientLevel, x, y, z);
    this.setSize(0.1F, 0.1F);
    this.startX = (float) x;
    this.startY = (float) y;
    this.startZ = (float) z;
    this.isCrit = isCrit;
    if (this.isCrit > 0) {
      this.lifetime = numberLifetime * 2;
    } else {
      this.lifetime = numberLifetime;
    }
    float scale = this.isCrit > 0 ? 1.1f : 0.8f;
    float number = (float) Math.abs(amount);
    String text = /*AdamUtil.formatNumber(number)*/null;
    this.damageNumberId = DamageNumberRenderer.registerDamageNumber(text, (int) damageTypeID, x, y, z, scale, this.isCrit, this.lifetime);
    calculateRandomTarget();
    this.x = this.startX;
    this.y = this.startY;
    this.z = this.startZ;
  }

  private void calculateRandomTarget() {
    float verticalDistance = this.random.nextFloat();
    float horizontalRadius = 0.5f;
    float randomAngle = this.random.nextFloat() * 360.0f;
    float angleRad = randomAngle * Mth.DEG_TO_RAD;
    float randomRadius = this.random.nextFloat() * horizontalRadius;
    float horizontalX = Mth.cos(angleRad) * randomRadius;
    float horizontalZ = Mth.sin(angleRad) * randomRadius;
    this.targetX = this.startX + horizontalX;
    this.targetZ = this.startZ + horizontalZ;
    this.targetY = this.startY + verticalDistance;
    if (this.isCrit > 0) {
      this.targetY += 0.5f;
      this.targetX += (this.random.nextFloat() - 0.5f) * 0.2f;
      this.targetZ += (this.random.nextFloat() - 0.5f) * 0.2f;
    }
  }

  private void handleRisePhase() {
    int riseTime = this.isCrit > 0 ? numberLifetime * 2 / 3 + 5 : numberLifetime / 3 + 5;
    float progress = Math.min(1.0f, phaseTimer / (float) riseTime);
    progress = smoothStep(progress);
    this.x = Mth.lerp(progress, this.startX, this.targetX);
    this.y = Mth.lerp(progress, this.startY, this.targetY);
    this.z = Mth.lerp(progress, this.startZ, this.targetZ);
    if (progress >= 1.0f) {
      phase = 1;
      phaseTimer = 0;
      this.startX = (float) this.x;
      this.startY = (float) this.y;
      this.startZ = (float) this.z;
    }
  }

  private void handleHoverPhase() {
    int hoverTime = this.isCrit > 0 ? numberLifetime * 2 : numberLifetime / 3;

    if (phaseTimer >= hoverTime) {
      phase = 2;
      phaseTimer = 0;
    } else {
      float floatOffset = Mth.sin(this.age * 0.2f) * 0.05f;
      this.y = this.targetY + floatOffset;
      float swingOffset = Mth.sin(this.age * 0.15f) * 0.02f;
      this.x = this.targetX + swingOffset;
      this.z = this.targetZ + swingOffset;
    }
  }

  private void handleFallPhase() {
    int fallTime = this.isCrit > 0 ? numberLifetime * 2 : numberLifetime / 3;
    float fallProgress = Math.min(1.0f, phaseTimer / (float) fallTime);
    fallProgress = easeOutCubic(fallProgress);
    float fallDistance = 0.5f + (this.isCrit > 0 ? 0.3f : 0f);
    this.y = this.targetY - fallProgress * fallDistance;
    this.alpha = 1.0f - fallProgress;
    if (phaseTimer < fallTime) {
      float drift = Mth.sin(this.age * 0.1f) * 0.01f;
      this.x += drift;
      this.z += drift;
    }
  }

  private float smoothStep(float t) {
    return t * t * (3.0f - 2.0f * t);
  }


  private float easeOutCubic(float t) {
    float f = t - 1.0f;
    return f * f * f + 1.0f;
  }

  @Override
  public void tick() {
    this.xo = this.x;
    this.yo = this.y;
    this.zo = this.z;

    if (this.age++ >= this.lifetime) {
      DamageNumberRenderer.removeDamageNumber(this.damageNumberId);
      this.remove();
    } else {
      phaseTimer++;
      switch (phase) {
        case 0 -> handleRisePhase();
        case 1 -> handleHoverPhase();
        case 2 -> handleFallPhase();
      }

      DamageNumberRenderer.updateDamageNumber(
        this.damageNumberId, this.x, this.y, this.z, this.alpha
      );
    }
  }

  @Override
  public void render(@NotNull VertexConsumer consumer, @NotNull Camera camera, float partialTicks) {
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return ParticleRenderType.NO_RENDER;
  }

  public static class Factory implements ParticleProvider<SimpleParticleType> {
    public Factory(SpriteSet ignoredSpriteSet) {
    }

    @Override
    public @NotNull Particle createParticle(@NotNull SimpleParticleType typeIn, @NotNull ClientLevel worldIn,
                                            double x, double y, double z,
                                            double xSpeed, double ySpeed, double zSpeed) {
      return new DamageNumberParticle1(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
    }
  }
}
