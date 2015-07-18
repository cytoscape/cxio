package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.CytoscapeVisualStyleElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CytoscapeVisualStyleFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public static CytoscapeVisualStyleFragmentReader createInstance() {
        return new CytoscapeVisualStyleFragmentReader();
    }

    private CytoscapeVisualStyleFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return CytoscapeVisualStyleElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + getAspectName() + "'");
        }
        final List<AspectElement> aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                final CytoscapeVisualStyleElement c = new CytoscapeVisualStyleElement(Util.getTextValueRequired(o,
                        CytoscapeVisualStyleElement.TITLE));
                aspects.add(c);

            }
            t = jp.nextToken();
        }
        return aspects;
    }

}
