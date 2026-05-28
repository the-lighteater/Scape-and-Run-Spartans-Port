package dot.lighteater.srp_spartans.custom;

import krelox.spartantoolkit.SpartanMaterial;
import krelox.spartantoolkit.WeaponType;

public class WeaponConfig {
    public final SpartanMaterial livingMaterial;
    public final SpartanMaterial sentientMaterial;
    public final WeaponType type;
    public final RecipeData recipe;

    public WeaponConfig(SpartanMaterial livingMaterial,
                        SpartanMaterial sentientMaterial,
                        WeaponType type,
                        RecipeData recipe) {
        this.livingMaterial = livingMaterial;
        this.sentientMaterial = sentientMaterial;
        this.type = type;
        this.recipe = recipe;
    }
}
