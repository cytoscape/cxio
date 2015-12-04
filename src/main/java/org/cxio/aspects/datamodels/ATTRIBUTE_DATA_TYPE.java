package org.cxio.aspects.datamodels;

/**
 * The supported data types (either as atomic value or as list).
 *
 */
public enum ATTRIBUTE_DATA_TYPE {

    BOOLEAN("boolean"),
    BYTE("byte"),
    CHAR("char"),
    DOUBLE("double"),
    FLOAT("float"),
    INTEGER("integer"),
    LONG("long"),
    SHORT("short"),
    STRING("string"),
    LIST_OF_BOOLEAN("list_of_boolean"),
    LIST_OF_BYTE("list_of_byte"),
    LIST_OF_CHAR("list_of_char"),
    LIST_OF_DOUBLE("list_of_double"),
    LIST_OF_FLOAT("list_of_float"),
    LIST_OF_INTEGER("list_of_integer"),
    LIST_OF_LONG("list_of_long"),
    LIST_OF_SHORT("list_of_short"),
    LIST_OF_STRING("list_of_string");

    private final String _name;

    private ATTRIBUTE_DATA_TYPE(final String name) {
        _name = name;
    }

    @Override
    public String toString() {
        return _name;
    }

    public static String toCxLabel(final ATTRIBUTE_DATA_TYPE dt) {
        switch (dt) {
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
            return "list_of_boolean";
        case LIST_OF_BYTE:
            return "list_of_byte";
        case LIST_OF_CHAR:
            return "list_of_char";
        case LIST_OF_DOUBLE:
            return "list_of_double";
        case LIST_OF_FLOAT:
            return "list_of_float";
        case LIST_OF_INTEGER:
            return "list_of_integer";
        case LIST_OF_LONG:
            return "list_of_long";
        case LIST_OF_SHORT:
            return "list_of_short";
        case LIST_OF_STRING:
            return "list_of_string";
        default:
            throw new IllegalStateException("don't know type " + dt);
        }
    }
}