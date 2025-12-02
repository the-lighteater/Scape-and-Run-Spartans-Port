package dot.lighteater.srp_spartans.event;

import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.effect.ModEffects;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mod.EventBusSubscriber(modid = SRPSpartans.MODID)
public class RepulseEvent {
    private static Set<ResourceLocation> validShieldIds;

    private static Set<ResourceLocation> getValidShieldIds() {
        if (validShieldIds == null) {
            validShieldIds = Set.of(
                    SENTIENT_BUCKLER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location(),
                    SENTIENT_IMPALER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location(),
                    LIVING_BUCKLER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location(),
                    LIVING_IMPALER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location()
            );
        }
        return validShieldIds;
    }

    private static Set<ResourceLocation> validImpalerShieldIds;

    private static Set<ResourceLocation> getValidImpalerShieldIds() {
        if (validImpalerShieldIds == null) {
            validImpalerShieldIds = Set.of(
                    SENTIENT_IMPALER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location(),
                    LIVING_IMPALER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location()
            );
        }
        return validImpalerShieldIds;
    }

    private static Set<ResourceLocation> validBucklerShieldIds;

    private static Set<ResourceLocation> getValidBucklerShieldIds() {
        if (validBucklerShieldIds == null) {
            validBucklerShieldIds = Set.of(
                    SENTIENT_BUCKLER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location(),
                    LIVING_BUCKLER.get().getDefaultInstance().getItem().builtInRegistryHolder().key().location()
            );
        }
        return validBucklerShieldIds;
    }

    private static Set<ResourceLocation> validDaggerIds;

    private static Set<ResourceLocation> getValidDaggerIds() {
        if (validDaggerIds == null) {
            validDaggerIds = Set.of(
                    WEAPONS.get(LIVING_REPULSE_2, WeaponType.PARRYING_DAGGER).get().asItem().builtInRegistryHolder().key().location(),
                    WEAPONS.get(SENTIENT_REPULSE_3, WeaponType.PARRYING_DAGGER).get().asItem().builtInRegistryHolder().key().location()
            );
        }
        return validDaggerIds;
    }

    @SubscribeEvent
    public static void onPlayerGuard(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Entity attackerEntity = event.getSource().getEntity();
        if (!(attackerEntity instanceof LivingEntity attacker)) return;

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.isEmpty()) return;

        if (!(player.isUsingItem() && player.getUseItem() == mainHand)) return;

        ResourceLocation id = mainHand.getItem().builtInRegistryHolder().key().location();

        boolean isValidDagger = (!getValidDaggerIds().contains(id));
        boolean isValidShield = (!getValidBucklerShieldIds().contains(id));

        if (!isValidDagger && !isValidShield) return;

        ResourceLocation itemId = mainHand.getItem().builtInRegistryHolder().key().location();
        if (!itemId.getNamespace().equals("srp_spartans")) return;

        if (!player.level().isClientSide) {
            player.addEffect(new MobEffectInstance(
                    ModEffects.RAGE.get(),
                    200,
                    0
            ));
        }

        double dx = attacker.getX() - player.getX();
        double dz = attacker.getZ() - player.getZ();
        attacker.knockback(1.0D, -dx, -dz);

        Level level = player.level();
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);

        if (level instanceof ServerLevel server) {
            server.sendParticles(
                    ParticleTypes.CRIT,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    10,
                    0.3, 0.3, 0.3,
                    0.1
            );
        }

        if (isValidShield) {
            CompoundTag tag = mainHand.getOrCreateTag();

            int blocked = tag.getInt("BlockedDamage");
            blocked += event.getAmount();

            tag.putInt("BlockedDamage", blocked);
        }
    }


    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.isEmpty()) return;

        ResourceLocation id = stack.getItem().builtInRegistryHolder().key().location();

        if (!getValidShieldIds().contains(id)) return;

        List<Component> tooltip = event.getToolTip();

        tooltip.add(Component.empty());

        tooltip.add(Component.literal("§6Material Traits:"));

        if (getValidBucklerShieldIds().contains(id)) tooltip.add(Component.literal("§bRepulse"));

        if (getValidImpalerShieldIds().contains(id)) tooltip.add(Component.literal("§bCharger"));
    }
}
