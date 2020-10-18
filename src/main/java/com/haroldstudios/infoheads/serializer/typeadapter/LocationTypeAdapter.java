package com.haroldstudios.infoheads.serializer.typeadapter;

import com.google.gson.*;
import com.haroldstudios.infoheads.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            object.add("x", new JsonPrimitive(location.getX()));
            object.add("y", new JsonPrimitive(location.getY()));
            object.add("z", new JsonPrimitive(location.getZ()));
            object.add("world", new JsonPrimitive(location.getWorld() == null ? "world" : location.getWorld().getName()));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            InfoHeads.getInstance().getLogger().log(Level.WARNING, "Error encountered while serializing a Location. Is it a valid world?");
            return object;
        }
    }


    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {

            return new Location(Bukkit.getWorld(object.get("world").getAsString()),
                    object.get("x").getAsDouble(),
                    object.get("y").getAsDouble(),
                    object.get("z").getAsDouble());
        } catch (Exception ex) {
            ex.printStackTrace();
            InfoHeads.getInstance().getLogger().log(Level.WARNING, "Error encountered while" +
                    " deserializing a Location.");
            return null;
        }


    }
}
