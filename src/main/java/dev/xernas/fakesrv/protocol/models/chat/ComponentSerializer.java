package dev.xernas.fakesrv.protocol.models.chat;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class ComponentSerializer {

    public static JsonElement serialize(Component component) {
        if (component instanceof TextComponent && !component.hasFormatting()) {
            return new JsonPrimitive(((TextComponent) component).getText());
        }
        JsonObject jsonObject = new JsonObject();
        if (component instanceof TextComponent) {
            jsonObject.addProperty("text", ((TextComponent) component).getText());
        }

        if (component.bold != null) {
            jsonObject.addProperty("bold", component.bold);
        }
        if (component.italic != null) {
            jsonObject.addProperty("italic", component.italic);
        }
        if (component.underlined != null) {
            jsonObject.addProperty("underlined", component.underlined);
        }
        if (component.strikethrough != null) {
            jsonObject.addProperty("strikethrough", component.strikethrough);
        }
        if (component.obfuscated != null) {
            jsonObject.addProperty("obfuscated", component.obfuscated);
        }
        if (component.color != null) {
            jsonObject.addProperty("color", component.color.toString().toLowerCase());
        }
        if (component.extra != null) {
            JsonArray array = new JsonArray();
            for (Component c : component.extra) {
                array.add(serialize(c));
            }
            jsonObject.add("extra", array);
        }
        return jsonObject;
    }

    public static Component deserialize(JsonObject object) {
        Component component;
        if (object.has("text")) {
            component = new TextComponent(object.get("text").getAsString());
        } else {
            throw new RuntimeException("Unhandled component");
        }

        if (object.has("bold")) {
            component.bold = object.get("bold").getAsBoolean();
        }
        if (object.has("italic")) {
            component.italic = object.get("italic").getAsBoolean();
        }
        if (object.has("underlined")) {
            component.underlined = object.get("underlined").getAsBoolean();
        }
        if (object.has("strikethrough")) {
            component.strikethrough = object.get("strikethrough").getAsBoolean();
        }
        if (object.has("obfuscated")) {
            component.obfuscated = object.get("obfuscated").getAsBoolean();
        }
        if (object.has("color")) {
            component.color = object.get("color").getAsString();
        }

        if (object.has("extra")) {
            for (Component c : deserialize(object.getAsJsonArray("extra"))) {
                component.addComponent(c);
            }
        }
        return component;
    }

    private static List<Component> deserialize(JsonArray array) {
        List<Component> componentArray = new ArrayList<>();
        for(int i = 0; i < array.size(); i++) {
            componentArray.add(deserialize(array.get(i).getAsJsonObject()));
        }
        return componentArray;
    }
}
