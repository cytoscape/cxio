package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NodesFragmentReader extends AbstractFragmentReader {

    public final static NodesFragmentReader createInstance() {
        return new NodesFragmentReader();
    }

    private NodesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return NodesElement.NAME;
    }

    @Override
    public final AspectElement readElement(final ObjectNode o) throws IOException {
        return new NodesElement(ParserUtils.getTextValueRequired(o, NodesElement.ID));
    }

}