package ctn.imaginarycraft.linkage.lowdraglib2;

import com.lowdragmc.lowdraglib2.gui.texture.*;
import com.lowdragmc.lowdraglib2.gui.ui.*;
import com.lowdragmc.lowdraglib2.gui.ui.elements.*;
import com.lowdragmc.lowdraglib2.gui.ui.styletemplate.*;
import com.lowdragmc.lowdraglib2.plugin.*;

@LDLibPlugin
public final class ModLowdraglib2Plugin implements ILDLibPlugin {
  private static ModularUI createModularUI() {
    // create a root element
    var root = new UIElement();
    root.addChildren(
      // add a label to display text
      new Label().setText("My First UI"),
      // add a button with text
      new Button().setText("Click Me!"),
      // add an element to display an image based on a resource location
      new UIElement().layout(layout -> layout.width(80).height(80))
        .style(style -> style.background(
          SpriteTexture.of("ldlib2:textures/gui/icon.png"))
        )
    ).style(style -> style.background(Sprites.BORDER)); // set a background for the root element
    // create a UI
    var ui = UI.of(root);
    // return a modular UI for runtime instance
    return ModularUI.of(ui);
  }

  @Override
  public void onLoad() {
  }
}
