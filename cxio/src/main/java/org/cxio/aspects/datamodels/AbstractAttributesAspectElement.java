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
    public enum ATTRIBUTE_DATA_TYPE {

        BOOLEAN("boolean"), BYTE("byte"), CHAR("char"), DOUBLE("double"), FLOAT("float"), INTEGER("integer"), LONG("long"), SHORT("short"), STRING("string"), LIST_OF_BOOLEAN("list_of_boolean"), LIST_OF_BYTE(
                "list_of_byte"), LIST_OF_CHAR("list_of_char"), LIST_OF_DOUBLE("list_of_double"), LIST_OF_FLOAT("list_of_float"), LIST_OF_INTEGER("list_of_integer"), LIST_OF_LONG("list_of_long"), LIST_OF_SHORT("list_of_short"), LIST_OF_STRING("list_of_string");

        private final String _name;

        private ATTRIBUTE_DATA_TYPE(final String name) {
            _name = name;
        }

        @Override
        public String toString() {
            return _name;
        }
        
      
        public static String toCxLabel( ATTRIBUTE_DATA_TYPE dt ) {
            switch ( dt ) {
            case BOOLEAN:
                return "boolean";
            case BYTE:
                return "byte";
            case CHAR:
                return "char";
            case DOUBLE:
                return "double";
            case FLOAT:
                return "float";
            case INTEGER:
                return "integer";
            case LONG:
                return "long";
            case SHORT:
                return "short";
            case STRING:
                return "string";
            case LIST_OF_BOOLEAN:
                return "boolean";
            case LIST_OF_BYTE:
                return "byte";
            case LIST_OF_CHAR:
                return "char";
            case LIST_OF_DOUBLE:
                return "double";
            case LIST_OF_FLOAT:
                return "float";
            case LIST_OF_INTEGER:
                return "integer";
            case LIST_OF_LONG:
                return "long";
            case LIST_OF_SHORT:
                return "short";
            case LIST_OF_STRING:
                return "string";
            default: throw new IllegalStateException( "don't know type " + dt);
            }
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
    ATTRIBUTE_DATA_TYPE        _data_type;
    List<String>               _values;
    boolean                    _is_single_value;

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
    public final ATTRIBUTE_DATA_TYPE getDataType() {
        return _data_type;
    }

    /**
     * This returns the list values of the attribute as list of Strings.
     *
     * @return the list values of the attribute as list of Strings
     */
    public final List<String> getValues() {
        if (isSingleValue()) {
            throw new IllegalStateException("attempt to return single value as list of values");
        }
        return _values;
    }

    /**
     * This returns the value of the attribute as String.
     *
     * @return the value of the attribute as Strings
     */
    public final String getValue() {
        if (!isSingleValue()) {
            throw new IllegalStateException("attempt to return list of values as single value");
        }
        return _values.get(0);
    }

    /**
     * This returns true if the value of this attribute is a single value,
     * false if it is a list of values (even if the list just contains one value).
     *
     *
     * @return true if single value, false if list of values
     */
    public final boolean isSingleValue() {
        return _is_single_value;
    }

    /**
     * Convenience method to determine the type of an object.
     *
     * @param o
     * @return
     */
    final static ATTRIBUTE_DATA_TYPE determineDataType(final Object o) {

        if (o instanceof String) {
            return ATTRIBUTE_DATA_TYPE.STRING;
        }
        else if (o instanceof Boolean) {
            return ATTRIBUTE_DATA_TYPE.BOOLEAN;
        }
        else if (o instanceof Double) {
            return ATTRIBUTE_DATA_TYPE.DOUBLE;
        }
        else if (o instanceof Integer) {
            return ATTRIBUTE_DATA_TYPE.INTEGER;
        }
        else if (o instanceof Long) {
            return ATTRIBUTE_DATA_TYPE.LONG;
        }
        else if (o instanceof Float) {
            return ATTRIBUTE_DATA_TYPE.FLOAT;
        }
        else if (o instanceof Short) {
            return ATTRIBUTE_DATA_TYPE.SHORT;
        }
        else if (o instanceof Byte) {
            return ATTRIBUTE_DATA_TYPE.BYTE;
        }
        else if (o instanceof Character) {
            return ATTRIBUTE_DATA_TYPE.CHAR;
        }
        else if (o instanceof List) {
            throw new IllegalArgumentException("cannot determine type of list");
        }
        else {
            throw new IllegalArgumentException("type '" + o.getClass() + "' is not supported");
        }
    }

    final public static ATTRIBUTE_DATA_TYPE toList(final ATTRIBUTE_DATA_TYPE type) {

        if (type == ATTRIBUTE_DATA_TYPE.STRING) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_STRING;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.BOOLEAN) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_BOOLEAN;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.DOUBLE) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.INTEGER) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_INTEGER;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.LONG) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_LONG;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.FLOAT) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_FLOAT;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.SHORT) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.BYTE) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_BYTE;
        }
        else if (type == ATTRIBUTE_DATA_TYPE.CHAR) {
            return ATTRIBUTE_DATA_TYPE.LIST_OF_CHAR;
        }

        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Convenience method to go from a type described by a string to an actual
     * type enum entry.
     *
     * @param s
     * @return
     */
    public final static ATTRIBUTE_DATA_TYPE toDataType(final String s) {
        if (s.equals(ATTRIBUTE_DATA_TYPE.STRING.toString())) {
            return ATTRIBUTE_DATA_TYPE.STRING;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.BOOLEAN.toString())) {
            return ATTRIBUTE_DATA_TYPE.BOOLEAN;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.DOUBLE.toString())) {
            return ATTRIBUTE_DATA_TYPE.DOUBLE;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.INTEGER.toString())) {
            return ATTRIBUTE_DATA_TYPE.INTEGER;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.LONG.toString())) {
            return ATTRIBUTE_DATA_TYPE.LONG;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.FLOAT.toString())) {
            return ATTRIBUTE_DATA_TYPE.FLOAT;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.SHORT.toString())) {
            return ATTRIBUTE_DATA_TYPE.SHORT;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.BYTE.toString())) {
            return ATTRIBUTE_DATA_TYPE.BYTE;
        }
        else if (s.equals(ATTRIBUTE_DATA_TYPE.CHAR.toString())) {
            return ATTRIBUTE_DATA_TYPE.CHAR;
        }
        else {
            throw new IllegalArgumentException("type '" + s + "' is not supported");
        }
    }

    public final static boolean isListType(final ATTRIBUTE_DATA_TYPE data_type) {
        return (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_BOOLEAN) || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_BYTE) || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_CHAR)
                || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE) || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_FLOAT) || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_INTEGER)
                || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_LONG) || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT) || (data_type == ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
    }

}