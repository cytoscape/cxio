package org.cxio.aspects.datamodels;

import java.io.IOException;

import org.cxio.core.interfaces.AspectElement;

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
public final class AnonymousElement implements AspectElement {

    private final ObjectNode _data;
    private final String     _name;

    public AnonymousElement(final String name, final ObjectNode data) {
        _name = name;
        _data = data;
    }

    @Override
    final public String getAspectName() {
        return _name;
    }

    final public ObjectNode getData() {
        return _data;
    }

    public final String toJsonString() throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(_data);
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
