package org.cxio.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.readers.AnonymousFragmentReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * This class is used to read aspect fragments (lists of aspect elements) from a
 * input stream.
 *
 *
 * @author cmzmasek
 *
 */
public final class CxReader {

    private List<AspectElement>                         _current;
    private final HashMap<String, AspectFragmentReader> _element_readers;
    private final Object                                _input;
    private JsonParser                                  _jp;
    private int                                         _level;
    private final boolean                               _read_anonymous_aspect_fragments;
    private JsonToken                                   _token;
    private boolean                                     _was_in_recognized_aspect;

    /**
     * This creates a new CxReader.
     *
     * @param file the File to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final File file, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxReader(file, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     * @param file the File to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final File file, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(file, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     * @param file the File to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final File file, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(file, fragment_readers);
    }

    /**
     * This creates a new CxReader.
     *
     * @param input_stream the InputStream to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final InputStream input_stream, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxReader(input_stream, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     * @param input_stream the InputStream to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final InputStream input_stream, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(input_stream, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     * @param input_stream the InputStream to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final InputStream input_stream, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(input_stream, fragment_readers);
    }

    /**
     * This creates a new CxReader.
     *
     *
     * @param string the String to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final String string, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxReader(string, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     *
     * @param string the String to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final String string, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(string, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     *
     * @param string the String to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final String string, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(string, fragment_readers);
    }

    /**
     * This creates a new CxReader.
     *
     * @param url the URL to parse from
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final URL url, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxReader(url, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     * @param url the URL to parse from
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final URL url, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(url, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxReader.
     *
     * @param url the URL to parse from
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxReader
     * @throws IOException
     */
    public final static CxReader createInstance(final URL url, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxReader(url, fragment_readers);
    }

    private final static JsonParser createJsonParser(final Object input) throws IOException {
        final JsonFactory f = new JsonFactory();
        JsonParser jp = null;
        if (input instanceof String) {
            jp = f.createParser((String) input);
        }
        else if (input instanceof File) {
            jp = f.createParser((File) input);
        }
        else if (input instanceof InputStream) {
            jp = f.createParser((InputStream) input);
        }
        else if (input instanceof URL) {
            jp = f.createParser((URL) input);
        }
        else {
            throw new IllegalStateException("cx parser does not know how to handle input of type " + input.getClass());
        }
        return jp;
    }

    /**
     * Convenience method. Returns a sorted map of lists of aspects, where the
     * keys are the names of the aspect. Takes a CxReader as argument.
     *
     */
    public static SortedMap<String, List<AspectElement>> parseAsMap(final CxReader cxr) throws IOException {
        if (cxr == null) {
            throw new IllegalArgumentException("reader is null");
        }
        final SortedMap<String, List<AspectElement>> all_aspects = new TreeMap<String, List<AspectElement>>();
        while (cxr.hasNext()) {
            final List<AspectElement> aspects = cxr.getNext();
            if ((aspects != null) && !aspects.isEmpty()) {
                final String name = aspects.get(0).getAspectName();
                if (!all_aspects.containsKey(name)) {
                    all_aspects.put(name, aspects);
                }
                else {
                    all_aspects.get(name).addAll(aspects);
                }
            }
        }
        return all_aspects;
    }

    private final static HashMap<String, AspectFragmentReader> setupAspectReaders() {
        final HashMap<String, AspectFragmentReader> ahs = new HashMap<String, AspectFragmentReader>();
        return ahs;
    }

    private final static HashMap<String, AspectFragmentReader> setupAspectReaders(final Set<AspectFragmentReader> aspect_readers, final boolean allow_empty) {
        if (!allow_empty) {
            if ((aspect_readers == null) || aspect_readers.isEmpty()) {
                throw new IllegalArgumentException("aspect handlers are null or empty");
            }
        }
        final HashMap<String, AspectFragmentReader> ahs = new HashMap<String, AspectFragmentReader>();
        for (final AspectFragmentReader aspect_reader : aspect_readers) {
            ahs.put(aspect_reader.getAspectName(), aspect_reader);
        }
        return ahs;
    }

    private CxReader(final Object input) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;
        _element_readers = setupAspectReaders();
        _read_anonymous_aspect_fragments = false;
    }

    private CxReader(final Object input, final boolean read_anonymous_aspect_fragments) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;
        _element_readers = setupAspectReaders();
        _read_anonymous_aspect_fragments = read_anonymous_aspect_fragments;
        if (read_anonymous_aspect_fragments) {
            reset();
        }
    }

    private CxReader(final Object input, final Set<AspectFragmentReader> aspect_readers) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;
        _element_readers = setupAspectReaders(aspect_readers, false);
        _read_anonymous_aspect_fragments = false;
        reset();
    }

    private CxReader(final Object input, final Set<AspectFragmentReader> aspect_readers, final boolean read_anonymous_aspect_fragments) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;

        _element_readers = setupAspectReaders(aspect_readers, read_anonymous_aspect_fragments);
        _read_anonymous_aspect_fragments = read_anonymous_aspect_fragments;
        reset();
    }

    /**
     * This returns a list of aspect elements and advances to the reader to the
     * next list of aspect elements.
     *
     * @return
     * @throws IOException
     */
    public final List<AspectElement> getNext() throws IOException {
        if (_token == null) {
            throw new IllegalStateException("this should never have happened: token is null");
        }
        if (_jp == null) {
            throw new IllegalStateException("this should never have happened: json parser is null");
        }
        final List<AspectElement> prev = _current;
        _current = null;
        while ((_token != JsonToken.END_ARRAY) || (_jp.getCurrentName() != null)) {
            List<AspectElement> elements = null;
            final String name = _jp.getCurrentName();
            _was_in_recognized_aspect = false;
            if ((_level == 2) && (_token == JsonToken.FIELD_NAME) && (name != null)) {
                if (_element_readers.containsKey(name)) {
                    elements = _element_readers.get(name).readAspectFragment(_jp);
                    _was_in_recognized_aspect = true;
                }
                else if (_read_anonymous_aspect_fragments) {
                    final AnonymousFragmentReader reader = AnonymousFragmentReader.createInstance();
                    reader.setAspectName(name);
                    elements = reader.readAspectFragment(_jp);
                    if (!reader.isList()) {
                        --_level;
                        if (_level < 1) {
                            throw new IllegalStateException("this should never have happened (likely cause: problem with '" + name + "' reader)");
                        }
                    }
                    _was_in_recognized_aspect = true;
                }
            }
            if (_was_in_recognized_aspect && (_jp.getCurrentToken() != JsonToken.END_ARRAY) && (_jp.getCurrentToken() != JsonToken.END_OBJECT)) {
                throw new IllegalStateException("this should never have happened (likely cause: problem with '" + name + "' reader)");
            }
            if ((_token == JsonToken.START_ARRAY) || (_token == JsonToken.START_OBJECT)) {
                ++_level;
            }
            else if ((_token == JsonToken.END_ARRAY) || (_token == JsonToken.END_OBJECT)) {
                --_level;
                if (_level < 1) {
                    throw new IllegalStateException("this should never have happened (likely cause: problem with '" + name + "' reader)");
                }
            }
            _token = _jp.nextToken();
            if (elements != null) {
                _current = elements;
                return prev;
            }
        }
        _jp.close();
        return prev;
    }

    /**
     * Returns true if more lists of aspect elements can be read in.
     *
     * @return
     * @throws IOException
     */
    public final boolean hasNext() throws IOException {
        return _current != null;
    }

    /**
     * This attempts to reset the iterator.
     * <br>
     * ONLY works when the input is based on a String.
     *
     * @throws IOException
     */
    public final void reset() throws IOException {
        if (_input == null) {
            throw new IllegalStateException("input for cx parser is null");
        }
        if (!_read_anonymous_aspect_fragments) {
            if ((_element_readers == null) || _element_readers.isEmpty()) {
                throw new IllegalStateException("aspect handlers are null or empty");
            }
        }
        _token = null;
        _was_in_recognized_aspect = false;
        _level = 0;
        _current = null;
        _jp = createJsonParser(_input);
        _token = _jp.nextToken();
        if (_token != JsonToken.START_ARRAY) {
            throw new IllegalStateException("illegal cx json format: expected to start with an array: " + _token.asString());
        }
        getNext();
    }

    private final static void checkInputType(final Object input) {
        if (!(input instanceof File) && !(input instanceof InputStream) && !(input instanceof Reader) && !(input instanceof String) && !(input instanceof URL)) {
            throw new IllegalArgumentException("don't know how to process" + input.getClass());
        }
    }

}
