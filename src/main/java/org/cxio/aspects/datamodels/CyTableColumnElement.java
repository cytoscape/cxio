package org.cxio.aspects.datamodels;

public class CyTableColumnElement extends AbstractAttributesAspectElement {

    public final static String ASPECT_NAME = "cyTableColumn";
    public final static String APPLIES_TO  = "applies_to";
    private final String       _applies_to;

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    public CyTableColumnElement(final Long subnetwork, final String applies_to, final String name, final ATTRIBUTE_DATA_TYPE type) {
        _applies_to = applies_to;
        _data_type = type;
        _subnetwork = subnetwork;
        _name = name;
        _values = null;
    }

    public CyTableColumnElement(final String applies_to, final String name, final ATTRIBUTE_DATA_TYPE type) {
        _applies_to = applies_to;
        _data_type = type;
        _subnetwork = null;
        _name = name;
        _values = null;
    }

    public final String getAppliesTo() {
        return _applies_to;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("name               : ");
        sb.append(_name);
        sb.append("\n");
        if (_subnetwork != null) {
            sb.append("property of network: ");
            sb.append(_subnetwork);
            sb.append("\n");
        }
        sb.append("applies to         : ");
        sb.append(_applies_to);
        sb.append("\n");
        sb.append("type               : ");
        sb.append(_data_type.toString());
        return sb.toString();
    }

}
