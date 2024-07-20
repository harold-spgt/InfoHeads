package me.harry0198.infoheads.legacy.typeadapter;

import com.google.gson.*;
import me.harry0198.infoheads.legacy.PackageNameMapper;

import java.lang.reflect.Type;

public class AbstractTypeAdapter<T>
        implements JsonDeserializer<T> {

    @Override
    public final T deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) elem;
        final JsonElement typeString = get(member, "type");
        final JsonElement data = get(member, "data");

        String typeStr = PackageNameMapper.getNewType(typeString.getAsString());
        final Type actualType = typeForName(typeStr);

        return context.deserialize(data, actualType);
    }

    private Type typeForName(final String typeString) {
        try {
            return Class.forName(typeString);
        }
        catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, final String memberName) {
        final JsonElement elem = wrapper.get(memberName);

        if (elem == null) {
            throw new JsonParseException(
                    "no '" + memberName + "' member found in json file.");
        }
        return elem;
    }

}