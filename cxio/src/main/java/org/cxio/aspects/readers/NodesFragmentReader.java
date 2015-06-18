package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NodesFragmentReader implements AspectFragmentReader {
    
    public static NodesFragmentReader createInstance() {
        return new NodesFragmentReader();
    }

    private NodesFragmentReader() {
    }

    @Override
    public String getAspectName() {
        return NodesElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp)
    throws IOException {
        final ObjectMapper m = new ObjectMapper();
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" 
                                 + NodesElement.NAME + "'");
        }
        final List<AspectElement> node_elements = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode x = m.readTree(jp);
                String id = null;
                if ((x != null) && x.has(NodesElement.ID)) {
                    id = x.get(NodesElement.ID).asText();
                }
                if (Util.isEmpty(id)) {
                    throw new IOException("malformed cx json: node id missing");
                }
                node_elements.add(new NodesElement(id));
            }
            t = jp.nextToken();
        }
        return node_elements;
    }

}