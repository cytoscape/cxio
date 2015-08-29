package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

/**
 * This is a convenience class for classes implementing the AspectFragmentWriter
 * interface.
 *
 *
 * @author cmzmasek
 *
 */
public abstract class AbstractFragmentWriter implements AspectFragmentWriter {

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        throw new UnsupportedOperationException("this writer does not implement aspect key filtering");
    }

    @Override
    public void write(final List<AspectElement> aspect_elements, final JsonWriter w) throws IOException {
        if (aspect_elements == null) {
            return;
        }
        w.startArray(getAspectName());
        for (final AspectElement aspect_element : aspect_elements) {
            writeElement(aspect_element, w);
        }
        w.endArray();
    }

    @Override
    abstract public String getAspectName();

}
