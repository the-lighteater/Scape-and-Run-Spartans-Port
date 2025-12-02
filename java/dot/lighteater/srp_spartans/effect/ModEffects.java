package dot.lighteater.srp_spartans.effect;

import dot.lighteater.srp_spartans.SRPSpartans;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SRPSpartans.MODID);

    public static final RegistryObject<MobEffect> CORROSION = EFFECTS.register("corrosion", CorrosionEffect::new);
    public static final RegistryObject<MobEffect> VIRULENT = EFFECTS.register("virulent", VirulentEffect::new);
    public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<MobEffect> RAGE = EFFECTS.register("rage", RageEffect::new);

}
