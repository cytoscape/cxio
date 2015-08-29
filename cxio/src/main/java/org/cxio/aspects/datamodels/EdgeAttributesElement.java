package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.util.Util;

/**
 * This class is used to present one attribute of a network edge.
 * An attribute consists of a name, value(s), type, and
 * a(n) identifier(s) of the edges(s) the attribute is a property of.
 *
 * @author cmzmasek
 *
 */
public final class EdgeAttributesElement extends AbstractAttributesElement {

    public final static String NAME = "edgeAttributes";

    public EdgeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final List<String> values) {
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = values;
        _type = ATTRIBUTE_TYPE.STRING;
    }

    public EdgeAttributesElement(final String subnetwork, final List<String> property_of, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _property_of = property_of;
        _name = name;
        _values = values;
        _type = type;
    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = values;
        _type = type;
    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final String value) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _type = ATTRIBUTE_TYPE.STRING;
    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final Object value) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _type = determineType(value);
        _values.add(String.valueOf(value));
    }

    public EdgeAttributesElement(final String subnetwork, final String property_of, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _type = type;
    }

    public EdgeAttributesElement(final String property_of, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _type = type;
    }

    public EdgeAttributesElement(final String property_of, final String name, final Object value) {
        _subnetwork = null;
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _type = determineType(value);
        _values.add(String.valueOf(value));
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("property of edges: ");
        sb.append(_property_of);
        sb.append("\n");
        if (!Util.isEmpty(_subnetwork)) {
            sb.append("subnetwork       : ");
            sb.append(_subnetwork);
        }
        sb.append("\n");
        sb.append("name             : ");
        sb.append(_name);
        sb.append("\n");
        sb.append("values           : ");
        sb.append(_values);
        sb.append("\n");
        sb.append("type             : ");
        sb.append(_type.toString());
        return sb.toString();
    }

}
