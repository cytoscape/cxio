package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.util.Util;

/**
 * This class is used to present one attribute of a network edge.
 * An attribute consists of a name, value(s), data type (optional, if not set
 * data type is string), a(n) identifier(s) of the edges(s) the attribute is a property of,
 * and a (optional) sub-network identifier.
 *
 * @author cmzmasek
 *
 */
public final class EdgeAttributesElement extends AbstractAttributesAspectElement {

    public final static String NAME = "edgeAttributes";

    public EdgeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final List<String> values) {
        _data_type = ATTRIBUTE_DATA_TYPE.LIST_OF_STRING;
        _is_single_value = false;
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = values;
    }

    public EdgeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final List<String> values, final ATTRIBUTE_DATA_TYPE type) {
        if (!isListType(type)) {
            throw new IllegalArgumentException("list of values provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = false;
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = values;
    }

    public EdgeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {

        if (isListType(type)) {
            throw new IllegalArgumentException("single value provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = true;
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final List<String> values, final ATTRIBUTE_DATA_TYPE type) {

        if (!isListType(type)) {
            throw new IllegalArgumentException("list of values provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = false;
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = values;

    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final String value) {
        _data_type = ATTRIBUTE_DATA_TYPE.STRING;
        _is_single_value = true;
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);

    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final Object value) {
        if (value instanceof List) {
            throw new IllegalArgumentException("constructor only applicable for singe values");
        }
        _data_type = determineDataType(value);
        _is_single_value = true;
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(String.valueOf(value));
    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {

        if (isListType(type)) {
            throw new IllegalArgumentException("single value provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = true;
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);

    }

    public EdgeAttributesElement(final String property_of, final String name, final List<String> values, final ATTRIBUTE_DATA_TYPE type) {

        if (!isListType(type)) {
            throw new IllegalArgumentException("list of values provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = false;
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = values;
    }

    public EdgeAttributesElement(final String property_of, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {

        if (isListType(type)) {
            throw new IllegalArgumentException("single value provided, but given data type is " + type.toString());
        }
        _data_type = type;
        _is_single_value = true;
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
    }

    public EdgeAttributesElement(final String property_of, final String name, final Object value) {
        if (value instanceof List) {
            throw new IllegalArgumentException("constructor only applicable for singe values");
        }
        _data_type = determineDataType(value);
        _is_single_value = true;
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(String.valueOf(value));
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(NAME);
        sb.append(": ");
        sb.append("\n");
        sb.append("edges: ");
        sb.append(_property_of);
        sb.append("\n");
        if (!Util.isEmpty(_subnetwork)) {
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
        sb.append("\n");
        return sb.toString();
    }

}
