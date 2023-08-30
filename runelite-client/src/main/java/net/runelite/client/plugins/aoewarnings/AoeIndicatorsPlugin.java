package net.runelite.client.plugins.aoewarnings;

import com.google.inject.Provides;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Projectile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
    name = "AoE indicators plugin"
)
@Slf4j
public class AoeIndicatorsPlugin extends Plugin
{
    @Inject
    private Client client;
    @Inject
    private OverlayManager overlayManager;

    @Inject
    AoeIndicatorsOverlay overlay;

    @Inject
    AoeIndicatorsConfig config;

    private final Map<Projectile, AoeProjectile> projectiles = new HashMap<>();


    @Provides
    AoeIndicatorsConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AoeIndicatorsConfig.class);
    }


    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
        log.debug("\n\n\n Aoe indicators plugin started\n\n\n");
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
    }

    public Map<Projectile, AoeProjectile> getProjectiles()
    {
        return projectiles;
    }

    /**
     * Called when a projectile is set to move towards a point. For
     * projectiles that target the ground, like AoE projectiles from
     * Lizardman Shamans, this is only called once
     *
     * @param event Projectile moved event
     */
    @Subscribe
    public void onProjectileMoved(ProjectileMoved event)
    {
        Projectile projectile = event.getProjectile();
        log.debug("Projectile event: " + projectile.getId() + "-> " + event.getPosition().toString());

        // AoE projectiles do not target anything
        if (projectile.getInteracting() != null)
        {
            return;
        }

        int projectileId = projectile.getId();
        AoeProjectileInfo aoeProjectileInfo = AoeProjectileInfo.getById(projectileId);

        if (aoeProjectileInfo != null && isConfigEnabledForProjectileId(projectileId))
        {
            LocalPoint targetLocalPoint = event.getPosition();
            AoeProjectile aoeProjectile = new AoeProjectile(Instant.now(), targetLocalPoint, aoeProjectileInfo);
            projectiles.put(projectile, aoeProjectile);
        }
    }

    private boolean isConfigEnabledForProjectileId(int projectileId)
    {
        AoeProjectileInfo projectileInfo = AoeProjectileInfo.getById(projectileId);
        if (projectileInfo == null)
        {
            return false;
        }

        switch (projectileInfo)
        {
            case LIZARDMAN_SHAMAN_AOE:
                return config.isShamansEnabled();
            case CRAZY_ARCHAEOLOGIST_AOE:
                return config.isArchaeologistEnabled();
            case ICE_DEMON_RANGED_AOE:
            case ICE_DEMON_ICE_BARRAGE_AOE:
                return config.isIceDemonEnabled();
            case VASA_AWAKEN_AOE:
            case VASA_RANGED_AOE:
                return config.isVasaEnabled();
            case TEKTON_METEOR_AOE:
                return config.isTektonEnabled();
            case VORKATH_BOMB:
            case VORKATH_TICK_FIRE:
            case VORKATH_SPAWN:
            case VORKATH_POISON_POOL:
                return config.isVorkathEnabled();
            case VETION_LIGHTNING:
                return config.isVetionEnabled();
            case CHAOS_FANATIC:
                return config.isChaosFanaticEnabled();
            case GALVEK_BOMB:
            case GALVEK_MINE:
                return config.isGalvekEnabled();
            case DAWN_FREEZE:
            case DUSK_CEILING:
                return config.isGargBossEnabled();
            case OLM_FALLING_CRYSTAL:
            case OLM_BURNING:
                return config.isOlmEnabled();
            case CORPOREAL_BEAST:
            case CORPOREAL_BEAST_DARK_CORE:
                return config.isCorpEnabled();
            case WINTERTODT_SNOW_FALL:
                return config.isWintertodtEnabled();
            case TOA_OBELISK_LIGHTNING_SKULL:
                return config.isToaLightningSkullEnabled();
        }

        return false;
    }
}