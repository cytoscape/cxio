package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CartesianLayoutFragmentReader extends AbstractFragmentReader {

    public static CartesianLayoutFragmentReader createInstance() {
        return new CartesianLayoutFragmentReader();
    }

    private CartesianLayoutFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public final String getAspectName() {
        return CartesianLayoutElement.NAME;
    }

    @Override
    public final List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + CartesianLayoutElement.NAME + "'");
        }
        final List<AspectElement> layout_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                final ObjectNode o = _m.readTree(jp);
                if (o == null) {
                    throw new IOException("malformed CX json in element " + getAspectName());
                }
                if ((_time_stamp == null) && ParserUtils.isTimeStamp(o)) {
                    _time_stamp = ParserUtils.getTimeStampValue(o);
                }
                else {
                    if (o.has(CartesianLayoutElement.Z)) {
                        if (o.has(CartesianLayoutElement.VIEW)) {
                            layout_aspects.add(new CartesianLayoutElement(ParserUtils.getTextValueRequired(o, CartesianLayoutElement.NODE),
                                                                          ParserUtils.getTextValue(o, CartesianLayoutElement.VIEW),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.X),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.Y),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.Z)));
                        }
                        else {
                            layout_aspects.add(new CartesianLayoutElement(ParserUtils.getTextValueRequired(o, CartesianLayoutElement.NODE),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.X),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.Y),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.Z)));
                        }
                    }
                    else {
                        if (o.has(CartesianLayoutElement.VIEW)) {
                            layout_aspects.add(new CartesianLayoutElement(ParserUtils.getTextValueRequired(o, CartesianLayoutElement.NODE),
                                                                          ParserUtils.getTextValue(o, CartesianLayoutElement.VIEW),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.X),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.Y),
                                    "0"));

                        }
                        else {
                            layout_aspects.add(new CartesianLayoutElement(ParserUtils.getTextValueRequired(o, CartesianLayoutElement.NODE),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.X),
                                                                          ParserUtils.getTextValueRequired(o, CartesianLayoutElement.Y),
                                    "0"));

                        }
                    }
                }
            }
            t = jp.nextToken();
        }
        return layout_aspects;
    }

}
