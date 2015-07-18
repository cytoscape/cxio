package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EdgesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public final static EdgesFragmentReader createInstance() {
        return new EdgesFragmentReader();
    }

    private EdgesFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public final String getAspectName() {
        return EdgesElement.NAME;
    }

    @Override
    public final List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + EdgesElement.NAME + "'");
        }
        final List<AspectElement> edge_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                edge_aspects.add(new EdgesElement(Util.getTextValueRequired(o, EdgesElement.ID), Util
                        .getTextValueRequired(o, EdgesElement.SOURCE_NODE_ID), Util.getTextValueRequired(o,
                                EdgesElement.TARGET_NODE_ID)));
            }
            t = jp.nextToken();
        }

        return edge_aspects;
    }

}
