package org.cxio.aspects.datamodels;

import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;

/**
 * This class is used to represent a Cytoscape edge aspect element.
 *
 *
 * @author cmzmasek
 *
 */
public final class EdgesElement implements AspectElement {

    final private String       _id;
    final private String       _source;
    final private String       _target;
    public final static String TARGET_NODE_ID = "target";
    public final static String SOURCE_NODE_ID = "source";
    public final static String ID             = CxConstants.ID;
    final public static String NAME           = "edges";

    public EdgesElement(final String id, final String source, final String target) {
        _id = id;
        _source = source;
        _target = target;
    }

    public EdgesElement(final long id, final String source, final String target) {
        _id = String.valueOf(id);
        _source = source;
        _target = target;
    }

    public EdgesElement(final long id, final long source, final long target) {
        _id = String.valueOf(id);
        _source = String.valueOf(source);
        _target = String.valueOf(target);
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

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getId());
        sb.append(" ");
        sb.append(getSource());
        sb.append(" ");
        sb.append(getTarget());
        return sb.toString();
    }

}
