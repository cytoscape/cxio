package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

public class ExamplesM {

    public static void main(final String[] args) throws IOException {

        // Creating some CX formatted data and writing it to cx_json_str
        // -------------------------------------------------------------
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1", "rel 1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2", "rel 2"));
        edges_elements.add(new EdgesElement("edge2", "node0", "node3", "rel 3"));
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);

        w.start();
        w.writeAspectElements(edges_elements);
        w.end();

        final String cx_json_str = out.toString();

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers = new HashSet<>();
        readers.add(EdgesFragmentReader.createInstance());

        final CxElementReader reader = CxElementReader.createInstance(cx_json_str, false, // Reading
                                                                                          // anonymous
                                                                                          // elements
                                                                      false, // Checksum
                                                                             // calculation
                                                                      readers);

        for (final AspectElement e : reader) {
            if (e.getAspectName() == EdgesElement.NAME) {
                final EdgesElement ee = (EdgesElement) e;
                final String s = ee.getSource();
                final String t = ee.getTarget();
                final String r = ee.getRelationship();
                System.out.println(ee);
            }
        }
    }

}
