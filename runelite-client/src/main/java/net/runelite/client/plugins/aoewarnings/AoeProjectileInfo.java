package net.runelite.client.plugins.aoewarnings;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import net.runelite.api.GraphicID;

public enum AoeProjectileInfo
{
    LIZARDMAN_SHAMAN_AOE(GraphicID.LIZARDMAN_SHAMAN_AOE, 3000, 3),
    CRAZY_ARCHAEOLOGIST_AOE(GraphicID.CRAZY_ARCHAEOLOGIST_AOE, 3000, 3),
    ICE_DEMON_RANGED_AOE(GraphicID.ICE_DEMON_RANGED_AOE, 3000, 3),
    /**
     * When you don't have pray range on ice demon does an ice barrage
     */
    ICE_DEMON_ICE_BARRAGE_AOE(GraphicID.ICE_DEMON_ICE_BARRAGE_AOE, 3000, 3),
    /**
     * The AOE when vasa first starts
     */
    VASA_AWAKEN_AOE(GraphicID.VASA_AWAKEN_AOE, 4500, 3),
    VASA_RANGED_AOE(GraphicID.VASA_RANGED_AOE, 3000, 3),
    TEKTON_METEOR_AOE(GraphicID.TEKTON_METEOR_AOE, 4000, 3),

    /**
     * The AOEs of Vorkath
     */
    VORKATH_BOMB(GraphicID.VORKATH_BOMB_AOE, 2400, 3),
    VORKATH_POISON_POOL(GraphicID.VORKATH_POISON_POOL_AOE, 1800, 1),
    VORKATH_SPAWN(GraphicID.VORKATH_SPAWN_AOE, 3000, 1), //extra tick because hard to see otherwise
    VORKATH_TICK_FIRE(GraphicID.VORKATH_TICK_FIRE_AOE, 600, 1),

    /**
     * the AOEs of Galvek
     */
    GALVEK_MINE(GraphicID.GALVEK_MINE, 3600, 3),
    GALVEK_BOMB(GraphicID.GALVEK_BOMB, 2400, 3),

    DAWN_FREEZE(GraphicID.DAWN_FREEZE, 3000, 3),
    DUSK_CEILING(GraphicID.DUSK_CEILING, 3000, 3),

    /**
     * the AOE of Vet'ion
     */
    VETION_LIGHTNING(GraphicID.VETION_LIGHTNING, 3000, 1),

    /**
     * the AOE of Chaos Fanatic
     */
    CHAOS_FANATIC(GraphicID.CHAOS_FANATIC_AOE, 3000, 1),

    /**
     * the AOE of the Corporeal Beast
     */

    CORPOREAL_BEAST(GraphicID.CORPOREAL_BEAST_AOE, 3000, 1),
    CORPOREAL_BEAST_DARK_CORE(GraphicID.CORPOREAL_BEAST_DARK_CORE_AOE, 3000, 3),

    /**
     * the AOEs of The Great Olm
     * missing ids and length, please help
     */
    OLM_FALLING_CRYSTAL(GraphicID.OLM_FALLING_CRYSTAL_AOE, 2400, 3),
    OLM_BURNING(GraphicID.OLM_BURNING_AOE, 2400, 3),

    /**
     * the AOE of the Wintertodt snow that falls
     */
    WINTERTODT_SNOW_FALL(GraphicID.WINTERTODT_SNOW_FALL_AOE, 4000, 3),

    TOA_OBELISK_LIGHTNING_SKULL(GraphicID.TOA_OBELISK_LIGHTNING_SKULL, 2400, 7);

    /**
     * The id of the projectile to trigger this AoE warning
     */
    private final int id;

    /**
     * How long the indicator should last for this AoE warning This might
     * need to be a bit longer than the projectile actually takes to land as
     * there is a fade effect on the warning
     */
    private final Duration lifeTime;

    /**
     * The size of the splash radius of the AoE warning Ex. Lizardman shaman
     * AoE is a 3x3, so aoeSize = 3
     */
    private final int aoeSize;

    private static final Map<Integer, AoeProjectileInfo> map = new HashMap<>();

    static
    {
        for (AoeProjectileInfo aoe : values())
        {
            map.put(aoe.id, aoe);
        }
    }

    AoeProjectileInfo(int id, int lifeTimeMillis, int aoeSize)
    {
        this.id = id;
        this.lifeTime = Duration.ofMillis(lifeTimeMillis);
        this.aoeSize = aoeSize;
    }

    public Duration getLifeTime()
    {
        return lifeTime;
    }

    public int getId()
    {
        return id;
    }

    public int getAoeSize()
    {
        return aoeSize;
    }

    public static AoeProjectileInfo getById(int id)
    {
        return map.get(id);
    }
}