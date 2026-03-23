package ctn.imaginarycraft.client.model.animmodels.armature;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.Map;

public class GrantUsLoveArmature extends Armature {
  public final Joint root;
  public final Joint tablet;
  public final Joint tentacle1_0_L;
  public final Joint tentacle1_1_L;
  public final Joint tentacle1_2_L;
  public final Joint tentacle2_0_L;
  public final Joint tentacle2_1_L;
  public final Joint tentacle2_2_L;
  public final Joint tentacle3_0_L;
  public final Joint tentacle3_1_L;
  public final Joint tentacle3_2_L;
  public final Joint tentacle1_0_R;
  public final Joint tentacle1_1_R;
  public final Joint tentacle1_2_R;
  public final Joint tentacle2_0_R;
  public final Joint tentacle2_1_R;
  public final Joint tentacle2_2_R;
  public final Joint tentacle3_0_R;
  public final Joint tentacle3_1_R;
  public final Joint tentacle3_2_R;
  public final Joint rock;

  public GrantUsLoveArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
    super(name, jointNumber, rootJoint, jointMap);
    this.root = getOrLogException(jointMap, "root");
    this.tablet = getOrLogException(jointMap, "tablet");
    this.tentacle1_0_L = getOrLogException(jointMap, "tentacle1.0.L");
    this.tentacle1_1_L = getOrLogException(jointMap, "tentacle1.1.L");
    this.tentacle1_2_L = getOrLogException(jointMap, "tentacle1.2.L");
    this.tentacle2_0_L = getOrLogException(jointMap, "tentacle2.0.L");
    this.tentacle2_1_L = getOrLogException(jointMap, "tentacle2.1.L");
    this.tentacle2_2_L = getOrLogException(jointMap, "tentacle2.2.L");
    this.tentacle3_0_L = getOrLogException(jointMap, "tentacle3.0.L");
    this.tentacle3_1_L = getOrLogException(jointMap, "tentacle3.1.L");
    this.tentacle3_2_L = getOrLogException(jointMap, "tentacle3.2.L");
    this.tentacle1_0_R = getOrLogException(jointMap, "tentacle1.0.R");
    this.tentacle1_1_R = getOrLogException(jointMap, "tentacle1.1.R");
    this.tentacle1_2_R = getOrLogException(jointMap, "tentacle1.2.R");
    this.tentacle2_0_R = getOrLogException(jointMap, "tentacle2.0.R");
    this.tentacle2_1_R = getOrLogException(jointMap, "tentacle2.1.R");
    this.tentacle2_2_R = getOrLogException(jointMap, "tentacle2.2.R");
    this.tentacle3_0_R = getOrLogException(jointMap, "tentacle3.0.R");
    this.tentacle3_1_R = getOrLogException(jointMap, "tentacle3.1.R");
    this.tentacle3_2_R = getOrLogException(jointMap, "tentacle3.2.R");
    this.rock = getOrLogException(jointMap, "rock");
  }
}
