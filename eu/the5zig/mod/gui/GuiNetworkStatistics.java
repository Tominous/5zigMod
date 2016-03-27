package eu.the5zig.mod.gui;

import com.google.common.collect.Lists;
import eu.the5zig.mod.I18n;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.chat.NetworkStats;
import eu.the5zig.mod.chat.network.NetworkManager;
import eu.the5zig.mod.chat.network.packets.PacketServerStats;
import eu.the5zig.mod.gui.elements.BasicRow;
import eu.the5zig.mod.gui.elements.IButton;
import eu.the5zig.mod.gui.elements.IGuiList;
import eu.the5zig.mod.gui.elements.Row;
import eu.the5zig.mod.manager.DataManager;
import eu.the5zig.mod.util.IVariables;
import eu.the5zig.util.Container;
import eu.the5zig.util.minecraft.ChatColor;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.List;

public class GuiNetworkStatistics
  extends Gui
{
  private IGuiList statistics;
  private List<Row> rows = Lists.newArrayList();
  private int ticks = 300;
  
  public GuiNetworkStatistics(Gui lastScreen)
  {
    super(lastScreen);
  }
  
  public void initGui()
  {
    addButton(The5zigMod.getVars().createButton(200, getWidth() / 2 - 100, getHeight() - 35, The5zigMod.getVars().translate("gui.done", new Object[0])));
    
    this.rows.clear();
    List<String> lines = Lists.newArrayList();
    lines.add(ChatColor.UNDERLINE + I18n.translate("stats.current") + ":");
    NetworkStats networkStats = The5zigMod.getDataManager().getNetworkStats();
    lines.add(I18n.translate("stats.packets_sent") + ": " + networkStats.getCurrentPacketsSent());
    lines.add(I18n.translate("stats.packets_received") + ": " + networkStats.getCurrentPacketsReceived());
    lines.add(I18n.translate("stats.bytes_sent") + ": " + networkStats.getCurrentBytesSent());
    lines.add(I18n.translate("stats.bytes_received") + ": " + networkStats.getCurrentBytesReceived());
    lines.add("");
    lines.add(ChatColor.UNDERLINE + I18n.translate("stats.total") + ":");
    lines.add(I18n.translate("stats.packets_sent") + ": " + networkStats.getTotalPacketsSent());
    lines.add(I18n.translate("stats.packets_received") + ": " + networkStats.getTotalPacketsReceived());
    lines.add(I18n.translate("stats.bytes_sent") + ": " + networkStats.getTotalBytesSent());
    lines.add(I18n.translate("stats.bytes_received") + ": " + networkStats.getTotalBytesReceived());
    lines.add("");
    lines.add(ChatColor.UNDERLINE + I18n.translate("stats.total_packets") + ": " + networkStats.getPacketsTotal());
    lines.add(ChatColor.UNDERLINE + I18n.translate("stats.total_bytes") + ": " + networkStats.getBytesTotal());
    lines.add(ChatColor.ITALIC + I18n.translate("stats.since") + ": " + networkStats.since());
    lines.add("");
    lines.add(ChatColor.UNDERLINE + I18n.translate("stats.server") + ":");
    for (String line : lines) {
      this.rows.add(new BasicRow(line, 200, 14));
    }
    this.rows.add(new StatisticRow("stats.ping", 0, 0, new Container[] { networkStats.getPing() }));
    this.rows.add(new StatisticRow("stats.up_time", 0, 0, new Container[] { networkStats.getServerUpTime() }));
    this.rows.add(new StatisticRow("stats.lag", 0, 0, new Container[] { networkStats.getLag() }));
    this.rows.add(new StatisticRow("stats.connected_players", 0, 0, new Container[] { networkStats.getConnectedClients(), networkStats.getMaxClients() }));
    this.statistics = The5zigMod.getVars().createGuiList(null, getWidth(), getHeight(), 50, getHeight() - 50, 0, getWidth(), this.rows);
    this.statistics.setDrawSelection(false);
    this.statistics.setRowWidth(220);
    this.statistics.setScrollX(getWidth() / 2 + 120);
    addGuiList(this.statistics);
  }
  
  protected void tick()
  {
    this.ticks += 1;
    if (this.ticks > 300)
    {
      The5zigMod.getNetworkManager().sendPacket(new PacketServerStats(), new GenericFutureListener[0]);
      this.ticks = 0;
    }
  }
  
  public String getTitleKey()
  {
    return "stats.title";
  }
  
  protected void actionPerformed(IButton button) {}
}
