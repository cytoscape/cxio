package org.cxio.aspects.datamodels;

public final class CyViewsElement extends AbstractAspectElement {

    public final static String ASPECT_NAME   = "cyViews";
    public final static String SUBWORKNET_ID = "s";
    public final static String VIEW_ID = "id";
    private final Long         _subnetwork_id;
    private final Long         _view_id;

    public CyViewsElement(final Long view_id, final Long subnetwork_id) {
        if (view_id == null ) {
            throw new IllegalArgumentException("view id must not be null");
        }
        if (subnetwork_id== null ) {
            throw new IllegalArgumentException("sub-network id id must not be null");
        }
        _view_id = view_id;
        _subnetwork_id = subnetwork_id;
    }

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    public Long getSubnetworkId() {
        return _subnetwork_id;
    }
    
    public Long getViewId() {
        return _view_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getViewId());
        sb.append("->");
        sb.append(getSubnetworkId());
        return sb.toString();
    }

}
