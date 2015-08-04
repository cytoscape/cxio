package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class AnonymousFragmentReader implements AspectFragmentReader {

    private boolean            _is_list;
    private final ObjectMapper _m;
    private String             _name;

    public final static AnonymousFragmentReader createInstance() {
        return new AnonymousFragmentReader();
    }

    private AnonymousFragmentReader() {
        _name = null;
        _m = new ObjectMapper();
        _is_list = false;
    }

    @Override
    public final String getAspectName() {
        return _name;
    }

    public final boolean isList() {
        return _is_list;
    }

    @Override
    public final List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        _is_list = false;
        if (t == JsonToken.START_ARRAY) {
            _is_list = true;
        }
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        if (_is_list) {
            while (t != JsonToken.END_ARRAY) {
                if (t == JsonToken.START_OBJECT) {
                    elements.add(new AnonymousElement(_name, _m.readTree(jp)));
                }
                t = jp.nextToken();
            }
        }
        else {
            elements.add(new AnonymousElement(_name, _m.readTree(jp)));
            t = jp.nextToken();
        }
        return elements;
    }

    public final void setAspectName(final String name) {
        _name = name;
    }

    @Override
    public String getTimeStamp() {
        throw new NoSuchMethodError();
    }
}
