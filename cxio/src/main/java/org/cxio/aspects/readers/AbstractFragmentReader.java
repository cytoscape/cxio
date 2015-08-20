package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractFragmentReader implements AspectFragmentReader {

    protected ObjectMapper _m          = null;
    protected String       _time_stamp = null;

    protected AbstractFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + getAspectName() + "'");
        }
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed cx json in '" + getAspectName() + "'");
                }
                if ((_time_stamp == null) && ParserUtils.isTimeStamp(o)) {
                    _time_stamp = ParserUtils.getTimeStampValue(o);
                }
                else if (ParserUtils.isTimeStamp(o)) {
                    throw new IOException("multiple time stamps in '" + getAspectName() + "'");
                }
                else {
                    final AspectElement e = readElement(o);
                    if (e != null) {
                        elements.add(e);
                    }
                }
            }
            t = jp.nextToken();
        }
        return elements;
    }

    protected abstract AspectElement readElement(final ObjectNode o) throws IOException;
}
