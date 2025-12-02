package dot.lighteater.srp_spartans.traits;

import krelox.spartantoolkit.BetterWeaponTrait;

public class CloakingTrait extends BetterWeaponTrait {

    public CloakingTrait() {
        super("cloaking", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Grants invisibility while crouching";
    }
}
