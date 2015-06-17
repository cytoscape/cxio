package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JsonWriter {
    
    private final JsonGenerator _g;
    private final ObjectMapper  _m;

    final static JsonWriter createInstance(final OutputStream out) throws IOException {
        return createInstance(out, false);
    }
    final static JsonWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer)
            throws IOException {
        return new JsonWriter(out, use_default_pretty_printer);
    }

    private JsonWriter(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final JsonFactory f = new JsonFactory();
        _g = f.createGenerator(out);
        _m = new ObjectMapper();
        if (use_default_pretty_printer) {
            _g.useDefaultPrettyPrinter();
        }
    }

    public final void end() throws IOException {
        _g.writeEndArray();
        _g.close();
    }

    public final void endArray() throws IOException {
        _g.writeEndArray();
        _g.writeEndObject();
    }

    public final void start() throws IOException {
        _g.writeStartArray();
    }

    public final void startArray(final String label) throws IOException {
        _g.writeStartObject();
        _g.writeArrayFieldStart(label);
    }

    public final void writeBooleanField(final String field_name, final boolean value) throws IOException {
        _g.writeBooleanField(field_name, value);
    }

    public final void writeEndArray() throws IOException {
        _g.writeEndArray();
    }

    public final void writeEndObject() throws IOException {
        _g.writeEndObject();
    }

    public void writeJsonObject(final String label, final ObjectNode data_parent) throws JsonProcessingException,
            IOException {
        final ObjectNode node = _m.createObjectNode();
        node.set(label, data_parent);
        node.serialize(_g, null);
    }

    public final void writeList(final String label, final Iterator<String> it) throws IOException {
        _g.writeArrayFieldStart(label);
        while (it.hasNext()) {
            _g.writeString(it.next().toString());
        }
        _g.writeEndArray();
    }

    public final void writeList(final String label, final List<String> list) throws IOException {
        if ((list != null) && !list.isEmpty()) {
            _g.writeArrayFieldStart(label);
            for (final String s : list) {
                _g.writeString(s);
            }
            _g.writeEndArray();
        }
    }

    public final void writeNumberField(final String field_name, final double value) throws IOException {
        _g.writeNumberField(field_name, value);
    }

    public final void writeObjectFieldStart(final String label) throws IOException {
        _g.writeObjectFieldStart(label);
    }

    public final void writeStartArray(final String label) throws IOException {
        _g.writeArrayFieldStart(label);
    }

    public final void writeStartObject() throws IOException {
        _g.writeStartObject();
    }

    public final void writeStringField(final String field_name, final String value) throws IOException {
        _g.writeStringField(field_name, value);
    }

    public final void writeStringFieldIfNotEmpty(final String field_name, final String value) throws IOException {
        if ((value != null) && (value.length() > 0)) {
            _g.writeStringField(field_name, value);
        }
    }

}
