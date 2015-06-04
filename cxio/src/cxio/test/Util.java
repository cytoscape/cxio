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
import cxio.CxReader;
import cxio.EdgeAttributesFragmentWriter;
import cxio.EdgesFragmentWriter;
import cxio.JsonWriter;
import cxio.NodeAttributesFragmentWriter;
import cxio.NodesFragmentWriter;

final class Util {

    final static String cyCxRoundTrip(final String input_cx) throws IOException {
        final CxReader p = CxReader.createInstance(input_cx, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter jw = JsonWriter.createInstance(out);

        jw.start();
        NodesFragmentWriter.createInstance().write(res.get(CxConstants.NODES), jw);
        EdgesFragmentWriter.createInstance().write(res.get(CxConstants.EDGES), jw);
        CartesianLayoutFragmentWriter.createInstance().write(res.get(CxConstants.CARTESIAN_LAYOUT), jw);
        NodeAttributesFragmentWriter.createInstance().write(res.get(CxConstants.NODE_ATTRIBUTES), jw);
        EdgeAttributesFragmentWriter.createInstance().write(res.get(CxConstants.EDGE_ATTRIBUTES), jw);
        jw.end();

        return out.toString();
    }
}
