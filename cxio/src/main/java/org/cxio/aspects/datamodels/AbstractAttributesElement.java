package org.cxio.aspects.datamodels;

import java.util.List;

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
        BOOLEAN("boolean"), DOUBLE("double"), FLOAT("float"), INTEGER("integer"), LONG("long"), SHORT(
                "short"), STRING("string");

        private final String _name;

        private ATTRIBUTE_TYPE(final String name) {
            _name = name;
        }

        @Override
        public String toString() {
            return _name;
        }
    }

    public final static String ATTR_NAME       = "n";
    public final static String ATTR_PROERTY_OF = "po";
    public final static String ATTR_TYPE       = "t";
    public final static String ATTR_VALUES      = "v";
    
    String                     _name;
    List<String>               _property_of;
    ATTRIBUTE_TYPE             _type;
    List<String>                     _values;

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
   

    public final String getName() {
        return _name;
    }

    public final List<String> getPropertyOf() {
        return _property_of;
    }

    public final ATTRIBUTE_TYPE getType() {
        return _type;
    }

    public final List<String> getValues() {
        return _values;
    }

}