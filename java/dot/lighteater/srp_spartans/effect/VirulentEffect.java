package dot.lighteater.srp_spartans.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class VirulentEffect extends MobEffect {

    public static final float DAMAGE_MODIFIER = 100f;

    public VirulentEffect() {
        super(MobEffectCategory.HARMFUL, 0x00660f);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
