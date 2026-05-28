package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.Config;
import dot.lighteater.srp_spartans.SRPSpartans;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class TrackerEvent {
    private static final String DAMAGE_KEY = "total_damage_dealt";

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty() || !stack.hasTag()) return;
        ResourceLocation itemId = event.getItemStack().getItem().builtInRegistryHolder().key().location();
        if (!itemId.getNamespace().equals("srp_spartans")) return;
        CompoundTag tag = stack.getOrCreateTag();

        float total = tag.getFloat(DAMAGE_KEY);
        if (total > 0) {
            event.getToolTip().add(Component.literal("Damage: ")
                    .append(Component.literal(String.valueOf(total))
                            .append(Component.literal(" / "))
                            .append(Component.literal(String.valueOf(Config.DAMAGE_CAP.get())))));
        }
    }
}
