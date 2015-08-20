package org.cxio.aspects.readers;

import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractFragmentReader implements AspectFragmentReader {

    ObjectMapper _m          = null;
    String       _time_stamp = null;

    @Override
    public String getTimeStamp() {
        return _time_stamp;
    }
}
