package dot.lighteater.srp_spartans.item;

import dot.lighteater.srp_spartans.Config;
import dot.lighteater.srp_spartans.SRPSpartans;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class ShieldEvents {

    public static float BlockedTotal = 20000f;

    private static final double CHARGE_THRESHOLD = 10.0;

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {

        ItemStack stack = event.getItemStack();

        if (stack.isEmpty()) return;
        if (!(stack.getItem() instanceof ShieldItem)) return;

        ResourceLocation itemId = stack.getItem()
                .builtInRegistryHolder()
                .key()
                .location();

        if (!itemId.getNamespace().equals("srp_spartans")) return;

        CompoundTag tag = stack.getOrCreateTag();

        float total = tag.getFloat("BlockedDamage");
        float cap = Config.BLOCKED_DAMAGE_CAP.get();

        if (total <= 0) return;

        int segments = 10;
        float ratio = Math.min(total / cap, 1.0f);

        int filled = (int) (ratio * segments);

        String bar = "█".repeat(filled) + "░".repeat(segments - filled);

        event.getToolTip().add(
                Component.literal("Shield Evolution: ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(Component.literal(bar)
                                .withStyle(ChatFormatting.BLUE))
        );

        event.getToolTip().add(
                Component.literal("Blocked Damage: ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(Component.literal(String.format("%.1f", total))
                                .withStyle(ChatFormatting.AQUA))
                        .append(Component.literal(" / ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(String.format("%.1f", cap))
                                .withStyle(ChatFormatting.DARK_GRAY))
        );
    }

    @SubscribeEvent
    public static void onPlayerBlock(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ResourceLocation itemId = player.getUseItem().getItem().builtInRegistryHolder().key().location();
        if (!itemId.getNamespace().equals("srp_spartans")) return;
        ItemStack shieldStack = player.getUseItem();
        if (!(shieldStack.getItem() instanceof ShieldItem) && !(shieldStack.getItem() instanceof ChargedShieldItem)) return;


        CompoundTag tag = shieldStack.getOrCreateTag();
        double blocked = tag.getDouble("BlockedDamage");
        double blockedInstance;
        if (shieldStack.getItem() instanceof ChargedShieldItem) {
            blockedInstance = tag.getDouble("BlockedDamageInstance");
            blockedInstance += event.getAmount();
        } else blockedInstance = 0;
        blocked += event.getAmount();

        tag.putDouble("BlockedDamage", blocked);
        tag.putDouble("BlockedDamageInstance", blockedInstance);

        if ((shieldStack.getItem() instanceof ChargedShieldItem)) {
            if (blockedInstance >= CHARGE_THRESHOLD) {
                tag.putBoolean("Charged", true);
            }
        }

        if (blocked >= Config.BLOCKED_DAMAGE_CAP.get()) {
            if (shieldStack.getHoverName().getString().equals("Living Impaler")) {
                if (player.getMainHandItem().getHoverName().getString().equals("Living Impaler")) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, SENTIENT_IMPALER.get().getDefaultInstance());
                } else {
                    player.setItemInHand(InteractionHand.OFF_HAND, SENTIENT_IMPALER.get().getDefaultInstance());
                }
            } else if (shieldStack.getHoverName().getString().equals("Living Buckler")) {
                if (player.getMainHandItem().getHoverName().getString().equals("Living Buckler")) {
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
        tag.putDouble("BlockedDamageInstance", 0.0);
    }
}


