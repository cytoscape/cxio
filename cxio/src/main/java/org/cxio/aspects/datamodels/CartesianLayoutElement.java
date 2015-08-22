package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

/**
 * This class is used to represent the position of a network node in x, y, z coordinates.
 *
 * @author cmzmasek
 *
 */
public final class CartesianLayoutElement implements AspectElement {

    public final static String NAME = "cartesianLayout";
    public final static String NODE = "node";
    public final static String VIEW = "view";
    public final static String X    = "x";
    public final static String Y    = "y";
    public final static String Z    = "z";
    private final String       _node;
    private final String       _view;
    private final double       _x;
    private final double       _y;
    private final double       _z;

    public CartesianLayoutElement(final String node, final double x, final double y, final double z) {
        _node = node;
        _view = null;
        _x = x;
        _y = y;
        _z = z;
    }

    public CartesianLayoutElement(final String node, final String view, final double x, final double y, final double z) {
        _node = node;
        _view = view;
        _x = x;
        _y = y;
        _z = z;
    }

    public CartesianLayoutElement(final String node, final String x, final String y, final String z) {
        _node = node;
        _view = null;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
        _z = Double.parseDouble(z);
    }

    public CartesianLayoutElement(final String node, final String view, final String x, final String y, final String z) {
        _node = node;
        _view = view;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
        _z = Double.parseDouble(z);
    }

    @Override
    public String getAspectName() {
        return CartesianLayoutElement.NAME;
    }

    final public String getView() {
        return _view;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("node: ");
        sb.append(_node);
        if (!Util.isEmpty(_view)) {
            sb.append(", view: ");
            sb.append(_view);
        }
        sb.append(", x: ");
        sb.append(_x);
        sb.append(", y: ");
        sb.append(_y);
        sb.append(", z: ");
        sb.append(_z);
        return sb.toString();
    }

}
