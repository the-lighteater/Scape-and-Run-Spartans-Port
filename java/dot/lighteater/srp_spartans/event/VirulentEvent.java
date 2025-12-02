package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.effect.ModEffects;
import dot.lighteater.srp_spartans.effect.VirulentEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class VirulentEvent {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (target == null) return;

        MobEffectInstance effect = target.getEffect(ModEffects.VIRULENT.get());
        if (effect == null) return;

        int level = effect.getAmplifier() + 1;
        float baseDamage = event.getAmount();

        float multiplier = 1.0f + (VirulentEffect.DAMAGE_MODIFIER / 100f) * level;
        float boosted = baseDamage * multiplier;

        event.setAmount(boosted);
    }
}

