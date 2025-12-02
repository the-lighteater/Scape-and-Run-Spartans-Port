package dot.lighteater.srp_spartans.traits;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import krelox.spartantoolkit.BetterWeaponTrait;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

// Code for this was inspired by the Gob's Armory mod, link in mod description

public class ReaperTrait extends BetterWeaponTrait {

    private static final int DEVASTATING_HIT_INTERVAL = 8;
    private static final double BASE_AOE_RADIUS = 2.0;
    private static final double DEVASTATING_AOE_RADIUS = 8.0;
    private static final float ENCHANT_SHARPNESS_MULTIPLIER = 0.5f;


    public ReaperTrait() {
        super("reaper", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Deals area damage and triggers devastating hits";
    }

    @Override
    public void onHitEntity(WeaponMaterial material, ItemStack stack, LivingEntity target, LivingEntity attacker, Entity projectile) {
        if (!(attacker instanceof Player player)) return;
        Level level = attacker.level();
        if (level.isClientSide) return;

        int hitCount = incrementHitCount(stack);
        boolean devastatingHit = hitCount % DEVASTATING_HIT_INTERVAL == 0;
        if (devastatingHit) resetHitCount(stack);

        float totalDamage = calculateTotalDamage(attacker, stack);
        double radius = devastatingHit ? DEVASTATING_AOE_RADIUS : BASE_AOE_RADIUS;
        float areaDamage = totalDamage / 2;

        AABB area = createAoE(target, radius);
        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, area).stream()
                .filter(entity -> entity != target && entity != attacker)
                .filter(entity -> !(entity instanceof TamableAnimal tame && tame.isTame()))
                .filter(entity -> {
                    Vec3 start = player.getEyePosition();
                    Vec3 end = entity.getEyePosition();
                    ClipContext context = new ClipContext(
                            start,
                            end,
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.NONE,
                            player
                    );
                    HitResult result = level.clip(context);
                    return result.getType() == HitResult.Type.MISS;
                })
                .toList();

        for (LivingEntity entity : nearbyEntities) {
            entity.hurt(attacker.damageSources().mobAttack(attacker), areaDamage);
        }
    }

    private AABB createAoE(LivingEntity target, double radius) {
        return new AABB(
                target.getX() - radius, target.getY() - radius, target.getZ() - radius,
                target.getX() + radius, target.getY() + radius, target.getZ() + radius
        );
    }


    private float calculateTotalDamage(LivingEntity attacker, ItemStack stack) {
        float base = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
        int sharpness = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
        return base + sharpness * ENCHANT_SHARPNESS_MULTIPLIER;
    }

    private int incrementHitCount(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int count = tag.getInt("HitCount") + 1;
        tag.putInt("HitCount", count);
        return count;
    }

    private void resetHitCount(ItemStack stack) {
        stack.getOrCreateTag().putInt("HitCount", 0);
    }



}
