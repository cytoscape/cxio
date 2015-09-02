package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CyViewsElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class CyViewsFragmentReader extends AbstractFragmentReader {

    public final static CyViewsFragmentReader createInstance() {
        return new CyViewsFragmentReader();
    }

    private CyViewsFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return CyViewsElement.NAME;
    }

    @Override
    protected AspectElement readElement(final ObjectNode o) throws IOException {
        return new CyViewsElement(ParserUtils.getTextValueRequired(o, CyViewsElement.SUBWORKNET_ID));
    }

}
