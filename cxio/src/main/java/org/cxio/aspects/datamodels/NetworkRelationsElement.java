package org.cxio.aspects.datamodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;

/**
 * This is used to represent hierarchical relationship between networks/sub-networks/views.
 *
 *
 * @author cmzmasek
 *
 */
public final class NetworkRelationsElement extends AbstractAspectElement {

    private enum RELATIONSHIP_TYPE {
        SUBNETWORK, VIEW;
    }

    final public static String      CHILD           = "c";
    final public static String      ASPECT_NAME     = "networkRelations";
    final public static String      PARENT          = "p";
    final public static String      TYPE_SUBNETWORK = "subnetwork";
    final public static String      RELATIONSHIP    = "r";
    final public static String      CHILD_NAME      = "name";
    final public static String      TYPE_VIEW       = "view";

    final private Long              _child;
    final private Long              _parent;
    final private RELATIONSHIP_TYPE _relationship;
    final private String            _child_name;

    public NetworkRelationsElement(final Long parent, final Long child, final String relationship, final String child_name) throws IOException {
        _parent = parent;
        _child = child;
        _relationship = determineRelationship(relationship);
        _child_name = child_name;
    }

    public NetworkRelationsElement(final Long child, final String relationship, final String child_name) throws IOException {
        _parent = null;
        _child = child;
        _relationship = determineRelationship(relationship);
        _child_name = child_name;
    }

    public NetworkRelationsElement(final Long child, final String child_name) throws IOException {
        _parent = null;
        _child = child;
        _relationship = RELATIONSHIP_TYPE.SUBNETWORK;
        _child_name = child_name;
    }

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    public final Long getChild() {
        return _child;
    }

    public final Long getParent() {
        return _parent;
    }

    public final String getChildName() {
        return _child_name;
    }

    public final String getRelationship() {
        switch (_relationship) {
        case SUBNETWORK:
            return TYPE_SUBNETWORK;
        case VIEW:
            return TYPE_VIEW;
        default:
            throw new IllegalStateException("don't know how to handle relationship '" + _relationship + "'");
        }
    }

    public final static Set<Long> getAllSubNetworkParentNetworkIds(final List<AspectElement> networks_relations) {
        final Set<Long> parents = new HashSet<Long>();
        for (final AspectElement e : networks_relations) {
            final NetworkRelationsElement nwe = (NetworkRelationsElement) e;
            if (nwe.getRelationship() == TYPE_SUBNETWORK) {
                parents.add(nwe.getParent());
            }
        }
        return parents;
    }

    public final static List<Long> getSubNetworkIds(final Long parent_id, final List<AspectElement> networks_relations) {
        final List<Long> subnets = new ArrayList<Long>();
        for (final AspectElement e : networks_relations) {
            final NetworkRelationsElement nwe = (NetworkRelationsElement) e;
            if ((nwe.getRelationship() == TYPE_SUBNETWORK) && (nwe.getParent() == parent_id)) {
                subnets.add(nwe.getChild());
            }
        }
        return subnets;
    }

    private final static RELATIONSHIP_TYPE determineRelationship(final String type) throws IOException {
        if (CxioUtil.isEmpty(type)) {
            return RELATIONSHIP_TYPE.SUBNETWORK;
        }
        else if (type.equalsIgnoreCase(TYPE_SUBNETWORK)) {
            return RELATIONSHIP_TYPE.SUBNETWORK;
        }
        else if (type.equalsIgnoreCase(TYPE_VIEW)) {
            return RELATIONSHIP_TYPE.VIEW;
        }
        else {
            throw new IOException("unknown relationship type '" + type + "'");
        }
    }

}
