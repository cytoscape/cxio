package org.cxio.aspects.datamodels;


public final class CyViewsElement extends AbstractAspectElement {

    public final static String NAME          = "cyViews";
    public final static String SUBWORKNET_ID = "s";
    private final String       _subnetwork_id;

    public CyViewsElement(final String subnetwork_id) {
        _subnetwork_id = subnetwork_id;
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    public String getSubnetworkId() {
        return _subnetwork_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(NAME);
        sb.append(": ");
        sb.append("subnetwork: ");
        sb.append(getSubnetworkId());
        return sb.toString();
    }

}
