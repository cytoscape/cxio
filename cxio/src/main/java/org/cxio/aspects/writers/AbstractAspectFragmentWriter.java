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
public abstract class AbstractAspectFragmentWriter implements AspectFragmentWriter {

    String  _time_stamp         = null;
    boolean _time_stamp_written = false;

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        throw new UnsupportedOperationException("this writer does not implement aspect key filtering");
    }

    @Override
    public void setTimeStamp(final String time_stamp) {
        if ((_time_stamp != null) && !_time_stamp.equals(time_stamp)) {
            throw new IllegalStateException("illegal attempt to change the time stamp of a fragment writer");
        }
        _time_stamp = time_stamp;
    }

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }

    @Override
    public void write(final List<AspectElement> aspect_elements, final JsonWriter w) throws IOException {
        if (aspect_elements == null) {
            return;
        }
        w.startArray(getAspectName());
        if (!_time_stamp_written) {
            WriterUtils.writeTimeStamp(_time_stamp, w);
            _time_stamp_written = true; // To prevent written the time stamp in
                                        // multiple fragments of the same type.
        }
        for (final AspectElement aspect_element : aspect_elements) {
            writeElement(aspect_element, w);
        }
        w.endArray();
    }

    @Override
    abstract public String getAspectName();

}
