package org.cxio.core.interfaces;

import java.io.IOException;
import java.util.List;

import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public interface AspectFragmentWriter {

    public String getAspectName();

    public void setTimeStamp(String time_stamp);

    public void addAspectKeyFilter(final AspectKeyFilter filter);

    public void write(final List<AspectElement> aspects, final JsonWriter json_writer) throws IOException;

    public void writeElement(final AspectElement element, final JsonWriter json_writer) throws IOException;

}
