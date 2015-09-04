package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.util.Util;

/**
 * This class is used to present one hidden attribute.
 * An attribute consists of a name, value(s), data type (optional, if not set
 * data type is string), and a (optional) sub-network identifier.
 *
 * @author cmzmasek
 *
 */
public final class HiddenAttributesElement extends AbstractAttributesElement {

    public final static String NAME = "hiddenAttributes";

    public HiddenAttributesElement(final String subnetwork, final String name, final List<String> values) {
        _subnetwork = subnetwork;
        _name = name;
        _values = values;
        _data_type = ATTRIBUTE_TYPE.STRING;
    }

    public HiddenAttributesElement(final String subnetwork, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _name = name;
        _values = values;
        _data_type = type;
    }

    public HiddenAttributesElement(final String subnetwork, final String name, final String value) {
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = ATTRIBUTE_TYPE.STRING;
    }

    public HiddenAttributesElement(final String subnetwork, final String name, final Object value) {
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _data_type = determineDataType(value);
        _values.add(String.valueOf(value));
    }

    public HiddenAttributesElement(final String subnetwork, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = type;
    }

    public HiddenAttributesElement(final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = null;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = type;
    }

    public HiddenAttributesElement(final String name, final Object value) {
        _subnetwork = null;
        _name = name;
        _values = new ArrayList<String>();
        _data_type = determineDataType(value);
        _values.add(String.valueOf(value));
    }

    @Override
    public List<String> getPropertyOf() {
        throw new NoSuchMethodError("hidden attributes do not have a property-of data fieldment");
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
        if (!Util.isEmpty(_subnetwork)) {
            sb.append("subnetwork       : ");
            sb.append(_subnetwork);
            sb.append("\n");
        }
        sb.append("name             : ");
        sb.append(_name);
        sb.append("\n");
        sb.append("values           : ");
        sb.append(_values);
        sb.append("\n");
        sb.append("data type        : ");
        sb.append(_data_type.toString());
        return sb.toString();
    }

}
