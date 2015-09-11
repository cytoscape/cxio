package org.cxio.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is to hold various meta data and to serialize this
 * data to json, and the de-serialize from json.
 *
 * @author cmzmasek
 *
 */
public final class MetaData {

    public final static String              NAME = "metaData";

    final private SortedMap<String, Object> _data;

    public MetaData() {
        _data = new TreeMap<String, Object>();
    }

    public final void put(final String key, final Object value) {
        _data.put(key, value);
    }

    public final Object get(final String key) {
        return _data.get(key);
    }

    public final Set<String> keySet() {
        return _data.keySet();
    }

    public final SortedMap<String, Object> getMetaData() {
        return _data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Entry<String, Object> e : _data.entrySet()) {
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append(Util.LINE_SEPARATOR);
        }
        return sb.toString();
    }

    public final void toJson(final JsonWriter w) throws IOException {
        w.writeObject(this);
    }

    public final static MetaData createInstanceFromJson(final InputStream is) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(is, MetaData.class);
    }

    public final static MetaData createInstanceFromJson(final String str) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(str, MetaData.class);
    }

    public static MetaData createInstanceFromJson(final JsonParser jp) throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(jp, MetaData.class);
    }

}
