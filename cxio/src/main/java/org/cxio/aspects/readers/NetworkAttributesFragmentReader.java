package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NetworkAttributesFragmentReader extends AbstractFragmentReader {

    public static NetworkAttributesFragmentReader createInstance() {
        return new NetworkAttributesFragmentReader();
    }

    private NetworkAttributesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return NetworkAttributesElement.NAME;
    }

    @Override
    public final AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
        if (o.has(AbstractAttributesAspectElement.ATTR_DATA_TYPE)) {
            type = AbstractAttributesAspectElement.toDataType(ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_DATA_TYPE));
        }
        return new NetworkAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesAspectElement.ATTR_SUBNETWORK),
                                            ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_NAME),
                                            ParserUtils.getAsStringList(o, AbstractAttributesAspectElement.ATTR_VALUES),
                                            type);
    }

}
