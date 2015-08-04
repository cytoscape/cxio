package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.List;

import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubNetworkFragmentReader implements AspectFragmentReader {

    private final ObjectMapper _m;
    private String             _time_stamp;

    public static SubNetworkFragmentReader createInstance() {
        return new SubNetworkFragmentReader();
    }

    private SubNetworkFragmentReader() {
        _m = new ObjectMapper();
    }

    @Override
    public String getAspectName() {
        return SubNetworkElement.NAME;
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
