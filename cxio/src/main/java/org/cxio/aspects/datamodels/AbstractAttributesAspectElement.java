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
public abstract class AbstractAttributesAspectElement implements AspectElement {

    /**
     * The supported data types (either as atomic value or as list).
     *
     */
    public enum ATTRIBUTE_TYPE {
        BOOLEAN("boolean"), BYTE("byte"), CHAR("char"), DOUBLE("double"), FLOAT("float"), INTEGER("integer"), LONG("long"), SHORT("short"), STRING("string");

        private final String _name;

        private ATTRIBUTE_TYPE(final String name) {
            _name = name;
        }

        @Override
        public String toString() {
            return _name;
        }
    }

    /** The name of this attribute. */
    public final static String ATTR_NAME        = "n";

    /** The node or edge this attribute is a property of. */
    public final static String ATTR_PROPERTY_OF = "po";

    /** The subnetwork this attribute belongs to. */
    public final static String ATTR_SUBNETWORK  = "s";

    /** The data type of this attribute (either atomic or list). */
    public final static String ATTR_DATA_TYPE   = "d";

    /** The value(s) of this attribute. */
    public final static String ATTR_VALUES      = "v";

    String                     _name;
    List<String>               _property_of;
    String                     _subnetwork;
    ATTRIBUTE_TYPE             _data_type;
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
     * @return a list of identifiers of the elements this attribute is a property o
     */
    public List<String> getPropertyOf() {
        return _property_of;
    }

    /**
     * This returns the identifier of the subnetwork this attribute belongs to.
     *
     * @return the identifier of the subnetwork this attribute belongs to
     */
    public final String getSubnetwork() {
        return _subnetwork;
    }

    /**
     * This returns the data type of the attribute.
     *
     *
     * @return the data type of the attribute
     */
    public final ATTRIBUTE_TYPE getDataType() {
        return _data_type;
    }

    /**
     * This returns the values of the attribute as list of Strings.
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
    public final static ATTRIBUTE_TYPE determineDataType(final Object o) {

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
        else if (o instanceof Byte) {
            return ATTRIBUTE_TYPE.BYTE;
        }
        else if (o instanceof Character) {
            return ATTRIBUTE_TYPE.CHAR;
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
    public final static ATTRIBUTE_TYPE toDataType(final String s) {
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
        else if (s.equals(ATTRIBUTE_TYPE.BYTE.toString())) {
            return ATTRIBUTE_TYPE.BYTE;
        }
        else if (s.equals(ATTRIBUTE_TYPE.CHAR.toString())) {
            return ATTRIBUTE_TYPE.CHAR;
        }
        else {
            throw new IllegalArgumentException("type '" + s + "' is not supported");
        }
    }

}