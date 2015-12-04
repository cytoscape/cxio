package org.cxio.aspects.datamodels;

import java.util.List;

/**
 *
 * This is the base class for EdgeAttributeElement, NodeAttributeElement, and NetworkAttributesElement.
 *
 * @author cmzmasek
 *
 */
public abstract class AbstractAttributesAspectElement extends AbstractAspectElement {

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
    List<Long>                 _property_of;
    Long                       _subnetwork;
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
    public List<Long> getPropertyOf() {
        return _property_of;
    }

    /**
     * This returns the identifier of the subnetwork this attribute belongs to.
     *
     * @return the identifier of the subnetwork this attribute belongs to
     */
    public final Long getSubnetwork() {
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
     * This returns the list values of the (list) attribute as list of Strings.
     *
     * @return the list values of the (list) attribute as list of Strings
     */
    public final List<String> getValues() {
        if (isSingleValue()) {
            throw new IllegalStateException("attempt to return single value as list of values");
        }
        return _values;
    }

    /**
     * This returns the value of the (single) attribute as String.
     *
     * @return the value of the (single) attribute as String
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

}