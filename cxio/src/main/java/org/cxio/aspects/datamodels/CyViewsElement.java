package org.cxio.aspects.datamodels;

public final class CyViewsElement extends AbstractAspectElement {

    public final static String ASPECT_NAME   = "cyViews";
    public final static String SUBWORKNET_ID = "s";
    private final long         _subnetwork_id;

    public CyViewsElement(final long subnetwork_id) {
        _subnetwork_id = subnetwork_id;
    }

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    public long getSubnetworkId() {
        return _subnetwork_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ASPECT_NAME);
        sb.append(": ");
        sb.append("subnetwork: ");
        sb.append(getSubnetworkId());
        return sb.toString();
    }

}
