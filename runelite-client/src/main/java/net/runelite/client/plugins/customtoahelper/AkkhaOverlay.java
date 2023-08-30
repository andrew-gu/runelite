package net.runelite.client.plugins.customtoahelper;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

public class AkkhaOverlay extends Overlay {


    private final Client client;

    private final CustomToaHelperConfig config;

    private final CustomToaHelperPlugin plugin;

    @Inject
    private AkkhaOverlay(Client client, CustomToaHelperConfig config, CustomToaHelperPlugin plugin) {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }
    @Override
    public Dimension render(Graphics2D graphics) {
        NPC[] npcs = client.getNpcs()
            .stream()
            .filter(npc -> npc.getId() == NpcID.UNSTABLE_ORB || npc.getId() == NpcID.ORB_OF_DARKNESS)
            .toArray(NPC[]::new);
        Color color = config.orbTileBorderColor();
        Color fillColor = config.orbTileFillColor();
        double borderWidth = config.orbTileBorderWidth();
        for (NPC npc : npcs) {
            WorldPoint npcWorldPoint = npc.getWorldLocation();
            int orientation = npc.getOrientation();
            for(int i = 0; i <= config.orbRange(); i++) {
                WorldPoint renderWorldPoint = getRenderWorldPoint(npcWorldPoint, orientation, i);
                LocalPoint renderLocalPoint = LocalPoint.fromWorld(client, renderWorldPoint);
                Polygon poly = Perspective.getCanvasTilePoly(client, renderLocalPoint);
                OverlayUtil.renderPolygon(graphics, poly, color, fillColor, new BasicStroke((float) borderWidth));
            }
        }


        return null;
    }

    private WorldPoint getRenderWorldPoint(
        WorldPoint npcWorldPoint,
        int npcOrientation,
        int distance
    ) {
        int x = npcWorldPoint.getX();
        int y = npcWorldPoint.getY();
        int plane = npcWorldPoint.getPlane();
        if (npcOrientation == 0) { // S
            return new WorldPoint(x, y - distance, plane);
        }
        if (npcOrientation == 256) { // SW
            return new WorldPoint(x - distance, y - distance, plane);
        }
        if (npcOrientation == 512) { // W
            return new WorldPoint(x - distance, y, plane);
        }
        if (npcOrientation == 768) { // NW
            return new WorldPoint(x - distance, y + distance, plane);
        }
        if (npcOrientation == 1024) { // N
            return new WorldPoint(x, y + distance, plane);
        }
        if (npcOrientation == 1280) { // NE
            return new WorldPoint(x + distance, y + distance, plane);
        }
        if (npcOrientation == 1536) { // E
            return new WorldPoint(x + distance, y, plane);
        }
        if (npcOrientation == 1792) { // SE
            return new WorldPoint(x + distance, y - distance, plane);
        }
        return null;
    }
}
