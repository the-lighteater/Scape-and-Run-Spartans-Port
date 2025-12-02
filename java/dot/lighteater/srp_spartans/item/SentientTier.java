package dot.lighteater.srp_spartans.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class SentientTier implements Tier {

    // Add THIS so you can reference LivingTier.INSTANCE
    public static final SentientTier INSTANCE = new SentientTier();

    @Override
    public int getUses() {
        return 2050; // durability
    }

    @Override
    public float getSpeed() {
        return 9.0F; // mining speed
    }

    @Override
    public float getAttackDamageBonus() {
        return 35.0F; // added attack damage to weapon base
    }

    @Override
    public int getLevel() {
        return 6; // iron=2, diamond=3
    }

    @Override
    public int getEnchantmentValue() {
        return 18; // enchantability
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModSpartanWeaponry.LIVING_NUCLEUS.get());
    }
}