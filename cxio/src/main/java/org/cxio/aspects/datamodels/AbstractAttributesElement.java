package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.core.CxConstants;
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
     * Basic data types.
     *
     * @author cmzmasek
     *
     */
    public enum ATTRIBUTE_TYPE {
        BOOLEAN("boolean"), DOUBLE("double"), FLOAT("float"), INTEGER("integer"), LONG("long"), STRING("string"), SHORT("short");

        private final String _name;

        private ATTRIBUTE_TYPE(final String name) {
            _name = name;
        }

        @Override
        public String toString() {
            return _name;
        }
    }

    final SortedMap<String, List<String>>   _attributes       = new TreeMap<String, List<String>>();
    final SortedMap<String, ATTRIBUTE_TYPE> _attributes_types = new TreeMap<String, ATTRIBUTE_TYPE>();
    String                                  _id;
    public final static String              ATTRIBUTE_TYPES   = "types";
    public final static String              ATTRIBUTES        = "attributes";
    public final static String              ID                = CxConstants.ID;

    /**
     * This returns the attributes.
     *
     * @return the attributes
     */
    public final SortedMap<String, List<String>> getAttributes() {
        return _attributes;
    }

    /**
     * This returns the attributes types.
     *
     * @return the attributes types
     */
    public final SortedMap<String, ATTRIBUTE_TYPE> getAttributesTypes() {
        return _attributes_types;
    }

    /**
     * This returns the ID
     *
     * @return the ID
     */
    public final String getId() {
        return _id;
    }

    /**
     * This returns the attribute type for a given key.
     *
     * @param key
     * @return attribute type for a given key
     */
    public final ATTRIBUTE_TYPE getType(final String key) {
        return _attributes_types.get(key);
    }

    /**
     * This returns the attribute values for a given key.
     *
     * @param key
     * @return attribute values for a given key
     */
    public final List<String> getValues(final String key) {
        return _attributes.get(key);
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
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
        if (!_attributes.containsKey(key)) {
            _attributes.put(key, new ArrayList<String>());
            if (type != ATTRIBUTE_TYPE.STRING) {
                _attributes_types.put(key, type);
            }
        }
        _attributes.get(key).add(value);
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
            _attributes_types.put(key, type);
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
     * This used to put a value with its key, the type is inferred from the type
     * of the value object.
     *
     * @param key
     * @param value
     */
    public final void putValue(final String key, final Object value) {
        if (!_attributes.containsKey(key)) {
            _attributes.put(key, new ArrayList<String>());
            final ATTRIBUTE_TYPE t = determineType(value);
            if (t != ATTRIBUTE_TYPE.STRING) {
                _attributes_types.put(key, t);
            }
        }
        _attributes.get(key).add(String.valueOf(value));
    }

    /**
     * This is used to enter a value with its key, type is string.
     *
     *
     * @param key
     * @param value
     */
    public final void putValue(final String key, final String value) {
        if (!_attributes.containsKey(key)) {
            _attributes.put(key, new ArrayList<String>());
        }
        _attributes.get(key).add(value);
    }

    /**
     * This is used to enter a list of values with their key, type is string.
     *
     * @param key
     * @param values
     */
    public final void putValues(final String key, final List<String> values) {
        if (!_attributes.containsKey(key)) {
            _attributes.put(key, new ArrayList<String>());
        }
        _attributes.get(key).addAll(values);
    }
    
    /**
     * This is used to enter a list of values with their key, type is 
     * selectable by caller.
     * 
     * 
     * @param key
     * @param values
     * @param type
     */
    public final void putValues(final String key, final List<String> values, final String type) {
        if (!_attributes.containsKey(key)) {
            _attributes.put(key, new ArrayList<String>());
        }
        _attributes.get(key).addAll(values);
        putType(key, type); 
    }
    
    /**
     * This is used to enter a list of values with their key, type is 
     * selectable by caller.
     * 
     * 
     * @param key
     * @param values
     * @param type
     */
    public final void putValues(final String key, final List<String> values, final ATTRIBUTE_TYPE type) {
        if (!_attributes.containsKey(key)) {
            _attributes.put(key, new ArrayList<String>());
        }
        _attributes.get(key).addAll(values);
        putType(key, type);
    }
    
    

    /**
     * This is used to set the id.
     *
     *
     * @param id
     */
    public final void setId(final String id) {
        _id = id;
    }

    /**
     * Convenience method to go from a type described by a string to an actual
     * type enum entry.
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
        else if (s.equals(ATTRIBUTE_TYPE.SHORT.toString())) {
            return ATTRIBUTE_TYPE.SHORT;
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
        else if (o instanceof Short) {
            return ATTRIBUTE_TYPE.SHORT;
        }
        else {
            throw new IllegalArgumentException("type '" + o.getClass() + "' is not supported");
        }
    }

}