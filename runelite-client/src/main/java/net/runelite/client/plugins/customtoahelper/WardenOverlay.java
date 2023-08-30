package net.runelite.client.plugins.customtoahelper;

import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;

public class WardenOverlay extends Overlay {

    private final Client client;

    private final CustomToaHelperConfig config;

    private final CustomToaHelperPlugin plugin;

    @Inject
    private WardenOverlay(Client client, CustomToaHelperConfig config, CustomToaHelperPlugin plugin) {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Map<LocalPoint, Integer> skullLifetime = plugin.getSkullRemainingTicks();
        for(Iterator<LocalPoint> it = skullLifetime.keySet().iterator(); it.hasNext();) {
            LocalPoint point = it.next();
            int ticks = skullLifetime.get(point);
            Color textColor = new Color(0, 255, 0);
            Point textLocation = Perspective.getCanvasTextLocation(client, graphics, point, Integer.toString(ticks),0);
            OverlayUtil.renderTextLocation(graphics, textLocation, Integer.toString(ticks),textColor);
        }
        return null;
    }
}
