package dot.lighteater.srp_spartans;

import com.mojang.logging.LogUtils;

import dot.lighteater.srp_spartans.effect.ModEffects;
import dot.lighteater.srp_spartans.item.ModSpartanWeaponry;
import dot.lighteater.srp_spartans.item.WeaponEvoTracker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SRPSpartans.MODID)
public class SRPSpartans
{

    // Define mod id in a common place for everything to reference
    public static final String MODID = "srp_spartans";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public SRPSpartans(FMLJavaModLoadingContext context)
    {
        WeaponEvoTracker.init();

        IEventBus modEventBus = context.getModEventBus();

        ModSpartanWeaponry.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModEffects.EFFECTS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
//        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
