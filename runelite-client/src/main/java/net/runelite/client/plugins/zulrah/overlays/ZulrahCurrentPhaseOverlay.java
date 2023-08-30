package net.runelite.client.plugins.zulrah.overlays;

import net.runelite.client.plugins.zulrah.ZulrahInstance;
import net.runelite.client.plugins.zulrah.ZulrahPlugin;
import net.runelite.client.plugins.zulrah.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;


import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ZulrahCurrentPhaseOverlay extends Overlay
{
    private final ZulrahPlugin plugin;

    @Inject
    ZulrahCurrentPhaseOverlay(ZulrahPlugin plugin)
    {
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setPriority(OverlayPriority.HIGH);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        ZulrahInstance instance = plugin.getInstance();

        if (instance == null)
        {
            return null;
        }

        ZulrahPhase currentPhase = instance.getPhase();
        if (currentPhase == null)
        {
            return null;
        }

        String pattern = instance.getPattern() != null ? instance.getPattern().toString() : "Unknown";
        String title = currentPhase.isJad() ? "JAD PHASE" : pattern;
        Color backgroundColor = currentPhase.getColor();
        BufferedImage zulrahImage = ZulrahImageManager.getZulrahBufferedImage(currentPhase.getType());
        ImageComponent imageComponent = new ImageComponent(zulrahImage);
        TitleComponent titleComponent = TitleComponent.builder().text(title).build();
        PanelComponent panelComponent = new PanelComponent();
        panelComponent.getChildren().add(imageComponent);
        panelComponent.getChildren().add(titleComponent);
        panelComponent.setBackgroundColor(backgroundColor);

        return panelComponent.render(graphics);
    }
}