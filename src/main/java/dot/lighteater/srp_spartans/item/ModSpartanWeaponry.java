package dot.lighteater.srp_spartans.item;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.oblivioussp.spartanweaponry.api.data.model.ModelGenerator;
import com.oblivioussp.spartanweaponry.api.trait.ReachWeaponTrait;
import com.oblivioussp.spartanweaponry.api.trait.SpeedModifierWeaponTrait;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;
import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.custom.RecipeData;
import dot.lighteater.srp_spartans.custom.WeaponConfig;
import dot.lighteater.srp_spartans.traits.*;
import krelox.spartantoolkit.SpartanAddon;
import krelox.spartantoolkit.SpartanMaterial;
import krelox.spartantoolkit.WeaponItem;
import krelox.spartantoolkit.WeaponMap;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class ModSpartanWeaponry extends SpartanAddon {
    private static final String modid = "srp_spartans";

    private static final Logger LOGGER = LogManager.getLogger("SRP Spartans Addon");

    public static WeaponMap WEAPONS = new WeaponMap();
    public static DeferredRegister<Item> ITEMS = itemRegister(modid);
    public static final DeferredRegister<WeaponTrait> TRAITS = traitRegister(modid);
    public static final DeferredRegister<CreativeModeTab> TABS = tabRegister(modid);

    public static final RegistryObject<WeaponTrait> VIRULENT_1;
    public static final RegistryObject<WeaponTrait> VIRULENT_2;
    public static final RegistryObject<WeaponTrait> VIRULENT_3;
    public static final RegistryObject<WeaponTrait> CORROSION_1;
    public static final RegistryObject<WeaponTrait> CORROSION_2;
    public static final RegistryObject<WeaponTrait> CORROSION_3;
    public static final RegistryObject<WeaponTrait> BLEEDING_1;
    public static final RegistryObject<WeaponTrait> BLEEDING_2;
    public static final RegistryObject<WeaponTrait> BLEEDING_3;
    public static final RegistryObject<WeaponTrait> REPULSE_2;
    public static final RegistryObject<WeaponTrait> REPULSE_3;
    public static final RegistryObject<WeaponTrait> CLOAKING;
    public static final RegistryObject<WeaponTrait> REAPER;
    public static final RegistryObject<WeaponTrait> HEAVY_1;
    public static final RegistryObject<WeaponTrait> HEAVY_2;
    public static final RegistryObject<WeaponTrait> REACH_1;
    public static final RegistryObject<WeaponTrait> REACH_2;
    public static final RegistryObject<WeaponTrait> REACH_3;
    public static final RegistryObject<WeaponTrait> IMMALLEABLE_1;
    public static final RegistryObject<WeaponTrait> IMMALLEABLE_2;
    public static final RegistryObject<WeaponTrait> UNCAPPED;

    public static final ArrayList<SpartanMaterial> MATERIALS;

    public static final SpartanMaterial  LIVING_CLOAKED;
    public static final SpartanMaterial  LIVING_VIRAL_1;
    public static final SpartanMaterial  LIVING_VIRAL_REACH_1;
    public static final SpartanMaterial  LIVING_VIRAL_2;
    public static final SpartanMaterial  LIVING_REPULSE_2;
    public static final SpartanMaterial  LIVING_BLEEDING_1;
    public static final SpartanMaterial  LIVING_BLEEDING_REACH_1;
    public static final SpartanMaterial  LIVING_BLEEDING_2;
    public static final SpartanMaterial  LIVING_BLEEDING_REACH_2;
    public static final SpartanMaterial  LIVING_CORROSION_1;
    public static final SpartanMaterial  LIVING_CORROSION_REACH_1;
    public static final SpartanMaterial  LIVING_CORROSION_2;
    public static final SpartanMaterial  LIVING_CORROSION_REACH_2;
    public static final SpartanMaterial  LIVING_REAPER;
    public static final SpartanMaterial  LIVING_IMMALLEABLE;

    public static final SpartanMaterial  SENTIENT_CLOAKED;
    public static final SpartanMaterial  SENTIENT_VIRAL_2;
    public static final SpartanMaterial  SENTIENT_VIRAL_REACH_2;
    public static final SpartanMaterial  SENTIENT_VIRAL_3;
    public static final SpartanMaterial  SENTIENT_REPULSE_3;
    public static final SpartanMaterial  SENTIENT_BLEEDING_2;
    public static final SpartanMaterial  SENTIENT_BLEEDING_REACH_2;
    public static final SpartanMaterial  SENTIENT_BLEEDING_3;
    public static final SpartanMaterial  SENTIENT_BLEEDING_REACH_3;
    public static final SpartanMaterial  SENTIENT_CORROSION_2;
    public static final SpartanMaterial  SENTIENT_CORROSION_REACH_2;
    public static final SpartanMaterial  SENTIENT_CORROSION_3;
    public static final SpartanMaterial  SENTIENT_CORROSION_REACH_3;
    public static final SpartanMaterial  SENTIENT_REAPER;
    public static final SpartanMaterial  SENTIENT_IMMALLEABLE;

    public static final RegistryObject<CreativeModeTab> REDHOT_SPARTAN_TAB;
    private static final TagKey<Item> STICKS;
    public static final RegistryObject<Item> HARDENED_BONE_POLE;
    public static final RegistryObject<Item> LONG_BLADE_FRAGMENT;
    public static final RegistryObject<Item> LIVING_NUCLEUS;
    public static final RegistryObject<Item> SERRATED_SPINES;
    public static final RegistryObject<Item> VILE_PLATE;
    public static final RegistryObject<Item> LIVING_BUCKLER;
    public static final RegistryObject<Item> SENTIENT_BUCKLER;
    public static final RegistryObject<Item> LIVING_IMPALER;
    public static final RegistryObject<Item> SENTIENT_IMPALER;
    public static final RegistryObject<Item> LIVING_GAUNTLET;
    public static final RegistryObject<Item> SENTIENT_GAUNTLET;

    public static List<WeaponConfig> CONFIGS;

    public static List<Pair<RegistryObject<Item>, RecipeData>> itemRecipes;

    @SafeVarargs
    private static SpartanMaterial material(String name, boolean sentient, RegistryObject<WeaponTrait>... traits) {
        Tier tier = new SpartanTier(1250, 9.0F, 15.0F, 5, 18, () -> Ingredient.of(LIVING_NUCLEUS.get()));
        if (sentient) {
            tier = new SpartanTier(2050, 9.0F, 35.0F, 6, 18, () -> Ingredient.of(LIVING_NUCLEUS.get()));
        }
        SpartanMaterial material = new SpartanMaterial(name, modid, tier, ItemTags.create(new ResourceLocation(modid + ":" + name)), traits) {
            public TagKey<Item> getStick() {
                LOGGER.info("getStick() called for material {}", name);
                return ModSpartanWeaponry.STICKS;
            }
        };
        MATERIALS.add(material);
        LOGGER.info("Material {} added to MATERIALS list", name);
        return material;
    }

    public ModSpartanWeaponry() {
        LOGGER.info("Constructing ModSpartanWeaponry...");

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        for (WeaponConfig config : CONFIGS) {
            this.registerSpartanWeapon(ITEMS, config.livingMaterial, config.type);
            this.registerSpartanWeapon(ITEMS, config.sentientMaterial, config.type);
        }

        bus.addListener(this::gatherData);
    }

    public static void register(IEventBus modEventBus) {
        LOGGER.info("Registering ModSpartanWeaponry with event bus...");

        ITEMS.register(modEventBus);
        TRAITS.register(modEventBus);
        TABS.register(modEventBus);

        ModSpartanWeaponry instance = new ModSpartanWeaponry();

        MinecraftForge.EVENT_BUS.register(instance);
    }

    @SubscribeEvent
    public void gatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
//        Item item = event.getItemStack().getItem();
//        if (item instanceof WeaponItem weapon) {
//            if (weapon.getMaterial().equals(LIVING)) {
//                event.getTooltipElements().add(1,
//                        Either.left(Component.literal("Living Weapon").withStyle(ChatFormatting.GOLD)));
//            }
//        }
    }

    @Override
    protected void addTranslations(LanguageProvider provider, Function<RegistryObject<?>, String> formatName) {
        super.addTranslations(provider, formatName);
        LOGGER.info("Adding language translations...");
        provider.add(HARDENED_BONE_POLE.get(), "Hardened Bone Pole");
        provider.add(LONG_BLADE_FRAGMENT.get(), "Infectious Long Blade Fragment");
        provider.add(LIVING_NUCLEUS.get(), "Living Nucleus");
        provider.add(SERRATED_SPINES.get(), "Serrated Spines");
        provider.add(VILE_PLATE.get(), "Vile Plate");
        provider.add(LIVING_BUCKLER.get(), "Living Buckler");
        provider.add(SENTIENT_BUCKLER.get(), "Sentient Buckler");
        provider.add(LIVING_IMPALER.get(), "Living Impaler");
        provider.add(SENTIENT_IMPALER.get(), "Sentient Impaler");
        provider.add(SENTIENT_GAUNTLET.get(), "Sentient Gauntlet");
        provider.add(LIVING_GAUNTLET.get(), "Living Gauntlet");

    }

    @Override
    protected void registerModels(ItemModelProvider provider, ModelGenerator generator) {
        super.registerModels(provider, generator);
        provider.basicItem(LIVING_NUCLEUS.get());
        provider.basicItem(SERRATED_SPINES.get());
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        LOGGER.info("Building crafting recipes...");

        TriConsumer<ShapelessRecipeBuilder, Integer, TagKey<Item>> recipeHelper = (builder, count, ingredient) -> {
            LOGGER.info("Creating recipe for {} x {}", ForgeRegistries.ITEMS.getKey(builder.getResult().asItem()), count);
            builder.requires(Ingredient.of(STICKS), count)
                    .requires(ingredient)
                    .group(ForgeRegistries.ITEMS.getKey(builder.getResult()).toString())
                    .unlockedBy("has_stick", has(STICKS))
                    .save(consumer, ForgeRegistries.ITEMS.getKey(builder.getResult())
                            .withSuffix("_from_" + ingredient.location().getPath()));
        };

        WEAPONS.forEach((key, item) -> {
            for (WeaponConfig config : CONFIGS) {
                if (key.first() == config.livingMaterial && key.second() == config.type) {
                    buildRecipe(consumer, config, item);
                }
            }
        });

        for (Pair<RegistryObject<Item>, RecipeData> items : itemRecipes) {
            buildItemRecipe(consumer, items.getSecond(), items.getFirst().get());
        }
    }

    private Set<Character> getUsedChars(String[] pattern) {
        Set<Character> used = new HashSet<>();
        for (String row : pattern) {
            for (char c : row.toCharArray()) {
                if (c != ' ') used.add(c);
            }
        }
        return used;
    }

    private void buildRecipe(Consumer<FinishedRecipe> consumer, WeaponConfig config, RegistryObject<Item> item) {
        ShapedRecipeBuilder builder =
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get());

        Set<Character> used = getUsedChars(config.recipe.pattern);

        if (used.contains('V')) builder.define('V', VILE_PLATE.get());
        if (used.contains('H')) builder.define('H', HARDENED_BONE_POLE.get());
        if (used.contains('L')) builder.define('L', LIVING_NUCLEUS.get());
        if (used.contains('B')) builder.define('B', LONG_BLADE_FRAGMENT.get());
        if (used.contains('D')) builder.define('D', Items.LEAD);
        if (used.contains('C')) builder.define('C', Items.CROSSBOW);
        if (used.contains('G')) builder.define('G', Items.STRING);

        for (String row : config.recipe.pattern) {
            builder.pattern(row);
        }

        builder.unlockedBy("has_rotten_flesh", has(VILE_PLATE.get()))
                .save(consumer);
    }

    private void buildItemRecipe(Consumer<FinishedRecipe> consumer, RecipeData recipe, Item item) {
        ShapedRecipeBuilder builder =
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item);

        Set<Character> used = getUsedChars(recipe.pattern);

        if (used.contains('S')) builder.define('S', Items.SPIDER_EYE);
        if (used.contains('R')) builder.define('R', Items.ROTTEN_FLESH);
        if (used.contains('B')) builder.define('B', Items.SLIME_BALL);
        if (used.contains('D')) builder.define('D', Items.GLOWSTONE_DUST);
        if (used.contains('G')) builder.define('G', Items.GUNPOWDER);
        if (used.contains('O')) builder.define('O', Items.BONE);
        if (used.contains('E')) builder.define('E', Items.ENDER_PEARL);
        if (used.contains('A')) builder.define('A', Items.ARROW);
        if (used.contains('V')) builder.define('V', VILE_PLATE.get());
        if (used.contains('H')) builder.define('H', HARDENED_BONE_POLE.get());
        if (used.contains('L')) builder.define('L', LIVING_NUCLEUS.get());
        if (used.contains('P')) builder.define('P', SERRATED_SPINES.get());


        for (String row : recipe.pattern) {
            builder.pattern(row);
        }

        builder.unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(consumer);
    }

    public String modid() {
        return "srp_spartans";
    }

    public List<SpartanMaterial> getMaterials() {
        return MATERIALS;
    }

    public WeaponMap getWeaponMap() {
        return WEAPONS;
    }

    static {

        HEAVY_1 = registerTrait(TRAITS, new SpeedModifierWeaponTrait("heavy", WeaponTrait.TraitQuality.NEGATIVE).setMagnitude(-0.1f).setLevel(1));
        HEAVY_2 = registerTrait(TRAITS, new SpeedModifierWeaponTrait("heavy", WeaponTrait.TraitQuality.NEGATIVE).setMagnitude(-0.2f).setLevel(2));
        REACH_1 = registerTrait(TRAITS, new ReachWeaponTrait("reach", "spartanweaponry").setLevel(1).setMagnitude(6.0f));
        REACH_2 = registerTrait(TRAITS, new ReachWeaponTrait("reach", "spartanweaponry").setLevel(2).setMagnitude(6.0f));
        REACH_3 = registerTrait(TRAITS, new ReachWeaponTrait("reach", "spartanweaponry").setLevel(3).setMagnitude(6.0f));
        VIRULENT_1 = registerTrait(TRAITS, new VirulentTrait().setLevel(1));
        VIRULENT_2 = registerTrait(TRAITS, new VirulentTrait().setLevel(2));
        VIRULENT_3 = registerTrait(TRAITS, new VirulentTrait().setLevel(3));
        CORROSION_1 = registerTrait(TRAITS, new CorrosionTrait().setLevel(1));
        CORROSION_2 = registerTrait(TRAITS, new CorrosionTrait().setLevel(2));
        CORROSION_3 = registerTrait(TRAITS, new CorrosionTrait().setLevel(3));
        BLEEDING_1 = registerTrait(TRAITS, new BleedingTrait().setLevel(1));
        BLEEDING_2 = registerTrait(TRAITS, new BleedingTrait().setLevel(2));
        BLEEDING_3 = registerTrait(TRAITS, new BleedingTrait().setLevel(3));
        REPULSE_2 = registerTrait(TRAITS, new RepulseTrait().setLevel(2));
        REPULSE_3 = registerTrait(TRAITS, new RepulseTrait().setLevel(3));
        CLOAKING = registerTrait(TRAITS, new CloakingTrait());
        REAPER = registerTrait(TRAITS, new ReaperTrait());
        IMMALLEABLE_1 = registerTrait(TRAITS, new ImmalleableTrait().setLevel(1));
        IMMALLEABLE_2 = registerTrait(TRAITS, new ImmalleableTrait().setLevel(2));
        UNCAPPED = registerTrait(TRAITS, new UncappedTrait().setLevel(1));
        MATERIALS = new ArrayList();

        HARDENED_BONE_POLE = ITEMS.register("hardened_bone_pole", () -> new Item(new Item.Properties()));
        LONG_BLADE_FRAGMENT   = ITEMS.register("infectious_long_blade_fragment", () -> new SwordItem(
                Tiers.WOOD, -1, -2.4f, new Item.Properties()));
        LIVING_NUCLEUS  = ITEMS.register("living_nucleus", () -> new Item(new Item.Properties()));
        SERRATED_SPINES  = ITEMS.register("serrated_spines", () -> new Item(new Item.Properties()));
        VILE_PLATE  = ITEMS.register("vile_plate", () -> new Item(new Item.Properties()));
        LIVING_BUCKLER = ITEMS.register("living_buckler", () -> new ShieldItem(new Item.Properties()));
        SENTIENT_BUCKLER = ITEMS.register("sentient_buckler", () -> new ShieldItem(new Item.Properties()));
        LIVING_IMPALER = ITEMS.register("living_impaler", () -> new ChargedShieldItem(new Item.Properties()));
        SENTIENT_IMPALER = ITEMS.register("sentient_impaler", () -> new ChargedShieldItem(new Item.Properties()));
        LIVING_GAUNTLET = ITEMS.register("living_gauntlet", () -> new SwordItem(Tiers.IRON, -1, 1, new Item.Properties()));
        SENTIENT_GAUNTLET = ITEMS.register("sentient_gauntlet", () -> new SwordItem(Tiers.DIAMOND, -1, 1, new Item.Properties()));
        STICKS = Tags.Items.RODS_WOODEN;

        LIVING_CLOAKED = material("living_c", false, CLOAKING, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_VIRAL_1 = material("living_v1", false, VIRULENT_1, HEAVY_1, UNCAPPED);
        LIVING_VIRAL_REACH_1 = material("living_vr1", false, VIRULENT_1, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_VIRAL_2 = material("living_v2", false, VIRULENT_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_REPULSE_2 = material("living_r2", false, REPULSE_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_BLEEDING_1 = material("living_b1", false, BLEEDING_1, HEAVY_1, UNCAPPED);
        LIVING_BLEEDING_REACH_1 = material("living_br1", false, BLEEDING_1, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_BLEEDING_2 = material("living_b2", false, BLEEDING_2, HEAVY_1, UNCAPPED);
        LIVING_BLEEDING_REACH_2 = material("living_br2", false, BLEEDING_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_CORROSION_1 = material("living_c1", false, CORROSION_1, HEAVY_1, UNCAPPED);
        LIVING_CORROSION_REACH_1 = material("living_cr1", false, CORROSION_1, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_CORROSION_2 = material("living_c2", false, CORROSION_2, HEAVY_1, UNCAPPED);
        LIVING_CORROSION_REACH_2 = material("living_cr2", false, CORROSION_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_REAPER = material("living_reaper", false, REAPER, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_IMMALLEABLE = material("living_i", false, IMMALLEABLE_1, HEAVY_1, UNCAPPED);

        SENTIENT_CLOAKED = material("sentient_c", true, CLOAKING, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_VIRAL_2 = material("sentient_v2",  true, VIRULENT_2, HEAVY_2, UNCAPPED);
        SENTIENT_VIRAL_REACH_2 = material("sentient_vr2",  true, VIRULENT_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_VIRAL_3 = material("sentient_v3",  true, VIRULENT_3, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_REPULSE_3 = material("sentient_r3",  true, REPULSE_3, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_BLEEDING_2 = material("sentient_b2",  true, BLEEDING_2, HEAVY_2, UNCAPPED);
        SENTIENT_BLEEDING_REACH_2 = material("sentient_br2",  true, BLEEDING_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_BLEEDING_3 = material("sentient_b3",  true, BLEEDING_3, HEAVY_2, UNCAPPED);
        SENTIENT_BLEEDING_REACH_3 = material("sentient_br3",  true, BLEEDING_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_CORROSION_2 = material("sentient_c2",  true, CORROSION_2, HEAVY_2, UNCAPPED);
        SENTIENT_CORROSION_REACH_2 = material("sentient_cr2",  true, CORROSION_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_CORROSION_3 = material("sentient_c3",  true, CORROSION_3, HEAVY_2, UNCAPPED);
        SENTIENT_CORROSION_REACH_3 = material("sentient_cr3",  true, CORROSION_3, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_REAPER = material("sentient_reaper",  true, REAPER, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_IMMALLEABLE = material("sentient_i",  true, IMMALLEABLE_2, HEAVY_2, UNCAPPED);

        CONFIGS = List.of(
                new WeaponConfig(LIVING_VIRAL_1, SENTIENT_VIRAL_2, WeaponType.GREATSWORD, new RecipeData(new String[]{"VBB","VLB","HVV"})),
                new WeaponConfig(LIVING_CLOAKED, SENTIENT_CLOAKED, WeaponType.DAGGER, new RecipeData(new String[]{" B","VL"," H"})),
                new WeaponConfig(LIVING_BLEEDING_2, SENTIENT_BLEEDING_3, WeaponType.GLAIVE, new RecipeData(new String[]{" B","VL"," H"})),
                new WeaponConfig(LIVING_BLEEDING_2, SENTIENT_BLEEDING_3, WeaponType.PIKE, new RecipeData(new String[]{"VB","LH","VH"})),
                new WeaponConfig(LIVING_REPULSE_2, SENTIENT_REPULSE_3, WeaponType.PARRYING_DAGGER, new RecipeData(new String[]{"VB","VL"," H"})),
                new WeaponConfig(LIVING_VIRAL_2, SENTIENT_VIRAL_3, WeaponType.LONGSWORD, new RecipeData(new String[]{" BB","VLV","HV "})),
                new WeaponConfig(LIVING_VIRAL_REACH_1, SENTIENT_VIRAL_REACH_2, WeaponType.KATANA, new RecipeData(new String[]{" BB","BLV","HV "})),
                new WeaponConfig(LIVING_VIRAL_REACH_1, SENTIENT_VIRAL_REACH_2, WeaponType.THROWING_KNIFE, new RecipeData(new String[]{"HLB"," V "})),
                new WeaponConfig(LIVING_BLEEDING_REACH_2, SENTIENT_BLEEDING_REACH_3, WeaponType.SABER, new RecipeData(new String[]{" VB"," BL","VHV"})),
                new WeaponConfig(LIVING_BLEEDING_REACH_2, SENTIENT_BLEEDING_REACH_3, WeaponType.JAVELIN, new RecipeData(new String[]{"VV ","HLB"})),
                new WeaponConfig(LIVING_BLEEDING_REACH_1, SENTIENT_BLEEDING_REACH_2, WeaponType.RAPIER, new RecipeData(new String[]{" BV","VLB","HV "})),
                new WeaponConfig(LIVING_BLEEDING_1, SENTIENT_BLEEDING_2, WeaponType.LANCE, new RecipeData(new String[]{"  B"," VL","H  "})),
                new WeaponConfig(LIVING_BLEEDING_1, SENTIENT_BLEEDING_2, WeaponType.SPEAR, new RecipeData(new String[]{" B","VL"," H"})),
                new WeaponConfig(LIVING_BLEEDING_1, SENTIENT_BLEEDING_2, WeaponType.LONGBOW, new RecipeData(new String[]{" VG","L G"," VG"})),
                new WeaponConfig(LIVING_BLEEDING_1, SENTIENT_BLEEDING_2, WeaponType.HEAVY_CROSSBOW, new RecipeData(new String[]{"CDV","DH ","V H"})),
                new WeaponConfig(LIVING_CORROSION_1, SENTIENT_CORROSION_2, WeaponType.HALBERD, new RecipeData(new String[]{"VB","BL","VH"})),
                new WeaponConfig(LIVING_CORROSION_REACH_1, SENTIENT_CORROSION_REACH_2, WeaponType.TOMAHAWK, new RecipeData(new String[]{" BV","HLB"})),
                new WeaponConfig(LIVING_CORROSION_REACH_2, SENTIENT_CORROSION_REACH_3, WeaponType.FLANGED_MACE, new RecipeData(new String[]{" BL"," HV","HV "})),
                new WeaponConfig(LIVING_REAPER, SENTIENT_REAPER, WeaponType.SCYTHE, new RecipeData(new String[]{"BBV"," L ","H  "})),
                new WeaponConfig(LIVING_IMMALLEABLE, SENTIENT_IMMALLEABLE, WeaponType.BOOMERANG, new RecipeData(new String[]{"LVH","V  ","H  "})),
                new WeaponConfig(LIVING_IMMALLEABLE, SENTIENT_IMMALLEABLE, WeaponType.QUARTERSTAFF, new RecipeData(new String[]{" VV"," H ","VL "})),
                new WeaponConfig(LIVING_IMMALLEABLE, SENTIENT_IMMALLEABLE, WeaponType.WARHAMMER, new RecipeData(new String[]{"VV ","VLB"," H "})),
                new WeaponConfig(LIVING_IMMALLEABLE, SENTIENT_IMMALLEABLE, WeaponType.BATTLE_HAMMER, new RecipeData(new String[]{"VVV","VLV"," H "})),
                new WeaponConfig(LIVING_IMMALLEABLE, SENTIENT_IMMALLEABLE, WeaponType.BATTLEAXE, new RecipeData(new String[]{"BVB","BLB"," H "}))
                );

        itemRecipes = List.of(Pair.of(HARDENED_BONE_POLE, new RecipeData(new String[]{"SSR", "RSR", "SSR"})),
                Pair.of(LIVING_NUCLEUS, new RecipeData(new String[]{" G ", "RBR", " D "})),
                Pair.of(LONG_BLADE_FRAGMENT, new RecipeData(new String[]{"ROO", "RO ", "RO "})),
                Pair.of(SERRATED_SPINES, new RecipeData(new String[]{" S ", "RSR"})),
                Pair.of(VILE_PLATE, new RecipeData(new String[]{" R ", "EAE", "RER"})),
                Pair.of(LIVING_BUCKLER, new RecipeData(new String[]{"LVV", "VHV", "VV "})),
                Pair.of(LIVING_IMPALER, new RecipeData(new String[]{"LVP", "VHV", "PVV"}))
        );

        REDHOT_SPARTAN_TAB = registerTab(TABS, "srp_spartans", () -> {
            ItemStack stack = LIVING_NUCLEUS.get().asItem().getDefaultInstance();
            return stack.getItem();
        }, (parameters, output) -> {
            ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
        });
    }
}
