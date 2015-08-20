package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.GroupElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GroupFragmentReader extends AbstractFragmentReader {

    public static GroupFragmentReader createInstance() {
        return new GroupFragmentReader();
    }

    private GroupFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return GroupElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed CX json in element " + getAspectName());
        }
        final List<AspectElement> aspects = new ArrayList<AspectElement>();
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
                    final String name = ParserUtils.getTextValueRequired(o, GroupElement.GROUP_NAME);
                    final String group_node = ParserUtils.getTextValue(o, GroupElement.GROUP_NODE);
                    final String view = ParserUtils.getTextValue(o, GroupElement.VIEW);

                    final GroupElement e = new GroupElement(group_node, name, view);
                    if (o.has(GroupElement.NODES)) {
                        e.getNodes().addAll(ParserUtils.getAsStringList(o, GroupElement.NODES));
                    }
                    if (o.has(GroupElement.INTERNAL_EDGES)) {
                        e.getInternalEdges().addAll(ParserUtils.getAsStringList(o, GroupElement.INTERNAL_EDGES));
                    }
                    if (o.has(GroupElement.EXTERNAL_EDGES)) {
                        e.getExternalEdges().addAll(ParserUtils.getAsStringList(o, GroupElement.EXTERNAL_EDGES));
                    }
                    aspects.add(e);
                }
            }
            t = jp.nextToken();
        }
        return aspects;
    }

}
