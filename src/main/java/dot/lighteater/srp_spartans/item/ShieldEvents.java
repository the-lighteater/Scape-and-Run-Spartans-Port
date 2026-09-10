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

    private static final double CHARGE_THRESHOLD = 10.0;

    private static final String BLOCKED_DAMAGE_KEY = "BlockedDamage";
    private static final String BLOCKED_INSTANCE_KEY = "BlockedDamageInstance";
    private static final String CHARGED_KEY = "Charged";

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

        float total = tag.getFloat(BLOCKED_DAMAGE_KEY);
        float cap = Config.BLOCKED_DAMAGE_CAP.get();

        if (total <= 0) return;

        int segments = 10;
        float ratio = Math.min(total / cap, 1.0f);

        int filled = (int) (ratio * segments);

        String bar = "█".repeat(filled) + "░".repeat(segments - filled);

        event.getToolTip().add(
                Component.literal("Shield Evolution: ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(
                                Component.literal(bar)
                                        .withStyle(ChatFormatting.BLUE)
                        )
        );

        event.getToolTip().add(
                Component.literal("Blocked Damage: ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(
                                Component.literal(String.format("%.1f", total))
                                        .withStyle(ChatFormatting.AQUA)
                        )
                        .append(
                                Component.literal(" / ")
                                        .withStyle(ChatFormatting.DARK_GRAY)
                        )
                        .append(
                                Component.literal(String.format("%.1f", cap))
                                        .withStyle(ChatFormatting.DARK_GRAY)
                        )
        );
    }

    @SubscribeEvent
    public static void onPlayerBlock(LivingAttackEvent event) {

        if (event.isCanceled()) return;

        if (!(event.getEntity() instanceof Player player)) return;

        if (player.level().isClientSide()) return;

        ItemStack shieldStack = player.getUseItem();

        if (shieldStack.isEmpty()) return;

        if (!(shieldStack.getItem() instanceof ShieldItem)
                && !(shieldStack.getItem() instanceof ChargedShieldItem)) {
            return;
        }

        ResourceLocation itemId = shieldStack.getItem()
                .builtInRegistryHolder()
                .key()
                .location();

        if (!itemId.getNamespace().equals("srp_spartans")) return;

        CompoundTag tag = shieldStack.getOrCreateTag();

        double blocked = tag.getDouble(BLOCKED_DAMAGE_KEY);
        blocked += event.getAmount();

        tag.putDouble(BLOCKED_DAMAGE_KEY, blocked);

        /*
         * Charged shield tracking
         */
        if (shieldStack.getItem() instanceof ChargedShieldItem) {

            double blockedInstance =
                    tag.getDouble(BLOCKED_INSTANCE_KEY);

            blockedInstance += event.getAmount();

            tag.putDouble(
                    BLOCKED_INSTANCE_KEY,
                    blockedInstance
            );

            if (blockedInstance >= CHARGE_THRESHOLD) {
                tag.putBoolean(CHARGED_KEY, true);
            }
        }

        /*
         * Shield evolution
         */
        if (blocked >= Config.BLOCKED_DAMAGE_CAP.get()) {

            InteractionHand hand;

            if (player.getMainHandItem() == shieldStack) {
                hand = InteractionHand.MAIN_HAND;
            } else {
                hand = InteractionHand.OFF_HAND;
            }

            evolveShield(player, shieldStack, hand);
        }
    }

    private static void evolveShield(
            Player player,
            ItemStack oldShield,
            InteractionHand hand
    ) {
        ItemStack evolved;

        if (oldShield.is(LIVING_IMPALER.get())) {
            evolved = SENTIENT_IMPALER.get().getDefaultInstance();

        } else if (oldShield.is(LIVING_BUCKLER.get())) {
            evolved = SENTIENT_BUCKLER.get().getDefaultInstance();

        } else {
            return;
        }

        CompoundTag oldTag = oldShield.getTag();

        if (oldTag != null) {

            CompoundTag newTag = oldTag.copy();

            newTag.remove(BLOCKED_DAMAGE_KEY);
            newTag.remove(BLOCKED_INSTANCE_KEY);
            newTag.remove(CHARGED_KEY);

            evolved.setTag(newTag);
        }

        player.setItemInHand(hand, evolved);
    }

    @SubscribeEvent
    public static void onStopUsingShield(
            LivingEntityUseItemEvent.Stop event
    ) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack stack = event.getItem();

        if (!(stack.getItem() instanceof ChargedShieldItem)) return;

        CompoundTag tag = stack.getOrCreateTag();

        if (!tag.getBoolean(CHARGED_KEY)) return;

        ShieldDashHandler.dash(player);

        tag.putBoolean(CHARGED_KEY, false);
        tag.putDouble(BLOCKED_INSTANCE_KEY, 0.0);
    }
}