package dot.lighteater.srp_spartans.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedingEffect extends MobEffect {

    public static final float BASE_PERCENT_DAMAGE = 0.02f;
    public static final float MAX_DAMAGE_PER_TICK = 150.0f;

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B0000);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) return;

        float percent = BASE_PERCENT_DAMAGE * (amplifier + 1);
        float potentialDamage = entity.getMaxHealth() * percent;

        float finalDamage = Math.min(potentialDamage, MAX_DAMAGE_PER_TICK);

        DamageSource bleedSource = entity.damageSources().generic();

        entity.hurt(bleedSource, finalDamage);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
