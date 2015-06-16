package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class EdgeAttributesFragmentReader implements AspectFragmentReader {
    private static final boolean STRICT = true;

    public static EdgeAttributesFragmentReader createInstance() {
        return new EdgeAttributesFragmentReader();
    }

    private EdgeAttributesFragmentReader() {
    }

    @Override
    public String getAspectName() {
        return EdgeAttributesElement.EDGE_ATTRIBUTES;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + EdgeAttributesElement.EDGE_ATTRIBUTES + "'");
        }
        final List<AspectElement> ea_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final EdgeAttributesElement eae = new EdgeAttributesElement();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (CxConstants.ID.equals(namefield)) {
                        eae.setId(jp.getText());
                    }
                    else if (EdgeAttributesElement.EDGES.equals(namefield)) {
                        for (final String edge : Util.parseSimpleList(jp, t)) {
                            eae.addEdge(edge);
                        }
                    }
                    else if (AbstractAttributesElement.ATTRIBUTES.equals(namefield)) {
                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                            jp.nextToken(); // move to value
                            eae.putValues(jp.getCurrentName(), Util.parseSimpleList(jp, t));
                        }
                    }
                    else if (AbstractAttributesElement.ATTRIBUTE_TYPES.equals(namefield)) {
                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                            jp.nextToken(); // move to value
                            eae.putType(jp.getCurrentName(), jp.getText());
                        }
                    }
                    else if (STRICT) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }
                }
                if (Util.isEmpty(eae.getId())) {
                    throw new IOException("malformed cx json: attribute id in edge attributes is missing");
                }
                if ((eae.getEdges() == null) || eae.getEdges().isEmpty()) {
                    throw new IOException("malformed cx json: edge ids in edge attributes are missing");
                }
                ea_aspects.add(eae);
            }
            t = jp.nextToken();
        }

        return ea_aspects;
    }
}
