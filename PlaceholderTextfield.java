import eu.the5zig.mod.MinecraftFactory;
import eu.the5zig.mod.gui.elements.IPlaceholderTextfield;
import eu.the5zig.mod.util.IVariables;
import org.apache.commons.lang3.StringUtils;

public class PlaceholderTextfield
  extends Textfield
  implements IPlaceholderTextfield
{
  private String placeholder;
  
  public PlaceholderTextfield(String placeholder, int id, int x, int y, int width, int height, int maxStringLength)
  {
    super(id, x, y, width, height, maxStringLength);
    this.placeholder = placeholder;
  }
  
  public PlaceholderTextfield(String placeholder, int id, int x, int y, int width, int height)
  {
    super(id, x, y, width, height);
    this.placeholder = placeholder;
  }
  
  public String getPlaceholder()
  {
    return this.placeholder;
  }
  
  public void setPlaceholder(String placeholder)
  {
    this.placeholder = placeholder;
  }
  
  public void draw()
  {
    super.draw();
    if ((StringUtils.isEmpty(getText())) && (!isFocused())) {
      MinecraftFactory.getVars().drawString(this.placeholder, isBackgroundDrawing() ? getX() + 4 : getX(), isBackgroundDrawing() ? getY() + (getHeight() - 8) / 2 : getY(), 7368816);
    }
  }
}
