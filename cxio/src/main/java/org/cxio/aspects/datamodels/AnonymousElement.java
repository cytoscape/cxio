package org.cxio.aspects.datamodels;

import java.io.IOException;

import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class AnonymousElement implements AspectElement {

    private final String     _name;
    private final ObjectNode _node;

    public AnonymousElement(final String name, final ObjectNode node) {
        _name = name;
        _node = node;
    }

    @Override
    final public String getAspectName() {
        return _name;
    }

    final public ObjectNode getData() {
        return _node;
    }

    public final String toJsonString() throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(_node);

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
