package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

public final class CyViewsElement implements AspectElement {

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

}
