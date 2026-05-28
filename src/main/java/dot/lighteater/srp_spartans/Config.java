package dot.lighteater.srp_spartans;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = SRPSpartans.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Float> DAMAGE_CAP;
    public static final ForgeConfigSpec.ConfigValue<Float> BLOCKED_DAMAGE_CAP;

    static {

        BUILDER.push("Evolution Caps");

        DAMAGE_CAP = BUILDER.comment("The number of damage needed to deal for a living weapon to evolve into a" +
                        " sentient weapon.")
                .define("Damage Level", 20000f);

        BLOCKED_DAMAGE_CAP = BUILDER.comment("The number of damage needed to block for a living shield to evolve into a" +
                        " sentient shield.")
                .define("Blocked Damage Level", 20000f);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
    }
}
