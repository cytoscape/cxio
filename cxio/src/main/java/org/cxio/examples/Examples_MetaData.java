package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.aux.AspectElementCounts;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.metadata.MetaDataCollection;
import org.cxio.metadata.MetaDataElement;
import org.cxio.util.CxioUtil;

public class Examples_MetaData {

    public static void main(final String[] args) throws IOException {

        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement(0, 0, 1));
        edges_elements.add(new EdgesElement(1, 0, 2));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("0"));
        nodes_elements.add(new NodesElement("1"));
        nodes_elements.add(new NodesElement("2"));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement(0, 12, 21, 1));
        cartesian_elements.add(new CartesianLayoutElement(1, 42, 23, 2));
        cartesian_elements.add(new CartesianLayoutElement(2, 34, 23, 3));

        final EdgeAttributesElement ea0 = new EdgeAttributesElement(0, "name", "A", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea1 = new EdgeAttributesElement(0, "weight", "2", ATTRIBUTE_DATA_TYPE.INTEGER);
        final EdgeAttributesElement ea2 = new EdgeAttributesElement(1, "name", "B", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea3 = new EdgeAttributesElement(1, "weight", "3", ATTRIBUTE_DATA_TYPE.INTEGER);

        final List<AspectElement> edge_attributes_elements = new ArrayList<AspectElement>();
        edge_attributes_elements.add(ea0);
        edge_attributes_elements.add(ea1);
        edge_attributes_elements.add(ea2);
        edge_attributes_elements.add(ea3);

        final ArrayList<String> v0 = new ArrayList<String>();
        v0.add("0.0");
        v0.add("0.1");
        final ArrayList<String> v1 = new ArrayList<String>();
        v1.add("1.0");
        v1.add("1.1");
        final ArrayList<String> v2 = new ArrayList<String>();
        v2.add("2.0");
        v2.add("2.1");

        final NodeAttributesElement na0 = new NodeAttributesElement(0, "expression", v0, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na1 = new NodeAttributesElement(1, "expression", v1, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na2 = new NodeAttributesElement(2, "expression", v2, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);

        final ArrayList<Long> n = new ArrayList<Long>();
        n.add(0L);
        n.add(1L);
        n.add(2L);

        final NodeAttributesElement na3 = new NodeAttributesElement(1, n, "species", "Mus musculus", ATTRIBUTE_DATA_TYPE.STRING);

        final List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        // Meta data
        // ---------
        final MetaDataCollection md0 = new MetaDataCollection();

        final MetaDataElement meta_element1 = new MetaDataElement();

        meta_element1.put("edges", 2);
        meta_element1.put("nodes", 3);
        meta_element1.put("date", "150914");
        final int[] c = new int[] { 1, 2 };
        final double[] d = new double[] { 1.11, 2.22, 3.33 };
        final List<Object> li = new ArrayList<Object>();
        li.add(c);
        li.add(d);
        meta_element1.put("some datastructure", li);
        meta_element1.put("some booleans", new boolean[] { true, false });
        md0.add(meta_element1);
        final MetaDataCollection md1 = new MetaDataCollection();
        final MetaDataElement meta_element2 = new MetaDataElement();
        meta_element2.put("checksum", 23932);
        md1.add(meta_element2);

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);
        w.addPreMetaData(md0);
        w.addPostMetaData(md1);

        w.start();
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(cartesian_elements);
        w.writeAspectElements(edge_attributes_elements);
        w.writeAspectElements(node_attributes_elements);
        w.end(true, "");

        final String cx_json_str = out.toString();

        final AspectElementCounts cw = w.getAspectElementCounts();

        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers = new HashSet<>();

        final EdgesFragmentReader er = EdgesFragmentReader.createInstance();

        readers.add(er);
        readers.add(NodesFragmentReader.createInstance());
        readers.add(CartesianLayoutFragmentReader.createInstance());
        readers.add(EdgeAttributesFragmentReader.createInstance());
        readers.add(NodeAttributesFragmentReader.createInstance());
        final CxReader p = CxReader.createInstance(cx_json_str, true, true, readers);

        System.out.println();

        System.out.println("Pre meta datas:");

        System.out.println(p.getPreMetaData());

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

        System.out.println("Post meta datas:");

        System.out.println(p.getPostMetaData());
        System.out.println();

        final AspectElementCounts cr = p.getAspectElementCounts();
        System.out.println(cr);
        CxioUtil.validate(w.getMd5Checksum(), p.getMd5Checksum(), cw, cr);

    }

}
