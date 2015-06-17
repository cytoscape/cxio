package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.tools.Util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CxWriter {

    private boolean                                 ended;
    private final JsonWriter                        jw;
    private boolean                                 started;
    private final Map<String, AspectFragmentWriter> writers;

    public final static CxWriter createInstance(final OutputStream out) throws IOException {
        return new CxWriter(out, false);
    }

    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer)
            throws IOException {
        return new CxWriter(out, use_default_pretty_printer);
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

    public void write(final List<AspectElement> elements) throws IOException {
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

    public void write(final String label, final ObjectNode data_parent) throws JsonGenerationException,
            JsonMappingException, IOException {
        jw.writeJsonObject(label, data_parent);
    }

}
