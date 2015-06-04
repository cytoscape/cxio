package cxio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class CxWriter {

    private final OutputStream              out;
    private final Set<AspectFragmentWriter> writers;

    private CxWriter(final OutputStream out, final Set<AspectFragmentWriter> writers) {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        if (writers == null) {
            throw new IllegalArgumentException("set of writers is null");
        }
        this.out = out;
        this.writers = writers;
    }

    public void write() throws IOException {
        JsonWriter jw = JsonWriter.createInstance(out);

    }

}
