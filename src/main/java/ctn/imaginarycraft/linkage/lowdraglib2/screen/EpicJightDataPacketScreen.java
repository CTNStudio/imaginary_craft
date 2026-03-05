package ctn.imaginarycraft.linkage.lowdraglib2.screen;

import com.lowdragmc.lowdraglib2.gui.ui.*;
import com.lowdragmc.lowdraglib2.gui.ui.elements.*;
import com.lowdragmc.lowdraglib2.gui.ui.styletemplate.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;

public class EpicJightDataPacketScreen extends Screen {
  public static final String EPIC_JIGHT_DATA_PACKET = "epic_jight_data_packet";
  public static final String TITLE = getText("title");

  protected EpicJightDataPacketScreen(Component title) {
    super(title);
  }

  @Override
  public void init() {
    super.init();
    var modularUI = createModularUI();
    modularUI.setScreenAndInit(this);
    addRenderableWidget(modularUI.getWidget());
  }

  public static ModularUI createModularUI() {
    var root = new UIElement();
    root.addChildren(
      new Label().setText(Component.translatable(TITLE)),
      new Button().setText("Click Me!")
    ).style(style -> style.background(Sprites.BORDER));
    var ui = UI.of(root);
    return ModularUI.of(ui);
  }

  private static String getText(String text) {
    return ImaginaryCraft.modRlText(EPIC_JIGHT_DATA_PACKET + ".screen." + text);
  }
}
