package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class EdgesFragmentReader implements AspectFragmentReader {

    private static final boolean STRICT_DEFAULT = false;
    private final boolean        _strict;

    public static EdgesFragmentReader createInstance() {
        return new EdgesFragmentReader(STRICT_DEFAULT);
    }

    public static EdgesFragmentReader createInstance(final boolean strict) {
        return new EdgesFragmentReader(strict);
    }

    private EdgesFragmentReader(final boolean strict) {
        _strict = strict;
    }

    @Override
    public String getAspectName() {
        return EdgesElement.EDGES;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + EdgesElement.EDGES + "'");
        }
        final List<AspectElement> edge_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                String id = null;
                String source = null;
                String target = null;
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (CxConstants.ID.equals(namefield)) {
                        id = jp.getText().trim();
                    }
                    else if (EdgesElement.SOURCE_NODE_ID.equals(namefield)) {
                        source = jp.getText().trim();
                    }
                    else if (EdgesElement.TARGET_NODE_ID.equals(namefield)) {
                        target = jp.getText().trim();
                    }
                    else if (_strict) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }
                }
                if (Util.isEmpty(source)) {
                    throw new IOException("malformed cx json: edge source is missing");
                }
                if (Util.isEmpty(target)) {
                    throw new IOException("malformed cx json: edge target is missing");
                }
                edge_aspects.add(new EdgesElement(id, source, target));
            }
            t = jp.nextToken();
        }

        return edge_aspects;
    }

}
