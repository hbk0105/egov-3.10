package com.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonUtil {

    private static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(com.google.gson.Gson gson,
                                         TypeToken<T> type) {
            return type.getRawType() == Object.class ? (TypeAdapter<T>) new LongObjectTypeAdapter(
                    gson) : null;
        }
    };

    static {
        try {
            Field field = ObjectTypeAdapter.class.getDeclaredField("FACTORY");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField
                    .setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.setAccessible(true);
            field.set(null, FACTORY);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private com.google.gson.Gson gson = new com.google.gson.Gson();

    public <T> T fromJson(JsonArray jsonArray, Type type) {
        return gson.fromJson(jsonArray, type);
    }

    public <T> T fromJson2(JsonElement jsonElement, Type type) {
        return gson.fromJson(jsonElement, type);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public static class LongObjectTypeAdapter extends TypeAdapter<Object> {

        private com.google.gson.Gson gson;

        public LongObjectTypeAdapter(com.google.gson.Gson gson) {
            this.gson = gson;
        }

        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();

            switch (token) {
                case BEGIN_ARRAY:
                    ArrayList list = new ArrayList();
                    in.beginArray();

                    while (in.hasNext()) {
                        list.add(this.read(in));
                    }

                    in.endArray();
                    return list;
                case BEGIN_OBJECT:
                    LinkedTreeMap map = new LinkedTreeMap();
                    in.beginObject();

                    while (in.hasNext()) {
                        map.put(in.nextName(), this.read(in));
                    }

                    in.endObject();
                    return map;
                case STRING:
                    return in.nextString();
                case NUMBER:
                    String value = in.nextString();
                    if (value.contains(".")) {
                        return Double.valueOf(value);
                    } else {
                        return Long.valueOf(value);
                    }

                case BOOLEAN:
                    return Boolean.valueOf(in.nextBoolean());
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    throw new IllegalStateException();
            }
        }

        public void write(JsonWriter out, Object value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                TypeAdapter typeAdapter = gson.getAdapter(value.getClass());
                if (typeAdapter instanceof LongObjectTypeAdapter) {
                    out.beginObject();
                    out.endObject();
                } else {
                    typeAdapter.write(out, value);
                }
            }
        }
    }
}