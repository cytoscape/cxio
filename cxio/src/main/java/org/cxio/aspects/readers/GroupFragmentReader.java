package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CyGroupElement;
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
        return CyGroupElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        final String name = ParserUtils.getTextValueRequired(o, CyGroupElement.GROUP_NAME);
        final String group_id = ParserUtils.getTextValueRequired(o, CyGroupElement.GROUP_ID);
        final String view = ParserUtils.getTextValueRequired(o, CyGroupElement.VIEW);

        final CyGroupElement e = new CyGroupElement(group_id, name, view);
        if (o.has(CyGroupElement.NODES)) {
            e.getNodes().addAll(ParserUtils.getAsStringList(o, CyGroupElement.NODES));
        }
        if (o.has(CyGroupElement.INTERNAL_EDGES)) {
            e.getInternalEdges().addAll(ParserUtils.getAsStringList(o, CyGroupElement.INTERNAL_EDGES));
        }
        if (o.has(CyGroupElement.EXTERNAL_EDGES)) {
            e.getExternalEdges().addAll(ParserUtils.getAsStringList(o, CyGroupElement.EXTERNAL_EDGES));
        }
        return e;
    }

}
