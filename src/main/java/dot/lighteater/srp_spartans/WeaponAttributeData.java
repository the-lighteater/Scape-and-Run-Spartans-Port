package dot.lighteater.srp_spartans;

import com.google.gson.annotations.SerializedName;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;

public class WeaponAttributeData {

    @SerializedName("attributes")
    public List<AttributeEntry> attributes;

    public static class AttributeEntry {
        @SerializedName("attribute")
        public String attribute;

        @SerializedName("modifier")
        public double modifier;

        @SerializedName("operation")
        public String operation;

        @SerializedName("uuid")
        public String uuid;

        public AttributeModifier.Operation getOperation() {
            return switch (operation.toLowerCase()) {
                case "addition" -> AttributeModifier.Operation.ADDITION;
                case "multiply_base" -> AttributeModifier.Operation.MULTIPLY_BASE;
                case "multiply_total" -> AttributeModifier.Operation.MULTIPLY_TOTAL;
                default -> AttributeModifier.Operation.ADDITION;
            };
        }
    }
}

