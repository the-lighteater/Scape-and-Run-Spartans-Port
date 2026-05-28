package dot.lighteater.srp_spartans.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class SpartanTier implements Tier {

    private int durability;
    private float speed;
    private float attackDMG;
    private int level;
    private int enchanting;
    private Supplier<Ingredient> repair;

    public SpartanTier(int durability, float speed, float attackDMG, int level, int enchanting, Supplier<Ingredient> repair) {
        this.durability = durability;
        this.speed = speed;
        this.attackDMG = attackDMG;
        this.level = level;
        this.enchanting = enchanting;
        this.repair = repair;
    }

    @Override
    public int getUses() {
        return durability;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDMG;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchanting;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return (Ingredient) repair;
    }
}
