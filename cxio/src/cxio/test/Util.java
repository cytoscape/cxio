package cxio.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.SortedMap;

import cxio.AspectElement;
import cxio.AspectFragmentReaderManager;
import cxio.CartesianLayoutFragmentWriter;
import cxio.CxConstants;
import cxio.CxParser;
import cxio.EdgeAttributesFragmentWriter;
import cxio.EdgesFragmentWriter;
import cxio.JsonWriter;
import cxio.NodeAttributesFragmentWriter;
import cxio.NodesFragmentWriter;

final class Util {

    final static String cyCxRoundTrip(final String input_cx) throws IOException {
        final CxParser p = CxParser.createInstance(input_cx, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        final SortedMap<String, List<AspectElement>> res = CxParser.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter jw = JsonWriter.createInstance(out);

        jw.start();
        NodesFragmentWriter.createInstance(jw).write(res.get(CxConstants.NODES));
        EdgesFragmentWriter.createInstance(jw).write(res.get(CxConstants.EDGES));
        CartesianLayoutFragmentWriter.createInstance(jw).write(res.get(CxConstants.CARTESIAN_LAYOUT));
        NodeAttributesFragmentWriter.createInstance(jw).write(res.get(CxConstants.NODE_ATTRIBUTES));
        EdgeAttributesFragmentWriter.createInstance(jw).write(res.get(CxConstants.EDGE_ATTRIBUTES));
        jw.end();

        return out.toString();
    }
}
