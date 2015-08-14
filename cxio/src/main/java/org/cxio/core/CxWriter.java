package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.aspects.writers.WriterUtils;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This is used to write apspect fragments (lists of aspects).
 *
 * @author cmzmasek
 *
 */
public class CxWriter {

    private final JsonWriter                        _jw;
    private boolean                                 _started;
    private final String                            _time_stamp;
    private boolean                                 _fragment_started;

    private final Map<String, AspectFragmentWriter> _writers;

    public final static CxWriter createInstance(final OutputStream out) throws IOException {
        return new CxWriter(out, false);
    }

    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        return new CxWriter(out, use_default_pretty_printer);
    }

    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, time_stamp);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance(time_stamp));
        return w;
    }

    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        return w;
    }

    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers, final String time_stamp)
            throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, time_stamp);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance(time_stamp));
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    public final static CxWriter createInstanceWithAllAvailableWriters(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, time_stamp);
        for (final AspectFragmentWriter afw : Util.getAllAvailableAspectFragmentWriters(time_stamp)) {
            w.addAspectFragmentWriter(afw);
        }
        return w;
    }

    public final static CxWriter createInstanceWithAllAvailableWriters(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        for (final AspectFragmentWriter afw : Util.getAllAvailableAspectFragmentWriters(null)) {
            w.addAspectFragmentWriter(afw);
        }
        return w;
    }

    private CxWriter(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();
        _jw = JsonWriter.createInstance(out, use_default_pretty_printer);
        _time_stamp = null;
        _started = false;
        _fragment_started = false;
    }

    private CxWriter(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();
        _jw = JsonWriter.createInstance(out, use_default_pretty_printer);
        if (!Util.isEmpty(time_stamp)) {
            _time_stamp = time_stamp;
        }
        else {
            _time_stamp = null;
        }
        _started = false;
        _fragment_started = false;

    }

    public void addAspectFragmentWriter(final AspectFragmentWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("aspect fragment writer is null");
        }
        if (Util.isEmpty(writer.getAspectName())) {
            throw new IllegalArgumentException("aspect name is null or empty");
        }
        _writers.put(writer.getAspectName(), writer);
    }

    public void startAspectFragment(final String aspect_name) throws IOException {
        startAspectFragment(aspect_name, null);
    }

    public void startAspectFragment(final String aspect_name, final String time_stamp) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("fragment already started");
        }

        _fragment_started = true;
        _jw.startArray(aspect_name);
        if (!Util.isEmpty(time_stamp)) {
            WriterUtils.writeTimeStamp(time_stamp, _jw);
        }
        else if (!Util.isEmpty(_time_stamp)) {
            WriterUtils.writeTimeStamp(_time_stamp, _jw);
        }
    }

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

    public void end() throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        _started = false;
        _jw.end();
    }

    public void start() throws IOException {
        if (_started) {
            throw new IllegalStateException("already started");
        }
        _started = true;
        _jw.start();
    }

    public void writeAspectElements(final List<AspectElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        if (_writers.containsKey(elements.get(0).getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(elements.get(0).getAspectName());
            writer.write(elements, _jw);
        }
    }

    public void writeAspectElements(final List<AspectElement> elements, final AspectFragmentWriter writer) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        writer.write(elements, _jw);
    }

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
        }
    }

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
    }

    public void writeAnonymousAspectElement(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (element == null) {
            return;
        }
        _jw.writeJsonObject(element.getAspectName(), element.getData());
    }

    public void writeAnonymousAspectElementAsList(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (element == null) {
            return;
        }
        _jw.writeJsonObjectAsList(element.getAspectName(), element.getData());
    }

    public void writeAnonymousAspectElements(final List<AnonymousElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        final List<ObjectNode> datas = new ArrayList<ObjectNode>();
        for (final AnonymousElement elem : elements) {
            datas.add(elem.getData());
        }
        _jw.writeJsonObjects(elements.get(0).getAspectName(), datas);
    }

}
