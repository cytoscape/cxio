package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

/**
 * This class is used to represent a Cytoscape layout aspect element.
 *
 * @author cmzmasek
 *
 */
public final class CartesianLayoutElement implements AspectElement {

    private final String       _node;
    private final double       _x;
    private final double       _y;
    public final static String NODE = "node";
    public final static String NAME = "cartesianLayout";
    public final static String Y    = "y";
    public final static String X    = "x";

    public CartesianLayoutElement(final String node, final String x, final String y) {
        _node = node;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
    }

    public CartesianLayoutElement(final String node, final double x, final double y) {
        _node = node;
        _x = x;
        _y = y;
    }

    public CartesianLayoutElement(final long node, final double x, final double y) {
        _node = String.valueOf(node);
        _x = x;
        _y = y;
    }

    @Override
    public String getAspectName() {
        return CartesianLayoutElement.NAME;
    }

    public String getNode() {
        return _node;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("node: ");
        sb.append(_node);
        sb.append(", x: ");
        sb.append(_x);
        sb.append(", y: ");
        sb.append(_y);
        return sb.toString();
    }

}
