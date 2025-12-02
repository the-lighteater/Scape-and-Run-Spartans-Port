package dot.lighteater.srp_spartans.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.damagesource.DamageSource;

import java.util.List;

public class ShieldDashHandler {

    public static void dash(Player player) {
        Vec3 look = player.getLookAngle();
        player.push(look.x * 2.5, 0.1, look.z * 2.5);

        List<LivingEntity> hitEntities = player.level().getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(1.5, 1.0, 1.5),
                e -> e != player
        );

        for (LivingEntity target : hitEntities) {
            DamageSource bleedSource = target.damageSources().generic();

            target.hurt(bleedSource, 5);
        }

        if (!player.level().isClientSide()) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0F, 1.0F);

            if (player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        10, 0.3, 0.3, 0.3, 0.1);
            }
        }
    }
}

