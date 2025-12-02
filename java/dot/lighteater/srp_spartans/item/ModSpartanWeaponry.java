package dot.lighteater.srp_spartans.item;

import com.mojang.datafixers.util.Either;
import com.oblivioussp.spartanweaponry.api.data.model.ModelGenerator;
import com.oblivioussp.spartanweaponry.api.trait.ReachWeaponTrait;
import com.oblivioussp.spartanweaponry.api.trait.SpeedModifierWeaponTrait;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;
import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.traits.*;
import it.unimi.dsi.fastutil.Pair;
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
import net.minecraft.world.level.ItemLike;
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

import java.util.function.Consumer;
import java.util.function.Function;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class ModSpartanWeaponry extends SpartanAddon {
    private static final Logger LOGGER = LogManager.getLogger("RedHotKarmaAddon");


    public static WeaponMap WEAPONS = new WeaponMap();
    public static DeferredRegister<Item> ITEMS = itemRegister("srp_spartans");
    public static final DeferredRegister<WeaponTrait> TRAITS = traitRegister("srp_spartans");
    public static final DeferredRegister<CreativeModeTab> TABS = tabRegister("srp_spartans");

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
    public static final SpartanMaterial LIVING;
    public static final SpartanMaterial SENTIENT;
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

    @SafeVarargs
    private static SpartanMaterial material(String name, Tier tier, String tagPath, RegistryObject<WeaponTrait>... traits) {
        SpartanMaterial material = new SpartanMaterial(name, "srp_spartans", tier, ItemTags.create(new ResourceLocation(tagPath)), traits) {
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

        for (SpartanMaterial material : this.MATERIALS) {
            if (this.getBlacklist().contains(Pair.of(material, null))) continue;

            if (material == LIVING) {
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.GREATSWORD))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_VIRAL_1, WeaponType.GREATSWORD);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.DAGGER))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_CLOAKED, WeaponType.DAGGER);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.GLAIVE)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.PIKE)))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_2, WeaponType.GLAIVE);
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_2, WeaponType.PIKE);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.PARRYING_DAGGER))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_REPULSE_2, WeaponType.PARRYING_DAGGER);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.LONGSWORD))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_VIRAL_2, WeaponType.LONGSWORD);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.KATANA)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.THROWING_KNIFE)))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_VIRAL_REACH_1, WeaponType.KATANA);
                    this.registerSpartanWeapon(ITEMS, LIVING_VIRAL_REACH_1, WeaponType.THROWING_KNIFE);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.SABER)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.JAVELIN)))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_REACH_2, WeaponType.SABER);
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_REACH_2, WeaponType.JAVELIN);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.RAPIER))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_REACH_1, WeaponType.RAPIER);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.LANCE)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.SPEAR)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.LONGBOW)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.HEAVY_CROSSBOW)))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_1, WeaponType.LANCE);
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_1, WeaponType.SPEAR);
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_1, WeaponType.LONGBOW);
                    this.registerSpartanWeapon(ITEMS, LIVING_BLEEDING_1, WeaponType.HEAVY_CROSSBOW);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.HALBERD))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_CORROSION_1, WeaponType.HALBERD);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.TOMAHAWK))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_CORROSION_REACH_1, WeaponType.TOMAHAWK);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.FLANGED_MACE))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_CORROSION_REACH_2, WeaponType.FLANGED_MACE);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.SCYTHE))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_REAPER, WeaponType.SCYTHE);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.BOOMERANG)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.QUARTERSTAFF)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.WARHAMMER)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.BATTLE_HAMMER)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.BATTLEAXE)))) {
                    this.registerSpartanWeapon(ITEMS, LIVING_IMMALLEABLE, WeaponType.BOOMERANG);
                    this.registerSpartanWeapon(ITEMS, LIVING_IMMALLEABLE, WeaponType.QUARTERSTAFF);
                    this.registerSpartanWeapon(ITEMS, LIVING_IMMALLEABLE, WeaponType.WARHAMMER);
                    this.registerSpartanWeapon(ITEMS, LIVING_IMMALLEABLE, WeaponType.BATTLE_HAMMER);
                    this.registerSpartanWeapon(ITEMS, LIVING_IMMALLEABLE, WeaponType.BATTLEAXE);
                }
            } else if (material == SENTIENT) {
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.GREATSWORD))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_VIRAL_2, WeaponType.GREATSWORD);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.DAGGER))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_CLOAKED, WeaponType.DAGGER);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.GLAIVE)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.PIKE)))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_3, WeaponType.GLAIVE);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_3, WeaponType.PIKE);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.PARRYING_DAGGER))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_REPULSE_3, WeaponType.PARRYING_DAGGER);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.LONGSWORD))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_VIRAL_3, WeaponType.LONGSWORD);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.KATANA)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.THROWING_KNIFE)))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_VIRAL_REACH_2, WeaponType.KATANA);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_VIRAL_REACH_2, WeaponType.THROWING_KNIFE);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.SABER)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.JAVELIN)))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_REACH_3, WeaponType.SABER);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_REACH_3, WeaponType.JAVELIN);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.RAPIER))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_REACH_2, WeaponType.RAPIER);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.LANCE)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.SPEAR)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.LONGBOW)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.HEAVY_CROSSBOW)))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_2, WeaponType.LANCE);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_2, WeaponType.SPEAR);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_2, WeaponType.LONGBOW);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_BLEEDING_2, WeaponType.HEAVY_CROSSBOW);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.HALBERD))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_CORROSION_2, WeaponType.HALBERD);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.TOMAHAWK))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_CORROSION_REACH_2, WeaponType.TOMAHAWK);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.FLANGED_MACE))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_CORROSION_REACH_3, WeaponType.FLANGED_MACE);
                }
                if (!this.getBlacklist().contains(Pair.of(material, WeaponType.SCYTHE))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_REAPER, WeaponType.SCYTHE);
                }
                if ((!this.getBlacklist().contains(Pair.of(material, WeaponType.BOOMERANG)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.QUARTERSTAFF)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.WARHAMMER)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.BATTLE_HAMMER)))
                        && (!this.getBlacklist().contains(Pair.of(material, WeaponType.BATTLEAXE)))) {
                    this.registerSpartanWeapon(ITEMS, SENTIENT_IMMALLEABLE, WeaponType.BOOMERANG);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_IMMALLEABLE, WeaponType.QUARTERSTAFF);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_IMMALLEABLE, WeaponType.WARHAMMER);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_IMMALLEABLE, WeaponType.BATTLE_HAMMER);
                    this.registerSpartanWeapon(ITEMS, SENTIENT_IMMALLEABLE, WeaponType.BATTLEAXE);
                }
            }
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
        Item item = event.getItemStack().getItem();
        if (item instanceof WeaponItem weapon) {
            if (weapon.getMaterial().equals(LIVING)) {
                event.getTooltipElements().add(1,
                        Either.left(Component.literal("Living Weapon").withStyle(ChatFormatting.GOLD)));
            }
        }
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
            SpartanMaterial material = key.first();
            WeaponType type = key.second();
            if (material.equals(LIVING_VIRAL_1)) {
                if (type == WeaponType.GREATSWORD) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('N', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('O', LONG_BLADE_FRAGMENT.get())
                            .pattern("VOO")
                            .pattern("VNO")
                            .pattern("HVV")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_CLOAKED)) {
                if (type == WeaponType.DAGGER) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('N', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .pattern(" B")
                            .pattern("VN")
                            .pattern(" H")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_BLEEDING_2)) {
                if (type == WeaponType.GLAIVE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('N', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .pattern(" B")
                            .pattern("VN")
                            .pattern(" H")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.PIKE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('P', HARDENED_BONE_POLE.get())
                            .pattern("VB")
                            .pattern("LP")
                            .pattern("VP")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_REPULSE_2)) {
                if (type == WeaponType.PARRYING_DAGGER) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .pattern("VB")
                            .pattern("VL")
                            .pattern(" H")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_VIRAL_2)) {
                if (type == WeaponType.LONGSWORD) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('O', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern(" OB")
                            .pattern("VLV")
                            .pattern("HV ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_VIRAL_REACH_1)) {
                if (type == WeaponType.KATANA) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('O', LONG_BLADE_FRAGMENT.get())
                            .pattern(" OO")
                            .pattern("OLV")
                            .pattern("HV ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.THROWING_KNIFE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern("HLB")
                            .pattern(" V ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_BLEEDING_REACH_2)) {
                if (type == WeaponType.SABER) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern(" VB")
                            .pattern(" BL")
                            .pattern("VHV")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.JAVELIN) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('P', HARDENED_BONE_POLE.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .pattern("VV ")
                            .pattern("PLB")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_BLEEDING_REACH_1)) {
                if (type == WeaponType.RAPIER) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern(" BV")
                            .pattern("VLB")
                            .pattern("HV ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_BLEEDING_1)) {
                if (type == WeaponType.LANCE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('O', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern("  O")
                            .pattern(" VL")
                            .pattern("H  ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.SPEAR) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('P', HARDENED_BONE_POLE.get())
                            .pattern(" B")
                            .pattern("VL")
                            .pattern(" P")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.LONGBOW) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('S', Items.STRING)
                            .pattern(" VS")
                            .pattern("L S")
                            .pattern(" VS")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.HEAVY_CROSSBOW) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', Items.LEAD)
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('B', Items.CROSSBOW)
                            .pattern("BLV")
                            .pattern("LH ")
                            .pattern("V H")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_CORROSION_1)) {
                if (type == WeaponType.HALBERD) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('N', LIVING_NUCLEUS.get())
                            .define('P', HARDENED_BONE_POLE.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .pattern("VB")
                            .pattern("BN")
                            .pattern("VP")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_CORROSION_REACH_1)) {
                if (type == WeaponType.TOMAHAWK) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern(" BV")
                            .pattern("HLB")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_REAPER)) {
                if (type == WeaponType.SCYTHE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('P', HARDENED_BONE_POLE.get())
                            .pattern("BBV")
                            .pattern(" L ")
                            .pattern("P  ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_IMMALLEABLE)) {
                if (type == WeaponType.BOOMERANG) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern("LVH")
                            .pattern("V  ")
                            .pattern("H  ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.QUARTERSTAFF) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('P', HARDENED_BONE_POLE.get())
                            .pattern(" VV")
                            .pattern(" P ")
                            .pattern("VL ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.WARHAMMER) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern("VV ")
                            .pattern("VLB")
                            .pattern(" H ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.BATTLE_HAMMER) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern("VVV")
                            .pattern("VLV")
                            .pattern(" H ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                } else if (type == WeaponType.BATTLEAXE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .pattern("BVB")
                            .pattern("BLB")
                            .pattern(" H ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            } else if (material.equals(LIVING_CORROSION_REACH_2)) {
                if (type == WeaponType.FLANGED_MACE) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item.get())
                            .define('V', VILE_PLATE.get())
                            .define('L', LIVING_NUCLEUS.get())
                            .define('H', HARDENED_BONE_POLE.get())
                            .define('B', LONG_BLADE_FRAGMENT.get())
                            .pattern(" BL")
                            .pattern(" HV")
                            .pattern("HV ")
                            .unlockedBy("has_bone_pole", has(VILE_PLATE.get()))
                            .save(consumer, ForgeRegistries.ITEMS.getKey(item.get()).withSuffix("_manual"));
                }
            }
        });

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, HARDENED_BONE_POLE.get())
                .define('S', Items.SPIDER_EYE)
                .define('R', Items.ROTTEN_FLESH)
                .pattern("SSR")
                .pattern("RSR")
                .pattern("SSR")
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, LIVING_NUCLEUS.get())
                .define('L', Items.SLIME_BALL)
                .define('R', Items.ROTTEN_FLESH)
                .define('D', Items.GLOWSTONE_DUST)
                .define('G', Items.GUNPOWDER)
                .pattern(" G ")
                .pattern("RLR")
                .pattern(" D ")
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, LONG_BLADE_FRAGMENT.get())
                .define('R', Items.ROTTEN_FLESH)
                .define('B', Items.BONE)
                .pattern("RBB")
                .pattern("RB ")
                .pattern("RB ")
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SERRATED_SPINES.get())
                .define('R', Items.ROTTEN_FLESH)
                .define('S', Items.SPIDER_EYE)
                .pattern(" S ")
                .pattern("RSR")
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VILE_PLATE.get())
                .define('R', Items.ROTTEN_FLESH)
                .define('E', Items.ENDER_PEARL)
                .define('A', Items.ARROW)
                .pattern(" R ")
                .pattern("EAE")
                .pattern("RER")
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, LIVING_BUCKLER.get())
                .define('P', VILE_PLATE.get())
                .define('S', VILE_PLATE.get())
                .define('H', HARDENED_BONE_POLE.get())
                .define('L', LIVING_NUCLEUS.get())
                .pattern("LSP")
                .pattern("SHS")
                .pattern("PS ")
                .unlockedBy("has_rotten_flesh", has(VILE_PLATE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, LIVING_IMPALER.get())
                .define('P', VILE_PLATE.get())
                .define('S', VILE_PLATE.get())
                .define('H', HARDENED_BONE_POLE.get())
                .define('L', LIVING_NUCLEUS.get())
                .define('E', SERRATED_SPINES.get())
                .pattern("LSE")
                .pattern("SHS")
                .pattern("ESP")
                .unlockedBy("has_rotten_flesh", has(VILE_PLATE.get()))
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
        LIVING = material("living", Tiers.IRON, "srp_spartans:living");
        SENTIENT = material("sentient", Tiers.IRON, "srp_spartans:sentient");

        LIVING_CLOAKED = material("living_c", LivingTier.INSTANCE, "srp_spartans:living_c", CLOAKING, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_VIRAL_1 = material("living_v1", LivingTier.INSTANCE, "srp_spartans:living_v1", VIRULENT_1, HEAVY_1, UNCAPPED);
        LIVING_VIRAL_REACH_1 = material("living_vr1", LivingTier.INSTANCE, "srp_spartans:living_vr1", VIRULENT_1, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_VIRAL_2 = material("living_v2", LivingTier.INSTANCE, "srp_spartans:living_v2", VIRULENT_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_REPULSE_2 = material("living_r2", LivingTier.INSTANCE, "srp_spartans:living_r2", REPULSE_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_BLEEDING_1 = material("living_b1", LivingTier.INSTANCE, "srp_spartans:living_b1", BLEEDING_1, HEAVY_1, UNCAPPED);
        LIVING_BLEEDING_REACH_1 = material("living_br1", LivingTier.INSTANCE, "srp_spartans:living_br1", BLEEDING_1, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_BLEEDING_2 = material("living_b2", LivingTier.INSTANCE, "srp_spartans:living_b2", BLEEDING_2, HEAVY_1, UNCAPPED);
        LIVING_BLEEDING_REACH_2 = material("living_br2", LivingTier.INSTANCE, "srp_spartans:living_br2", BLEEDING_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_CORROSION_1 = material("living_c1", LivingTier.INSTANCE, "srp_spartans:living_c1", CORROSION_1, HEAVY_1, UNCAPPED);
        LIVING_CORROSION_REACH_1 = material("living_cr1", LivingTier.INSTANCE, "srp_spartans:living_cr1", CORROSION_1, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_CORROSION_2 = material("living_c2", LivingTier.INSTANCE, "srp_spartans:living_c2", CORROSION_2, HEAVY_1, UNCAPPED);
        LIVING_CORROSION_REACH_2 = material("living_cr2", LivingTier.INSTANCE, "srp_spartans:living_cr2", CORROSION_2, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_REAPER = material("living_reaper", LivingTier.INSTANCE, "srp_spartans:living_reaper", REAPER, HEAVY_1, REACH_1, UNCAPPED);
        LIVING_IMMALLEABLE = material("living_i", LivingTier.INSTANCE, "srp_spartans:living_i", IMMALLEABLE_1, HEAVY_1, UNCAPPED);

        SENTIENT_CLOAKED = material("sentient_c", SentientTier.INSTANCE, "srp_spartans:sentient_c", CLOAKING, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_VIRAL_2 = material("sentient_v2", SentientTier.INSTANCE, "srp_spartans:sentient_v2", VIRULENT_2, HEAVY_2, UNCAPPED);
        SENTIENT_VIRAL_REACH_2 = material("sentient_vr2", SentientTier.INSTANCE, "srp_spartans:sentient_vr2", VIRULENT_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_VIRAL_3 = material("sentient_v3", SentientTier.INSTANCE, "srp_spartans:sentient_v3", VIRULENT_3, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_REPULSE_3 = material("sentient_r3", SentientTier.INSTANCE, "srp_spartans:sentient_r3", REPULSE_3, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_BLEEDING_2 = material("sentient_b2", SentientTier.INSTANCE, "srp_spartans:sentient_b2", BLEEDING_2, HEAVY_2, UNCAPPED);
        SENTIENT_BLEEDING_REACH_2 = material("sentient_br2", SentientTier.INSTANCE, "srp_spartans:sentient_br2", BLEEDING_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_BLEEDING_3 = material("sentient_b3", SentientTier.INSTANCE, "srp_spartans:sentient_b3", BLEEDING_3, HEAVY_2, UNCAPPED);
        SENTIENT_BLEEDING_REACH_3 = material("sentient_br3", SentientTier.INSTANCE, "srp_spartans:sentient_br3", BLEEDING_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_CORROSION_2 = material("sentient_c2", SentientTier.INSTANCE, "srp_spartans:sentient_c2", CORROSION_2, HEAVY_2, UNCAPPED);
        SENTIENT_CORROSION_REACH_2 = material("sentient_cr2", SentientTier.INSTANCE, "srp_spartans:sentient_cr2", CORROSION_2, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_CORROSION_3 = material("sentient_c3", SentientTier.INSTANCE, "srp_spartans:sentient_c3", CORROSION_3, HEAVY_2, UNCAPPED);
        SENTIENT_CORROSION_REACH_3 = material("sentient_cr3", SentientTier.INSTANCE, "srp_spartans:sentient_cr3", CORROSION_3, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_REAPER = material("sentient_reaper", SentientTier.INSTANCE, "srp_spartans:sentient_reaper", REAPER, HEAVY_2, REACH_1, UNCAPPED);
        SENTIENT_IMMALLEABLE = material("sentient_i", SentientTier.INSTANCE, "srp_spartans:sentient_i", IMMALLEABLE_2, HEAVY_2, UNCAPPED);

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

        REDHOT_SPARTAN_TAB = registerTab(TABS, "srp_spartans", () -> {
            ItemStack stack = LIVING_NUCLEUS.get().asItem().getDefaultInstance();
            return stack.getItem();
        }, (parameters, output) -> {
            ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
        });
    }
}
