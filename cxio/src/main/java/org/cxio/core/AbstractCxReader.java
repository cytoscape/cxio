package org.cxio.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;

import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.metadata.MetaData;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;

class AbstractCxReader {

    boolean             _calculate_element_counts;
    boolean             _calculate_md5_checksum;
    AspectElementCounts _element_counts;
    boolean             _meta_data;
    Set<MetaData>       _pre_meta_datas;
    Set<MetaData>       _post_meta_datas;
    MessageDigest       _md;
    boolean             _encountered_non_meta_content;

    /**
     * This returns an object which gives access to a checksum and element counts
     * for the aspect element read in.
     *
     * @return the ElementCounts
     */
    public final AspectElementCounts getAspectElementCounts() {
        return _element_counts;
    }

    /**
     * This returns a set of post meta data objects encountered so far.
     *
     * @return set of MetaData
     */
    public final Set<MetaData> getPostMetaData() {
        return _post_meta_datas;
    }

    /**
     * This returns a set of pre meta data objects encountered so far.
     *
     * @return set of MetaData
     */
    public final Set<MetaData> getPreMetaData() {
        return _pre_meta_datas;
    }

    /**
     * To turn on/off the calculation of checksum and aspect element counts.
     *
     * @param calculate_element_counts
     */
    public final void setCalculateAspectElementCounts(final boolean calculate_element_counts) {
        _calculate_element_counts = calculate_element_counts;
    }

    final static void checkInputType(final Object input) {
        if (!(input instanceof URL) && !(input instanceof String) && !(input instanceof File) && !(input instanceof InputStream)) {
            throw new IllegalArgumentException("don't know how to process input type " + input.getClass());
        }
    }

    final static HashMap<String, AspectFragmentReader> setupAspectReaders(final Set<AspectFragmentReader> aspect_readers, final boolean allow_empty) {
        if (!allow_empty) {
            if ((aspect_readers == null) || aspect_readers.isEmpty()) {
                throw new IllegalArgumentException("aspect handlers are null or empty");
            }
        }
        final HashMap<String, AspectFragmentReader> ahs = new HashMap<String, AspectFragmentReader>();
        if (aspect_readers != null) {
            for (final AspectFragmentReader aspect_reader : aspect_readers) {
                ahs.put(aspect_reader.getAspectName(), aspect_reader);
            }
        }
        return ahs;
    }

    final JsonParser createJsonParser(final Object input) throws IOException, NoSuchAlgorithmException {
        final JsonFactory f = new JsonFactory();
        JsonParser jp = null;
        InputStream my_is = null;

        if (input instanceof String) {
            my_is = new ByteArrayInputStream(((String) input).getBytes(StandardCharsets.UTF_8));
        }
        else if (input instanceof File) {
            my_is = new FileInputStream((File) input);
        }
        else if (input instanceof URL) {
            my_is = ((URL) input).openStream();
        }
        else if (input instanceof InputStream) {
            my_is = (InputStream) input;
        }
        else {
            throw new IllegalStateException("cx parser does not know how to handle input of type " + input.getClass());
        }

        if (_calculate_md5_checksum) {
            _md = MessageDigest.getInstance(Util.MD5);
            my_is = new DigestInputStream(my_is, _md);
        }
        else {
            _md = null;
        }

        jp = f.createParser(my_is);
        return jp;
    }

    public final byte[] getMd5Checksum() {
        if (_md == null) {
            throw new IllegalStateException("cx reader is not set up to calculare checksum");
        }
        return _md.digest();
    }

    void addMetaData(final JsonParser _jp) throws JsonParseException, JsonMappingException, IOException {
        final MetaData md = MetaData.createInstanceFromJson(_jp);
        if ((md != null) && !md.getMetaData().isEmpty()) {
            if (_encountered_non_meta_content) {
                _post_meta_datas.add(md);
            }
            else {
                _pre_meta_datas.add(md);
            }
        }
    }

}
