package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class CartesianLayoutFragmentWriter extends AbstractAspectFragmentWriter {

    public static CartesianLayoutFragmentWriter createInstance() {
        return new CartesianLayoutFragmentWriter();
    }

    private CartesianLayoutFragmentWriter() {
    }

    @Override
    final void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CartesianLayoutElement c = (CartesianLayoutElement) element;
        w.writeStartObject();
        w.writeStringField(CartesianLayoutElement.NODE, c.getNode());
        w.writeNumberField(CartesianLayoutElement.X, c.getX());
        w.writeNumberField(CartesianLayoutElement.Y, c.getY());
        w.writeEndObject();
    }

    @Override
    public String getAspectName() {
        return CartesianLayoutElement.NAME;
    }

}
