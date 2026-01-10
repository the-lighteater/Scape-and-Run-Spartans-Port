package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.effect.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class RageEvent {

    private static final UUID RAGE_SPEED_BOOST_ID = UUID.fromString("a1b2c3d4-e5f6-47a8-9012-abcdef123456");

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.RAGE.get())) {
            int level = entity.getEffect(ModEffects.RAGE.get()).getAmplifier();
            double boost = 0.1 * (level + 1);

            AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null) {
                speed.removeModifier(RAGE_SPEED_BOOST_ID);

                speed.addPermanentModifier(new AttributeModifier(
                        RAGE_SPEED_BOOST_ID,
                        "Rage Speed Boost",
                        boost,
                        AttributeModifier.Operation.MULTIPLY_BASE
                ));
            }
            if (!entity.level().isClientSide && entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.SMOKE,
                        entity.getX(), entity.getY() + 1, entity.getZ(),
                        10,
                        0.5, 0.5, 0.5, 0.02
                );
            }
        } else {
            AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null && speed.getModifier(RAGE_SPEED_BOOST_ID) != null) {
                speed.removeModifier(RAGE_SPEED_BOOST_ID);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();

        if (!(source instanceof LivingEntity attacker)) {
            return;
        }

        if (attacker.hasEffect(ModEffects.RAGE.get())) {
            int level = attacker.getEffect(ModEffects.RAGE.get()).getAmplifier();
            float multiplier = 1.0f + (0.25f * (level + 1));
            event.setAmount(event.getAmount() * multiplier);
        }
    }

    @SubscribeEvent
    public static void onBowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        ItemStack bow = event.getBow();

        if (player.hasEffect(ModEffects.RAGE.get())) {
            int amplifier = player.getEffect(ModEffects.RAGE.get()).getAmplifier();

            int charge = event.getCharge();

            float speedMultiplier = 1.0f + (0.25f * (amplifier + 1));

            int newCharge = Math.min((int)(charge * speedMultiplier), 20);
            event.setCharge(newCharge);
        }
    }
}

