
package dot.lighteater.srp_spartans.mixin;

import com.oblivioussp.spartanweaponry.item.BoltItem;
import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import dot.lighteater.srp_spartans.SRPSpartans;
import dot.lighteater.srp_spartans.item.ModSpartanWeaponry;
import krelox.spartantoolkit.WeaponType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeavyCrossbowItem.class)
public abstract class HeavyCrossbowItemMixin {

    @Shadow
    protected abstract void spawnProjectile(
            ItemStack crossbow,
            BoltItem boltItem,
            ItemStack boltStack,
            Level level,
            Player player,
            boolean creativeOrInfinite,
            float inaccuracyModifier,
            float projectileAngle
    );

    @Redirect(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/oblivioussp/spartanweaponry/item/HeavyCrossbowItem;spawnProjectile(Lnet/minecraft/world/item/ItemStack;Lcom/oblivioussp/spartanweaponry/item/BoltItem;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;ZFF)V",
                    ordinal = 0
            )
    )
    private void srp$spawnCustomProjectiles(
            HeavyCrossbowItem instance,
            ItemStack crossbow,
            BoltItem boltItem,
            ItemStack boltStack,
            Level level,
            Player player,
            boolean creativeOrInfinite,
            float inaccuracyModifier,
            float projectileAngle
    ) {
        Item sentientWeapon = (Item) ModSpartanWeaponry.WEAPONS
                .get(
                        ModSpartanWeaponry.SENTIENT_BLEEDING_2,
                        WeaponType.HEAVY_CROSSBOW
                ).get();

        Item livingWeapon = (Item) ModSpartanWeaponry.WEAPONS
                .get(
                        ModSpartanWeaponry.LIVING_BLEEDING_1,
                        WeaponType.HEAVY_CROSSBOW
                ).get();

        int projectileCount = crossbow.is(sentientWeapon) ? 3
                : crossbow.is(livingWeapon) ? 2
                : 1;

        SRPSpartans.LOGGER.debug(
                "[HeavyCrossbowMixin] Firing {} projectile(s) from {}",
                projectileCount,
                crossbow.getItem()
        );

        HeavyCrossbowItemMixin mixin =
                (HeavyCrossbowItemMixin) (Object) instance;

        if (projectileCount == 2) {
            // Two-projectile spread.
            mixin.spawnProjectile(
                    crossbow, boltItem, boltStack, level, player,
                    creativeOrInfinite, inaccuracyModifier, -5.0F
            );

            mixin.spawnProjectile(
                    crossbow, boltItem, boltStack, level, player,
                    creativeOrInfinite, inaccuracyModifier, 5.0F
            );

        } else {
            // Preserve the original shot.
            mixin.spawnProjectile(
                    crossbow, boltItem, boltStack, level, player,
                    creativeOrInfinite, inaccuracyModifier, projectileAngle
            );

            if (projectileCount == 3) {
                mixin.spawnProjectile(
                        crossbow, boltItem, boltStack, level, player,
                        creativeOrInfinite, inaccuracyModifier, -10.0F
                );

                mixin.spawnProjectile(
                        crossbow, boltItem, boltStack, level, player,
                        creativeOrInfinite, inaccuracyModifier, 10.0F
                );
            }
        }
    }
}
