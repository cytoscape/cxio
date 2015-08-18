package org.cxio.aspects.datamodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.core.interfaces.AspectElement;

public final class NetworkRelationsElement implements AspectElement {

    private enum RELATIONSHIP_TYPE {
        SUBNETWORK, VIEW;
    }

    final public static String      CHILD           = "child";
    final public static String      NAME            = "networkRelations";
    final public static String      PARENT          = "parent";
    final private static String     SUBNETWORK_TYPE = "subnetwork";
    final public static String      TYPE            = "type";
    final private static String     VIEW_TYPE       = "view";

    final private String            _child;
    final private String            _parent;
    final private RELATIONSHIP_TYPE _type;

    public NetworkRelationsElement(final String parent, final String child, final String type) throws IOException {
        _parent = parent;
        _child = child;
        _type = determineType(type);
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    public final String getChild() {
        return _child;
    }

    public final String getParent() {
        return _parent;
    }

    public final String getType() {
        switch (_type) {
        case SUBNETWORK:
            return SUBNETWORK_TYPE;
        case VIEW:
            return VIEW_TYPE;
        default:
            throw new IllegalStateException("don't know how to handle type '" + _type + "'");
        }
    }

    public final static Set<String> getAllParentNetworkIds(final List<AspectElement> networks_relations) {
        final Set<String> parents = new HashSet<String>();
        for (final AspectElement e : networks_relations) {
            final NetworkRelationsElement nwe = (NetworkRelationsElement) e;
            if (nwe.getType() == SUBNETWORK_TYPE) {
                parents.add(nwe.getParent());
            }
        }
        return parents;
    }

    public final static List<String> getSubNetworkIds(final String parent_id, final List<AspectElement> networks_relations) {
        final List<String> subnets = new ArrayList<String>();
        for (final AspectElement e : networks_relations) {
            final NetworkRelationsElement nwe = (NetworkRelationsElement) e;
            if ((nwe.getType() == SUBNETWORK_TYPE) && nwe.getParent().equals(parent_id)) {
                subnets.add(nwe.getChild());
            }
        }
        return subnets;
    }

    private final static RELATIONSHIP_TYPE determineType(final String type) throws IOException {
        if (type.equalsIgnoreCase(SUBNETWORK_TYPE)) {
            return RELATIONSHIP_TYPE.SUBNETWORK;
        }
        else if (type.equalsIgnoreCase(VIEW_TYPE)) {
            return RELATIONSHIP_TYPE.VIEW;
        }
        else {
            throw new IOException("unknown relationship type '" + type + "'");
        }
    }

}
