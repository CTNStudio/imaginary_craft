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
    CANNON("cannon", DatagenItemTag.CANNON, 100, 15),
    /**
     * 枪
     */
    GUN("gun", DatagenItemTag.GUN, -1, -1),
    /**
     * 手枪 攻击速度 0.667 攻击距离 10
     */
    PISTOL("pistol", DatagenItemTag.PISTOL, 12, 10),
    /**
     * 来复枪 攻击速度 1 攻击距离 15
     */
    RIFLE("rifle", DatagenItemTag.RIFLE, 20, 15),
    /**
     * 弩 攻击速度 2 攻击距离 20
     */
    CROSSBOW("crossbow", DatagenItemTag.CROSSBOW, 40, 20),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final int attackSpeedTick;
    private final float attackDistance;

    RemoteTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, int attackSpeedTick, float attackDistance) {
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

    public int getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public float getAttackSpeed() {
      return 20.0f / this.attackSpeedTick - 4;
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
    AXE("axe", DatagenItemTag.AXE, 20, 2),
    /**
     * 拳套 攻击速度 2 攻击距离 2 每次造成2次伤害
     */
    FIST("fist", DatagenItemTag.FIST, 40, 2),
    /**
     * 锤 攻击速度 3 攻击距离 5
     */
    HAMMER("hammer", DatagenItemTag.HAMMER, 60, 5),
    /**
     * 刀 攻击速度 0.667 攻击距离 2
     */
    KNIFE("knife", DatagenItemTag.KNIFE, 10, 2),
    /**
     * 棁 攻击速度 2 攻击距离 3
     */
    MACE("mace", DatagenItemTag.MACE, 40, 3),
    /**
     * 矛 攻击速度 1.5 攻击距离 4
     */
    SPEAR("spear", DatagenItemTag.SPEAR, 30, 4),
    /**
     * 剑  攻击速度 1.667 攻击距离 3
     */
    SWORDS("swords", DatagenItemTag.SWORDS, 12, 0),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final int attackSpeedTick;
    private final float attackDistance;

    MeleeTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, int attackSpeedTick, float attackDistance) {
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

    public int getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public float getAttackSpeed() {
      return 20.0f / this.attackSpeedTick - 4;
    }

    public float getAttackDistance() {
      return this.attackDistance != -1 ? this.attackDistance - 3 : -1;
    }
  }
}
