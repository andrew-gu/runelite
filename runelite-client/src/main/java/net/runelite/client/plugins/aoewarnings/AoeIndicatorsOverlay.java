package net.runelite.client.plugins.aoewarnings;

import java.awt.*;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Projectile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;

@Slf4j
public class AoeIndicatorsOverlay extends Overlay
{
    private static final int FILL_START_ALPHA = 25;
    private static final int OUTLINE_START_ALPHA = 255;

    private final Client client;
    private final AoeIndicatorsPlugin plugin;
    private final AoeIndicatorsConfig config;

    @Inject
    public AoeIndicatorsOverlay(@Nullable Client client, AoeIndicatorsPlugin plugin, AoeIndicatorsConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
//        setLayer(OverlayLayer.UNDER_WIDGETS);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.LOW);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!config.enabled())
        {
            return null;
        }

        Instant now = Instant.now();
        Map<Projectile, AoeProjectile> projectiles = plugin.getProjectiles();
        for (Iterator<AoeProjectile> it = projectiles.values().iterator(); it.hasNext();)
        {
            AoeProjectile aoeProjectile = it.next();

            if (now.isAfter(aoeProjectile.getStartTime().plus(aoeProjectile.getAoeProjectileInfo().getLifeTime())))
            {
                it.remove();
                continue;
            }
            Polygon tilePoly = Perspective.getCanvasTileAreaPoly(client, aoeProjectile.getTargetPoint(), aoeProjectile.getAoeProjectileInfo().getAoeSize());
            if (tilePoly == null)
            {
                continue;
            }

            if (aoeProjectile.getAoeProjectileInfo().getId() == AoeProjectileInfo.TOA_OBELISK_LIGHTNING_SKULL.getId()) {
                renderLightningSkullSafeTiles(graphics, aoeProjectile);
            }

            // how far through the projectiles lifetime between 0-1.
            double progress = (System.currentTimeMillis() - aoeProjectile.getStartTime().toEpochMilli()) / (double) aoeProjectile.getAoeProjectileInfo().getLifeTime().toMillis();

            int fillAlpha = (int) ((1 - progress) * FILL_START_ALPHA);//alpha drop off over lifetime
            int outlineAlpha = (int) ((1 - progress) * OUTLINE_START_ALPHA);

            if (fillAlpha < 0)
            {
                fillAlpha = 0;
            }
            if (outlineAlpha < 0)
            {
                outlineAlpha = 0;
            }

            if (fillAlpha > 255)
            {
                fillAlpha = 255;
            }
            if (outlineAlpha > 255)
            {
                outlineAlpha = 255;//Make sure we don't pass in an invalid alpha
            }

            Color borderColor = new Color(255, 0, 0, outlineAlpha);
            Color fillColor = new Color(255, 0, 0, fillAlpha);
            OverlayUtil.renderPolygon(graphics, tilePoly, borderColor, fillColor, new BasicStroke((float) 2));
        }
        return null;
    }

    private void renderLightningSkullSafeTiles(Graphics2D graphics, AoeProjectile aoeProjectile) {
        WorldPoint targetWorldPoint = WorldPoint.fromLocal(client, aoeProjectile.getTargetPoint());
        int x = targetWorldPoint.getX();
        int y = targetWorldPoint.getY();
        Polygon tilePolyNorth1 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x, y+1));
        Polygon tilePolyNorth2 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x, y+2));
        Polygon tilePolyEast1 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x+1, y));
        Polygon tilePolyEast2 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x+2, y));
        Polygon tilePolyWest1 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x-1, y));
        Polygon tilePolyWest2 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x-2, y));
        Polygon tilePolySouth1 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x, y-1));
        Polygon tilePolySouth2 = Perspective.getCanvasTilePoly(client, LocalPoint.fromWorld(client, x, y-2));

        Color borderColor = new Color(0, 255, 0, 200);
        Color fillColor = new Color(0, 255, 0, 100);
        OverlayUtil.renderPolygon(graphics, tilePolyNorth1, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolyNorth2, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolyEast1, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolyEast2, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolyWest1, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolyWest2, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolySouth1, borderColor, fillColor, new BasicStroke((float) 2));
        OverlayUtil.renderPolygon(graphics, tilePolySouth2, borderColor, fillColor, new BasicStroke((float) 2));
    }
}