package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

public abstract class AbstractAspectElement implements AspectElement {

    @Override
    public int compareTo(final AspectElement o) {
        if ((o != null) && (o.getAspectName() != null) && (getAspectName() != null)) {
            return getAspectName().compareTo(o.getAspectName());
        }
        return 0;
    }

}
