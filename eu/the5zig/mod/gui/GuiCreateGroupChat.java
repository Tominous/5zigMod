package eu.the5zig.mod.gui;

import eu.the5zig.mod.I18n;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.gui.elements.IButton;
import eu.the5zig.mod.gui.elements.ITextfield;
import eu.the5zig.mod.util.IVariables;
import eu.the5zig.util.minecraft.ChatColor;

public class GuiCreateGroupChat
  extends Gui
{
  public GuiCreateGroupChat(Gui lastScreen)
  {
    super(lastScreen);
  }
  
  public void initGui()
  {
    addButton(The5zigMod.getVars().createButton(1, getWidth() / 2 + 5, getHeight() - 30, 150, 20, I18n.translate("gui.continue"), false));
    addButton(The5zigMod.getVars().createButton(200, getWidth() / 2 - 155, getHeight() - 30, 150, 20, The5zigMod.getVars().translate("gui.cancel", new Object[0])));
    addTextField(The5zigMod.getVars().createTextfield(301, getWidth() / 2 - 100, getHeight() / 6 + 40, 200, 20, 30));
  }
  
  protected void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    The5zigMod.getVars().drawString(I18n.translate("group.create.name"), getWidth() / 2 - 100, getHeight() / 6 + 28);
    if (getTextfieldById(301).getText().length() < 3) {
      drawCenteredString(ChatColor.RED + I18n.translate("group.create.name.min_length"), getWidth() / 2, getHeight() / 6 + 70);
    }
  }
  
  protected void onKeyType(char character, int key)
  {
    getButtonById(1).setEnabled(getTextfieldById(301).getText().length() > 2);
  }
  
  protected void actionPerformed(IButton button)
  {
    if (button.getId() == 1)
    {
      if (getTextfieldById(301).getText().length() < 3) {
        return;
      }
      The5zigMod.getVars().displayScreen(new GuiCreateGroupChatSelectFriends(this, getTextfieldById(301).getText()));
    }
  }
  
  public String getTitleKey()
  {
    return "group.create.title";
  }
}
