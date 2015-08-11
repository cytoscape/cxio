package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class VisualPropertiesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;
    private String             _time_stamp;

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
        _time_stamp = null;
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                if ((_time_stamp == null) && ParserUtils.isTimeStamp(o)) {
                    _time_stamp = ParserUtils.getTimeStampValue(o);
                }
                else {
                    VisualPropertiesElement vpe;
                    if (o.has(VisualPropertiesElement.APPLIES_TO) && (o.has(VisualPropertiesElement.VIEW))) {
                        vpe = new VisualPropertiesElement(ParserUtils.getTextValueRequired(o, VisualPropertiesElement.PROPERTIES_OF),
                                                          ParserUtils.getAsStringList(o, VisualPropertiesElement.APPLIES_TO),
                                                          ParserUtils.getTextValue(o, VisualPropertiesElement.VIEW));
                    }
                    else if (o.has(VisualPropertiesElement.APPLIES_TO)) {
                        vpe = new VisualPropertiesElement(ParserUtils.getTextValueRequired(o, VisualPropertiesElement.PROPERTIES_OF),
                                                          ParserUtils.getAsStringList(o, VisualPropertiesElement.APPLIES_TO));
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

                            aspects.add(vpe);
                        }
                    }
                }
            }
            t = jp.nextToken();
        }
        return aspects;
    }

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }

}
