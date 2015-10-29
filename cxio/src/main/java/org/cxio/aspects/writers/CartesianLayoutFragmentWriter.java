package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class CartesianLayoutFragmentWriter extends AbstractFragmentWriter {

    public static CartesianLayoutFragmentWriter createInstance() {
        return new CartesianLayoutFragmentWriter();
    }

    private CartesianLayoutFragmentWriter() {
    }

    @Override
    public final void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CartesianLayoutElement c = (CartesianLayoutElement) element;
        w.writeStartObject();
        w.writeStringField(CartesianLayoutElement.NODE, c.getNode());
        w.writeStringFieldIfNotEmpty(CartesianLayoutElement.VIEW, c.getView());
        w.writeNumberField(CartesianLayoutElement.X, Double.valueOf(c.getX()));
        w.writeNumberField(CartesianLayoutElement.Y, Double.valueOf(c.getY()));
        if (c.isZset()) {
            w.writeNumberField(CartesianLayoutElement.Z, Double.valueOf(c.getZ()));
        }
        w.writeEndObject();
    }

    @Override
    public String getAspectName() {
        return CartesianLayoutElement.ASPECT_NAME;
    }

}
