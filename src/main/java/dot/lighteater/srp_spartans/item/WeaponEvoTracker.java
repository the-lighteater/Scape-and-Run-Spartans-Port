package dot.lighteater.srp_spartans.item;

import dot.lighteater.srp_spartans.Config;
import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.custom.WeaponConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fml.common.Mod;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class WeaponEvoTracker {
    private static final String DAMAGE_KEY = "total_damage_dealt";

    public static void init() {
        MinecraftForge.EVENT_BUS.register(WeaponEvoTracker.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        if (event.getSource().getEntity() == null) {
            return;
        }

        var attacker = event.getSource().getEntity();

        if (!(attacker instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        ItemStack weapon = player.getMainHandItem();

        if (weapon.isEmpty()) {
            return;
        }

        WeaponConfig config = getEvolutionConfig(weapon.getItem());

        if (config == null) {
            return;
        }

        float dealt = event.getAmount();

        CompoundTag tag = weapon.getOrCreateTag();

        float previousTotal = tag.getFloat(DAMAGE_KEY);
        float total = previousTotal + dealt;

        tag.putFloat(DAMAGE_KEY, total);

        if (total >= Config.DAMAGE_CAP.get()) {
            evolveWeapon(player, weapon, config);
        }
    }

    private static WeaponConfig getEvolutionConfig(Item item) {

        for (WeaponConfig config : ModSpartanWeaponry.CONFIGS) {

            Item livingItem = WEAPONS
                    .get(config.livingMaterial, config.type)
                    .get();

            if (livingItem == item) {
                return config;
            }
        }

        return null;
    }

    private static void evolveWeapon(
            Player player,
            ItemStack oldWeapon,
            WeaponConfig config
    ) {

        ItemStack evolved = WEAPONS
                .get(config.sentientMaterial, config.type)
                .get()
                .getDefaultInstance();

        CompoundTag oldTag = oldWeapon.getTag();

        if (oldTag != null) {
            CompoundTag newTag = oldTag.copy();
            newTag.remove(DAMAGE_KEY);

            evolved.setTag(newTag);
        }

        player.setItemInHand(
                InteractionHand.MAIN_HAND,
                evolved
        );

        player.displayClientMessage(
                Component.literal("Your weapon has evolved!"),
                true
        );
    }
}
