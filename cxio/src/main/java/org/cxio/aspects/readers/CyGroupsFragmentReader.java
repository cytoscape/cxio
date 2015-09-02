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
        return CyGroupsElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        final String name = ParserUtils.getTextValueRequired(o, CyGroupsElement.GROUP_NAME);
        final String group_id = ParserUtils.getTextValueRequired(o, CyGroupsElement.GROUP_ID);
        final String view = ParserUtils.getTextValueRequired(o, CyGroupsElement.VIEW);

        final CyGroupsElement e = new CyGroupsElement(group_id, view, name);
        if (o.has(CyGroupsElement.NODES)) {
            e.getNodes().addAll(ParserUtils.getAsStringList(o, CyGroupsElement.NODES));
        }
        if (o.has(CyGroupsElement.INTERNAL_EDGES)) {
            e.getInternalEdges().addAll(ParserUtils.getAsStringList(o, CyGroupsElement.INTERNAL_EDGES));
        }
        if (o.has(CyGroupsElement.EXTERNAL_EDGES)) {
            e.getExternalEdges().addAll(ParserUtils.getAsStringList(o, CyGroupsElement.EXTERNAL_EDGES));
        }
        return e;
    }

}
