package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NodesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public final static NodesFragmentReader createInstance() {
        return new NodesFragmentReader();
    }

    private NodesFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public final String getAspectName() {
        return NodesElement.NAME;
    }

    @Override
    public final List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {

        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + NodesElement.NAME + "'");
        }
        final List<AspectElement> node_elements = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                node_elements.add(new NodesElement(ParserUtils.getTextValueRequired(o, NodesElement.ID)));
            }
            t = jp.nextToken();
        }
        return node_elements;
    }

}