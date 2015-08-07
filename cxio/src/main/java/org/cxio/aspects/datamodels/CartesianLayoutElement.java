package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

/**
 * This class is used to represent a Cytoscape layout aspect element.
 *
 * @author cmzmasek
 *
 */
public final class CartesianLayoutElement implements AspectElement {

    public final static String NAME = "cartesianLayout";
    public final static String NODE = "node";
    public final static String NETWORK = "network";
    public final static String X    = "x";
    public final static String Y    = "y";
    public final static String Z    = "z";
    private final String       _node;
    private  String       _network;
    private final double       _x;
    private final double       _y;
    private final double       _z;

   

    public CartesianLayoutElement(final String node, final double x, final double y, final double z) {
        _node = node;
        _x = x;
        _y = y;
        _z = z;
        _network = null;
    }
    
    public CartesianLayoutElement(final String node, final double x, final double y, final double z, final String network) {
        _node = node;
        _x = x;
        _y = y;
        _z = z;
        _network = network;
    }

    public CartesianLayoutElement(final String node, final String x, final String y, final String z) {
        _node = node;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
        _z = Double.parseDouble(z);
        _network = null;
    }
    
    public CartesianLayoutElement(final String node, final String x, final String y, final String z, final String network) {
        _node = node;
        _x = Double.parseDouble(x);
        _y = Double.parseDouble(y);
        _z = Double.parseDouble(z);
        _network = network;
    }

    final public String getNetwork() {
        return _network;
    }
    
    final public void setNetwork( String network ) {
        _network = network;
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

    

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("node: ");
        sb.append(_node);
        sb.append(", x: ");
        sb.append(_x);
        sb.append(", y: ");
        sb.append(_y);
            sb.append(", z: ");
            sb.append(_z);
        
        return sb.toString();
    }

}
