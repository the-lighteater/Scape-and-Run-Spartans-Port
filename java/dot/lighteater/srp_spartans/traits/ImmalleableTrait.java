package dot.lighteater.srp_spartans.traits;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import krelox.spartantoolkit.BetterWeaponTrait;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ImmalleableTrait extends BetterWeaponTrait {

    public ImmalleableTrait() {
        super("immalleable", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Occasionally deals a critical strike, massively increasing damage.";
    }

    @Override
    public float modifyDamageDealt(WeaponMaterial material,
                                   float baseDamage,
                                   DamageSource source,
                                   LivingEntity attacker,
                                   LivingEntity victim) {

        if (!(attacker instanceof Player player))
            return baseDamage;

        int traitLevel = this.getLevel();

        float critChance = traitLevel * 0.10f;

        if (player.getRandom().nextFloat() < critChance) {

            float critMultiplier = 1.0f + (0.50f * traitLevel);

            float newDamage = baseDamage * critMultiplier;

            player.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.PLAYER_ATTACK_CRIT,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.2f
            );

            return newDamage;
        }

        return baseDamage;
    }
}

