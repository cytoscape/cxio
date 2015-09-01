package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.util.Util;

/**
 * This class is used to present one attribute of a network.
 * An attribute consists of a name, value(s), type, and
 * a(n) identifier(s) of the network(s) the attribute is a property of.
 *
 * @author cmzmasek
 *
 */
public final class NetworkAttributesElement extends AbstractAttributesElement {

    public final static String NAME = "networkAttributes";

    public NetworkAttributesElement(final String subnetwork, final String name, final List<String> values) {
        _subnetwork = subnetwork;
        _name = name;
        _values = values;
        _data_type = ATTRIBUTE_TYPE.STRING;
    }

    public NetworkAttributesElement(final String subnetwork, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _name = name;
        _values = values;
        _data_type = type;
    }

    public NetworkAttributesElement(final String subnetwork, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = type;
    }

    public NetworkAttributesElement(final String subnetwork, final String name, final String value) {
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _data_type = ATTRIBUTE_TYPE.STRING;
    }

    public NetworkAttributesElement(final String subnetwork, final String name, final Object value) {
        _subnetwork = subnetwork;
        _name = name;
        _values = new ArrayList<String>();
        _data_type = determineDataType(value);
        _values.add(String.valueOf(value));
    }

    public NetworkAttributesElement(final String name, final Object value) {
        _subnetwork = null;
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
    public final List<String> getPropertyOf() {
        throw new NoSuchMethodError("network attributes do not have a property-of data field");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (Util.isEmpty(_subnetwork)) {
            sb.append("property of network");
        }
        else {
            sb.append("property of network: ");
            sb.append(_subnetwork);
        }
        sb.append("\n");
        sb.append("name               : ");
        sb.append(_name);
        sb.append("\n");
        sb.append("values             : ");
        sb.append(_values);
        sb.append("\n");
        sb.append("type               : ");
        sb.append(_data_type.toString());
        return sb.toString();
    }

}
