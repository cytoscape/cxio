package org.cxio.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.readers.AnonymousFragmentReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * CxElementReader is used to read aspect elements from a
 * input stream.
 *
 * @author cmzmasek
 *
 */
public final class CxElementReader implements Iterable<AspectElement> {

    private final static boolean                        DEBUG = false;
    private boolean                                     _anonymous_reader_used;
    private String                                      _aspect_name;
    private AspectElement                               _current;
    private final HashMap<String, AspectFragmentReader> _element_readers;
    private final Object                                _input;
    private JsonParser                                  _jp;
    private int                                         _level;
    private ObjectMapper                                _m;
    private AspectElement                               _prev;
    private final boolean                               _read_anonymous_aspect_fragments;
    private AspectFragmentReader                        _reader;
    private JsonToken                                   _token;

    /**
     * This creates a new CxElementReader.
     *
     * @param file the File to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final File file, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(file, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader with all AspectFragmentReaders implemented in this library already added.
     *
     * @param file the File to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableWriters(final File file, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(file, Util.getAllAvailableAspectFragmentReaders(), read_anonymous_aspect_fragments);

    }

    /**
     * This creates a new CxElementReader.
     *
     * @param file the File to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final File file, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(file, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param file the File to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final File file, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(file, fragment_readers);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param input_stream the InputStream to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final InputStream input_stream, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(input_stream, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader with all AspectFragmentReaders implemented in this library already added.
     *
     * @param input_stream the InputStream to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableReaders(final InputStream input_stream, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(input_stream, Util.getAllAvailableAspectFragmentReaders(), read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param input_stream the InputStream to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final InputStream input_stream, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers)
            throws IOException {
        return new CxElementReader(input_stream, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param input_stream the InputStream to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final InputStream input_stream, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(input_stream, fragment_readers);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param string the String to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final String string, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(string, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader with all AspectFragmentReaders implemented in this library already added.
     *
     * @param string the String to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableReaders(final String string, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(string, Util.getAllAvailableAspectFragmentReaders(), read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     *
     * @param string the String to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final String string, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(string, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     *
     * @param string the String to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final String string, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(string, fragment_readers);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param url the URL to parse from
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final URL url, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(url, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader with all AspectFragmentReaders implemented in this library already added..
     *
     * @param url the URL to parse from
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableReaders(final URL url, final boolean read_anonymous_aspect_fragments) throws IOException {
        return new CxElementReader(url, Util.getAllAvailableAspectFragmentReaders(), read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param url the URL to parse from
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final URL url, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(url, fragment_readers, read_anonymous_aspect_fragments);
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param url the URL to parse from
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final URL url, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        return new CxElementReader(url, fragment_readers);
    }

    /**
     * Convenience method. Returns a sorted map of lists of aspects, where the
     * keys are the names of the aspect. Takes a CxElementReader as argument.
     *
     */
    public static SortedMap<String, List<AspectElement>> parseAsMap(final CxElementReader cxr) throws IOException {
        if (cxr == null) {
            throw new IllegalArgumentException("reader is null");
        }
        final SortedMap<String, List<AspectElement>> all_aspects = new TreeMap<String, List<AspectElement>>();
        for (final AspectElement e : cxr) {
            if (e != null) {
                final String name = e.getAspectName();
                if (!all_aspects.containsKey(name)) {
                    all_aspects.put(name, new ArrayList<AspectElement>());
                }
                all_aspects.get(name).add(e);
            }
        }
        return all_aspects;
    }

    /**
     * This returns one aspect element and advances to the reader to the
     * next aspect element.
     *
     * @return one aspect element, null if end of stream has been reached
     * @throws IOException
     */
    public final AspectElement getNext() throws JsonProcessingException, IOException {
        _prev = _current;
        _current = null;
        while (_current == null) {
            if (_reader == null) {
                nextToken();
                if (_token == JsonToken.START_OBJECT) {
                    nextToken();
                    if (DEBUG) {
                        System.out.println("1 " + _token + " " + _jp.getCurrentName());
                    }
                    if (_token != JsonToken.FIELD_NAME) {
                        throw new IOException("malformed cx json in '" + _jp.getCurrentName() + "'");
                    }
                    _aspect_name = _jp.getCurrentName();
                    if (DEBUG) {
                        System.out.println("aspect name =  " + _aspect_name);
                    }
                    if (_element_readers.containsKey(_aspect_name)) {
                        _reader = _element_readers.get(_aspect_name);
                        _anonymous_reader_used = false;
                    }
                    else if (_read_anonymous_aspect_fragments) {
                        _reader = AnonymousFragmentReader.createInstance(_jp, _aspect_name);
                        _anonymous_reader_used = true;
                    }
                    nextToken();
                    if (DEBUG) {
                        System.out.println("2 " + _token + " " + _jp.getCurrentName());
                    }
                    if ((_reader != null) && !_anonymous_reader_used && (_token != JsonToken.START_ARRAY)) {
                        throw new IOException("malformed cx json in '" + _aspect_name + "'");
                    }
                    nextToken();
                    if (DEBUG) {
                        System.out.println("3 " + _token + " " + _jp.getCurrentName());
                    }
                }
                else if (_token == null) {
                    _jp.close();
                    return _prev;
                }
            }
            if (_reader != null) {
                if (_token != JsonToken.END_ARRAY) {
                    if (_token == JsonToken.START_OBJECT) {

                        final ObjectNode o = _m.readTree(_jp);
                        --_level;
                        if (DEBUG) {
                            System.out.println(">" + o);
                        }
                        if (_reader != null) {
                            _current = _reader.readElement(o);
                        }
                        else {
                            throw new IOException("malformed cx json in '" + _aspect_name + "'");
                        }
                    }
                    else if (_anonymous_reader_used) {
                        if (_token == JsonToken.VALUE_STRING) {
                            _current = new AnonymousElement(_aspect_name, _jp.getText());
                        }
                        else {
                            final ObjectNode o = _m.readTree(_jp);
                            if (DEBUG) {
                                System.out.println(">>>" + o);
                            }
                            if (_reader != null) {
                                _current = _reader.readElement(o);
                            }
                        }
                    }
                    else {
                        throw new IOException("malformed cx json in '" + _aspect_name + "'");
                    }
                }
                else {
                    _reader = null;
                }
            }
        }
        nextToken();
        return _prev;
    }

    /**
     * Returns true if more aspect elements can be read in.
     *
     * @return true if more aspect elements can be read in
     * @throws IOException
     */
    public final boolean hasNext() throws IOException {
        return _current != null;
    }

    /**
     * This returns a Iterator for AspectElements
     *
     * @return Iterator for AspectElements
     *
     */
    @Override
    public Iterator<AspectElement> iterator() {
        final Iterator<AspectElement> it = new Iterator<AspectElement>() {

            @Override
            public boolean hasNext() {
                try {
                    return CxElementReader.this.hasNext();

                }
                catch (final IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public AspectElement next() {
                try {
                    return CxElementReader.this.getNext();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
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
        _level = 0;
        _current = null;
        _jp = createJsonParser(_input);
        _token = _jp.nextToken();
        _aspect_name = null;
        _m = new ObjectMapper();
        if (_token != JsonToken.START_ARRAY) {
            throw new IllegalStateException("illegal cx json format: expected to start with an array: " + _token.asString());
        }
        getNext();
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

    private CxElementReader(final Object input) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;
        _element_readers = setupAspectReaders();
        _read_anonymous_aspect_fragments = false;
    }

    private CxElementReader(final Object input, final boolean read_anonymous_aspect_fragments) throws IOException {
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

    private CxElementReader(final Object input, final Set<AspectFragmentReader> aspect_readers) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;
        _element_readers = setupAspectReaders(aspect_readers, false);
        _read_anonymous_aspect_fragments = false;
        reset();
    }

    private CxElementReader(final Object input, final Set<AspectFragmentReader> aspect_readers, final boolean read_anonymous_aspect_fragments) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;

        _element_readers = setupAspectReaders(aspect_readers, read_anonymous_aspect_fragments);
        _read_anonymous_aspect_fragments = read_anonymous_aspect_fragments;
        reset();
    }

    private final static void checkInputType(final Object input) {
        if (!(input instanceof File) && !(input instanceof InputStream) && !(input instanceof Reader) && !(input instanceof String) && !(input instanceof URL)) {
            throw new IllegalArgumentException("don't know how to process" + input.getClass());
        }
    }

    private void nextToken() throws IOException {
        if ((_token == JsonToken.START_ARRAY) || (_token == JsonToken.START_OBJECT)) {
            ++_level;
        }
        else if ((_token == JsonToken.END_ARRAY) || (_token == JsonToken.END_OBJECT)) {
            --_level;
        }
        _token = _jp.nextToken();
    }

}
