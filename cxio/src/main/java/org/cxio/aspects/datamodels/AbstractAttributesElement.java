package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.core.interfaces.AspectElement;

/**
 * 
 * This is the base class for EdgeAttributeElement and NodeAttributeElement.
 * 
 * @author cmzmasek
 *
 */
public abstract class AbstractAttributesElement implements AspectElement {

    /**
     * Datatypes used by Cytoscape.
     * 
     * @author cmzmasek
     *
     */
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

    /**
     * This returns the attributes.
     * 
     * @return the attributes
     */
    public final SortedMap<String, List<String>> getAttributes() {
        return attributes;
    }

    /**
     *  This returns the attributes types.
     * 
     * @return the attributes types
     */
    public final SortedMap<String, ATTRIBUTE_TYPE> getAttributesTypes() {
        return attributes_types;
    }

    /**
     * This returns the ID
     * 
     * @return the ID
     */
    public final String getId() {
        return id;
    }

    /**
     * This returns the attribute type for a given key.
     * 
     * @param key
     * @return attribute type for a given key
     */
    public final ATTRIBUTE_TYPE getType(final String key) {
        return attributes_types.get(key);
    }

    /**
     * This returns the attribute values for a given key.
     * 
     * @param key
     * @return attribute values for a given key
     */
    public final List<String> getValues(final String key) {
        return attributes.get(key);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * This used to put a value with its key and type.
     * 
     * 
     * @param key
     * @param value
     * @param type
     */
    public final void put(final String key, final String value, final ATTRIBUTE_TYPE type) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
            if (type != ATTRIBUTE_TYPE.STRING) {
                attributes_types.put(key, type);
            }
        }
        attributes.get(key).add(value);
    }

    /**
     * This used to put a value with its key and type.
     * 
     * @param key
     * @param value
     * @param type
     */
    public final void put(final String key, final String value, final String type) {
        put(key, value, toType(type));
    }

    /**
     * This is used to set the type for a given key.
     * 
     * @param key
     * @param type
     */
    public final void putType(final String key, final ATTRIBUTE_TYPE type) {
        if (type != ATTRIBUTE_TYPE.STRING) {
            attributes_types.put(key, type);
        }
    }

    /**
     * This is used to set the type for a given key.
     * 
     * @param key
     * @param type
     */
    public final void putType(final String key, final String type) {
        putType(key, toType(type));
    }

    /**
     * This used to put a value with its key, the type is inferred from the type of the value object.
     * 
     * @param key
     * @param value
     */
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

    /**
     * This is used to enter a value with its key, type is string.
     * 
     * 
     * @param key
     * @param value
     */
    public final void putValue(final String key, final String value) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
        }
        attributes.get(key).add(value);
    }

   /**
    * This is used to enter a list of values with their key, type is string.
    * 
    * @param key
    * @param values
    */
    public final void putValues(final String key, final List<String> values) {
        if (!attributes.containsKey(key)) {
            attributes.put(key, new ArrayList<String>());
        }
        attributes.get(key).addAll(values);
    }

    /**
     * This is used to set the id.
     * 
     * 
     * @param id
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Convenience method to go from a type described by a string to an actual type enum entry.
     * 
     * @param s
     * @return
     */
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

    /**
     * Convenience method to determine the type of a object.
     * 
     * @param o
     * @return
     */
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