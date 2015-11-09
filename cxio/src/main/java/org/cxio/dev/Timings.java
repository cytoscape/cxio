package org.cxio.dev;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgeAttributesFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodeAttributesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

/**
 * This class is for timing of reading and writing CX formatted data.
 *
 * Usage:
 *
 * java -cp
 * path/to/cxio-0.0.1.jar:path/to/jackson-databind-2.5.0.jar:path/to/jackson
 * -core-2.5.0.jar:path/to/jackson-annotations-2.5.0.jar org.cxio.dev.Timings
 *
 *
 */
public class Timings {

    private static File                  _cx_out;
    private static int                   _elements;
    private static ByteArrayOutputStream _baos;
    private static boolean               _pretty_printing;
    private static boolean               _write_to_dev_null;
    private static final String          OUTPUT_FILE = "CX_timing_output.cx";

    private static void error() {
        System.out.println("Usage: Timings [number of elements] [number of repeats] [pretty printing: true/false] [write to /dev/null: true/false]");
        System.exit(-1);
    }

    public static void main(final String[] args) throws IOException {

        int repeats = 10;
        _elements = 1000;
        _pretty_printing = true;
        _write_to_dev_null = true;

        System.out.println();
        if (args.length == 0) {
            System.out.println("Using default arguments");
        }
        else if (args.length == 4) {
            try {
                _elements = Integer.parseInt(args[0]);
                repeats = Integer.parseInt(args[1]);
                _pretty_printing = Boolean.parseBoolean(args[2]);
                _write_to_dev_null = Boolean.parseBoolean(args[3]);
            }
            catch (final NumberFormatException e) {
                error();
            }
        }
        else {
            error();
        }

        if ((_elements <= 0) || (repeats <= 0)) {
            error();
        }

        _cx_out = new File(OUTPUT_FILE);
        if (_cx_out.exists()) {
            _cx_out.delete();
        }

        if (_write_to_dev_null) {
            _cx_out = null;
        }

        System.out.println("Number of elements:\t" + _elements);
        System.out.println("Repeats           :\t" + repeats);
        System.out.println("Pretty printing   :\t" + _pretty_printing);
        if (_write_to_dev_null) {
            System.out.println("Writing to        :\t/dev/null");
            System.out.println("(first writing number is to dev/null, second one is to ByteArrayOutputStream)");
        }
        else {
            System.out.println("Writing to        :\tfile '" + _cx_out.getAbsolutePath() + "'");
            System.out.println("(first writing number is to '" + _cx_out.getAbsolutePath() + "', second one is to ByteArrayOutputStream)");
        }

        System.out.println();

        long sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeNodesWriting(true);
        }
        System.out.println("nodes writing [ms]:\t" + (sum / repeats));
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeNodesWriting(false);
        }
        System.out.println("nodes writing [ms]:\t" + (sum / repeats));
        System.out.println("nodes size [bytes]:\t" + _baos.size());
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeNodesParsing();
        }
        System.out.println("nodes reading [ms]:\t" + (sum / repeats));
        // //
        System.out.println();
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeEdgesWriting(true);
        }
        System.out.println("edges writing [ms]:\t" + (sum / repeats));
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeEdgesWriting(false);
        }
        System.out.println("edges writing [ms]:\t" + (sum / repeats));
        System.out.println("edges size [bytes]:\t" + _baos.size());
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeEdgesParsing();
        }
        System.out.println("edges reading [ms]:\t" + (sum / repeats));
        // //
        System.out.println();
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeLayoutWriting(true);
        }
        System.out.println("cartesian layout writing [ms]:\t" + (sum / repeats));
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeLayoutWriting(false);
        }
        System.out.println("cartesian layout writing [ms]:\t" + (sum / repeats));
        System.out.println("cartesian layout size [bytes]:\t" + _baos.size());
        //
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeLayoutParsing();
        }
        System.out.println("cartesian layout reading [ms]:\t" + (sum / repeats));
        // //
        System.out.println();
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeNodeAttributesWriting(true);
        }
        System.out.println("node attributes writing [ms]:\t" + (sum / repeats));
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeNodeAttributesWriting(false);
        }
        System.out.println("node attributes writing [ms]:\t" + (sum / repeats));
        System.out.println("node attributes size [bytes]:\t" + _baos.size());
        //
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeNodeAttributesParsing();
        }
        System.out.println("node attributes reading [ms]:\t" + (sum / repeats));
        // //
        System.out.println();
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeEdgeAttributesWriting(true);
        }
        System.out.println("edge attributes writing [ms]:\t" + (sum / repeats));
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeEdgeAttributesWriting(false);
        }
        System.out.println("edge attributes writing [ms]:\t" + (sum / repeats));
        System.out.println("edge attributes size [bytes]:\t" + _baos.size());
        //
        sum = 0;
        for (int i = 0; i < repeats; ++i) {
            sum += timeEdgeAttributesParsing();
        }
        System.out.println("edge attributes reading [ms]:\t" + (sum / repeats));
        // //
    }

    private static CxWriter makeCxWriter(final boolean to_file) throws IOException, FileNotFoundException {
        CxWriter w;
        if (to_file) {
            _baos = null;
            if (_write_to_dev_null) {
                w = CxWriter.createInstance(new FileOutputStream(new File("/dev/null")), _pretty_printing);
            }
            else {
                if (_cx_out.exists()) {
                    _cx_out.delete();
                }
                w = CxWriter.createInstance(new FileOutputStream(_cx_out), _pretty_printing);
            }
        }
        else {
            _baos = new ByteArrayOutputStream();
            w = CxWriter.createInstance(_baos, _pretty_printing);
        }
        return w;
    }

    final static CxReader makeReader(final String cx_json_str, final Set<AspectFragmentReader> readers) throws IOException {
        if (_write_to_dev_null) {
            return CxReader.createInstance(cx_json_str, readers);
        }
        else {
            return CxReader.createInstance(new FileInputStream(_cx_out), readers);
        }
    }

    private static long timeEdgeAttributesParsing() throws IOException {
        String cx_json_str = null;
        if (_write_to_dev_null) {
            cx_json_str = _baos.toString();
        }

        final Set<AspectFragmentReader> readers = new HashSet<>();

        readers.add(EdgeAttributesFragmentReader.createInstance());

        final long t0 = System.currentTimeMillis();

        final CxReader p = makeReader(_write_to_dev_null ? cx_json_str : null, readers);

        while (p.hasNext()) {
            p.getNext();
        }
        return System.currentTimeMillis() - t0;
    }

    private static long timeEdgeAttributesWriting(final boolean to_devnull) throws IOException {
        final CxWriter w = makeCxWriter(to_devnull);
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        final long t0 = System.currentTimeMillis();

        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());
        for (int i = 0; i < _elements; ++i) {
            final EdgeAttributesElement e = new EdgeAttributesElement(1, "n", "v", ATTRIBUTE_DATA_TYPE.STRING);

            elements.add(e);
        }

        w.start();
        w.writeAspectElements(elements);
        w.end(true, "");
        return System.currentTimeMillis() - t0;
    }

    private static long timeEdgesParsing() throws IOException {
        String cx_json_str = null;
        if (_write_to_dev_null) {
            cx_json_str = _baos.toString();
        }

        final Set<AspectFragmentReader> readers = new HashSet<>();

        readers.add(EdgesFragmentReader.createInstance());

        final long t0 = System.currentTimeMillis();

        final CxReader p = makeReader(_write_to_dev_null ? cx_json_str : null, readers);

        while (p.hasNext()) {
            p.getNext();
        }
        return System.currentTimeMillis() - t0;
    }

    private static long timeEdgesWriting(final boolean to_devnull) throws IOException {
        final CxWriter w = makeCxWriter(to_devnull);
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        final long t0 = System.currentTimeMillis();

        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        for (int i = 0; i < _elements; ++i) {
            elements.add(new EdgesElement("e", "1", "2"));
        }

        w.start();
        w.writeAspectElements(elements);
        w.end(true, "");
        return System.currentTimeMillis() - t0;
    }

    private static long timeLayoutParsing() throws IOException {
        String cx_json_str = null;
        if (_write_to_dev_null) {
            cx_json_str = _baos.toString();
        }

        final Set<AspectFragmentReader> readers = new HashSet<>();

        readers.add(CartesianLayoutFragmentReader.createInstance());

        final long t0 = System.currentTimeMillis();

        final CxReader p = makeReader(_write_to_dev_null ? cx_json_str : null, readers);

        while (p.hasNext()) {
            p.getNext();
        }
        return System.currentTimeMillis() - t0;
    }

    private static long timeLayoutWriting(final boolean to_devnull) throws IOException {
        final CxWriter w = makeCxWriter(to_devnull);
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        final long t0 = System.currentTimeMillis();

        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        for (int i = 0; i < _elements; ++i) {
            elements.add(new CartesianLayoutElement(i, 1, 2, 0));
        }

        w.start();
        w.writeAspectElements(elements);
        w.end(true, "");
        return System.currentTimeMillis() - t0;
    }

    private static long timeNodeAttributesParsing() throws IOException {
        String cx_json_str = null;
        if (_write_to_dev_null) {
            cx_json_str = _baos.toString();
        }

        final Set<AspectFragmentReader> readers = new HashSet<>();

        readers.add(NodeAttributesFragmentReader.createInstance());

        final long t0 = System.currentTimeMillis();

        final CxReader p = makeReader(_write_to_dev_null ? cx_json_str : null, readers);

        while (p.hasNext()) {
            p.getNext();
        }
        return System.currentTimeMillis() - t0;
    }

    private static long timeNodeAttributesWriting(final boolean to_devnull) throws IOException {
        final CxWriter w = makeCxWriter(to_devnull);
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        final long t0 = System.currentTimeMillis();

        w.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());
        for (int i = 0; i < _elements; ++i) {
            final NodeAttributesElement e = new NodeAttributesElement(1, "n", "v", ATTRIBUTE_DATA_TYPE.STRING);
            elements.add(e);
        }

        w.start();
        w.writeAspectElements(elements);
        w.end(true, "");
        return System.currentTimeMillis() - t0;
    }

    private static long timeNodesParsing() throws IOException {
        String cx_json_str = null;
        if (_write_to_dev_null) {
            cx_json_str = _baos.toString();
        }

        final Set<AspectFragmentReader> readers = new HashSet<>();

        readers.add(NodesFragmentReader.createInstance());

        final long t0 = System.currentTimeMillis();

        final CxReader p = makeReader(_write_to_dev_null ? cx_json_str : null, readers);

        while (p.hasNext()) {
            p.getNext();
        }
        return System.currentTimeMillis() - t0;
    }

    private static long timeNodesWriting(final boolean to_devnull) throws IOException {
        final CxWriter w = makeCxWriter(to_devnull);
        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        final long t0 = System.currentTimeMillis();

        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        for (int i = 0; i < _elements; ++i) {
            nodes_elements.add(new NodesElement("n"));
        }

        w.start();
        w.writeAspectElements(nodes_elements);
        w.end(true, "");
        return System.currentTimeMillis() - t0;
    }

}
