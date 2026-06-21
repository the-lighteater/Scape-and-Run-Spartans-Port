package dot.lighteater.srp_spartans;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class WeaponAttributeLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    public static final Map<ResourceLocation, WeaponAttributeData> ATTRIBUTES = new HashMap<>();

    public WeaponAttributeLoader() {super(GSON, "weapon_attributes_json"); }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        ATTRIBUTES.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation id = entry.getKey();

            WeaponAttributeData data = GSON.fromJson(entry.getValue(), WeaponAttributeData.class);
            ATTRIBUTES.put(id, data);
        }
    }

    public static WeaponAttributeData get(ResourceLocation id) {return ATTRIBUTES.get(id);}
}
