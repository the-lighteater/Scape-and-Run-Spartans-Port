package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.SRPSpartans;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;
import java.util.function.Supplier;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    private static final List<Supplier<Item>> BLOCKING_ITEMS = List.of(
            SENTIENT_BUCKLER,
            LIVING_BUCKLER,
            SENTIENT_IMPALER,
            LIVING_IMPALER
    );

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

            ResourceLocation blocking = new ResourceLocation(SRPSpartans.MODID, "blocking");

            BLOCKING_ITEMS.forEach(item ->
                    ItemProperties.register(item.get(), blocking, ClientModEvents::blockingPredicate)
            );
        });
    }

    private static float blockingPredicate(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        if (entity == null) return 0.0F;

        if (entity.isUsingItem() && entity.getUseItem() == stack) {
            return 1.0F;
        }

        return 0.0F;
    }
}