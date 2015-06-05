package cxio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CxWriter {

    private final Map<String, AspectFragmentWriter> writers;
    private final JsonWriter                            jw;
    private boolean started;

    private CxWriter(final OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        this.writers = new HashMap<String, AspectFragmentWriter>();
        this.jw = JsonWriter.createInstance(out);
        started = false;
    }

    public void start() throws  IOException {
        if ( started) {
            throw new IllegalStateException("already started");
        }
        started = true;
        jw.start();
    }
    
    public void end() throws  IOException {
        if ( !started) {
            throw new IllegalStateException("not started");
        }
        jw.end();
    }
    
    public void write(final List<AspectElement> elements) throws IOException {
        if ( !started) {
            throw new IllegalStateException("not started");
        }
        if (elements == null) {
            throw new IllegalArgumentException("list of aspect elements is null");
        }
        
        if (!elements.isEmpty()) {
            if (writers.containsKey(elements.get(0).getAspectName())) {
                final AspectFragmentWriter writer = writers.get(elements.get(0).getAspectName());
                writer.write(elements, jw);
            }
        }
    }

    public final static CxWriter createInstance(final OutputStream out)
            throws IOException {
        return new CxWriter(out);
    }

    public void addAspectFragmentWriter(final AspectFragmentWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("aspect fragment writer is null");
        }
        if (Util.isEmpty(writer.getAspectName())) {
            throw new IllegalArgumentException("aspect name is null or empty");
        }
        writers.put(writer.getAspectName(), writer);
    }

}
