package dot.lighteater.srp_spartans.traits;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.TraitDataLoader;
import dot.lighteater.srp_spartans.TraitEffectData;
import dot.lighteater.srp_spartans.effect.ModEffects;
import krelox.spartantoolkit.BetterWeaponTrait;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class BleedingTrait extends BetterWeaponTrait {

    public BleedingTrait() {
        super("bleeding", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Inflicts Bleed: Lose a percentage of your health.";
    }

    @Override
    public void onHitEntity(WeaponMaterial material, ItemStack stack, LivingEntity target, LivingEntity attacker, Entity projectile) {
        if (target.level().isClientSide()) return;

        int level = this.getLevel();
        if (level <= 0) return;

        TraitEffectData data = TraitDataLoader.get(new ResourceLocation("srp_spartans", "bleeding"));
        if (data == null) return;

        for (TraitEffectData.EffectEntry effectEntry : data.effects) {

            ResourceLocation effectRL = new ResourceLocation(effectEntry.effect);
            var effect = ForgeRegistries.MOB_EFFECTS.getValue(effectRL);
            if (effect == null) continue;

            int duration = effectEntry.baseDuration + (effectEntry.durationPerLevel * (level - 1));
            int amplifier = Math.max(0, (level - 1) * effectEntry.amplifierPerLevel);

            target.addEffect(new MobEffectInstance(
                    effect,
                    duration,
                    amplifier,
                    effectEntry.ambient,
                    effectEntry.showParticles,
                    effectEntry.showIcon
            ));
        }
    }
}
