package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.aspects.datamodels.Mapping;
import org.cxio.aspects.readers.CyVisualPropertiesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class CyVisualPropertiesFragmentReaderTest {

    @Test
    public void test1() throws IOException {
        final String t0 = "["
                + TestUtil.NUMBER_VERIFICATION
                + ",{\"cyVisualProperties\":[{\"properties_of\":\"nodes:default\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"}},{\"properties_of\":\"nodes:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}},{\"properties_of\":\"nodes\",\"applies_to\":[\"1\",\"2\"],\"properties\":{\"background-color\":\"rgb(0,0,0)\"}}]}]";
        final CyVisualPropertiesFragmentReader r = CyVisualPropertiesFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + CyVisualPropertiesElement.ASPECT_NAME + " aspect", r0.containsKey(CyVisualPropertiesElement.ASPECT_NAME));

        assertFalse("failed to parse " + CyVisualPropertiesElement.ASPECT_NAME + " aspect", r0.get(CyVisualPropertiesElement.ASPECT_NAME).isEmpty());

        assertTrue("failed to parse expected number of " + CyVisualPropertiesElement.ASPECT_NAME + " aspects", r0.get(CyVisualPropertiesElement.ASPECT_NAME).size() == 3);

        final List<AspectElement> aspects = r0.get(CyVisualPropertiesElement.ASPECT_NAME);

        final CyVisualPropertiesElement v0 = (CyVisualPropertiesElement) aspects.get(0);
        assertTrue(v0.getAspectName().equals(CyVisualPropertiesElement.ASPECT_NAME));
        assertTrue(v0.getPropertiesOf().equals("nodes:default"));

        assertTrue(v0.getProperties().size() == 3);

    }

    @Test
    public void test2() throws IOException {
        final String t0 = "["
                + TestUtil.NUMBER_VERIFICATION
                + ",{\"cyVisualProperties\":[{\"properties_of\":\"nodes:default\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"},\"mappings\":{\"NODE_COLOR\":{\"type\":\"cont\",\"definition\":\"rgb12,0-0-0\\\"asdef\\\"\"}}},{\"properties_of\":\"nodes:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}},{\"properties_of\":\"nodes\",\"applies_to\":[1,2],\"properties\":{\"background-color\":\"rgb(0,0,0)\"}}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";
        final CyVisualPropertiesFragmentReader r = CyVisualPropertiesFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + CyVisualPropertiesElement.ASPECT_NAME + " aspect", r0.containsKey(CyVisualPropertiesElement.ASPECT_NAME));

        assertFalse("failed to parse " + CyVisualPropertiesElement.ASPECT_NAME + " aspect", r0.get(CyVisualPropertiesElement.ASPECT_NAME).isEmpty());

        assertTrue("failed to parse expected number of " + CyVisualPropertiesElement.ASPECT_NAME + " aspects", r0.get(CyVisualPropertiesElement.ASPECT_NAME).size() == 3);

        final List<AspectElement> aspects = r0.get(CyVisualPropertiesElement.ASPECT_NAME);

        final CyVisualPropertiesElement v0 = (CyVisualPropertiesElement) aspects.get(0);
        assertTrue(v0.getAspectName().equals(CyVisualPropertiesElement.ASPECT_NAME));
        assertTrue(v0.getPropertiesOf().equals("nodes:default"));
        assertTrue(v0.getProperties().size() == 3);
        assertTrue(v0.getMappings().size() == 1);

        assertTrue(v0.getMappings().get("NODE_COLOR") != null);

        final Mapping m = v0.getMappings().get("NODE_COLOR");
        assertTrue(m.getType().equals("cont"));
        assertTrue(m.getDefintion().equals("rgb12,0-0-0\"asdef\""));

    }

    @Test
    public void test3() throws IOException {
        final String t0 = "["
                + TestUtil.NUMBER_VERIFICATION

                + ",{\"cyVisualProperties\":[{\"properties_of\":\"nodes:default\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"},\"dependencies\":{\"dependency1\":\"true\"},\"mappings\":{\"NODE_COLOR\":{\"type\":\"cont\",\"definition\":\"rgb12,0-0-0\\\"asdef\\\"\"}}},{\"properties_of\":\"nodes:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}},{\"properties_of\":\"nodes\",\"applies_to\":[1,2],\"properties\":{\"background-color\":\"rgb(0,0,0)\"}}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

        final CyVisualPropertiesFragmentReader r = CyVisualPropertiesFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + CyVisualPropertiesElement.ASPECT_NAME + " aspect", r0.containsKey(CyVisualPropertiesElement.ASPECT_NAME));

        assertFalse("failed to parse " + CyVisualPropertiesElement.ASPECT_NAME + " aspect", r0.get(CyVisualPropertiesElement.ASPECT_NAME).isEmpty());

        assertTrue("failed to parse expected number of " + CyVisualPropertiesElement.ASPECT_NAME + " aspects", r0.get(CyVisualPropertiesElement.ASPECT_NAME).size() == 3);

        final List<AspectElement> aspects = r0.get(CyVisualPropertiesElement.ASPECT_NAME);

        final CyVisualPropertiesElement v0 = (CyVisualPropertiesElement) aspects.get(0);
        assertTrue(v0.getAspectName().equals(CyVisualPropertiesElement.ASPECT_NAME));
        assertTrue(v0.getPropertiesOf().equals("nodes:default"));
        assertTrue(v0.getProperties().size() == 3);
        assertTrue(v0.getMappings().size() == 1);

        assertTrue(v0.getMappings().get("NODE_COLOR") != null);

        final Mapping m = v0.getMappings().get("NODE_COLOR");
        assertTrue(m.getType().equals("cont"));
        assertTrue(m.getDefintion().equals("rgb12,0-0-0\"asdef\""));

        assertTrue(v0.getDependencies().size() == 1);
        assertTrue(v0.getDependencies().get("dependency1").equals("true"));

    }

}
