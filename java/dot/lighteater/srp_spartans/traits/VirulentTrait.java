package dot.lighteater.srp_spartans.traits;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import dot.lighteater.srp_spartans.effect.ModEffects;
import krelox.spartantoolkit.BetterWeaponTrait;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class VirulentTrait extends BetterWeaponTrait {

    private static final int BASE_DURATION_TICKS = 20 * 6;


    public VirulentTrait() {
        super("virulent", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Applies Virulent, increasing damage taken by 100% per level.";
    }

    @Override
    public void onHitEntity(WeaponMaterial material, ItemStack stack, LivingEntity target, LivingEntity attacker, Entity projectile) {
        if (target.level().isClientSide()) return;

        int level = this.getLevel();
        if (level <= 0) return;

        int duration = BASE_DURATION_TICKS * level;
        int amplifier = Math.max(0, level - 1);

        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(ModEffects.VIRULENT.get(), duration, amplifier, false, true, true));
    }
}

