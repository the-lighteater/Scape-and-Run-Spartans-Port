package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.WeaponAttributeData;
import dot.lighteater.srp_spartans.WeaponAttributeLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class WeaponAttributeEvent {

    @SubscribeEvent
    public static void onItemAttribute(ItemAttributeModifierEvent event) {


        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        EquipmentSlot slot = event.getSlotType();

        if (stack.getItem() instanceof ShieldItem) {
            if (slot != EquipmentSlot.OFFHAND) return;
        } else {
            if (slot != EquipmentSlot.MAINHAND) return;
        }
        // Get item ID
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (itemId == null) return;

        // Lookup JSON
        WeaponAttributeData data = WeaponAttributeLoader.get(itemId);
        if (data == null || data.attributes == null) return;

        // Apply attributes
        for (WeaponAttributeData.AttributeEntry entry : data.attributes) {

            var attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(entry.attribute));
            if (attr == null) continue;

            event.addModifier(attr, new AttributeModifier(
                    getItemUUID(stack, stack.getDisplayName().getString() + "_" + entry.uuid + "_added"),
                    "srp_spartans",
                    entry.modifier,
                    entry.getOperation()
            ));
        }
    }

    public static UUID getItemUUID(ItemStack stack, String attributeKey) {
        String base = stack.getOrCreateTag().getString("srp_spartans:item_uuid");

        if (base.isEmpty()) {
            base = UUID.randomUUID().toString();
            stack.getOrCreateTag().putString("srp_spartans:item_uuid", base);
        }

        return UUID.nameUUIDFromBytes((base + attributeKey).getBytes());
    }
}
