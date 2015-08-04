package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.aspects.readers.VisualPropertiesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class VisualPropertiesFragmentReaderTest {

    @Test
    public void test() throws IOException {
        final String t0 = "[{\"visualProperties\":[{\"properties_of\":\"nodes:default\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"}},{\"properties_of\":\"nodes:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}},{\"properties_of\":\"nodes\",\"applies_to\":[\"1\",\"2\"],\"properties\":{\"background-color\":\"rgb(0,0,0)\"}}]}]";
        final VisualPropertiesFragmentReader r = VisualPropertiesFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + VisualPropertiesElement.NAME + " aspect",
                   r0.containsKey(VisualPropertiesElement.NAME));

        assertFalse("failed to parse " + VisualPropertiesElement.NAME + " aspect", r0.get(VisualPropertiesElement.NAME)
                .isEmpty());

        assertTrue("failed to parse expected number of " + VisualPropertiesElement.NAME + " aspects",
                   r0.get(VisualPropertiesElement.NAME).size() == 3);

        final List<AspectElement> aspects = r0.get(VisualPropertiesElement.NAME);

        final VisualPropertiesElement v0 = (VisualPropertiesElement) aspects.get(0);
        assertTrue(v0.getAspectName().equals(VisualPropertiesElement.NAME));
        assertTrue(v0.getPropertiesOf().equals("nodes:default"));

        assertTrue(v0.getProperties().size() == 3);

    }

}
