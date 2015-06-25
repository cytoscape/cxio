package org.cxio.core.interfaces;

import java.io.IOException;
import java.util.List;

import org.cxio.core.JsonWriter;
import org.cxio.filters.AspectKeyFilter;

public interface AspectFragmentWriter {

    public String getAspectName();

    public void addAspectKeyFilter(final AspectKeyFilter filter);

    public void write(final List<AspectElement> aspects, final JsonWriter json_writer) throws IOException;

}
