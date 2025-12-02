package dot.lighteater.srp_spartans.traits;

import krelox.spartantoolkit.BetterWeaponTrait;

public class RepulseTrait extends BetterWeaponTrait {
    public RepulseTrait() {
        super("repulse", "srp_spartans", TraitQuality.POSITIVE);
        this.setUniversal();
    }

    @Override
    public String getDescription() {
        return "Knocks attacking enemies away and gives the user rage whilst blocking.";
    }
}
