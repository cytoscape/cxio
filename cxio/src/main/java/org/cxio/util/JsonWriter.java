package org.cxio.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JsonWriter {

    private final JsonGenerator _g;
    private final ObjectMapper  _m;

    final static JsonWriter createInstance(final OutputStream out) throws IOException {
        return createInstance(out, false);
    }

    public final static JsonWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        return new JsonWriter(out, use_default_pretty_printer);
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

    public void writeAnonymousAspectElement(final String json_string) throws IOException {
        writeJsonObject(_m.readTree(json_string));
    }

    public final void writeAnonymousAspectElements(final String label, final Collection<String> json_strings) throws IOException {
        final ObjectNode new_parent = _m.createObjectNode();
        final ArrayNode array_node = new_parent.arrayNode();
        for (final String json_string : json_strings) {
            array_node.add(_m.readTree(json_string));
        }
        new_parent.set(label, array_node);
        new_parent.serialize(_g, null);
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

    public final void writeJsonNodeAsList(final String label, final JsonNode data_node) throws IOException {
        final ObjectNode new_parent = _m.createObjectNode();
        new_parent.set(label, new_parent.arrayNode().add(data_node));
        new_parent.serialize(_g, null);
    }

    public final void writeJsonNodeAsList(final String label, final String json_string) throws IOException {
        writeJsonNodeAsList(label, _m.readTree(json_string));
    }

    public final void writeJsonNodes(final String label, final List<JsonNode> json_nodes) throws IOException {
        final ObjectNode new_parent = _m.createObjectNode();
        final ArrayNode array_node = new_parent.arrayNode();

        for (final JsonNode json_node : json_nodes) {
            array_node.add(json_node);
        }
        new_parent.set(label, array_node);
        new_parent.serialize(_g, null);
    }

    public final void writeJsonObject(final JsonNode data_node) throws IOException {
        data_node.serialize(_g, null);
    }

    public final void writeList(final String label, final Collection<String> list) throws IOException {
        if ((list != null) && !list.isEmpty()) {
            _g.writeArrayFieldStart(label);
            for (final String s : list) {
                _g.writeString(s);
            }
            _g.writeEndArray();
        }
    }

    public final void writeList(final String label, final Iterator<String> it) throws IOException {
        _g.writeArrayFieldStart(label);
        while (it.hasNext()) {
            _g.writeString(it.next().toString());
        }
        _g.writeEndArray();
    }

    public final void writeNumberField(final String field_name, final double value) throws IOException {
        _g.writeNumberField(field_name, value);
    }

    public final void writeObject(final Object obj) throws IOException {
        final ObjectWriter ow = _m.writer();
        ow.writeValue(_g, obj);
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

    private JsonWriter(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final JsonFactory f = new JsonFactory();
        _g = f.createGenerator(out);
        _m = new ObjectMapper();
        if (use_default_pretty_printer) {
            _g.useDefaultPrettyPrinter();
        }
    }

}
