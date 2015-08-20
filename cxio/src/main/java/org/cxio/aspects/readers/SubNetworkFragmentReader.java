package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class SubNetworkFragmentReader extends AbstractFragmentReader {

    public static SubNetworkFragmentReader createInstance() {
        return new SubNetworkFragmentReader();
    }

    private SubNetworkFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return SubNetworkElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        final SubNetworkElement e = new SubNetworkElement(ParserUtils.getTextValueRequired(o, SubNetworkElement.SUBNET_ID), ParserUtils.getTextValueRequired(o, SubNetworkElement.SUBNET_NAME));
        if (o.has(SubNetworkElement.SUBNET_NODES)) {
            e.getNodes().addAll(ParserUtils.getAsStringList(o, SubNetworkElement.SUBNET_NODES));
        }
        if (o.has(SubNetworkElement.SUBNET_EDGES)) {
            e.getEdges().addAll(ParserUtils.getAsStringList(o, SubNetworkElement.SUBNET_EDGES));
        }
        return e;
    }

}
