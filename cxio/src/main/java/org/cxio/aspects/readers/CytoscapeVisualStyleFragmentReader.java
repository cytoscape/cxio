package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.CytoscapeVisualProperties;
import org.cxio.aspects.datamodels.CytoscapeVisualStyleElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
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
                final CytoscapeVisualStyleElement visual_style = new CytoscapeVisualStyleElement(
                        Util.getTextValueRequired(o, CytoscapeVisualStyleElement.TITLE));
                final JsonNode styles = o.get(CytoscapeVisualStyleElement.STYLES);
                for (int i = 0; i < styles.size(); ++i) {
                    final JsonNode style = styles.get(i);
                    final String selector = style.get(CytoscapeVisualStyleElement.SELECTOR).asText();
                    final String applies_to = style.get(CytoscapeVisualStyleElement.APPLIES_TO).asText();
                    if (Util.isEmpty(selector)) {
                        throw new IOException("selector is null or empty");
                    }
                    final CytoscapeVisualProperties properties = new CytoscapeVisualProperties(selector, applies_to);
                    final Iterator<Entry<String, JsonNode>> it = style.get(CytoscapeVisualStyleElement.PROPERTIES).fields();
                    while (it.hasNext()) {
                        final Entry<String, JsonNode> kv = it.next();
                        properties.put(kv.getKey(), kv.getValue().asText());
                    }
                    visual_style.addProperties(properties);
                }
                aspects.add(visual_style);
            }
            t = jp.nextToken();
        }
        return aspects;
    }

}
