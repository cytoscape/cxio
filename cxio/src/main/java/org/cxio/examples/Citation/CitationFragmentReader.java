package org.cxio.examples.Citation;

import java.io.IOException;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;

public class CitationFragmentReader implements AspectFragmentReader {

    @Override
    public String getAspectName() {
        return CitationElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
