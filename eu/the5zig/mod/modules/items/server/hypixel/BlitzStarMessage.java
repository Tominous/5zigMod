package eu.the5zig.mod.modules.items.server.hypixel;

import eu.the5zig.mod.I18n;
import eu.the5zig.mod.modules.items.server.LargeTextItem;
import eu.the5zig.mod.server.GameState;
import eu.the5zig.mod.server.hypixel.ServerHypixel;
import eu.the5zig.mod.server.hypixel.ServerHypixel.Blitz;

public class BlitzStarMessage
  extends LargeTextItem<ServerHypixel.Blitz>
{
  public BlitzStarMessage()
  {
    super(ServerHypixel.class, ServerHypixel.Blitz.class, new GameState[0]);
  }
  
  protected String getText()
  {
    if ((((ServerHypixel.Blitz)getGameMode()).getWinner() == null) && (((ServerHypixel.Blitz)getGameMode()).getStar() != -1L) && (((ServerHypixel.Blitz)getGameMode()).getStar() - System.currentTimeMillis() > 0L))
    {
      String time = shorten((((ServerHypixel.Blitz)getGameMode()).getStar() - System.currentTimeMillis()) / 1000.0D);
      if (((ServerHypixel.Blitz)getGameMode()).getStar() - System.currentTimeMillis() < 15000L) {
        return I18n.translate("ingame.star_in", new Object[] { time });
      }
    }
    return null;
  }
}
