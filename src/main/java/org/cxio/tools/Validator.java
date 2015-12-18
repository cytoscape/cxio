package org.cxio.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aux.Status;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp
 * path/to/cxio-0.0.1.jar:path/to/jackson-databind-2.5.0.jar:path/to/jackson
 * -core-2.5.0.jar:path/to/jackson-annotations-2.5.0.jar
 * org.cxio.tools.Validator
 *
 *
 */
public final class Validator {

    private SortedMap<String, Integer> _aspect_element_counts;
    private String[]                   _aspects;
    private String                     _error;
    private long                       _total_time;
    private int                        _pre_meta_data_elements;
    private int                        _post_meta_data_elements;
    private Status                     _status;

    public final static Validator getInstance() {
        return new Validator();
    }

    private Validator() {
        init();
    }

    public final SortedMap<String, Integer> getAspectElementCounts() {
        return _aspect_element_counts;
    }

    public final String[] getAspectNames() {
        return _aspects;
    }

    public final String getError() {
        return _error;
    }

    public final int getNumberOfAspects() {
        return _aspects.length;
    }

    public final long getTotalTimeMillis() {
        return _total_time;
    }

    public int getPreMetaDataElementCount() {
        return _pre_meta_data_elements;
    }

    public int getPostMetaDataElementCount() {
        return _post_meta_data_elements;
    }

    public Status getStatus() {
        return _status;
    }

    private final void init() {
        _error = "";
        _total_time = 0;
        _aspects = new String[0];
        _aspect_element_counts = new TreeMap<String, Integer>();
        _pre_meta_data_elements = 0;
        _post_meta_data_elements = 0;
        _status = null;
    }

    public final boolean validate(final InputStream in) {
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        return validate(in, true, readers);
    }

    public final boolean validate(final InputStream in, final boolean allow_anonymous_readers, final Set<AspectFragmentReader> readers) {
        init();
        try {
            final long t0 = System.currentTimeMillis();
            final CxReader cxr = makeReader(in, allow_anonymous_readers, readers);

            while (cxr.hasNext()) {
                final List<AspectElement> aspects = cxr.getNext();
                if ((aspects != null) && !aspects.isEmpty()) {
                    final String name = aspects.get(0).getAspectName();
                    if (!_aspect_element_counts.containsKey(name)) {
                        _aspect_element_counts.put(name, aspects.size());
                    }
                    else {
                        _aspect_element_counts.put(name, _aspect_element_counts.get(name) + aspects.size());
                    }
                }
            }
            if (cxr.getPreMetaData() != null) {
                _pre_meta_data_elements = cxr.getPreMetaData().size();
            }
            if (cxr.getPostMetaData() != null) {
                _post_meta_data_elements = cxr.getPostMetaData().size();
            }
            _status = cxr.getStatus();
            _total_time = System.currentTimeMillis() - t0;
        }
        catch (final Exception e) {
            e.printStackTrace();
            _error = e.getMessage();
            return false;
        }

        _aspects = new String[_aspect_element_counts.size()];

        int i = 0;
        for (final String aspect : _aspect_element_counts.keySet()) {
            _aspects[i++] = aspect;
        }

        return true;
    }

    private final static CxReader makeReader(final InputStream in, final boolean allow_anonymous_readers, final Set<AspectFragmentReader> readers) throws IOException {
        return CxReader.createInstance(in, allow_anonymous_readers, false, readers);
    }

    public static void main(final String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: Validator [CX formatted infile]");
            System.exit(-1);
        }
        final String infile = args[0];
        final Validator val = Validator.getInstance();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(infile));
        }
        catch (final FileNotFoundException e) {
            System.out.println("Could not create input stream from '" + infile + "'");
            System.exit(-1);
        }

        final boolean valid = val.validate(in);

        if (valid) {
            System.out.println("Valid");
            System.out.println(String.format("%-32s %7d", "Total time for parsing [ms]:", val.getTotalTimeMillis()));
            System.out.println(String.format("%-32s %7d", "Number of aspects:", val.getNumberOfAspects()));
            final String[] names = val.getAspectNames();
            for (final String name : names) {
                System.out.println(String.format("  %-30s %7d", name + ":", val.getAspectElementCounts().get(name)));
            }
            System.out.println(String.format("%-32s %7d", "Pre-metadata elements:", val.getPreMetaDataElementCount()));
            System.out.println(String.format("%-32s %7d", "Post-metadata elements:", val.getPostMetaDataElementCount()));
            if ((val.getStatus() != null) && !val.getStatus().isSuccess()) {
                System.out.println("No success: " + val.getStatus().getError());
            }
        }
        else {
            System.out.println("Not valid");
            System.out.println("Error:\t" + val.getError());
        }
    }

}
