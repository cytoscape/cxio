package org.cxio.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.CyVisualPropertiesFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NetworkAttributesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

public class Examples4 {

    public static void main(final String[] args) throws IOException {
        final File f = new File("/Users/cmzmasek/Desktop/cx example files/0nn6.cx");
        final InputStream in = new FileInputStream(f);
        final Set<AspectFragmentReader> readers = new HashSet<>();
        readers.add(EdgesFragmentReader.createInstance());
        readers.add(NodesFragmentReader.createInstance());
        readers.add(CartesianLayoutFragmentReader.createInstance());
        readers.add(EdgeAttributesFragmentReader.createInstance());
        readers.add(NodeAttributesFragmentReader.createInstance());
        readers.add(NetworkAttributesFragmentReader.createInstance());
        readers.add(CyVisualPropertiesFragmentReader.createInstance());

        final CxReader p = CxReader.createInstance(in, readers);

        while (p.hasNext()) {
            final List<AspectElement> elements = p.getNext();
            if (!elements.isEmpty()) {
                final String aspect_name = elements.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                System.out.println("===========================================");
                for (final AspectElement element : elements) {
                    System.out.println(element.toString());
                }
            }
        }

    }

}
