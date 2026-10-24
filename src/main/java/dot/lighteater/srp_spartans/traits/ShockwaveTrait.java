package dot.lighteater.srp_spartans.traits;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import krelox.spartantoolkit.BetterWeaponTrait;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ShockwaveTrait extends BetterWeaponTrait {

    public ShockwaveTrait() {
        super("shockwave", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Doesn't do anything...yet.";
    }
}

