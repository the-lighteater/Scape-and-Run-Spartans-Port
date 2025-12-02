package dot.lighteater.srp_spartans.item;

import dot.lighteater.srp_spartans.SRPSpartans;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class WeaponEvoTracker {
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

        if (total >= 20000f) {
            evolveWeapon(player, weapon);
        }
    }

    private static void evolveWeapon(Player player, ItemStack oldWeapon) {
        ItemStack evolved = null;
        switch (oldWeapon.getHoverName().getString()) {
            case "Living Greatsword" ->
                    evolved = WEAPONS.get(SENTIENT_VIRAL_2, WeaponType.GREATSWORD).get().asItem().getDefaultInstance();
            case "Living Dagger" ->
                    evolved = WEAPONS.get(SENTIENT_CLOAKED, WeaponType.DAGGER).get().asItem().getDefaultInstance();
            case "Living Glaive" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_3, WeaponType.GLAIVE).get().asItem().getDefaultInstance();
            case "Living Pike" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_3, WeaponType.PIKE).get().asItem().getDefaultInstance();
            case "Living Parrying Dagger" ->
                    evolved = WEAPONS.get(SENTIENT_REPULSE_3, WeaponType.PARRYING_DAGGER).get().asItem().getDefaultInstance();
            case "Living Longsword" ->
                    evolved = WEAPONS.get(SENTIENT_VIRAL_3, WeaponType.LONGSWORD).get().asItem().getDefaultInstance();
            case "Living Katana" ->
                    evolved = WEAPONS.get(SENTIENT_VIRAL_REACH_2, WeaponType.KATANA).get().asItem().getDefaultInstance();
            case "Living Throwing Knife" ->
                    evolved = WEAPONS.get(SENTIENT_VIRAL_REACH_2, WeaponType.THROWING_KNIFE).get().asItem().getDefaultInstance();
            case "Living Saber" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_REACH_3, WeaponType.SABER).get().asItem().getDefaultInstance();
            case "Living Javelin" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_REACH_3, WeaponType.JAVELIN).get().asItem().getDefaultInstance();
            case "Living Rapier" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_REACH_2, WeaponType.RAPIER).get().asItem().getDefaultInstance();
            case "Living Lance" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.LANCE).get().asItem().getDefaultInstance();
            case "Living Spear" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.SPEAR).get().asItem().getDefaultInstance();
            case "Living Longbow" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.LONGBOW).get().asItem().getDefaultInstance();
            case "Living Heavy Crossbow" ->
                    evolved = WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.HEAVY_CROSSBOW).get().asItem().getDefaultInstance();
            case "Living Halberd" ->
                    evolved = WEAPONS.get(SENTIENT_CORROSION_2, WeaponType.HALBERD).get().asItem().getDefaultInstance();
            case "Living Tomahawk" ->
                    evolved = WEAPONS.get(SENTIENT_CORROSION_REACH_2, WeaponType.TOMAHAWK).get().asItem().getDefaultInstance();
            case "Living Flanged Mace" ->
                    evolved = WEAPONS.get(SENTIENT_CORROSION_REACH_3, WeaponType.FLANGED_MACE).get().asItem().getDefaultInstance();
            case "Living Scythe" ->
                    evolved = WEAPONS.get(SENTIENT_REAPER, WeaponType.SCYTHE).get().asItem().getDefaultInstance();
            case "Living Boomerang" ->
                    evolved = WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.BOOMERANG).get().asItem().getDefaultInstance();
            case "Living Quarterstaff" ->
                    evolved = WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.QUARTERSTAFF).get().asItem().getDefaultInstance();
            case "Living Warhammer" ->
                    evolved = WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.WARHAMMER).get().asItem().getDefaultInstance();
            case "Living Battle Hammer" ->
                    evolved = WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.BATTLE_HAMMER).get().asItem().getDefaultInstance();
            case "Living Battleaxe" ->
                    evolved = WEAPONS.get(SENTIENT_IMMALLEABLE, WeaponType.BATTLEAXE).get().asItem().getDefaultInstance();
            case "Living Gauntlet" ->
                    evolved = ModSpartanWeaponry.SENTIENT_GAUNTLET.get().getDefaultInstance();
        }
        if (evolved != null) {
            player.setItemInHand(InteractionHand.MAIN_HAND, evolved);
            player.displayClientMessage(Component.literal("Your weapon has evolved!"), true);
        }
    }
}
