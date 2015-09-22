package org.cxio.examples.custom_aspects;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.cxio.aspects.readers.AbstractFragmentReader;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ContextFragmentReader extends AbstractFragmentReader {

    public static ContextFragmentReader createInstance() {
        return new ContextFragmentReader();
    }

    private ContextFragmentReader() {
        super();
    }

    @Override
    public String getAspectName() {
        return ContextElement.NAME;
    }

    @Override
    public AspectElement readElement(final ObjectNode o) throws IOException {
        final ContextElement e = new ContextElement();
        final Iterator<Entry<String, JsonNode>> it = o.fields();
        while (it.hasNext()) {
            final Entry<String, JsonNode> i = it.next();
            e.put(i.getKey(), i.getValue().asText());
        }
        return e;
    }
}
