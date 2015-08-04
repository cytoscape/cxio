package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NetworkAttributesFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;
    private String             _time_stamp;

    public static NetworkAttributesFragmentReader createInstance() {
        return new NetworkAttributesFragmentReader();
    }

    private NetworkAttributesFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return NetworkAttributesElement.NAME;
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
                if (ParserUtils.isTimeStamp(o)) {
                    _time_stamp = ParserUtils.getTimeStampValue(o);
                }
                else {
                    ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
                    if (o.has(AbstractAttributesElement.ATTR_TYPE)) {
                        type = AbstractAttributesElement.toType(ParserUtils
                                .getTextValueRequired(o, AbstractAttributesElement.ATTR_TYPE));
                    }
                    na_aspects.add(new NetworkAttributesElement(ParserUtils
                            .getAsStringListRequired(o, AbstractAttributesElement.ATTR_PROERTY_OF), ParserUtils
                            .getTextValueRequired(o, AbstractAttributesElement.ATTR_NAME), ParserUtils
                            .getAsStringList(o, AbstractAttributesElement.ATTR_VALUES), type));
                }
            }
            t = jp.nextToken();
        }
        return na_aspects;
    }

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }
}
