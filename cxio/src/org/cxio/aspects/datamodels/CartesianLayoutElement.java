package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

public final class CartesianLayoutElement implements AspectElement {

    private final String       node;
    private final double       x;
    private final double       y;
    public final static String NODE             = "node";
    public final static String CARTESIAN_LAYOUT = "cartesianLayout";
    public final static String Y                = "y";
    public final static String X                = "x";

    public CartesianLayoutElement(final String node, final String x, final String y) {
        this.node = node;
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public CartesianLayoutElement(final String node, final double x, final double y) {
        this.node = node;
        this.x = x;
        this.y = y;
    }

    public CartesianLayoutElement(final long node, final double x, final double y) {
        this.node = String.valueOf(node);
        this.x = x;
        this.y = y;
    }

    @Override
    public String getAspectName() {
        return CartesianLayoutElement.CARTESIAN_LAYOUT;
    }

    public String getNode() {
        return node;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("node: ");
        sb.append(node);
        sb.append(", x: ");
        sb.append(x);
        sb.append(", y: ");
        sb.append(y);
        return sb.toString();
    }

}
