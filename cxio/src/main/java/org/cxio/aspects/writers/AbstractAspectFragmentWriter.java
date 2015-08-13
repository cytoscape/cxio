package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

/**
 * This is a convenience class for classes implementing the AspectFragmentWriter
 * interface.
 *
 *
 * @author cmzmasek
 *
 */
public abstract class AbstractAspectFragmentWriter implements AspectFragmentWriter {

    String _time_stamp = null;

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        throw new UnsupportedOperationException("this writer does not implement aspect key filtering");
    }

    @Override
    public void setTimeStamp(final String time_stamp) {
        _time_stamp = time_stamp;
    }

    @Override
    public void write(final List<AspectElement> aspect_elements, final JsonWriter w) throws IOException {
        if (aspect_elements == null) {
            return;
        }
        w.startArray(getAspectName());
        if (!Util.isEmpty(_time_stamp)) {
            WriterUtils.writeTimeStamp(_time_stamp, w);
        }
        for (final AspectElement aspect_element : aspect_elements) {
            writeElement(aspect_element, w);
        }
        w.endArray();
    }

    abstract protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException;

    @Override
    abstract public String getAspectName();

}
