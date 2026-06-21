package dot.lighteater.srp_spartans;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class TraitDataLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    public static final Map<ResourceLocation, TraitEffectData> TRAITS = new HashMap<>();

    public TraitDataLoader() {
        super(GSON, "weapon_traits");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profiler) {
        TRAITS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation id = entry.getKey();

            TraitEffectData data = GSON.fromJson(entry.getValue(), TraitEffectData.class);
            TRAITS.put(id, data);
        }
    }

    public static TraitEffectData get(ResourceLocation id) {
        return TRAITS.get(id);
    }
}