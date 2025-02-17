package net.runelite.client.plugins.aoewarnings;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("aoewarning")
public interface AoeIndicatorsConfig extends Config
{
    @ConfigItem(
        keyName = "enabled",
        name = "AoE Warnings Enabled",
        description = "Configures whether or not AoE Projectile Warnings plugin is displayed"
    )
    default boolean enabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "lizardmanaoe",
        name = "Lizardman Shamans",
        description = "Configures whether or not AoE Projectile Warnings for Lizardman Shamans is displayed"
    )
    default boolean isShamansEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "archaeologistaoe",
        name = "Crazy Archaeologist",
        description = "Configures whether or not AoE Projectile Warnings for Archaeologist is displayed"
    )
    default boolean isArchaeologistEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "icedemon",
        name = "Ice Demon",
        description = "Configures whether or not AoE Projectile Warnings for Ice Demon is displayed"
    )
    default boolean isIceDemonEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "vasa",
        name = "Vasa",
        description = "Configures whether or not AoE Projectile Warnings for Vasa is displayed"
    )
    default boolean isVasaEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "tekton",
        name = "Tekton",
        description = "Configures whether or not AoE Projectile Warnings for Tekton is displayed"
    )
    default boolean isTektonEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "vorkath",
        name = "Vorkath",
        description = "Configures whether or not AoE Projectile Warnings for Vorkath are displayed"
    )
    default boolean isVorkathEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "galvek",
        name = "Galvek",
        description = "Configures whether or not AoE Projectile Warnings for Galvek are displayed"
    )
    default boolean isGalvekEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "gargboss",
        name = "Gargoyle Boss",
        description = "Configs whether or not AoE Projectile Warnings for Dawn/Dusk are displayed"
    )
    default boolean isGargBossEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "vetion",
        name = "Vet'ion",
        description = "Configures whether or not AoE Projectile Warnings for Vet'ion are displayed"
    )
    default boolean isVetionEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "chaosfanatic",
        name = "Chaos Fanatic",
        description = "Configures whether or not AoE Projectile Warnings for Chaos Fanatic are displayed"
    )
    default boolean isChaosFanaticEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "olm",
        name = "Great Olm",
        description = "Configures whether or not AoE Projectile Warnings for The Great Olm are displayed"
    )
    default boolean isOlmEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "corp",
        name = "Corporeal Beast",
        description = "Configures whether or not AoE Projectile Warnings for the Corporeal Beast are displayed"
    )
    default boolean isCorpEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "wintertodt",
        name = "Wintertodt Snow Fall",
        description = "Configures whether or not AOE Projectile Warnings for the Wintertodt snow fall are displayed"
    )
    default boolean isWintertodtEnabled()
    {
        return true;
    }
    @ConfigItem(
        keyName = "toaobelisklightningskull",
        name = "ToA Obelisk Lightning Skull",
        description = "Configures whether or not AoE Projectile Warnings for the ToA Obelisk lightning skulls are displayed"
    )
    default boolean isToaLightningSkullEnabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "outline",
        name = "Display Outline",
        description = "Configures whether or not AoE Projectile Warnings have an outline"
    )
    default boolean isOutlineEnabled()
    {
        return true;
    }

}