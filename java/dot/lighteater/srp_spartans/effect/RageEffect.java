package dot.lighteater.srp_spartans.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RageEffect extends MobEffect {

    private static final Logger LOGGER = LogManager.getLogger("Bleeding Effect");


    public static final float DAMAGE_BOOST = .1f;
    public static final float SPEED_BOOST = .1f;
    public static final float DRAW_SPEED = .1f;
    public static final int particle_amount = 50;

    public RageEffect() {
        super(MobEffectCategory.HARMFUL, 0x540903);
    }

    public Component getDisplayName() {
        return Component.translatable("Rage Effect");
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
