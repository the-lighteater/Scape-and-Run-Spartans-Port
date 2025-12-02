package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.SRPSpartans;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class CloakingEvent {

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.isEmpty()) return;

        ResourceLocation mainHandItemId = mainHand.getItem().builtInRegistryHolder().key().location();

        Set<ResourceLocation> validIds = Set.of(
                WEAPONS.get(LIVING_CLOAKED, WeaponType.DAGGER).get().asItem().builtInRegistryHolder().key().location(),
                WEAPONS.get(SENTIENT_CLOAKED, WeaponType.DAGGER).get().asItem().builtInRegistryHolder().key().location()
        );

        if (!validIds.contains(mainHandItemId)) return;

        if (player.isCrouching()) player.addEffect(new MobEffectInstance(
                MobEffects.INVISIBILITY,
                2,
                0,
                false,
                false
        ));
    }
}
