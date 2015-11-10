package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CyGroupsFragmentReader extends AbstractFragmentReader {

    public static CyGroupsFragmentReader createInstance() {
        return new CyGroupsFragmentReader();
    }

    private CyGroupsFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return CyGroupsElement.ASPECT_NAME;
    }

    @Override
    public final AspectElement readElement(final ObjectNode o) throws IOException {
        final String name = ParserUtils.getTextValueRequired(o, CyGroupsElement.GROUP_NAME);
        final Long group_id = ParserUtils.getTextValueRequiredAsLong(o, CyGroupsElement.GROUP_ID);
        final Long view = ParserUtils.getTextValueRequiredAsLong(o, CyGroupsElement.VIEW);

        final CyGroupsElement e = new CyGroupsElement(group_id, view, name);
        if (o.has(CyGroupsElement.NODES)) {
            e.getNodes().addAll(ParserUtils.getAsLongList(o, CyGroupsElement.NODES));
        }
        if (o.has(CyGroupsElement.INTERNAL_EDGES)) {
            e.getInternalEdges().addAll(ParserUtils.getAsLongList(o, CyGroupsElement.INTERNAL_EDGES));
        }
        if (o.has(CyGroupsElement.EXTERNAL_EDGES)) {
            e.getExternalEdges().addAll(ParserUtils.getAsLongList(o, CyGroupsElement.EXTERNAL_EDGES));
        }
        return e;
    }

}
