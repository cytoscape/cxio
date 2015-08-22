package org.cxio.aspects.datamodels;

import java.util.List;

import org.cxio.core.interfaces.AspectElement;

/**
 *
 * This is the base class for EdgeAttributeElement, NodeAttributeElement, and NetworkAttributesElement.
 *
 * @author cmzmasek
 *
 */
public abstract class AbstractAttributesElement implements AspectElement {

    /**
     * The supported data types (either as atomic value or as list).
     *
     */
    public enum ATTRIBUTE_TYPE {
        BOOLEAN("boolean"), DOUBLE("double"), FLOAT("float"), INTEGER("integer"), LONG("long"), SHORT("short"), STRING("string");

        private final String _name;

        private ATTRIBUTE_TYPE(final String name) {
            _name = name;
        }

        @Override
        public String toString() {
            return _name;
        }
    }

    public final static String ATTR_NAME        = "n";
    public final static String ATTR_PROPERTY_OF = "po";
    public final static String ATTR_TYPE        = "t";
    public final static String ATTR_VALUES      = "v";

    String                     _name;
    List<String>               _property_of;
    ATTRIBUTE_TYPE             _type;
    List<String>               _values;

    /**
     * This is for getting the name of the attribute.
     *
     * @return the name of the attribute
     */
    public final String getName() {
        return _name;
    }

    /**
     * This returns a list of identifiers of the elements this attribute is a property of.
     *
     *
     * @return a list of identifiers of the elements this attribute is a property o
     */
    public final List<String> getPropertyOf() {
        return _property_of;
    }

    /**
     * This returns the type of the attribute.
     *
     *
     * @return the type of the attribute
     */
    public final ATTRIBUTE_TYPE getType() {
        return _type;
    }

    /**
     * This returns the values of the attribute as list of Strings.
     *
     *
     * @return the values of the attribute as list of Strings
     */
    public final List<String> getValues() {
        return _values;
    }

    /**
     * Convenience method to determine the type of an object.
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

}