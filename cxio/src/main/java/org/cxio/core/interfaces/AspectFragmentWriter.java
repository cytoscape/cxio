package org.cxio.core.interfaces;

import java.io.IOException;
import java.util.List;

import org.cxio.core.JsonWriter;

public interface AspectFragmentWriter {
    public String getAspectName();

    public void write(final List<AspectElement> aspects, final JsonWriter json_writer) throws IOException;

}
