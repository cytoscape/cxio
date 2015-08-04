package org.cxio.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.GroupFragmentReader;
import org.cxio.aspects.readers.NetworkAttributesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.aspects.readers.SubNetworkFragmentReader;
import org.cxio.aspects.readers.VisualPropertiesFragmentReader;
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgeAttributesFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.GroupFragmentWriter;
import org.cxio.aspects.writers.NetworkAttributesFragmentWriter;
import org.cxio.aspects.writers.NodeAttributesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.aspects.writers.SubNetworkFragmentWriter;
import org.cxio.aspects.writers.VisualPropertiesFragmentWriter;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.core.interfaces.AspectFragmentWriter;

public final class Util {

    final public static boolean isEmpty(final String s) {
        return (s == null) || (s.length() < 1);
    }

    public final static String writeAspectElementsToString(final ArrayList<AspectElement> elements,
                                                           final boolean use_default_pretty_printer) throws IOException {
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out,
                                                   use_default_pretty_printer,
                                                   getAllAvailableAspectFragmentWriters());

        w.start();
        w.writeAspectElements(elements);
        w.end();

        return out.toString();
    }

    public final static String writeAspectElementsToString(final String cx_string,
                                                           final boolean use_default_pretty_printer) throws IOException {
        final CxReader p = CxReader.createInstance(cx_string, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out,
                                                   use_default_pretty_printer,
                                                   getAllAvailableAspectFragmentWriters());
        w.start();
        w.writeAspectElements(res.get(NodesElement.NAME));
        w.writeAspectElements(res.get(EdgesElement.NAME));
        w.writeAspectElements(res.get(CartesianLayoutElement.NAME));
        w.writeAspectElements(res.get(NetworkAttributesElement.NAME));
        w.writeAspectElements(res.get(NodeAttributesElement.NAME));
        w.writeAspectElements(res.get(EdgeAttributesElement.NAME));
        w.writeAspectElements(res.get(VisualPropertiesElement.NAME));
        w.end();

        return out.toString();
    }

    public final static Set<AspectFragmentReader> getAllAvailableAspectFragmentReaders() {
        final AspectFragmentReader node_reader = NodesFragmentReader.createInstance();
        final AspectFragmentReader edge_reader = EdgesFragmentReader.createInstance();
        final AspectFragmentReader cartesian_layout_reader = CartesianLayoutFragmentReader.createInstance();
        final AspectFragmentReader network_attributes_reader = NetworkAttributesFragmentReader.createInstance();
        final AspectFragmentReader edge_attributes_reader = EdgeAttributesFragmentReader.createInstance();
        final AspectFragmentReader node_attributes_reader = NodeAttributesFragmentReader.createInstance();
        final AspectFragmentReader visual_properties_reader = VisualPropertiesFragmentReader.createInstance();
        final AspectFragmentReader group_reader = GroupFragmentReader.createInstance();
        final AspectFragmentReader subnetwork_reader = SubNetworkFragmentReader.createInstance();
        
        final Set<AspectFragmentReader> aspect_readers = new HashSet<AspectFragmentReader>();
        
        aspect_readers.add(node_reader);
        aspect_readers.add(edge_reader);
        aspect_readers.add(cartesian_layout_reader);
        aspect_readers.add(network_attributes_reader);
        aspect_readers.add(edge_attributes_reader);
        aspect_readers.add(node_attributes_reader);
        aspect_readers.add(visual_properties_reader);
        aspect_readers.add(group_reader);
        aspect_readers.add(subnetwork_reader );
        
        return aspect_readers;
    }

    public final static Set<AspectFragmentWriter> getAllAvailableAspectFragmentWriters() {
        final AspectFragmentWriter node_writer = NodesFragmentWriter.createInstance();
        final AspectFragmentWriter edge_writer = EdgesFragmentWriter.createInstance();
        final AspectFragmentWriter cartesian_layout_writer = CartesianLayoutFragmentWriter.createInstance();
        final AspectFragmentWriter network_attributes_writer = NetworkAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter edge_attributes_writer = EdgeAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter node_attributes_writer = NodeAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter visual_properties_writer = VisualPropertiesFragmentWriter.createInstance();
        final AspectFragmentWriter group_writer = GroupFragmentWriter.createInstance();
        final AspectFragmentWriter subnetwork_writer = SubNetworkFragmentWriter.createInstance();
            
        final Set<AspectFragmentWriter> aspect_writers = new HashSet<AspectFragmentWriter>();
        aspect_writers.add(node_writer);
        aspect_writers.add(edge_writer);
        aspect_writers.add(cartesian_layout_writer);
        aspect_writers.add(network_attributes_writer);
        aspect_writers.add(edge_attributes_writer);
        aspect_writers.add(node_attributes_writer);
        aspect_writers.add(visual_properties_writer);
        aspect_writers.add(group_writer);
        aspect_writers.add(subnetwork_writer);
        return aspect_writers;
    }
    
    public final static Set<AspectFragmentWriter> getAllAvailableAspectFragmentWriters( final String time_stamp) {
        final AspectFragmentWriter node_writer = NodesFragmentWriter.createInstance();
        final AspectFragmentWriter edge_writer = EdgesFragmentWriter.createInstance();
        final AspectFragmentWriter cartesian_layout_writer = CartesianLayoutFragmentWriter.createInstance();
        final AspectFragmentWriter network_attributes_writer = NetworkAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter edge_attributes_writer = EdgeAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter node_attributes_writer = NodeAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter visual_properties_writer = VisualPropertiesFragmentWriter.createInstance();
        final AspectFragmentWriter group_writer = GroupFragmentWriter.createInstance();
        final AspectFragmentWriter subnetwork_writer = SubNetworkFragmentWriter.createInstance();
            
        node_writer.setTimeStamp(time_stamp);
        edge_writer.setTimeStamp(time_stamp);
        cartesian_layout_writer.setTimeStamp(time_stamp);
        network_attributes_writer.setTimeStamp(time_stamp);
        edge_attributes_writer.setTimeStamp(time_stamp);
        node_attributes_writer.setTimeStamp(time_stamp);
        visual_properties_writer.setTimeStamp(time_stamp);
        group_writer.setTimeStamp(time_stamp);
        subnetwork_writer.setTimeStamp(time_stamp);
        
        final Set<AspectFragmentWriter> aspect_writers = new HashSet<AspectFragmentWriter>();
        aspect_writers.add(node_writer);
        aspect_writers.add(edge_writer);
        aspect_writers.add(cartesian_layout_writer);
        aspect_writers.add(network_attributes_writer);
        aspect_writers.add(edge_attributes_writer);
        aspect_writers.add(node_attributes_writer);
        aspect_writers.add(visual_properties_writer);
        aspect_writers.add(group_writer);
        aspect_writers.add(subnetwork_writer);
        return aspect_writers;
    }
    

}
