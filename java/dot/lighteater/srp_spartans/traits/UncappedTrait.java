package dot.lighteater.srp_spartans.traits;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import krelox.spartantoolkit.BetterWeaponTrait;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class UncappedTrait extends BetterWeaponTrait {

    public UncappedTrait() {
        super("uncapped", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "A portion of damage ignores armor and is dealt as true damage.";
    }

    @Override
    public float modifyDamageDealt(WeaponMaterial material,
                                   float baseDamage,
                                   DamageSource source,
                                   LivingEntity attacker,
                                   LivingEntity victim) {

        if (!(attacker instanceof Player player))
            return baseDamage;

        float trueDamagePercent = 0.30f;

        float trueDamage = baseDamage * trueDamagePercent;

        if (!victim.level().isClientSide) {
            DamageSource trueDmgSource =
                    attacker.damageSources().magic();

            victim.hurt(trueDmgSource, trueDamage);
        }
        return baseDamage;
    }
}

