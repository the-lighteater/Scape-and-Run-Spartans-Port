package dot.lighteater.srp_spartans.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CorrosionEffect extends MobEffect {
    private static final Logger LOGGER = LogManager.getLogger("Corrosion Effect");


    public static final int TICK_INTERVAL = 20;
    public static final int DURABILITY_PER_TICK = 3;
    public static final double CHANCE_PER_ITEM = 1.0;

    public CorrosionEffect() {
        super(MobEffectCategory.HARMFUL, 0x556B2F);
    }

    @Override
    public Component getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) return;

        int durationLeft = entity.getEffect(this).getDuration();
        if (durationLeft % TICK_INTERVAL != 0) return;

        int extraDurability = DURABILITY_PER_TICK * amplifier;
        LOGGER.info(extraDurability);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.getType().equals(EquipmentSlot.Type.ARMOR)) continue;
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            if (Math.random() <= (CHANCE_PER_ITEM / ( 1 + stack.getEnchantmentLevel(Enchantments.UNBREAKING)))) {
                if (!stack.isEmpty() && stack.isDamageableItem()) {
                    int unbreaking = stack.getEnchantmentLevel(Enchantments.UNBREAKING);
                    extraDurability = extraDurability - (unbreaking - 1);
                    stack.hurt(extraDurability, entity.getRandom(), null);

                    if (stack.getDamageValue() >= stack.getMaxDamage()) {
                        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1f, 1f);

                        stack.setCount(0);
                    }
                }
            }
        }

        ItemStack main = entity.getMainHandItem();
        if (Math.random() <= (CHANCE_PER_ITEM / ( 1 + main.getEnchantmentLevel(Enchantments.UNBREAKING)))) {
            if (!main.isEmpty() && main.isDamageableItem()) {
                int unbreaking = main.getEnchantmentLevel(Enchantments.UNBREAKING);
                extraDurability = extraDurability - (unbreaking - 1);
                main.hurt(extraDurability, entity.getRandom(), null);

                if (main.getDamageValue() >= main.getMaxDamage()) {
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1f, 1f);
                    main.setCount(0);
                }
            }
        }

        ItemStack off = entity.getOffhandItem();
        if (Math.random() <= (CHANCE_PER_ITEM / ( 1 + off.getEnchantmentLevel(Enchantments.UNBREAKING)))) {
            if (!off.isEmpty() && off.isDamageableItem()) {
                int unbreaking = off.getEnchantmentLevel(Enchantments.UNBREAKING);
                extraDurability = extraDurability - (unbreaking - 1);
                off.hurt(extraDurability, entity.getRandom(), null);

                if (off.getDamageValue() >= off.getMaxDamage()) {
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1f, 1f);
                    off.setCount(0);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return (duration % TICK_INTERVAL) == 0;
    }
}
