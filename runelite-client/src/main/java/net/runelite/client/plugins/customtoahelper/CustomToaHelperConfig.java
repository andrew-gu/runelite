package net.runelite.client.plugins.customtoahelper;


import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup(CustomToaHelperConfig.CUSTOM_TOA_HELPER_CONFIG_GROUP)
public interface CustomToaHelperConfig extends Config {

    String CUSTOM_TOA_HELPER_CONFIG_GROUP = "customToaHelper";

    @ConfigItem(
        keyName = "orbRange",
        name = "Orb Range",
        description = "The number of tiles to display in front of orbs"
    )
    default int orbRange() { return 3; }

    @Alpha
    @ConfigItem(
        keyName = "orbTileBorderColor",
        name = "Orb Tile Border Color",
        description = "The color of the tile border"
    )
    default Color orbTileBorderColor() { return new Color(0, 0, 0, 0); }

    @Alpha
    @ConfigItem(
        keyName = "orbTileFillColor",
        name = "Orb Tile Fill Color",
        description = "The fill color of the tile"
    )
    default Color orbTileFillColor() { return new Color(0, 0, 0, 100); }

    @ConfigItem(
        keyName = "orbTileBorderWidth",
        name = "Orb Tile Border Width",
        description = "The width of the tile border"
    )
    default double orbTileBorderWidth() { return 2; }
}
