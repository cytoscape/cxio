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
import cxio.CxWriter;
import cxio.EdgeAttributesFragmentWriter;
import cxio.EdgesFragmentWriter;
import cxio.NodeAttributesFragmentWriter;
import cxio.NodesFragmentWriter;

final class Util {

    final static String cyCxRoundTrip(final String input_cx) throws IOException {
        final CxReader p = CxReader.createInstance(input_cx, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        w.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w.start();
        w.write(res.get(CxConstants.NODES));
        w.write(res.get(CxConstants.EDGES));
        w.write(res.get(CxConstants.CARTESIAN_LAYOUT));
        w.write(res.get(CxConstants.NODE_ATTRIBUTES));
        w.write(res.get(CxConstants.EDGE_ATTRIBUTES));
        w.end();

        return out.toString();
    }
}
