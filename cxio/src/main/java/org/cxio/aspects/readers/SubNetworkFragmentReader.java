package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SubNetworkFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;
    private String             _time_stamp;

    public static SubNetworkFragmentReader createInstance() {
        return new SubNetworkFragmentReader();
    }

    private SubNetworkFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return SubNetworkElement.NAME;
    }

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed CX json in element " + getAspectName());
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
                    final SubNetworkElement e = new SubNetworkElement(
                            ParserUtils.getTextValueRequired(o, SubNetworkElement.SUBNET_ID),
                            ParserUtils.getTextValueRequired(o, SubNetworkElement.SUBNET_NAME));
                    if (o.has(SubNetworkElement.SUBNET_NODES)) {
                        e.getNodes().addAll(ParserUtils.getAsStringList(o, SubNetworkElement.SUBNET_NODES));
                    }
                    if (o.has(SubNetworkElement.SUBNET_EDGES)) {
                        e.getEdges().addAll(ParserUtils.getAsStringList(o, SubNetworkElement.SUBNET_EDGES));
                    }
                    aspects.add(e);
                }
            }
            t = jp.nextToken();
        }
        return aspects;
    }

}
