package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.List;

import org.cxio.aspects.datamodels.GroupElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GroupFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;
    private String             _time_stamp;

    public static GroupFragmentReader createInstance() {
        return new GroupFragmentReader();
    }

    private GroupFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return GroupElement.NAME;
    }

    @Override
    public String getTimeStamp() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
