package eu.the5zig.mod.modules.items.server.timolia;

import eu.the5zig.mod.modules.items.server.GameModeItem;
import eu.the5zig.mod.server.GameState;
import eu.the5zig.mod.server.timolia.ServerTimolia;
import eu.the5zig.mod.server.timolia.ServerTimolia.BrainBow;

public class BrainbowScore
  extends GameModeItem<ServerTimolia.BrainBow>
{
  public BrainbowScore()
  {
    super(ServerTimolia.class, ServerTimolia.BrainBow.class, new GameState[0]);
  }
  
  protected Object getValue(boolean dummy)
  {
    if (dummy) {
      return Integer.valueOf(0);
    }
    return ((ServerTimolia.BrainBow)getGameMode()).getScore() > 0 ? Integer.valueOf(((ServerTimolia.BrainBow)getGameMode()).getScore()) : null;
  }
  
  public String getTranslation()
  {
    return "ingame.scores";
  }
}
