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
import org.cxio.aux.AspectElementCounts;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

final class TestUtil {

    final static String cyCxRoundTrip(final String input_cx, final boolean compare_counts) throws IOException {
        final CxReader p = CxReader.createInstance(input_cx, true, true, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);
        final AspectElementCounts cr = p.getAspectElementCounts();

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, false, Util.getAllAvailableAspectFragmentWriters());

        w.start();
        w.writeAspectElements(res.get(NodesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(EdgesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(CartesianLayoutElement.ASPECT_NAME));
        w.writeAspectElements(res.get(NetworkAttributesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(NodeAttributesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(EdgeAttributesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(CyVisualPropertiesElement.ASPECT_NAME));
        w.end(true, "");

        if (compare_counts) {
            final AspectElementCounts cw = w.getAspectElementCounts();
            if (!AspectElementCounts.isCountsAreEqual(cw, cr)) {
                throw new IllegalStateException("counts are not equal:\n" + cw + "\n" + cr);
            }
        }

        return out.toString();
    }

    final static String cyCxElementRoundTrip(final String input_cx, final boolean compare_counts) throws IOException {
        final CxElementReader p = CxElementReader.createInstance(input_cx, true, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxElementReader.parseAsMap(p);
        final AspectElementCounts cr = p.getAspectElementCounts();

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, false, Util.getAllAvailableAspectFragmentWriters());

        w.start();
        w.writeAspectElements(res.get(NodesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(EdgesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(CartesianLayoutElement.ASPECT_NAME));
        w.writeAspectElements(res.get(NetworkAttributesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(NodeAttributesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(EdgeAttributesElement.ASPECT_NAME));
        w.writeAspectElements(res.get(CyVisualPropertiesElement.ASPECT_NAME));
        w.end(true, "");

        if (compare_counts) {
            final AspectElementCounts cw = w.getAspectElementCounts();
            if (!AspectElementCounts.isCountsAreEqual(cw, cr)) {
                throw new IllegalStateException("counts are not equal:\n" + cw + "\n" + cr);
            }

        }

        return out.toString();
    }
}
