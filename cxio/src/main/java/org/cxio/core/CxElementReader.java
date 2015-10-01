package org.cxio.core;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.readers.AnonymousFragmentReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.metadata.MetaData;
import org.cxio.util.Util;

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
public final class CxElementReader extends AbstractCxReader implements Iterable<AspectElement> {

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
     * This creates a new CxElementReader with all AspectFragmentReaders implemented in this library already added.
     *
     * @param input the input object to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableReaders(final Object input, final boolean read_anonymous_aspect_fragments) throws IOException {
        try {
            return new CxElementReader(input, Util.getAllAvailableAspectFragmentReaders(), read_anonymous_aspect_fragments, false);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * This creates a new CxElementReader with all AspectFragmentReaders implemented in this library already added.
     *
     * @param input the input object to parse
     * @param read_anonymous_aspect_fragments
     * @param calculate_md5_checksum to turn checksum calculation on/off
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableReaders(final Object input, final boolean read_anonymous_aspect_fragments, final boolean calculate_md5_checksum)
            throws IOException {
        try {
            return new CxElementReader(input, Util.getAllAvailableAspectFragmentReaders(), read_anonymous_aspect_fragments, calculate_md5_checksum);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     *  This creates a new CxtReader with all AspectFragmentReaders implemented in this library already added.
     *  It allows to add additional AspectFragmentReader.
     *
     * @param input the input object to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param calculate_md5_checksum to turn checksum calculation on/off
     * @param fragment_readers the set of additional AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstanceWithAllAvailableReaders(final Object input,
                                                                              final boolean read_anonymous_aspect_fragments,
                                                                              final boolean calculate_md5_checksum,
                                                                              final Set<AspectFragmentReader> fragment_readers) throws IOException {

        final Set<AspectFragmentReader> r = Util.getAllAvailableAspectFragmentReaders();
        r.addAll(fragment_readers);
        try {
            return new CxElementReader(input, r, read_anonymous_aspect_fragments, calculate_md5_checksum);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param input the input object to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final Object input, final boolean read_anonymous_aspect_fragments, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        try {
            return new CxElementReader(input, fragment_readers, read_anonymous_aspect_fragments, false);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param input the input object to parse
     * @param read_anonymous_aspect_fragments to enable reading of anonymous aspect fragments
     * @param calculate_md5_checksum to turn checksum calculation on/off
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReade
     * @throws IOException
     */
    public final static CxElementReader createInstance(final Object input,
                                                       final boolean read_anonymous_aspect_fragments,
                                                       final boolean calculate_md5_checksum,
                                                       final Set<AspectFragmentReader> fragment_readers) throws IOException {
        try {
            return new CxElementReader(input, fragment_readers, read_anonymous_aspect_fragments, calculate_md5_checksum);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * This creates a new CxElementReader.
     *
     * @param input the input object to parse
     * @param fragment_readers the set of AspectFragmentReaders to use
     * @return a CxElementReader
     * @throws IOException
     */
    public final static CxElementReader createInstance(final Object input, final Set<AspectFragmentReader> fragment_readers) throws IOException {
        try {
            return new CxElementReader(input, fragment_readers, false, false);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    public final static CxElementReader createInstance(final Object input, final Set<AspectFragmentReader> fragment_readers, final boolean calculate_md5_checksum) throws IOException {
        try {
            return new CxElementReader(input, fragment_readers, false, calculate_md5_checksum);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
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
                        _encountered_non_meta_content = true;
                        _meta_data = false;
                    }
                    else if (_aspect_name.equals(MetaData.NAME)) {
                        addMetaData(_jp);
                        _anonymous_reader_used = false;
                        _meta_data = true;
                    }
                    else if (_read_anonymous_aspect_fragments) {
                        _reader = AnonymousFragmentReader.createInstance(_jp, _aspect_name);
                        _anonymous_reader_used = true;
                        _encountered_non_meta_content = true;
                        _meta_data = false;
                    }
                    if (!_meta_data) {
                        nextToken();
                    }
                    if (DEBUG) {
                        System.out.println("2 " + _token + " " + _jp.getCurrentName());
                    }
                    if ((_reader != null) && !_anonymous_reader_used && (_token != JsonToken.START_ARRAY)) {
                        throw new IOException("malformed cx json in '" + _aspect_name + "'");
                    }
                    if (!_meta_data) {
                        nextToken();
                    }
                    if (DEBUG) {
                        System.out.println("3 " + _token + " " + _jp.getCurrentName());
                    }
                }
                else if (_token == null) {
                    _jp.close();
                    if (_calculate_element_counts) {
                        if (_prev != null) {
                            _element_counts.processAspectElement(_prev);
                        }
                    }
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
                        // if (_reader != null) { //could remove //TODO
                        _current = _reader.readElement(o);
                        // }
                        // else {
                        // throw new IOException("malformed cx json in '" +
                        // _aspect_name + "'");
                        // }
                        _encountered_non_meta_content = true;
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
                            // if (_reader != null) { //could remove //TODO
                            _current = _reader.readElement(o);
                            // }
                        }
                        _encountered_non_meta_content = true;
                    }
                    else if (_meta_data) {
                        addMetaData(_jp);
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
        if (_calculate_element_counts) {
            if (_prev != null) {
                _element_counts.processAspectElement(_prev);
            }
        }
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
     * @throws NoSuchAlgorithmException
     */
    public final void reset() throws IOException, NoSuchAlgorithmException {
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
        _encountered_non_meta_content = false;
        _pre_meta_datas = new HashSet<MetaData>();
        _post_meta_datas = new HashSet<MetaData>();
        if (_token != JsonToken.START_ARRAY) {
            throw new IllegalStateException("illegal cx json format: expected to start with an array: " + _token.asString());
        }
        getNext();
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

    /**
     * Hidden constructor.
     *
     * @param input
     * @param aspect_readers
     * @param read_anonymous_aspect_fragments
     * @param calculate_md5_checksum
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private CxElementReader(final Object input, final Set<AspectFragmentReader> aspect_readers, final boolean read_anonymous_aspect_fragments, final boolean calculate_md5_checksum)
            throws IOException, NoSuchAlgorithmException {
        if (input == null) {
            throw new IllegalArgumentException("cx input is null");
        }
        checkInputType(input);
        _input = input;
        _element_readers = setupAspectReaders(aspect_readers, read_anonymous_aspect_fragments);
        _read_anonymous_aspect_fragments = read_anonymous_aspect_fragments;
        _calculate_element_counts = true;
        _calculate_md5_checksum = calculate_md5_checksum;
        _element_counts = AspectElementCounts.createInstance();
        _encountered_non_meta_content = false;
        _pre_meta_datas = new HashSet<MetaData>();
        _post_meta_datas = new HashSet<MetaData>();
        reset();
    }

}
