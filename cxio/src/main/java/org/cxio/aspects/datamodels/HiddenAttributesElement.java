package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to present one hidden attribute.
 * An attribute consists of a name, value(s), data type (optional, if not set
 * data type is string), and a (optional) sub-network identifier.
 *
 * @author cmzmasek
 *
 */
public final class HiddenAttributesElement extends AbstractAttributesAspectElement {

    public final static String ASPECT_NAME = "hiddenAttributes";

    public HiddenAttributesElement(final long subnetwork, final String name, final List<String> values) {
        _data_type = ATTRIBUTE_DATA_TYPE.LIST_OF_STRING;
        _is_single_value = false;
        _subnetwork = subnetwork;
        _name = name;
        _values = values;
    }

    public HiddenAttributesElement(final long subnetwork, final String name, final List<String> values, final ATTRIBUTE_DATA_TYPE type) {
        if (!AttributesAspectUtils.isListType(type)) {
            throw new IllegalArgumentException("list of values provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = false;
        _subnetwork = subnetwork;
        _name = name;
        _values = values;
    }

    public HiddenAttributesElement(final long subnetwork, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {
        if (AttributesAspectUtils.isListType(type)) {
            throw new IllegalArgumentException("single value provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = true;
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
    }

    public HiddenAttributesElement(final long subnetwork, final String name, final String value) {
        _data_type = ATTRIBUTE_DATA_TYPE.STRING;
        _is_single_value = true;
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);

    }

    public HiddenAttributesElement(final long subnetwork, final String name, final Object value) {
        if (value instanceof List) {
            throw new IllegalArgumentException("constructor only applicable for singe values");
        }
        _data_type = AttributesAspectUtils.determineDataType(value);
        _is_single_value = true;
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(String.valueOf(value));
    }

    @Override
    public List<Long> getPropertyOf() {
        throw new NoSuchMethodError("hidden attributes do not have a property-of data fieldment");
    }

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ASPECT_NAME);
        sb.append(": ");
        sb.append("\n");
        if (_subnetwork != null) {
            sb.append("subnetwork       : ");
            sb.append(_subnetwork);
            sb.append("\n");
        }
        sb.append("name             : ");
        sb.append(_name);
        sb.append("\n");
        if (_is_single_value) {
            sb.append("value            : ");
            sb.append(_values.get(0));
        }
        else {
            sb.append("values           : ");
            sb.append(_values);
        }
        sb.append("\n");
        sb.append("data type        : ");
        sb.append(_data_type.toString());
        return sb.toString();
    }

    public final static HiddenAttributesElement createInstanceWithSingleValue(final long subnetwork, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {

        return new HiddenAttributesElement(subnetwork, name, DatamodelsUtil.removeParenthesis(value, type), type);
    }

    public final static HiddenAttributesElement createInstanceWithMultipleValues(final long subnetwork, final String name, final String values, final ATTRIBUTE_DATA_TYPE type) {

        return new HiddenAttributesElement(subnetwork, name, DatamodelsUtil.parseStringToStringList(values, type), type);
    }

}
