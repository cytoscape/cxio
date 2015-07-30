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
    private  final double       _y;
    private final double       _z;
    private final boolean _z_set;
    public final static String NODE = "node";
    public final static String NAME = "cartesianLayout";
    public final static String Y    = "y";
    public final static String X    = "x";
    public final static String Z    = "z";

    public CartesianLayoutElement(final String node, final String x, final String y) {
        _node = node;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
        _z = 0;
        _z_set = false;
    }

    public CartesianLayoutElement(final String node, final double x, final double y) {
        _node = node;
        _x = x;
        _y = y;
        _z = 0;
        _z_set = false;
    }

    public CartesianLayoutElement(final long node, final double x, final double y) {
        _node = String.valueOf(node);
        _x = x;
        _y = y;
        _z = 0;
        _z_set = false;
    }
    
    public CartesianLayoutElement(final String node, final String x, final String y, final String z) {
        _node = node;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
        _z = Double.parseDouble(z);
        _z_set = true;
    }

    public CartesianLayoutElement(final String node, final double x, final double y, final double z) {
        _node = node;
        _x = x;
        _y = y;
        _z = z;
        _z_set = true;
    }

    public CartesianLayoutElement(final long node, final double x, final double y, final double z) {
        _node = String.valueOf(node);
        _x = x;
        _y = y;
        _z = z;
        _z_set = true;
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
    
    public double getZ() {
        return _z;
    }
    
    public boolean isZSet() {
        return _z_set;
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
        if ( _z_set ) {
            sb.append(", z: ");
            sb.append(_z);
        }
        return sb.toString();
    }

}
