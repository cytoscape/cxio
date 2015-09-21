package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxConstants;
import org.cxio.util.Util;

/**
 * This class is used to represent a edge in a network.
 *
 *
 * @author cmzmasek
 *
 */
public final class EdgesElement implements AspectElement {

    public final static String ID             = CxConstants.ID;
    final public static String NAME           = "edges";
    public final static String SOURCE_NODE_ID = "s";
    public final static String TARGET_NODE_ID = "t";
    public final static String RELATIONSHIP   = "r";
    final private String       _id;
    final private String       _source;
    final private String       _target;
    final private String       _relationship;

    public EdgesElement(final long id, final long source, final long target) {
        _id = String.valueOf(id);
        _source = String.valueOf(source);
        _target = String.valueOf(target);
        _relationship = null;
    }

    public EdgesElement(final long id, final String source, final String target) {
        _id = String.valueOf(id);
        _source = source;
        _target = target;
        _relationship = null;
    }

    public EdgesElement(final String id, final String source, final String target) {
        _id = id;
        _source = source;
        _target = target;
        _relationship = null;
    }

    public EdgesElement(final long id, final long source, final long target, final String relationship) {
        _id = String.valueOf(id);
        _source = String.valueOf(source);
        _target = String.valueOf(target);
        _relationship = relationship;
    }

    public EdgesElement(final long id, final String source, final String target, final String relationship) {
        _id = String.valueOf(id);
        _source = source;
        _target = target;
        _relationship = relationship;
    }

    public EdgesElement(final String id, final String source, final String target, final String relationship) {
        _id = id;
        _source = source;
        _target = target;
        _relationship = relationship;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof EdgesElement) && _id.equals(((EdgesElement) o).getId());

    }

    @Override
    public String getAspectName() {
        return EdgesElement.NAME;
    }

    public final String getId() {
        return _id;
    }

    public final String getSource() {
        return _source;
    }

    public final String getTarget() {
        return _target;
    }

    public final String getRelationship() {
        return _relationship;
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(NAME);
        sb.append(": ");
        sb.append(getId());
        sb.append(" ");
        sb.append(getSource());
        sb.append("->");
        sb.append(getTarget());
        if (!Util.isEmpty(getRelationship())) {
            sb.append(" ");
            sb.append(getRelationship());
        }
        return sb.toString();
    }

}
