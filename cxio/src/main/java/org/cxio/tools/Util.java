package org.cxio.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.AbstractAttributesElement;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Util {

    final public static boolean isEmpty(final String s) {
        return (s == null) || (s.length() < 1);
    }

    public final static List<String> parseSimpleStringList(final JsonParser jp, JsonToken t) throws IOException,
            JsonParseException {
        final List<String> elements = new ArrayList<String>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.VALUE_STRING) {
                elements.add(jp.getText());
            }
            else if (t != JsonToken.START_OBJECT) {
                throw new IOException("malformed cx json, expected " + JsonToken.START_OBJECT + ", got " + t);
            }
            t = jp.nextToken();
        }
        return elements;
    }

    public final static List<String> getStringList(final ObjectNode o, final String label) {
        if (o.has(label)) {
            final List<String> l = new ArrayList<String>();
            final Iterator<JsonNode> it = o.get(label).iterator();
            while (it.hasNext()) {
                final String s = it.next().asText();
                if (!isEmpty(s)) {
                    l.add(s);
                }
            }
            return l;
        }
        return null;
    }

    public final static List<String> getStringListRequired(final ObjectNode o, final String label) throws IOException {
        final List<String> l = new ArrayList<String>();
        if (o.has(label)) {
            final Iterator<JsonNode> it = o.get(label).iterator();
            while (it.hasNext()) {
                final String s = it.next().asText();
                if (!isEmpty(s)) {
                    l.add(s);
                }
            }
        }
        if (l.isEmpty()) {
            throw new IOException("malformed CX json: list '" + label + "' is missing or empty");
        }
        return l;
    }

    public final static String getTextValue(final ObjectNode o, final String label) {
        if (o.has(label)) {
            return o.get(label).asText();
        }
        return null;
    }

    public final static String getTextValueRequired(final ObjectNode o, final String label) throws IOException {
        String s = null;
        if (o.has(label)) {
            s = o.get(label).asText();
        }
        if (isEmpty(s)) {
            throw new IOException("malformed CX json: " + label + " is missing");
        }
        return s;
    }

    public final static SortedMap<String, List<String>> getMap(final ObjectNode o, final String label) {
        final SortedMap<String, List<String>> map = new TreeMap<String, List<String>>();
        if (o.has(label)) {
            final Iterator<Entry<String, JsonNode>> it1 = o.get(label).fields();
            while (it1.hasNext()) {
                final Entry<String, JsonNode> s = it1.next();
                final ArrayList<String> l = new ArrayList<String>();
                map.put(s.getKey(), l);
                final Iterator<JsonNode> it2 = s.getValue().iterator();
                while (it2.hasNext()) {
                    l.add(it2.next().asText());
                }
            }
        }
        return map;
    }

    public final static void putAttributes(final ObjectNode o, final String label, final AbstractAttributesElement ae) {
        if (o.has(label)) {
            final Iterator<Entry<String, JsonNode>> it1 = o.get(label).fields();
            while (it1.hasNext()) {
                final Entry<String, JsonNode> s = it1.next();
                final String key = s.getKey();
                final Iterator<JsonNode> it2 = s.getValue().iterator();
                while (it2.hasNext()) {
                    ae.putValue(key, it2.next().asText());
                }
            }
        }
    }

    public final static void putAttributeTypes(final ObjectNode o,
                                               final String label,
                                               final AbstractAttributesElement ae) {
        if (o.has(label)) {
            final Iterator<Entry<String, JsonNode>> it1 = o.get(label).fields();
            while (it1.hasNext()) {
                final Entry<String, JsonNode> s = it1.next();
                ae.putType(s.getKey(), s.getValue().asText());

            }
        }
    }

}
