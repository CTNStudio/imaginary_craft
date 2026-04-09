package ctn.imaginarycraft.linkage.lowdraglib2.screen;

import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Button;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Label;
import com.lowdragmc.lowdraglib2.gui.ui.styletemplate.Sprites;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class EpicJightDataPacketScreen extends Screen {
  public static final String EPIC_JIGHT_DATA_PACKET = "epic_jight_data_packet";
  public static final String TITLE = getText("title");

  protected EpicJightDataPacketScreen(Component title) {
    super(title);
  }

  private static String getText(String text) {
    return ImaginaryCraft.modRlText(EPIC_JIGHT_DATA_PACKET + ".screen." + text);
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
}
