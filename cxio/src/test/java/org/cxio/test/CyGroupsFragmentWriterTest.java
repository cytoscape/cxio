package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.aspects.writers.CyGroupsFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.junit.Test;

public class CyGroupsFragmentWriterTest {

    final static String CX_GROUPS_STR = "[{\"cyGroups\":[{\"group\":\"group_id\",\"view\":\"view\",\"name\":\"name\",\"nodes\":[\"n1\",\"n2\"],\"external_edges\":[\"ext1\",\"ext2\"],\"internal_edges\":[\"int1\",\"int2\"]}]}]";

    @Test
    public void test() throws IOException {

        final CyGroupsElement e0 = new CyGroupsElement("group_id", "view", "name");

        e0.addExternalEdge("ext1");
        e0.addExternalEdge("ext2");
        e0.addInternalEdge("int1");
        e0.addInternalEdge("int2");

        e0.addNode("n1");
        e0.addNode("n2");

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(e0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(CyGroupsFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals(CX_GROUPS_STR, out1.toString());

    }

}
