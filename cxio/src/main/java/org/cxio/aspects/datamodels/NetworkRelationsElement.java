package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

public final class NetworkRelationsElement implements AspectElement {

    final public static String NAME = "NetworkRelations";
    final public static String PARENT = "parent";
    final public static String CHILD = "child";

    final private String       _parent;
    final private String       _child;

    @Override
    public String getAspectName() {
        return NAME;
    }

    public NetworkRelationsElement(final String parent, final String child) {
        _parent = parent;
        _child = child;
    }

    public final String getParent() {
        return _parent;
    }

    public final String getChild() {
        return _child;
    }

}
