package dot.lighteater.srp_spartans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TraitEffectData {

    @SerializedName("effects")
    public List<EffectEntry> effects;

    public static class EffectEntry {

        @SerializedName("effect")
        public String effect;

        @SerializedName("base_duration")
        public int baseDuration;

        @SerializedName("duration_per_level")
        public int durationPerLevel;

        @SerializedName("amplifier_per_level")
        public int amplifierPerLevel;

        @SerializedName("show_particles")
        public boolean showParticles;

        @SerializedName("ambient")
        public boolean ambient;

        @SerializedName("show_icon")
        public boolean showIcon;
    }
}