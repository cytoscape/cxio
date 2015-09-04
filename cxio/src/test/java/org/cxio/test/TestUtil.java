package org.cxio.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

final class TestUtil {

    final static String cyCxRoundTrip(final String input_cx) throws IOException {
        final CxReader p = CxReader.createInstance(input_cx, true, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, false, Util.getAllAvailableAspectFragmentWriters());

        w.start();
        w.writeAspectElements(res.get(NodesElement.NAME));
        w.writeAspectElements(res.get(EdgesElement.NAME));
        w.writeAspectElements(res.get(CartesianLayoutElement.NAME));
        w.writeAspectElements(res.get(NetworkAttributesElement.NAME));
        w.writeAspectElements(res.get(NodeAttributesElement.NAME));
        w.writeAspectElements(res.get(EdgeAttributesElement.NAME));
        w.writeAspectElements(res.get(CyVisualPropertiesElement.NAME));
        w.end();

        return out.toString();
    }

    final static String cyCxElementRoundTrip(final String input_cx) throws IOException {
        final CxElementReader p = CxElementReader.createInstance(input_cx, true, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxElementReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, false, Util.getAllAvailableAspectFragmentWriters());

        w.start();
        w.writeAspectElements(res.get(NodesElement.NAME));
        w.writeAspectElements(res.get(EdgesElement.NAME));
        w.writeAspectElements(res.get(CartesianLayoutElement.NAME));
        w.writeAspectElements(res.get(NetworkAttributesElement.NAME));
        w.writeAspectElements(res.get(NodeAttributesElement.NAME));
        w.writeAspectElements(res.get(EdgeAttributesElement.NAME));
        w.writeAspectElements(res.get(CyVisualPropertiesElement.NAME));
        w.end();

        return out.toString();
    }
}
