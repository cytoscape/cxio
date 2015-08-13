package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.writers.WriterUtils;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This is used to write apspect fragments (lists of aspects).
 *
 * @author cmzmasek
 *
 */
public class CxWriter {

    private boolean                                 _ended;
    private final JsonWriter                        _jw;
    private boolean                                 _started;
    private final Map<String, AspectFragmentWriter> _writers;

    public final static CxWriter createInstance(final OutputStream out) throws IOException {
        return new CxWriter(out, false);
    }

    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        return new CxWriter(out, use_default_pretty_printer);
    }

    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    private CxWriter(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();
        _jw = JsonWriter.createInstance(out, use_default_pretty_printer);
        _started = false;
        _ended = false;
    }

    public void addAspectFragmentWriter(final AspectFragmentWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("aspect fragment writer is null");
        }
        if (Util.isEmpty(writer.getAspectName())) {
            throw new IllegalArgumentException("aspect name is null or empty");
        }
        _writers.put(writer.getAspectName(), writer);
    }
    
    
    public void startAspectFragment( final String aspect_name ) throws IOException {
        _jw.startArray( aspect_name);
    }
    
    public void startAspectFragment( final String aspect_name, final String time_stamp ) throws IOException {
        _jw.startArray( aspect_name);
        if (!Util.isEmpty(time_stamp)) {
            WriterUtils.writeTimeStamp(time_stamp, _jw);
        }
    }
    
    public void endAspectFragment() throws IOException {
        _jw.endArray();
    }
   
   

    public void end() throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        _ended = true;
        _jw.end();
    }

    public void start() throws IOException {
        if (_started) {
            throw new IllegalStateException("already started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        _started = true;
        _jw.start();
    }

    public void writeAspectElements(final List<AspectElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        if (_writers.containsKey(elements.get(0).getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(elements.get(0).getAspectName());
            writer.write(elements, _jw);
        }
    }
    
    public void writeAspectElement(final AspectElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        if (element == null) {
            return;
        }
        if (_writers.containsKey(element.getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(element.getAspectName());
            writer.writeElement(element,_jw);
        }
    }

    public void writeAnonymousAspectElement(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        if (element == null) {
            return;
        }
        _jw.writeJsonObject(element.getAspectName(), element.getData());
    }
    
    

    public void writeAnonymousAspectElementAsList(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        if (element == null) {
            return;
        }
        _jw.writeJsonObjectAsList(element.getAspectName(), element.getData());
    }

    public void writeAnonymousAspectElements(final List<AnonymousElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_ended) {
            throw new IllegalStateException("already ended");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        final List<ObjectNode> datas = new ArrayList<ObjectNode>();
        for (final AnonymousElement elem : elements) {
            datas.add(elem.getData());
        }
        _jw.writeJsonObjects(elements.get(0).getAspectName(), datas);
    }

}
