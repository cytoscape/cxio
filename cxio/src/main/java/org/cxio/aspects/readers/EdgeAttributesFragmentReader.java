package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EdgeAttributesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;

    public static EdgeAttributesFragmentReader createInstance() {
        return new EdgeAttributesFragmentReader();
    }

    private EdgeAttributesFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return EdgeAttributesElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed CX json in element " + getAspectName());
        }
        final List<AspectElement> ea_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                final EdgeAttributesElement eae = new EdgeAttributesElement();
                eae.setId(Util.getTextValueRequired(o, AbstractAttributesElement.ID));
                eae.addEdges(Util.getStringListRequired(o, EdgeAttributesElement.EDGES));
                Util.putAttributes(o, AbstractAttributesElement.ATTRIBUTES, eae);
                Util.putAttributeTypes(o, AbstractAttributesElement.ATTRIBUTE_TYPES, eae);
                ea_aspects.add(eae);
            }
            t = jp.nextToken();
        }
        return ea_aspects;
    }

}
