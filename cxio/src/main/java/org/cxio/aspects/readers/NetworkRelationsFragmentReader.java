package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.NetworkRelationsElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NetworkRelationsFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;
    private String             _time_stamp;

    public static NetworkRelationsFragmentReader createInstance() {
        return new NetworkRelationsFragmentReader();
    }

    private NetworkRelationsFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return NetworkRelationsElement.NAME;
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
                    aspects.add(new NetworkRelationsElement(ParserUtils.getTextValueRequired(o, NetworkRelationsElement.PARENT),
                                                            ParserUtils.getTextValueRequired(o, NetworkRelationsElement.CHILD),
                                                            ParserUtils.getTextValueRequired(o, NetworkRelationsElement.TYPE)));
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
