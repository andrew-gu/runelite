package net.runelite.client.plugins.customtoahelper;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@PluginDescriptor(
    name = "Custom TOA Helper",
    description = "Custom TOA Helper"
)
public class CustomToaHelperPlugin extends Plugin {

    @Inject
    private Client client;
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private AkkhaOverlay akkhaOverlay;

    @Inject
    private WardenOverlay wardenOverlay;

    private Map<Integer, Integer> SOLO_SKULL_LIFETIME = Map.of(
        4, 7,
        5, 8,
        6, 9,
        7,10
    );

    private Map<Integer, Integer> TEAM_SKULL_LIFETIME = Map.of(
        9, 16,
        10, 17,
        13, 21,
        15, 24
    );

    private Map<LocalPoint, Integer> skullRemainingTicks = new HashMap<>();

    public Map<LocalPoint, Integer> getSkullRemainingTicks() { return skullRemainingTicks; }

    @Provides
    CustomToaHelperConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CustomToaHelperConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(akkhaOverlay);
        overlayManager.add(wardenOverlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(akkhaOverlay);
        overlayManager.remove(wardenOverlay);
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event) {
        NPC eventNpc = event.getNpc();
        if (eventNpc.getId() != NpcID.ENERGY_SIPHON) {
            return;
        }
        List<NPC> skulls = client.getNpcs()
            .stream()
            .filter(npc -> npc.getId() == NpcID.ENERGY_SIPHON)
            .collect(Collectors.toList());
        // GameTick events are sent after all other events
        // add 1 tick to account for onGameTick subtracting 1 on the same tick as NpcSpawned
        if (client.getPlayers().size() > 1) {
            skullRemainingTicks.put(eventNpc.getLocalLocation(), TEAM_SKULL_LIFETIME.get(skulls.size()) + 1);
        } else {
            skullRemainingTicks.put(eventNpc.getLocalLocation(), SOLO_SKULL_LIFETIME.get(skulls.size()) + 1);
        }
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        List<NPC> skulls = client.getNpcs()
            .stream()
            .filter(npc -> npc.getId() == NpcID.ENERGY_SIPHON)
            .collect(Collectors.toList());

        if (skulls.size() == 0) {
            skullRemainingTicks.clear();
            return;
        }

        for (Iterator<LocalPoint> it = skullRemainingTicks.keySet().iterator(); it.hasNext();) {
            LocalPoint localPoint = it.next();
            skullRemainingTicks.put(localPoint, skullRemainingTicks.get(localPoint) - 1);
        }
    }
}
