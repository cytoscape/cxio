package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class NodesFragmentReader implements AspectFragmentReader {

    private static final boolean STRICT_DEFAULT = false;
    private final boolean _strict;

    public static NodesFragmentReader createInstance() {
        return new NodesFragmentReader(STRICT_DEFAULT);
    }
    
    public static NodesFragmentReader createInstance(final boolean strict) {
        return new NodesFragmentReader(strict);
    }

    private NodesFragmentReader(final boolean strict) {
        _strict = strict;
    }

    @Override
    public String getAspectName() {
        return NodesElement.NODES;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {

        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + NodesElement.NODES + "'");
        }
        final List<AspectElement> node_aspects = new ArrayList<AspectElement>();
        String id = null;
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (CxConstants.ID.equals(namefield)) {
                        id = jp.getText().trim();
                    }
                    else if (_strict) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }
                }
                if (Util.isEmpty(id)) {
                    throw new IOException("malformed cx json: node id missing");
                }
                node_aspects.add(new NodesElement(id));
            }
            t = jp.nextToken();
        }
        return node_aspects;
    }

}