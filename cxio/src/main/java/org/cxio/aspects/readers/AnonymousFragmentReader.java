package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AnonymousFragmentReader implements AspectFragmentReader {

    private String _name;
    
    public static AnonymousFragmentReader createInstance() {
        return new AnonymousFragmentReader();
    }

    private AnonymousFragmentReader() {
        _name = null;
    }

    public void setAspectName(final String name) {
        _name = name;
    }
    
    @Override
    public String getAspectName() {
        return _name;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            //throw new IOException("malformed cx json in '" + CartesianLayoutElement.CARTESIAN_LAYOUT + "'");
        }
        int counter = 0;
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        
        while (t != JsonToken.END_ARRAY) {
            System.out.println(t);
            
            if (t == JsonToken.START_OBJECT) {
                System.out.println(counter);
                counter++;
                //final ObjectNode n = m.createObjectNode();
                final ObjectNode x = m.readTree(jp);
                //System.out.println(x.asText());
                //System.out.println(x.get("A"));
                //System.out.println(x.get("B"));
                elements.add(new AnonymousElement(_name, x));
            }
            t = jp.nextToken();
        }

        return elements;
    }

    public static void main(final String[] args) throws IOException {
        final String t0 = "["
                + "{\"nodes\":[{\"@id\":\"_4\"}]},"
                + "{\"unknown\":[{\"A\":\"a\",\"B\":\"b\"},{\"A\":\"aa\",\"B\":\"bb\"},{\"A\":\"aaa\",\"B\":\"bbb\"}]},"
                + "{\"nodes\":[{\"@id\":\"_6\"}]}"
                + "]";

        final CxReader p = CxReader.createInstance(t0);

        p.addAspectFragmentReader(AnonymousFragmentReader.createInstance());
        
        while (p.hasNext()) {
            final List<AspectElement> elements = p.getNext();
            if (!elements.isEmpty()) {
                final String aspect_name = elements.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                for (final AspectElement element : elements) {
                    System.out.println(element.toString());
                }
            }
        }

    }

}
