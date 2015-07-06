package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CartesianLayoutFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public static CartesianLayoutFragmentReader createInstance() {
        return new CartesianLayoutFragmentReader();
    }

    private CartesianLayoutFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public final String getAspectName() {
        return CartesianLayoutElement.NAME;
    }

    @Override
    public final List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + CartesianLayoutElement.NAME + "'");
        }
        final List<AspectElement> layout_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                layout_aspects.add(new CartesianLayoutElement(
                        Util.getTextValueRequired(o, CartesianLayoutElement.NODE), Util.getTextValueRequired(o,
                                CartesianLayoutElement.X), Util.getTextValueRequired(o, CartesianLayoutElement.Y)));
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

        final Set<AspectFragmentReader> readers = new HashSet<>();
        readers.add(CartesianLayoutFragmentReader.createInstance());
        final CxReader p = CxReader.createInstance(t0, readers);

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
