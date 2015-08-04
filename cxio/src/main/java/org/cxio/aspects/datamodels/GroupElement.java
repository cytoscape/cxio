package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

public final class GroupElement implements AspectElement {
    
    final public static String NAME           = "Group";

    @Override
    public final String getAspectName() {
        return NAME;
    }

}
