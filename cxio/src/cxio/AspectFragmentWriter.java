package cxio;

import java.io.IOException;
import java.util.List;

public interface AspectFragmentWriter {

    public void write(final List<AspectElement> aspects, final JsonWriter json_writer) throws IOException;

}
