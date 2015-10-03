package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.metadata.MetaData;
import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class is for writing aspect fragments (lists of aspects).
 *
 * @author cmzmasek
 *
 */
public final class CxWriter {

    private final JsonWriter                        _jw;
    private final MessageDigest                     _md;
    private boolean                                 _started;
    private boolean                                 _fragment_started;
    private final Map<String, AspectFragmentWriter> _writers;
    private final AspectElementCounts               _element_counts;
    private boolean                                 _calculate_element_counts;
    private final Set<MetaData>                     _pre_meta_datas;
    private final Set<MetaData>                     _post_meta_datas;

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out) throws IOException {
        return new CxWriter(out, false);
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        return new CxWriter(out, use_default_pretty_printer);
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param calculate_md5_checksum to turn MD5 checksum calculation on/off
     * @return a CxWriter writer
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final boolean calculate_md5_checksum) throws IOException, NoSuchAlgorithmException {
        return new CxWriter(out, use_default_pretty_printer, calculate_md5_checksum);
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param aspect_writers the set of {@link org.cxio.core.interfaces.AspectFragmentWriter} to use
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param aspect_writers the set of {@link org.cxio.core.interfaces.AspectFragmentWriter} to use
     * @param calculate_md5_checksum to turn MD5 checksum calculation on/off
     * @return a CxWriter writer
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final boolean calculate_md5_checksum, final Set<AspectFragmentWriter> aspect_writers)
            throws IOException, NoSuchAlgorithmException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, calculate_md5_checksum);
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with AspectFragmentWriters for nodes, edges, and cartesian layout elements
     * already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with AspectFragmentWriters for nodes, edges, and cartesian layout elements
     * already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param calculate_md5_checksum  to turn MD5 checksum calculation on/off
     * @param aspect_writers the (additional) set of {@link org.cxio.core.interfaces.AspectFragmentWriter} to use
     * @return a CxWriter writer
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public final static CxWriter createInstanceNEC(final OutputStream out,
                                                   final boolean use_default_pretty_printer,
                                                   final boolean calculate_md5_checksum,
                                                   final Set<AspectFragmentWriter> aspect_writers) throws IOException, NoSuchAlgorithmException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, calculate_md5_checksum);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with all AspectFragmentWriters implemented in this library already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param calculate_md5_checksum  to turn MD5 checksum calculation on/off
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceWithAllAvailableWriters(final OutputStream out, final boolean use_default_pretty_printer, final boolean calculate_md5_checksum) throws IOException {
        CxWriter w;
        try {
            w = new CxWriter(out, use_default_pretty_printer, calculate_md5_checksum);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
        for (final AspectFragmentWriter afw : Util.getAllAvailableAspectFragmentWriters()) {
            w.addAspectFragmentWriter(afw);
        }
        return w;
    }

    /**
     * This method is for adding a {@link org.cxio.core.interfaces.AspectFragmentWriter} to this CxWriter.
     *
     * @param writer the AspectFragmentWrite to add
     */
    public void addAspectFragmentWriter(final AspectFragmentWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("aspect fragment writer is null");
        }
        if (Util.isEmpty(writer.getAspectName())) {
            throw new IllegalArgumentException("aspect name is null or empty");
        }
        _writers.put(writer.getAspectName(), writer);
    }

    /**
     * This method is to be called prior to writing individual aspect elements of a given type/name.
     * <br>
     *
     * @param aspect_name the name of the aspect elements to be written
     * @throws IOException
     */
    public void startAspectFragment(final String aspect_name) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("fragment already started");
        }
        _fragment_started = true;
        _jw.startArray(aspect_name);
    }

    /**
     * This method is to be called after writing individual aspect elements of a given type/name.
     *
     * @throws IOException
     */
    public void endAspectFragment() throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (!_fragment_started) {
            throw new IllegalStateException("fragment not started");
        }
        _fragment_started = false;
        _jw.endArray();
    }

    /**
     * This method is to be called at the end of writing to a stream.
     *
     * @throws IOException
     */
    public void end() throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        _started = false;
        writeMetaDatas(_post_meta_datas);
        _jw.end();
    }

    /**
     *  This method is to be called at the beginning of writing to a stream.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        if (_started) {
            throw new IllegalStateException("already started");
        }
        _started = true;
        _jw.start();
        writeMetaDatas(_pre_meta_datas);
    }

    private final void writeMetaDatas(final Set<MetaData> mds) throws IOException {
        if ((mds != null) && !mds.isEmpty()) {
            for (final MetaData md : mds) {
                if ((md != null) && !md.getMetaData().isEmpty()) {
                    writeMetaData(md);
                }
            }
        }
    }

    /**
     * This is for writing a list of AspectElements (a aspect fragment) of a given type.
     * <br>
     * A appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter} will be automatically
     * selected (if added previously).
     *
     *
     * @param elements the list of AspectElements to be written
     * @throws IOException
     */
    public void writeAspectElements(final List<AspectElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        if (_writers.containsKey(elements.get(0).getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(elements.get(0).getAspectName());
            writer.write(elements, _jw);
            if (_calculate_element_counts) {
                _element_counts.processAspectElements(elements);
            }
        }
    }

    /**
     * This is for writing a list of AspectElements (a aspect fragment) of a given type.
     *
     * @param elements the list of AspectElements to be written
     * @param writer a appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter}
     * @throws IOException
     */
    public void writeAspectElements(final List<AspectElement> elements, final AspectFragmentWriter writer) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        writer.write(elements, _jw);
        if (_calculate_element_counts) {
            _element_counts.processAspectElements(elements);
        }
    }

    /**
     * This is for writing a single AspectElement.
     * A appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter} will be automatically
     * selected (if added previously).
     * <br>
     * Prior to calling this method for a AspectElement of a given type/name,
     * {@link #startAspectFragment(String aspect_name)} needs to be called.
     * <br>
     * After all AspectElements of a given type/name a written, {@link #endAspectFragment()} needs to be called.
     *
     * @param element the AspectElements to be written
     * @throws IOException
     */
    public void writeAspectElement(final AspectElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (!_fragment_started) {
            throw new IllegalStateException("fragment not started");
        }
        if (element == null) {
            return;
        }
        if (_writers.containsKey(element.getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(element.getAspectName());
            writer.writeElement(element, _jw);
            if (_calculate_element_counts) {
                _element_counts.processAspectElement(element);
            }
        }
    }

    /**
     * This is for writing a single AspectElement.
     * <br>
     * Prior to calling this method for a AspectElement of a given type/name,
     * {@link #startAspectFragment(String aspect_name)} needs to be called.
     * <br>
     * After all AspectElements of a given type/name a written, {@link #endAspectFragment()} needs to be called.
     *
     * @param element the AspectElements to be written
     * @param writer a appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter}
     * @throws IOException
     */
    public void writeAspectElement(final AspectElement element, final AspectFragmentWriter writer) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (!_fragment_started) {
            throw new IllegalStateException("fragment not started");
        }

        if (element == null) {
            return;
        }
        writer.writeElement(element, _jw);
        if (_calculate_element_counts) {
            _element_counts.processAspectElement(element);
        }
    }

    /**
     * This is for writing a single {@link org.cxio.core.AnonymousElement}
     * as the single member of Json array.
     *
     *
     * @param element  the AnonymousElement to be written
     * @throws IOException
     */
    public void writeAnonymousAspectElementAsList(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if (element == null) {
            return;
        }
        _jw.writeJsonNodeAsList(element.getAspectName(), element.getData());

        if (_calculate_element_counts) {
            _element_counts.processAspectElement(element);
        }
    }

    /**
     * This is for writing a list of {@link org.cxio.core.AnonymousElement}.
     *
     * @param elements the AnonymousElements to be written
     * @throws IOException
     */
    public void writeAnonymousAspectElements(final List<AnonymousElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        final List<JsonNode> datas = new ArrayList<JsonNode>();
        for (final AnonymousElement elem : elements) {
            datas.add(elem.getData());
        }
        _jw.writeJsonObjects2(elements.get(0).getAspectName(), datas);
        if (_calculate_element_counts) {
            _element_counts.processAnonymousAspectElements(elements);
        }
    }

    /**
     * This returns an object which gives access to the element counts
     * for the aspect element written out.
     *
     * @return the ElementCounts
     */
    public final AspectElementCounts getAspectElementCounts() {
        return _element_counts;
    }

    /**
     * To turn on/off the calculation of checksum and aspect element counts.
     *
     * @param calculate_element_counts
     */
    public final void setCalculateAspectElementCounts(final boolean calculate_element_counts) {
        _calculate_element_counts = calculate_element_counts;
    }

    public final byte[] getMd5Checksum() {
        if (_md == null) {
            throw new IllegalStateException("cx writer is not set up to calculare checksum");
        }
        return _md.digest();
    }

    public final void addPreMetaData(final MetaData pre_meta_data) {
        _pre_meta_datas.add(pre_meta_data);

    }

    public final void addPostMetaData(final MetaData post_meta_data) {
        _post_meta_datas.add(post_meta_data);

    }

    public final void addPreMetaData(final Collection<MetaData> pre_meta_datas) {
        _pre_meta_datas.addAll(pre_meta_datas);

    }

    public final void addPostMetaData(final Collection<MetaData> post_meta_datas) {
        _post_meta_datas.addAll(post_meta_datas);

    }

    private final void writeMetaData(final MetaData meta_data) throws IOException {
        meta_data.toJson(_jw);
    }

    private CxWriter(final OutputStream os, final boolean use_default_pretty_printer, final boolean calculate_md5_checksum) throws IOException, NoSuchAlgorithmException {
        if (os == null) {
            throw new IllegalArgumentException("attempt to use null outputstream");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();

        OutputStream my_os;
        if (calculate_md5_checksum) {
            _md = MessageDigest.getInstance(Util.MD5);
            my_os = new DigestOutputStream(os, _md);
        }
        else {
            _md = null;
            my_os = os;
        }

        _jw = JsonWriter.createInstance(my_os, use_default_pretty_printer);
        _started = false;
        _fragment_started = false;
        _calculate_element_counts = true;
        _element_counts = AspectElementCounts.createInstance();
        _pre_meta_datas = new HashSet<MetaData>();
        _post_meta_datas = new HashSet<MetaData>();

    }

    private CxWriter(final OutputStream os, final boolean use_default_pretty_printer) throws IOException {
        if (os == null) {
            throw new IllegalArgumentException("attempt to use null outputstream");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();
        _md = null;
        _jw = JsonWriter.createInstance(os, use_default_pretty_printer);
        _started = false;
        _fragment_started = false;
        _calculate_element_counts = true;
        _element_counts = AspectElementCounts.createInstance();
        _pre_meta_datas = new HashSet<MetaData>();
        _post_meta_datas = new HashSet<MetaData>();
    }

    public void writeAnonymousAspectElements(final String name, final String json) throws IOException {
        final AnonymousElement a0 = new AnonymousElement(name, json);
        writeAnonymousAspectElementAsList(a0);

    }

}
