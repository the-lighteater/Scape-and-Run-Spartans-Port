package dot.lighteater.srp_spartans.item;

import dot.lighteater.srp_spartans.Config;
import dot.lighteater.srp_spartans.SRPSpartans;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class WeaponEvoTracker {

    public static float DamageLevel = 20000f;

    private static final Map<RegistryObject<Item>, Supplier<ItemStack>> EVOLUTION_MAP = new HashMap<>();

    static {
        EVOLUTION_MAP.put(WEAPONS.get(LIVING_VIRAL_1, WeaponType.GREATSWORD),
                () -> WEAPONS.get(SENTIENT_VIRAL_2, WeaponType.GREATSWORD).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_CLOAKED, WeaponType.DAGGER),
                () -> WEAPONS.get(SENTIENT_CLOAKED, WeaponType.DAGGER).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_2, WeaponType.PIKE),
                () -> WEAPONS.get(SENTIENT_BLEEDING_3, WeaponType.PIKE).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_2, WeaponType.GLAIVE),
                () -> WEAPONS.get(SENTIENT_BLEEDING_3, WeaponType.GLAIVE).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_REPULSE_2, WeaponType.PARRYING_DAGGER),
            () -> WEAPONS.get(SENTIENT_REPULSE_3, WeaponType.PARRYING_DAGGER).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_VIRAL_2, WeaponType.LONGSWORD),
                () -> WEAPONS.get(SENTIENT_VIRAL_3, WeaponType.LONGSWORD).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_VIRAL_REACH_1, WeaponType.KATANA),
                () -> WEAPONS.get(SENTIENT_VIRAL_REACH_2, WeaponType.KATANA).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_VIRAL_REACH_1, WeaponType.THROWING_KNIFE),
                () -> WEAPONS.get(SENTIENT_VIRAL_REACH_2, WeaponType.THROWING_KNIFE).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_REACH_2, WeaponType.SABER),
                () -> WEAPONS.get(SENTIENT_BLEEDING_REACH_3, WeaponType.SABER).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_REACH_2, WeaponType.JAVELIN),
                () -> WEAPONS.get(SENTIENT_BLEEDING_REACH_3, WeaponType.JAVELIN).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_REACH_1, WeaponType.RAPIER),
                () -> WEAPONS.get(SENTIENT_BLEEDING_REACH_2, WeaponType.RAPIER).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_1, WeaponType.LANCE),
                () -> WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.LANCE).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_1, WeaponType.SPEAR),
                () -> WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.SPEAR).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_1, WeaponType.LONGBOW),
                () -> WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.LONGBOW).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_BLEEDING_1, WeaponType.HEAVY_CROSSBOW),
                () -> WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.HEAVY_CROSSBOW).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_CORROSION_1, WeaponType.HALBERD),
                () -> WEAPONS.get(SENTIENT_CORROSION_2, WeaponType.HALBERD).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_CORROSION_REACH_1, WeaponType.TOMAHAWK),
                () -> WEAPONS.get(SENTIENT_CORROSION_REACH_2, WeaponType.TOMAHAWK).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_CORROSION_REACH_2, WeaponType.FLANGED_MACE),
                () -> WEAPONS.get(SENTIENT_CORROSION_REACH_3, WeaponType.FLANGED_MACE).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_REAPER, WeaponType.SCYTHE),
                () -> WEAPONS.get(SENTIENT_REAPER, WeaponType.SCYTHE).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_IMMALLEABLE, WeaponType.BOOMERANG),
                () -> WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.BOOMERANG).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_IMMALLEABLE, WeaponType.QUARTERSTAFF),
                () -> WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.QUARTERSTAFF).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_IMMALLEABLE, WeaponType.WARHAMMER),
                () -> WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.WARHAMMER).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_IMMALLEABLE, WeaponType.BATTLE_HAMMER),
                () -> WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.BATTLE_HAMMER).get().getDefaultInstance());

        EVOLUTION_MAP.put(WEAPONS.get(LIVING_IMMALLEABLE, WeaponType.BATTLEAXE),
                () -> WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.BATTLEAXE).get().getDefaultInstance());

        EVOLUTION_MAP.put(LIVING_GAUNTLET, () -> SENTIENT_GAUNTLET.get().getDefaultInstance());
    }
    private static final Logger LOGGER = LogManager.getLogger("Weapon Evolve Tracker");

    private static final String DAMAGE_KEY = "total_damage_dealt";

    public static void init() {
        MinecraftForge.EVENT_BUS.register(WeaponEvoTracker.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() == null) return;
        var attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        ItemStack weapon = player.getMainHandItem();
        if (weapon.isEmpty()) return;

        ResourceLocation itemId = weapon.getItem().builtInRegistryHolder().key().location();
        if (!itemId.getNamespace().equals("srp_spartans")) return;

        float dealt = event.getAmount();

        CompoundTag tag = weapon.getOrCreateTag();
        float total = tag.getFloat(DAMAGE_KEY) + dealt;
        tag.putFloat(DAMAGE_KEY, total);

        if (total >= Config.DAMAGE_CAP.get()) {
            evolveWeapon(player, weapon);
        }
    }

    private static void evolveWeapon(Player player, ItemStack oldWeapon) {

        for (var entry : EVOLUTION_MAP.entrySet()) {
            if (entry.getKey().get() == oldWeapon.getItem()) {
                ItemStack evolved = entry.getValue().get();
                player.setItemInHand(InteractionHand.MAIN_HAND, evolved);
                player.displayClientMessage(Component.literal("Your weapon has evolved!"), true);
            }
        }
    }
}
