package org.cxio.aspects.datamodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.core.interfaces.AspectElement;

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
    final public static String      SUBNETWORK_TYPE = "subnetwork";
    final public static String      RELATIONSHIP    = "r";
    final public static String      CHILD_NAME      = "name";
    final public static String      VIEW_TYPE       = "view";

    final private String            _child;
    final private String            _parent;
    final private RELATIONSHIP_TYPE _relationship;
    final private String            _child_name;

    public NetworkRelationsElement(final String parent, final String child, final String relationship, final String child_name) throws IOException {
        _parent = parent;
        _child = child;
        _relationship = determineRelationship(relationship);
        _child_name = child_name;
    }

    public NetworkRelationsElement(final String child, final String relationship, final String child_name) throws IOException {
        _parent = null;
        _child = child;
        _relationship = determineRelationship(relationship);
        _child_name = child_name;
    }

    public NetworkRelationsElement(final String child, final String child_name) throws IOException {
        _parent = null;
        _child = child;
        _relationship = null;
        _child_name = child_name;
    }

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    public final String getChild() {
        return _child;
    }

    public final String getParent() {
        return _parent;
    }

    public final String getChildName() {
        return _child_name;
    }

    public final String getRelationship() {
        switch (_relationship) {
        case SUBNETWORK:
            return SUBNETWORK_TYPE;
        case VIEW:
            return VIEW_TYPE;
        default:
            throw new IllegalStateException("don't know how to handle relationship '" + _relationship + "'");
        }
    }

    public final static Set<String> getAllParentNetworkIds(final List<AspectElement> networks_relations) {
        final Set<String> parents = new HashSet<String>();
        for (final AspectElement e : networks_relations) {
            final NetworkRelationsElement nwe = (NetworkRelationsElement) e;
            if (nwe.getRelationship() == SUBNETWORK_TYPE) {
                parents.add(nwe.getParent());
            }
        }
        return parents;
    }

    public final static List<String> getSubNetworkIds(final String parent_id, final List<AspectElement> networks_relations) {
        final List<String> subnets = new ArrayList<String>();
        for (final AspectElement e : networks_relations) {
            final NetworkRelationsElement nwe = (NetworkRelationsElement) e;
            if ((nwe.getRelationship() == SUBNETWORK_TYPE) && nwe.getParent().equals(parent_id)) {
                subnets.add(nwe.getChild());
            }
        }
        return subnets;
    }

    private final static RELATIONSHIP_TYPE determineRelationship(final String type) throws IOException {
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
