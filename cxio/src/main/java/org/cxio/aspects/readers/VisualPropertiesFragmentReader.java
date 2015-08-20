package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class VisualPropertiesFragmentReader extends AbstractFragmentReader {

    public static VisualPropertiesFragmentReader createInstance() {
        return new VisualPropertiesFragmentReader();
    }

    private VisualPropertiesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return VisualPropertiesElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        VisualPropertiesElement vpe;
        if (o.has(VisualPropertiesElement.APPLIES_TO) && (o.has(VisualPropertiesElement.VIEW))) {
            vpe = new VisualPropertiesElement(ParserUtils.getTextValueRequired(o, VisualPropertiesElement.PROPERTIES_OF),
                                              ParserUtils.getAsStringList(o, VisualPropertiesElement.APPLIES_TO),
                                              ParserUtils.getTextValue(o, VisualPropertiesElement.VIEW));
        }
        else if (o.has(VisualPropertiesElement.APPLIES_TO)) {
            vpe = new VisualPropertiesElement(ParserUtils.getTextValueRequired(o, VisualPropertiesElement.PROPERTIES_OF), ParserUtils.getAsStringList(o, VisualPropertiesElement.APPLIES_TO));
        }
        else {
            vpe = new VisualPropertiesElement(ParserUtils.getTextValueRequired(o, VisualPropertiesElement.PROPERTIES_OF));
        }
        if (o.has(VisualPropertiesElement.PROPERTIES)) {
            final Iterator<Entry<String, JsonNode>> it = o.get(VisualPropertiesElement.PROPERTIES).fields();
            if (it != null) {
                while (it.hasNext()) {
                    final Entry<String, JsonNode> kv = it.next();
                    vpe.putProperty(kv.getKey(), kv.getValue().asText());
                }

                return vpe;
            }
        }
        return null;
    }

}
