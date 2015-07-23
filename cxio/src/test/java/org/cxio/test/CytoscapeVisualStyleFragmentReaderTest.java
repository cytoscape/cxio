package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CytoscapeVisualProperties;
import org.cxio.aspects.datamodels.CytoscapeVisualStyleElement;
import org.cxio.aspects.readers.CytoscapeVisualStyleFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class CytoscapeVisualStyleFragmentReaderTest {

    @Test
    public void test() throws IOException {
        final String t0 = "[{\"visualStyle\":[{\"title\":\"Sample1\",\"styles\":[{\"applies_to\":\"nodes\",\"selector\":\"node:all\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"}},{\"applies_to\":\"nodes\",\"selector\":\"node:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}}]}]}]";
        final CytoscapeVisualStyleFragmentReader r = CytoscapeVisualStyleFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + CytoscapeVisualStyleElement.NAME + " aspect",
                r0.containsKey(CytoscapeVisualStyleElement.NAME));

        assertFalse("failed to parse " + CytoscapeVisualStyleElement.NAME + " aspect",
                r0.get(CytoscapeVisualStyleElement.NAME).isEmpty());

        assertTrue("failed to parse expected number of " + CytoscapeVisualStyleElement.NAME + " aspects",
                r0.get(CytoscapeVisualStyleElement.NAME).size() == 1);

        final List<AspectElement> aspects = r0.get(CytoscapeVisualStyleElement.NAME);

        final CytoscapeVisualStyleElement v = (CytoscapeVisualStyleElement) aspects.get(0);
        assertTrue(v.getAspectName().equals(CytoscapeVisualStyleElement.NAME));
        assertTrue(v.getTitle().equals("Sample1"));

        assertTrue(v.getProperties().size() == 2);

        final CytoscapeVisualProperties p0 = v.getProperties().get(0);
        final CytoscapeVisualProperties p1 = v.getProperties().get(1);

        assertTrue(p0.getSelector().equals("node:all"));
        assertTrue(p1.getSelector().equals("node:selected"));
        
        assertTrue(p0.getAppliesTo().equals("nodes"));
        assertTrue(p1.getAppliesTo().equals("nodes"));

        assertTrue(p0.getProperties().size() == 3);
        assertTrue(p1.getProperties().size() == 1);

        assertTrue(p0.getProperties().get("background-color").equals("rgb(204,204,255)"));
        assertTrue(p0.getProperties().get("text-opacity").equals("1.0"));
        assertTrue(p0.getProperties().get("width").equals("40.0"));
        assertTrue(p1.getProperties().get("background-color").equals("rgb(255,255,0)"));

    }

}
