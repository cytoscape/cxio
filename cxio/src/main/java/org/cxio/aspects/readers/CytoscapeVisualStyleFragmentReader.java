package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.List;

import org.cxio.aspects.datamodels.CytoscapeVisualStyleElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;

public class CytoscapeVisualStyleFragmentReader implements AspectFragmentReader {

    @Override
    public String getAspectName() {
        return CytoscapeVisualStyleElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(JsonParser jp) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
