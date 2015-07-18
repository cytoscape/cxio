package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NodeAttributesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public static NodeAttributesFragmentReader createInstance() {
        return new NodeAttributesFragmentReader();
    }

    private NodeAttributesFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return NodeAttributesElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + getAspectName() + "'");
        }
        final List<AspectElement> na_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                final NodeAttributesElement nae = new NodeAttributesElement();
                nae.setId(Util.getTextValueRequired(o, AbstractAttributesElement.ID));
                nae.addNodes(Util.getStringListRequired(o, NodeAttributesElement.NODES));
                Util.putAttributes(o, AbstractAttributesElement.ATTRIBUTES, nae);
                Util.putAttributeTypes(o, AbstractAttributesElement.ATTRIBUTE_TYPES, nae);
                na_aspects.add(nae);
            }
            t = jp.nextToken();
        }
        return na_aspects;
    }
}
