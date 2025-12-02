package dot.lighteater.srp_spartans.item;

import dot.lighteater.srp_spartans.SRPSpartans;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class ShieldEvents {

    private static final double CHARGE_THRESHOLD = 10.0;

    @SubscribeEvent
    public static void onPlayerBlock(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack shieldStack = player.getUseItem();
        if (!(shieldStack.getItem() instanceof ChargedShieldItem)) return;

        CompoundTag tag = shieldStack.getOrCreateTag();
        double blocked = tag.getDouble("BlockedDamage");
        blocked += event.getAmount();
        tag.putDouble("BlockedDamage", blocked);

        if (blocked >= CHARGE_THRESHOLD) {
            tag.putBoolean("Charged", true);
        }

        if (blocked >= 20000) {
            if (shieldStack.getHoverName().getString().equals("Living Impaler")) {
                if (player.getMainHandItem().getHoverName().getString().equals("Living Impaler")) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, SENTIENT_IMPALER.get().getDefaultInstance());
                } else {
                    player.setItemInHand(InteractionHand.OFF_HAND, SENTIENT_IMPALER.get().getDefaultInstance());
                }
            } else if (shieldStack.getHoverName().getString().equals("Living Buckler")) {
                if (player.getMainHandItem().getHoverName().getString().equals("Living Impaler")) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, SENTIENT_BUCKLER.get().getDefaultInstance());
                } else {
                    player.setItemInHand(InteractionHand.OFF_HAND, SENTIENT_BUCKLER.get().getDefaultInstance());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onStopUsingShield(LivingEntityUseItemEvent.Stop event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack stack = event.getItem();
        if (!(stack.getItem() instanceof ChargedShieldItem)) return;

        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean("Charged")) return;

        ShieldDashHandler.dash(player);

        tag.putBoolean("Charged", false);
        tag.putDouble("BlockedDamage", 0.0);
    }
}


