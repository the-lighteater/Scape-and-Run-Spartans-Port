package dot.lighteater.srp_spartans.mixin;

import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.item.BoltItem;
import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dot.lighteater.srp_spartans.item.ModSpartanWeaponry.*;

@Mixin(HeavyCrossbowItem.class)
public abstract class HeavyCrossbowItemMixin {

    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    private void injectSetItemStack(ItemStack stack, Level levelIn, LivingEntity entityLiving, int timeLeft, CallbackInfo ci) {
        if (stack.is(WEAPONS.get(SENTIENT_BLEEDING_2, WeaponType.HEAVY_CROSSBOW).get().asItem())) {
            ItemStack customStack = new ItemStack(ModItems.BOLT.get(), 3); // example
            stack.getOrCreateTag().put("Projectile", customStack.save(new CompoundTag()));
        } else if (stack.is(WEAPONS.get(LIVING_BLEEDING_1, WeaponType.HEAVY_CROSSBOW).get().asItem())) {
            ItemStack customStack = new ItemStack(ModItems.BOLT.get(), 2); // example
            stack.getOrCreateTag().put("Projectile", customStack.save(new CompoundTag()));
        }
    }
}
