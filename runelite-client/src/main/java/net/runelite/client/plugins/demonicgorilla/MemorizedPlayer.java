package net.runelite.client.plugins.demonicgorilla;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Hitsplat;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;

public class MemorizedPlayer
{
    @Getter
    private Player player;

    @Getter
    @Setter
    private WorldArea lastWorldArea;

    @Getter
    private List<Hitsplat> recentHitsplats;

    public MemorizedPlayer(Player player)
    {
        this.player = player;
        this.recentHitsplats = new ArrayList<>();
    }
}
