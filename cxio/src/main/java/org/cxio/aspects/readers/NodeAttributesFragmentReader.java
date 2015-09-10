package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NodeAttributesFragmentReader extends AbstractFragmentReader {

    public static NodeAttributesFragmentReader createInstance() {
        return new NodeAttributesFragmentReader();
    }

    private NodeAttributesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return NodeAttributesElement.NAME;
    }

    @Override
    public final AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
        if (o.has(AbstractAttributesAspectElement.ATTR_DATA_TYPE)) {
            type = AbstractAttributesAspectElement.toDataType(ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_DATA_TYPE));
        }
        return new NodeAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesAspectElement.ATTR_SUBNETWORK),
                                         ParserUtils.getAsStringListRequired(o, AbstractAttributesAspectElement.ATTR_PROPERTY_OF),
                                         ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_NAME),
                                         ParserUtils.getAsStringList(o, AbstractAttributesAspectElement.ATTR_VALUES),
                                         type);
    }

}
