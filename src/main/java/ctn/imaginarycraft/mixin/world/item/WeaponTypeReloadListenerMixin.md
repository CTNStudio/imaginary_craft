# 武器类型重载监听器配置文档

本文档说明如何使用条件化配置来扩展武器能力。支持基于实体条件的动态配置。

## 配置格式说明

## 目录

<!-- TOC -->

* [武器类型重载监听器配置文档](#武器类型重载监听器配置文档)
  * [配置格式说明](#配置格式说明)
  * [目录](#目录)
    * [通用规则](#通用规则)
  * [1. hit_particle - 命中粒子效果配置](#1-hit_particle---命中粒子效果配置)
  * [2. swing_sound - 挥舞声音配置](#2-swing_sound---挥舞声音配置)
  * [3. hit_sound - 命中声音配置](#3-hit_sound---命中声音配置)
  * [4. combos - 连招配置](#4-combos---连招配置)
    * [简单格式](#简单格式)
    * [单个动画的条件化配置](#单个动画的条件化配置)
    * [完整的条件化配置（嵌套）](#完整的条件化配置嵌套)
  * [5. innate_skills - 先天技能配置](#5-innate_skills---先天技能配置)
    * [简单格式](#简单格式-1)
    * [完整格式](#完整格式)
  * [6. livingmotion_modifier - 运动动画修饰配置](#6-livingmotion_modifier---运动动画修饰配置)
  * [简单格式](#简单格式-2)
  * [完整格式](#完整格式-1)
  * [7. collider - 碰撞箱](#7-collider---碰撞箱)
  * [示例文件](#示例文件)
  * [相关代码](#相关代码)

<!-- TOC -->

### 通用规则

- **default**: 默认值，当没有满足任何条件时使用
- **cases**: 条件分支数组，按顺序匹配
- **value**: 条件满足时使用的值
- **conditions**: 条件列表，包含 predicate 和参数
- **predicate**: 谓词 ID，用于判断条件是否满足
- 第一个匹配成功的 case 会被使用，如果没有匹配的 case 则使用 default

---

## 1. hit_particle - 命中粒子效果配置

```json
{
  "hit_particle": "epicfight:blood",
  "hit_particle_cases": [
    {
      "value": "minecraft:glow_squid_ink",
      "conditions": [
        {
          "predicate": "epicfight:entity_type",
          "entity_type": "minecraft:skeleton"
        }
      ]
    }
  ]
}
```

---

## 2. swing_sound - 挥舞声音配置

```json
{
  "swing_sound": "epicfight:blade_swing",
  "swing_sound_cases": [
    {
      "value": "minecraft:entity.warden.attack.swing",
      "conditions": [
        {
          "predicate": "epicfight:has_skill",
          "skill": "imaginarycraft:heavy_weapon"
        }
      ]
    }
  ]
}
```

---

## 3. hit_sound - 命中声音配置

```json
{
  "hit_sound": "epicfight:blade_hit",
  "hit_sound_cases": [
    {
      "value": "minecraft:entity.generic.burn",
      "conditions": [
        {
          "predicate": "epicfight:is_on_fire"
        }
      ]
    }
  ]
}
```

---

## 4. combos - 连招配置

### 简单格式

```json
{
  "combos": {
    "one_hand": [
      "imaginarycraft:slash_1",
      "imaginarycraft:slash_2",
      "imaginarycraft:stab"
    ]
  }
}
```

### 单个动画的条件化配置

```json
{
  "combos": {
    "one_hand": [
      {
        "default": "imaginarycraft:slash_1",
        "cases": [
          {
            "value": "imaginarycraft:pierce",
            "conditions": [
              {
                "predicate": "epicfight:is_sneaking"
              }
            ]
          }
        ]
      },
      "imaginarycraft:slash_2",
      "imaginarycraft:stab"
    ]
  }
}
```

### 完整的条件化配置（嵌套）

```json
{
  "combos": {
    "one_hand": {
      "default": [
        {
          "default": "imaginarycraft:slash_1",
          "cases": [
            {
              "value": "imaginarycraft:pierce",
              "conditions": [
                {
                  "predicate": "epicfight:is_sneaking"
                }
              ]
            }
          ]
        },
        "imaginarycraft:slash_2",
        "imaginarycraft:stab"
      ],
      "cases": [
        {
          "value": [
            {
              "default": "imaginarycraft:slash_1",
              "cases": [
                {
                  "value": "imaginarycraft:pierce",
                  "conditions": [
                    {
                      "predicate": "epicfight:is_sneaking"
                    }
                  ]
                }
              ]
            },
            "imaginarycraft:slash_2",
            "imaginarycraft:stab"
          ],
          "conditions": [
            {
              "predicate": "epicfight:has_skill",
              "skill": "imaginarycraft:mastery"
            }
          ]
        }
      ]
    }
  }
}
```

---

## 5. innate_skills - 先天技能配置

### 简单格式

```json
{
  "innate_skills": {
    "one_hand": "epicfight:battle_mastery"
  }
}
```

### 完整格式

```json
{
  "innate_skills": {
    "one_hand": {
      "default": "epicfight:battle_mastery",
      "cases": [
        {
          "value": "imaginarycraft:weapon_specialist",
          "conditions": [
            {
              "predicate": "epicfight:has_item",
              "item": "imaginarycraft:ancient_weapon"
            }
          ]
        }
      ]
    }
  }
}
```

---

## 6. livingmotion_modifier - 运动动画修饰配置

## 简单格式

```json
{
  "livingmotion_modifier": {
    "one_hand": {
      "idle": "imaginarycraft:idle_combat",
      "walk": "imaginarycraft:walk_combat"
    }
  }
}
```

## 完整格式

```json
{
  "livingmotion_modifier": {
    "one_hand": {
      "default": {
        "idle": "imaginarycraft:idle_combat",
        "walk": "imaginarycraft:walk_combat"
      },
      "cases": [
        {
          "value": {
            "idle": "imaginarycraft:idle_combat",
            "walk": "imaginarycraft:walk_combat"
          },
          "conditions": [
            {
              "predicate": "epicfight:low_health",
              "threshold": 0.3
            }
          ]
        }
      ]
    }
  }
}
```

---

## 7. collider - 碰撞箱

```json
{
  "collider": {
    "size": [0.4, 0.4, 0.95],
    "number": 3,
    "center": [0.0, 0.0, -0.95]
  },
  "collider_cases": [
    {
      "value": {
        "size": [
          0.4,
          0.4,
          0.95
        ],
        "number": 3,
        "center": [
          0.0,
          0.0,
          -0.95
        ]
      },
      "conditions": [
        {
          "predicate": "epicfight:low_health",
          "threshold": 0.3
        }
      ]
    }
  ]
}
```

---

## 示例文件

完整的武器能力配置文件示例：
`src/main/resources/data/imaginarycraft/capabilities/weapons/red_eyes_tachi_weapon.json`

## 相关代码

- Mixin 实现：`src/main/java/ctn/imaginarycraft/mixin/world/skill/WeaponTypeReloadListenerMixin.java`
- 工具类：`src/main/java/ctn/imaginarycraft/api/data/ModWeaponTypeReloadListener.java`
