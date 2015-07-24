package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class VisualPropertiesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public static VisualPropertiesFragmentReader createInstance() {
        return new VisualPropertiesFragmentReader();
    }

    private VisualPropertiesFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return VisualPropertiesElement.NAME;
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
                final VisualPropertiesElement vpe = new VisualPropertiesElement(Util.getTextValueRequired(o,
                        VisualPropertiesElement.TYPE));

                if (o.has(VisualPropertiesElement.APPLIES_TO)) {
                    final Iterator<JsonNode> e = o.get(VisualPropertiesElement.APPLIES_TO).elements();
                    while (e.hasNext()) {
                        final JsonNode n = e.next();
                        vpe.addAppliesTo(n.asText());
                    }
                }

                final Iterator<Entry<String, JsonNode>> it = o.get(VisualPropertiesElement.PROPERTIES).fields();
                while (it.hasNext()) {
                    final Entry<String, JsonNode> kv = it.next();
                    vpe.putProperty(kv.getKey(), kv.getValue().asText());
                }

                aspects.add(vpe);
            }
            t = jp.nextToken();
        }
        return aspects;
    }

}
