package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Set;

interface Type {
  String getName();

  void addItem(DeferredItem<? extends Item> item);

  enum SpecialTemplateType implements Type {
    /**
     * 特殊武器
     */
    SPECIAL("special", DatagenItemTag.SPECIAL),
    /**
     * 近战武器
     */
    MELEE("melee", DatagenItemTag.MELEE),
    /**
     * 远程武器
     */
    REMOTE("remote", DatagenItemTag.REMOTE),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;

    SpecialTemplateType(final String name, final Set<DeferredItem<? extends Item>> set) {
      this.name = name;
      this.set = set;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }
  }

  enum RemoteTemplateType implements Type {
    /**
     * 加农炮 蓄力射击 攻击速度 5 攻击距离 15
     */
    CANNON("cannon", DatagenItemTag.CANNON, 5, 15),
    /**
     * 枪
     */
    GUN("gun", DatagenItemTag.GUN, -1, -1),
    /**
     * 手枪 攻击速度 0.667 攻击距离 10
     */
    PISTOL("pistol", DatagenItemTag.PISTOL, 0.667f, 10),
    /**
     * 来复枪 攻击速度 1 攻击距离 15
     */
    RIFLE("rifle", DatagenItemTag.RIFLE, 1, 15),
    /**
     * 弩 攻击速度 2 攻击距离 20
     */
    CROSSBOW("crossbow", DatagenItemTag.CROSSBOW, 2, 20),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final float attackSpeedTick;
    private final float attackDistance;

    RemoteTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, float attackSpeedTick, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeedTick = attackSpeedTick;
      this.attackDistance = attackDistance;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }

    public float getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public int getInvincibleTick() {
      return (int) (20 * Math.min(1, getAttackSpeedTick()));
    }

    public float getAttackSpeed() {
      return EgoWeaponItems.minuteToSpeedConversion(getAttackSpeedTick());
    }

    public float getAttackDistance() {
      return this.attackDistance != -1 ? this.attackDistance - 3 : -1;
    }
  }

  enum MeleeTemplateType implements Type {
    // 近战武器
    /**
     * 斧 攻击速度 1 攻击距离 2
     */
    AXE("axe", DatagenItemTag.AXE, 1, 2),
    /**
     * 拳套 攻击速度 2 攻击距离 2 每次造成2次伤害
     */
    FIST("fist", DatagenItemTag.FIST, 2, 2),
    /**
     * 锤 攻击速度 3 攻击距离 5
     */
    HAMMER("hammer", DatagenItemTag.HAMMER, 3, 5),
    /**
     * 刀 攻击速度 0.667 攻击距离 2
     */
    KNIFE("knife", DatagenItemTag.KNIFE, 0.667f, 2),
    /**
     * 棁 攻击速度 2 攻击距离 3
     */
    MACE("mace", DatagenItemTag.MACE, 2, 3),
    /**
     * 矛 攻击速度 1.5 攻击距离 4
     */
    SPEAR("spear", DatagenItemTag.SPEAR, 1.5f, 4),
    /**
     * 剑  攻击速度 1.667 攻击距离 3
     */
    SWORDS("swords", DatagenItemTag.SWORDS, 1.667f, 0),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final float attackSpeedTick;
    private final float attackDistance;

    MeleeTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, float attackSpeedTick, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeedTick = attackSpeedTick;
      this.attackDistance = attackDistance;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }

    public float getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public float getAttackSpeed() {
      return EgoWeaponItems.minuteToSpeedConversion(getAttackSpeedTick());
    }

    public int getInvincibleTick() {
      return (int) (20 * Math.min(1, getAttackSpeedTick()));
    }

    public float getAttackDistance() {
      return this.attackDistance != -1 ? this.attackDistance - 3 : -1;
    }
  }
}
