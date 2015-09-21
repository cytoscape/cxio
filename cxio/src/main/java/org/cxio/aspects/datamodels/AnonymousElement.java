package org.cxio.aspects.datamodels;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This used to represent a "anonymous", general aspect element. Essentially it
 * is a wrapper for ObjectNode from faster XML library. A ObjectNode in turn is
 * the root of a tree-structure representing json-data.
 *
 * @author cmzmasek
 *
 */
public final class AnonymousElement extends AbstractAspectElement {

    private final ObjectNode _data;
    private final String     _string_data;
    private final String     _name;

    public AnonymousElement(final String name, final ObjectNode data) {
        _name = name;
        _data = data;
        _string_data = null;
    }

    public AnonymousElement(final String name, final String string_data) {
        _name = name;
        _data = null;
        _string_data = string_data;
    }

    @Override
    final public String getAspectName() {
        return _name;
    }

    final public ObjectNode getData() {
        return _data;
    }

    final public String getStingData() {
        return _string_data;
    }

    public final String toJsonString() throws IOException {
        if (_data != null) {
            final ObjectMapper m = new ObjectMapper();
            return m.writeValueAsString(_data);
        }
        else {
            final StringBuilder sb = new StringBuilder();
            sb.append("\"");
            sb.append(_string_data);
            sb.append("\"");
            return sb.toString();
        }
    }

    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(_name);
        sb.append(": ");
        try {
            sb.append(toJsonString());
        }
        catch (final IOException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

}
