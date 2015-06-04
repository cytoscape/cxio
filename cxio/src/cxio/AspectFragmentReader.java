package cxio;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParser;

public interface AspectFragmentReader {
    public String getAspectName();

    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException;
}
