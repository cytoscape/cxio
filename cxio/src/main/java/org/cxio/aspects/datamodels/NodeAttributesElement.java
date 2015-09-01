package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;

/**
 * This class is used to present one attribute of a network node.
 * An attribute consists of a name, value(s), type, and
 * a(n) identifier(s) of the node(s) the attribute is a property of.
 *
 * @author cmzmasek
 *
 */
public final class NodeAttributesElement extends AbstractAttributesElement {

    public final static String NAME = "nodeAttributes";

    public NodeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final List<String> values) {
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = values;
        _data_type = ATTRIBUTE_TYPE.STRING;
    }

    public NodeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = values;
        _data_type = type;
    }

    public NodeAttributesElement(final String subnetwork, final String property_of, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = values;
        _data_type = type;
    }

    public NodeAttributesElement(final String subnetwork, final String property_of, final String name, final String value) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = ATTRIBUTE_TYPE.STRING;
    }

    public NodeAttributesElement(final String subnetwork, final String property_of, final String name, final Object value) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _data_type = determineDataType(value);
        _values.add(String.valueOf(value));
    }

    public NodeAttributesElement(final String subnetwork, final String property_of, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = type;
    }

    public NodeAttributesElement(final String property_of, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = type;
    }

    public NodeAttributesElement(final String property_of, final String name, final Object value) {
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _data_type = determineDataType(value);
        _values.add(String.valueOf(value));
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("property of nodes: ");
        sb.append(_property_of);
        sb.append("\n");
        sb.append("name : ");
        sb.append(_name);
        sb.append("\n");
        sb.append("values: ");
        sb.append(_values);
        sb.append("\n");
        sb.append("type : ");
        sb.append(_data_type.toString());
        sb.append("\n");
        return sb.toString();
    }

}
