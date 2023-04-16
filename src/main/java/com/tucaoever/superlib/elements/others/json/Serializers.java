package com.tucaoever.superlib.elements.others.json;

import ch.njol.skript.variables.SerializedVariable;
import java.util.Base64;
import ch.njol.skript.registrations.Classes;
import org.json.simple.JSONObject;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

public class Serializers
{
    private static Map<String, Serializer> serializers;
    
    static {
        Serializers.serializers = new HashMap<String, Serializer>();
    }
    
    public static <T> void register(final Class<T> cls, final Function<T, JSONObject> serializer, final Function<JSONObject, T> deserializer) {
        Serializers.serializers.put(cls.getName(), new Serializer((Function<Object, JSONObject>)serializer, (Function<JSONObject, Object>)deserializer));
    }
    
    public static JSONObject serialize(final Object o) {
        final String cls = o.getClass().getName();
        JSONObject obj;
        if (Serializers.serializers.containsKey(cls)) {
            obj = Serializers.serializers.get(cls).serializer.apply(o);
            obj.put((Object)"__javaclass__", (Object)cls);
        }
        else {
            obj = new JSONObject();
            final SerializedVariable.Value value = Classes.serialize(o);
            if (value == null) {
                return null;
            }
            obj.put((Object)"__skriptclass__", (Object)value.type);
            obj.put((Object)"value", (Object)Base64.getEncoder().encodeToString(value.data));
        }
        return obj;
    }
    
    public static Object deserialize(final JSONObject obj) {
        final String cls = (String)obj.get((Object)"__javaclass__");
        if (cls != null && Serializers.serializers.containsKey(cls)) {
            return Serializers.serializers.get(cls).deserializer.apply(obj);
        }
        final String type = (String)obj.get((Object)"__skriptclass__");
        final String content = (String)obj.get((Object)"value");
        if (type == null || content == null) {
            return null;
        }
        return Classes.deserialize(type, Base64.getDecoder().decode(content));
    }
    
    private static class Serializer
    {
        Function<Object, JSONObject> serializer;
        Function<JSONObject, Object> deserializer;
        
        Serializer(final Function<Object, JSONObject> serializer, final Function<JSONObject, Object> deserializer) {
            this.serializer = serializer;
            this.deserializer = deserializer;
        }
    }
}
