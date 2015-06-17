package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class CartesianLayoutFragmentReader implements AspectFragmentReader {

    private static final boolean STRICT_DEFAULT = false;
    private final boolean        _strict;

    public static CartesianLayoutFragmentReader createInstance() {
        return new CartesianLayoutFragmentReader(STRICT_DEFAULT);
    }

    public static CartesianLayoutFragmentReader createInstance(final boolean strict) {
        return new CartesianLayoutFragmentReader(strict);
    }

    private CartesianLayoutFragmentReader(final boolean strict) {
        _strict = strict;
    }

    @Override
    public String getAspectName() {
        return CartesianLayoutElement.CARTESIAN_LAYOUT;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + CartesianLayoutElement.CARTESIAN_LAYOUT + "'");
        }
        final List<AspectElement> layout_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {

                String node_id = null;
                String x = null;
                String y = null;

                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (CartesianLayoutElement.NODE.equals(namefield)) {
                        node_id = jp.getText().trim();
                    }
                    else if (CartesianLayoutElement.X.equals(namefield)) {

                        x = jp.getValueAsString();
                    }
                    else if (CartesianLayoutElement.Y.equals(namefield)) {
                        y = jp.getValueAsString();

                    }
                    else if (_strict) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }

                }
                if (Util.isEmpty(node_id)) {
                    throw new IOException("malformed cx json: node id in cartesian layout is missing");
                }
                if (x == null) {
                    throw new IOException("malformed cx json: x coordinate in cartesian layout is missing");
                }
                if (y == null) {
                    throw new IOException("malformed cx json: y coordinate in cartesian layout is missing");
                }
                layout_aspects.add(new CartesianLayoutElement(node_id, x, y));
            }
            t = jp.nextToken();
        }

        return layout_aspects;
    }

    public static void main(final String[] args) throws IOException {
        final String t0 = "["
                + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"nodes\":\"_0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"_1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edges\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"_0\",\"property\":\"property zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"_1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"}]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"}]}]},"
                + "{\"weHaveNodesToo\":[{\"nodes\":\"nodes\"}]},"
                + "{\"weHaveEdgesToo\":[{\"edges\":\"edges\"}]},"
                + "{\"nodes\":[{\"@id\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_0\",\"x\":\"123\",\"y\":\"456\"}]},"
                + "{\"nodes\":[{\"@id\":\"_4\"}]},"
                + "{\"nodes\":[{\"@id\":\"_6\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_1\",\"x\":\"3\",\"y\":\"4\"},{\"node\":\"_2\",\"x\":\"5\",\"y\":\"6\"}]},"
                + "{\"nodes\":[{\"@id\":\"_7\"}]}" + "]";
        final CxReader p = CxReader.createInstance(t0);

        p.addAspectFragmentReader(CartesianLayoutFragmentReader.createInstance(false));
        p.reset();
        while (p.hasNext()) {
            final List<AspectElement> elements = p.getNext();
            if (!elements.isEmpty()) {
                final String aspect_name = elements.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                for (final AspectElement element : elements) {
                    System.out.println(element.toString());
                }
            }
        }

    }

}
