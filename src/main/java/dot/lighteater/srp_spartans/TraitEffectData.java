package dot.lighteater.srp_spartans;

import com.google.gson.annotations.SerializedName;

public class TraitEffectData {

    @SerializedName("effect")
    public String effect; // <-- STRING, not ResourceLocation

    @SerializedName("base_duration")
    public int baseDuration;

    @SerializedName("duration_per_level")
    public int durationPerLevel;

    @SerializedName("amplifier_per_level")
    public int amplifierPerLevel;

    @SerializedName("show_particles")
    public boolean showParticles;

    public boolean ambient;

    @SerializedName("show_icon")
    public boolean showIcon;
}