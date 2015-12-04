package org.cxio.aspects.datamodels;

public class CyTableColumnElement extends AbstractAttributesAspectElement {

    public final static String ASPECT_NAME = "cyTableColumn";

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    public CyTableColumnElement(final Long subnetwork, final String name, final ATTRIBUTE_DATA_TYPE type) {
        _data_type = type;
        _subnetwork = subnetwork;
        _name = name;
        _values = null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ASPECT_NAME);
        sb.append(": ");
        sb.append("\n");
        if (_subnetwork != null) {
            sb.append("property of network: ");
            sb.append(_subnetwork);
        }
        sb.append("\n");
        sb.append("name               : ");
        sb.append(_name);
        sb.append("\n");
        sb.append("type               : ");
        sb.append(_data_type.toString());
        return sb.toString();
    }

}
