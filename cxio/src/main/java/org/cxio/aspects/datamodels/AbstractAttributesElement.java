package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.core.interfaces.AspectElement;

public abstract class AbstractAttributesElement implements AspectElement {

    public enum ATTRIBUTE_TYPE {
        BOOLEAN("boolean"), DOUBLE("double"), FLOAT("float"), INTEGER("integer"), LONG("long"), STRING("string");

        private final String name;

        private ATTRIBUTE_TYPE(final String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    final SortedMap<String, List<String>>   attributes       = new TreeMap<String, List<String>>();
    final SortedMap<String, ATTRIBUTE_TYPE> attributes_types = new TreeMap<String, ATTRIBUTE_TYPE>();
    String                                  id;
    public final static String              ATTRIBUTE_TYPES  = "types";
    public final static String              ATTRIBUTES       = "attributes";
    public final static String              NODES            = "nodes";
    public final static String              EDGES            = "edges";

    public final SortedMap<String, List<String>> getAttributes() {
        return attributes;
    }

    public final SortedMap<String, ATTRIBUTE_TYPE> getAttributesTypes() {
        return attributes_types;
    }

    public final String getId() {
        return id;
    }

    public final ATTRIBUTE_TYPE getType(final String key) {
        return attributes_types.get(key);
    }

    public final List<String> getValues(final String key) {
        return attributes.get(key);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public final void put(final String key, final String value, final ATTRIBUTE_TYPE type) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
            if (type != ATTRIBUTE_TYPE.STRING) {
                attributes_types.put(key, type);
            }
        }
        attributes.get(key).add(value);
    }

    public final void put(final String key, final String value, final String type) {
        put(key, value, toType(type));
    }

    public final void putType(final String key, final ATTRIBUTE_TYPE type) {
        if (type != ATTRIBUTE_TYPE.STRING) {
            attributes_types.put(key, type);
        }
    }

    public final void putType(final String key, final String type) {
        putType(key, toType(type));
    }

    public final void putValue(final String key, final Object value) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
            final ATTRIBUTE_TYPE t = determineType(value);
            if (t != ATTRIBUTE_TYPE.STRING) {
                attributes_types.put(key, t);
            }
        }
        attributes.get(key).add(String.valueOf(value));
    }

    public final void putValue(final String key, final String value) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
        }
        attributes.get(key).add(value);
    }

    public final void putValues(final String key, final List<String> values) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
        }
        attributes.get(key).addAll(values);
    }

    public final void setId(final String id) {
        this.id = id;
    }

    public final static ATTRIBUTE_TYPE toType(final String s) {
        if (s.equals(ATTRIBUTE_TYPE.STRING.toString())) {
            return ATTRIBUTE_TYPE.STRING;
        }
        else if (s.equals(ATTRIBUTE_TYPE.BOOLEAN.toString())) {
            return ATTRIBUTE_TYPE.BOOLEAN;
        }
        else if (s.equals(ATTRIBUTE_TYPE.DOUBLE.toString())) {
            return ATTRIBUTE_TYPE.DOUBLE;
        }
        else if (s.equals(ATTRIBUTE_TYPE.INTEGER.toString())) {
            return ATTRIBUTE_TYPE.INTEGER;
        }
        else if (s.equals(ATTRIBUTE_TYPE.LONG.toString())) {
            return ATTRIBUTE_TYPE.LONG;
        }
        else if (s.equals(ATTRIBUTE_TYPE.FLOAT.toString())) {
            return ATTRIBUTE_TYPE.FLOAT;
        }
        else {
            throw new IllegalArgumentException("type '" + s + "' is not supported");
        }
    }

    public final static ATTRIBUTE_TYPE determineType(final Object o) {

        if (o instanceof String) {
            return ATTRIBUTE_TYPE.STRING;
        }
        else if (o instanceof Boolean) {
            return ATTRIBUTE_TYPE.BOOLEAN;
        }
        else if (o instanceof Double) {
            return ATTRIBUTE_TYPE.DOUBLE;
        }
        else if (o instanceof Integer) {
            return ATTRIBUTE_TYPE.INTEGER;
        }
        else if (o instanceof Long) {
            return ATTRIBUTE_TYPE.LONG;
        }
        else if (o instanceof Float) {
            return ATTRIBUTE_TYPE.FLOAT;
        }
        else {
            throw new IllegalArgumentException("type '" + o.getClass() + "' is not supported");
        }
    }

}