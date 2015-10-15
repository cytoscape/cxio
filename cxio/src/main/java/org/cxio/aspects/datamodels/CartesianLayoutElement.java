package org.cxio.aspects.datamodels;

import org.cxio.util.Util;

/**
 * This class is used to represent the position of a network node in x, y, z coordinates.
 *
 * @author cmzmasek
 *
 */
public final class CartesianLayoutElement extends AbstractAspectElement {

    public final static String NAME = "cartesianLayout";
    public final static String NODE = "node";
    public final static String VIEW = "view";
    public final static String X    = "x";
    public final static String Y    = "y";
    public final static String Z    = "z";
    private final String       _node;
    private final String       _view;
    private final String       _x;
    private final String       _y;
    private final String       _z;
    private final boolean      _z_set;

    public CartesianLayoutElement(final String node, final double x, final double y, final double z) {
        _node = node;
        _view = null;
        _x = String.valueOf(x);
        _y = String.valueOf(y);
        _z = String.valueOf(z);
        _z_set = true;
    }

    public CartesianLayoutElement(final String node, final String view, final double x, final double y, final double z) {
        _node = node;
        _view = view;
        _x = String.valueOf(x);
        _y = String.valueOf(y);
        _z = String.valueOf(z);
        _z_set = true;
    }

    public CartesianLayoutElement(final String node, final String view, final String x, final String y) {
        _node = node;
        _view = view;
        _x = x;
        _y = y;
        _z = String.valueOf(0);
        _z_set = false;
    }

    public CartesianLayoutElement(final String node, final String view, final String x, final String y, final String z) {
        _node = node;
        _view = view;
        _x = x;
        _y = y;
        _z = z;
        _z_set = true;
    }

    public CartesianLayoutElement(final String node, final double x, final double y) {
        _node = node;
        _view = null;
        _x = String.valueOf(x);
        _y = String.valueOf(y);
        _z = String.valueOf(0);
        _z_set = false;
    }

    public CartesianLayoutElement(final String node, final String view, final double x, final double y) {
        _node = node;
        _view = view;
        _x = String.valueOf(x);
        _y = String.valueOf(y);
        _z = String.valueOf(0);
        _z_set = false;
    }

    public CartesianLayoutElement(final String node, final String x, final String y) {
        _node = node;
        _view = null;
        _x = x;
        _y = y;
        _z = String.valueOf(0);
        _z_set = false;
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

    public String getX() {
        return _x;
    }

    public String getY() {
        return _y;
    }

    public String getZ() {
        return _z;
    }

    public boolean isZset() {
        return _z_set;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(NAME);
        sb.append(": ");
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
        if (_z_set) {
            sb.append(", z: ");
            sb.append(_z);
        }
        return sb.toString();
    }

}
