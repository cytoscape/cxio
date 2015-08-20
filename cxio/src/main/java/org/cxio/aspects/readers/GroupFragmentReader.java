package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.GroupElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class GroupFragmentReader extends AbstractFragmentReader {

    public static GroupFragmentReader createInstance() {
        return new GroupFragmentReader();
    }

    private GroupFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return GroupElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
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
        return e;
    }

}
