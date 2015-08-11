package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This is used to write apspect fragments (lists of aspects).
 *
 * @author cmzmasek
 *
 */
public class CxWriter {

    private boolean                                 ended;
    private final JsonWriter                        jw;
    private boolean                                 started;
    private final Map<String, AspectFragmentWriter> writers;

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

    private CxWriter(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        this.writers = new HashMap<String, AspectFragmentWriter>();
        this.jw = JsonWriter.createInstance(out, use_default_pretty_printer);
        started = false;
        ended = false;
    }

    public void addAspectFragmentWriter(final AspectFragmentWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("aspect fragment writer is null");
        }
        if (Util.isEmpty(writer.getAspectName())) {
            throw new IllegalArgumentException("aspect name is null or empty");
        }
        writers.put(writer.getAspectName(), writer);
    }

    public void end() throws IOException {
        if (!started) {
            throw new IllegalStateException("not started");
        }
        if (ended) {
            throw new IllegalStateException("already ended");
        }
        ended = true;
        jw.end();
    }

    public void start() throws IOException {
        if (started) {
            throw new IllegalStateException("already started");
        }
        if (ended) {
            throw new IllegalStateException("already ended");
        }
        started = true;
        jw.start();
    }

    public void writeAspectElements(final List<AspectElement> elements) throws IOException {
        if (!started) {
            throw new IllegalStateException("not started");
        }
        if (ended) {
            throw new IllegalStateException("already ended");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        if (writers.containsKey(elements.get(0).getAspectName())) {
            final AspectFragmentWriter writer = writers.get(elements.get(0).getAspectName());
            writer.write(elements, jw);
        }

    }

    // public void writeJsonObjects(final String label, final List<ObjectNode>
    // data_nodes) throws IOException {
    // jw.writeJsonObjects(label, data_nodes);
    // }

    // public void writeJsonObjectAsList(final String label, final ObjectNode
    // data_node) throws IOException {
    // jw.writeJsonObjectAsList(label, data_node);
    // }

    public void writeAnonymousAspectElement(final AnonymousElement element) throws IOException {
        if (!started) {
            throw new IllegalStateException("not started");
        }
        if (ended) {
            throw new IllegalStateException("already ended");
        }
        if (element == null) {
            return;
        }
        jw.writeJsonObject(element.getAspectName(), element.getData());
    }

    public void writeAnonymousAspectElementAsList(final AnonymousElement element) throws IOException {
        if (!started) {
            throw new IllegalStateException("not started");
        }
        if (ended) {
            throw new IllegalStateException("already ended");
        }
        if (element == null) {
            return;
        }
        jw.writeJsonObjectAsList(element.getAspectName(), element.getData());
    }

    public void writeAnonymousAspectElements(final List<AnonymousElement> elements) throws IOException {
        if (!started) {
            throw new IllegalStateException("not started");
        }
        if (ended) {
            throw new IllegalStateException("already ended");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        final List<ObjectNode> datas = new ArrayList<ObjectNode>();
        for (final AnonymousElement elem : elements) {
            datas.add(elem.getData());
        }
        jw.writeJsonObjects(elements.get(0).getAspectName(), datas);
    }

}
