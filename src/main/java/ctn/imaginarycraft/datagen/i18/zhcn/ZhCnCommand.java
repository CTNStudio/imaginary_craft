package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.common.command.RationalityCommands;
import ctn.imaginarycraft.datagen.i18.DatagenI18;
import net.minecraft.data.PackOutput;


public final class ZhCnCommand extends DatagenI18 {

  public ZhCnCommand(PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.VALUE.getName()), "已设置%s的理智值为：%d");
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.MAX_VALUE.getName()), "已设置%s的最大理智基础值为：%d");
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.NATURAL_RECOVERY_RATE.getName()), "已设置%s的基础理智值理智值自然恢复等待时间为：20*%d Tick");
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.RATIONALITY_RECOVERY_AMOUNT.getName()), "已设置%s的基础理智恢复为：每次%d");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.VALUE.getName()), "%s的理智值为：%d");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.MAX_VALUE.getName()), "%s的最大理智值为：%d");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.NATURAL_RECOVERY_RATE.getName()), "%s的理智值理智值自然恢复等待时间为：20*%d Tick");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.RATIONALITY_RECOVERY_AMOUNT.getName()), "%s的理智值自然恢复量为：每次%d点");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.VALUE.getName()), "已重置%s的理智值为：%d");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.MAX_VALUE.getName()), "已重置%s的最大理智基础值为：%d");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.NATURAL_RECOVERY_RATE.getName()), "已重置%s的基础理智值理智值自然恢复等待时间为：20*%dTick");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.RATIONALITY_RECOVERY_AMOUNT.getName()), "已重置%s的基础理智值自然恢复量为：每次%d点");
    add(getFormattedKey(RationalityCommands.RESET_KEY), "已重置%s的理智");
  }
}
