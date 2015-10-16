package org.cxio.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.aspects.datamodels.CyViewsElement;
import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.aspects.datamodels.NetworkRelationsElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.CyGroupsFragmentReader;
import org.cxio.aspects.readers.CyViewsFragmentReader;
import org.cxio.aspects.readers.CyVisualPropertiesFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.HiddenAttributesFragmentReader;
import org.cxio.aspects.readers.NetworkAttributesFragmentReader;
import org.cxio.aspects.readers.NetworkRelationsFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.aspects.readers.SubNetworkFragmentReader;
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.CyGroupsFragmentWriter;
import org.cxio.aspects.writers.CyViewsFragmentWriter;
import org.cxio.aspects.writers.EdgeAttributesFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.HiddenAttributesFragmentWriter;
import org.cxio.aspects.writers.NetworkAttributesFragmentWriter;
import org.cxio.aspects.writers.NetworkRelationsFragmentWriter;
import org.cxio.aspects.writers.NodeAttributesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.aspects.writers.SubNetworkFragmentWriter;
import org.cxio.aspects.writers.VisualPropertiesFragmentWriter;
import org.cxio.core.AspectElementCounts;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.core.interfaces.AspectFragmentWriter;

public final class Util {

    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String MD5            = "MD5";

    final public static boolean isEmpty(final String s) {
        return (s == null) || (s.length() < 1);
    }

    public final static boolean isAreByteArraysEqual(final byte[] a0, final byte[] a1) {
        if (a0.length != a1.length) {
            return false;
        }
        for (int i = 0; i < a1.length; ++i) {
            if (a0[i] != a1[i]) {
                return false;
            }
        }
        return true;
    }

    public final static String writeAspectElementsToString(final ArrayList<AspectElement> elements, final boolean use_default_pretty_printer) throws IOException {
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, use_default_pretty_printer, getAllAvailableAspectFragmentWriters());

        w.start();
        w.writeAspectElements(elements);
        w.end();

        return out.toString();
    }

    final public static int countChars(final String str, final char c) {
        int count = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == c) {
                ++count;
            }
        }
        return count;
    }

    public final static String getCurrentDate(final String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public final static String writeAspectElementsToString(final String cx_string, final boolean use_default_pretty_printer) throws IOException {
        final CxReader p = CxReader.createInstance(cx_string, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, use_default_pretty_printer, getAllAvailableAspectFragmentWriters());
        w.start();
        w.writeAspectElements(res.get(NodesElement.NAME));
        w.writeAspectElements(res.get(EdgesElement.NAME));
        w.writeAspectElements(res.get(NetworkRelationsElement.NAME));
        w.writeAspectElements(res.get(NetworkAttributesElement.NAME));
        w.writeAspectElements(res.get(NodeAttributesElement.NAME));
        w.writeAspectElements(res.get(EdgeAttributesElement.NAME));
        w.writeAspectElements(res.get(HiddenAttributesElement.NAME));
        w.writeAspectElements(res.get(CartesianLayoutElement.NAME));
        w.writeAspectElements(res.get(CyVisualPropertiesElement.NAME));
        w.writeAspectElements(res.get(CyGroupsElement.NAME));
        w.writeAspectElements(res.get(SubNetworkElement.NAME));
        w.writeAspectElements(res.get(CyViewsElement.NAME));
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
        final AspectFragmentReader hidden_attributes_reader = HiddenAttributesFragmentReader.createInstance();
        final AspectFragmentReader visual_properties_reader = CyVisualPropertiesFragmentReader.createInstance();
        final AspectFragmentReader group_reader = CyGroupsFragmentReader.createInstance();
        final AspectFragmentReader subnetwork_reader = SubNetworkFragmentReader.createInstance();
        final AspectFragmentReader network_rel_reader = NetworkRelationsFragmentReader.createInstance();
        final AspectFragmentReader views_reader = CyViewsFragmentReader.createInstance();

        final Set<AspectFragmentReader> aspect_readers = new HashSet<AspectFragmentReader>();

        aspect_readers.add(node_reader);
        aspect_readers.add(edge_reader);
        aspect_readers.add(cartesian_layout_reader);
        aspect_readers.add(network_attributes_reader);
        aspect_readers.add(edge_attributes_reader);
        aspect_readers.add(node_attributes_reader);
        aspect_readers.add(hidden_attributes_reader);
        aspect_readers.add(visual_properties_reader);
        aspect_readers.add(group_reader);
        aspect_readers.add(subnetwork_reader);
        aspect_readers.add(network_rel_reader);
        aspect_readers.add(views_reader);
        return aspect_readers;
    }

    public final static Set<AspectFragmentWriter> getAllAvailableAspectFragmentWriters() {
        final AspectFragmentWriter node_writer = NodesFragmentWriter.createInstance();
        final AspectFragmentWriter edge_writer = EdgesFragmentWriter.createInstance();
        final AspectFragmentWriter cartesian_layout_writer = CartesianLayoutFragmentWriter.createInstance();
        final AspectFragmentWriter network_attributes_writer = NetworkAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter edge_attributes_writer = EdgeAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter node_attributes_writer = NodeAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter hidden_attributes_writer = HiddenAttributesFragmentWriter.createInstance();
        final AspectFragmentWriter visual_properties_writer = VisualPropertiesFragmentWriter.createInstance();
        final AspectFragmentWriter group_writer = CyGroupsFragmentWriter.createInstance();
        final AspectFragmentWriter subnetwork_writer = SubNetworkFragmentWriter.createInstance();
        final AspectFragmentWriter network_rel_writer = NetworkRelationsFragmentWriter.createInstance();
        final AspectFragmentWriter views_writer = CyViewsFragmentWriter.createInstance();

        final Set<AspectFragmentWriter> aspect_writers = new HashSet<AspectFragmentWriter>();
        aspect_writers.add(node_writer);
        aspect_writers.add(edge_writer);
        aspect_writers.add(network_rel_writer);
        aspect_writers.add(network_attributes_writer);
        aspect_writers.add(node_attributes_writer);
        aspect_writers.add(edge_attributes_writer);
        aspect_writers.add(hidden_attributes_writer);
        aspect_writers.add(cartesian_layout_writer);
        aspect_writers.add(visual_properties_writer);
        aspect_writers.add(group_writer);
        aspect_writers.add(subnetwork_writer);
        aspect_writers.add(views_writer);
        return aspect_writers;
    }

    final public static BufferedReader obtainReader(final Object source) throws IOException, FileNotFoundException {
        BufferedReader reader = null;
        if (source instanceof File) {
            final File f = (File) source;
            if (!f.exists()) {
                throw new IOException("\"" + f.getAbsolutePath() + "\" does not exist");
            }
            else if (!f.isFile()) {
                throw new IOException("\"" + f.getAbsolutePath() + "\" is not a file");
            }
            else if (!f.canRead()) {
                throw new IOException("\"" + f.getAbsolutePath() + "\" is not a readable");
            }
            reader = new BufferedReader(new FileReader(f));
        }
        else if (source instanceof InputStream) {
            reader = new BufferedReader(new InputStreamReader((InputStream) source));
        }
        else if (source instanceof String) {
            reader = new BufferedReader(new StringReader((String) source));
        }
        else if (source instanceof StringBuffer) {
            reader = new BufferedReader(new StringReader(source.toString()));
        }
        else {
            throw new IllegalArgumentException("attempt to parse object of type [" + source.getClass() + "] (can only parse objects of type File, InputStream, String, or StringBuffer)");
        }
        return reader;
    }

    public static boolean validate(final byte[] writer_checksum, final byte[] reader_checksum, final AspectElementCounts writer_counts, final AspectElementCounts reader_counts) {
        if (!AspectElementCounts.isCountsAreEqual(reader_counts, writer_counts)) {
            System.out.println("something went wrong: element counts do not match");
        }
        else if (!isAreByteArraysEqual(reader_checksum, writer_checksum)) {
            System.out.println("something went wrong: checksums do not match");
        }
        else {
            System.out.println("OK");
            return true;
        }
        return false;
    }

    public final static List<String> parseStringToStringList(final String string) {
        final List<String> l = new ArrayList<String>();
        if ( string == null ) {
            return null;
        }
        String str = string.trim();
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
            for (String s : str.split(",")) {
                s = s.trim();
                if (s.startsWith("\"") && s.endsWith("\"")) {
                    l.add(s.substring(1, s.length() - 1));
                }
                else {
                    throw new IllegalArgumentException("illegal format: " + str);
                }
            }
        }
        else {
            throw new IllegalArgumentException("illegal format: " + str);
        }
        return l;
    }

    public final static String removeParanthesis(final String string) {
        if ( string == null ) {
            return null;
        }
        String str = string.trim();
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }

}
