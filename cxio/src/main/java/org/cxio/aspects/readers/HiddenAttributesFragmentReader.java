package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class HiddenAttributesFragmentReader extends AbstractFragmentReader {

    public static HiddenAttributesFragmentReader createInstance() {
        return new HiddenAttributesFragmentReader();
    }

    private HiddenAttributesFragmentReader() {
        super();
    }

    @Override
    public String getAspectName() {
        return HiddenAttributesElement.NAME;
    }

    @Override
    public AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
        if (o.has(AbstractAttributesElement.ATTR_DATA_TYPE)) {
            type = AbstractAttributesElement.toDataType(ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_DATA_TYPE));
        }
        return new HiddenAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesElement.ATTR_SUBNETWORK),
                                           ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_NAME),
                                           ParserUtils.getAsStringList(o, AbstractAttributesElement.ATTR_VALUES),
                                           type);
    }

}
