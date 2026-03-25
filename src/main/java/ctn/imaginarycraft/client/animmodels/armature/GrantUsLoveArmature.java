package ctn.imaginarycraft.client.animmodels.armature;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.Map;

public class GrantUsLoveArmature extends Armature {
  public final Joint root;

  public final Joint tablet;
  public final Joint rock;

  public final Joint tentacle1_0_L;
  public final Joint tentacle1_1_L;
  public final Joint tentacle1_2_L;
  public final Joint tentacle1_3_L;

  public final Joint tentacle2_0_L;
  public final Joint tentacle2_1_L;
  public final Joint tentacle2_2_L;
  public final Joint tentacle2_3_L;

  public final Joint tentacle3_0_L;
  public final Joint tentacle3_1_L;
  public final Joint tentacle3_2_L;
  public final Joint tentacle3_3_L;

  public final Joint tentacle1_0_R;
  public final Joint tentacle1_1_R;
  public final Joint tentacle1_2_R;
  public final Joint tentacle1_3_R;

  public final Joint tentacle2_0_R;
  public final Joint tentacle2_1_R;
  public final Joint tentacle2_2_R;
  public final Joint tentacle2_3_R;

  public final Joint tentacle3_0_R;
  public final Joint tentacle3_1_R;
  public final Joint tentacle3_2_R;
  public final Joint tentacle3_3_R;


  public GrantUsLoveArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
    super(name, jointNumber, rootJoint, jointMap);
    this.root = getOrLogException(jointMap, "Root");
    this.tablet = getOrLogException(jointMap, "Tablet");

    this.tentacle1_0_L = getOrLogException(jointMap, "Tentacle1_0.L");
    this.tentacle1_1_L = getOrLogException(jointMap, "Tentacle1_1.L");
    this.tentacle1_2_L = getOrLogException(jointMap, "Tentacle1_2.L");
    this.tentacle1_3_L = getOrLogException(jointMap, "Tentacle1_3.L");

    this.tentacle2_0_L = getOrLogException(jointMap, "Tentacle2_0.L");
    this.tentacle2_1_L = getOrLogException(jointMap, "Tentacle2_1.L");
    this.tentacle2_2_L = getOrLogException(jointMap, "Tentacle2_2.L");
    this.tentacle2_3_L = getOrLogException(jointMap, "Tentacle2_3.L");

    this.tentacle3_0_L = getOrLogException(jointMap, "Tentacle3_0.L");
    this.tentacle3_1_L = getOrLogException(jointMap, "Tentacle3_1.L");
    this.tentacle3_2_L = getOrLogException(jointMap, "Tentacle3_2.L");
    this.tentacle3_3_L = getOrLogException(jointMap, "Tentacle3_3.L");

    this.tentacle1_0_R = getOrLogException(jointMap, "Tentacle1_0.R");
    this.tentacle1_1_R = getOrLogException(jointMap, "Tentacle1_1.R");
    this.tentacle1_2_R = getOrLogException(jointMap, "Tentacle1_2.R");
    this.tentacle1_3_R = getOrLogException(jointMap, "Tentacle1_3.R");

    this.tentacle2_0_R = getOrLogException(jointMap, "Tentacle2_0.R");
    this.tentacle2_1_R = getOrLogException(jointMap, "Tentacle2_1.R");
    this.tentacle2_2_R = getOrLogException(jointMap, "Tentacle2_2.R");
    this.tentacle2_3_R = getOrLogException(jointMap, "Tentacle2_3.R");

    this.tentacle3_0_R = getOrLogException(jointMap, "Tentacle3_0.R");
    this.tentacle3_1_R = getOrLogException(jointMap, "Tentacle3_1.R");
    this.tentacle3_2_R = getOrLogException(jointMap, "Tentacle3_2.R");
    this.tentacle3_3_R = getOrLogException(jointMap, "Tentacle3_3.R");
    this.rock = getOrLogException(jointMap, "Rock");
  }
}
