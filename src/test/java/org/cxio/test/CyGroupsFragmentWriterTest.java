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

    final static String CX_GROUPS_STR = "["
                                              + TestUtil.NUMBER_VERIFICATION
                                              + ",{\"cyGroups\":[{\"@id\":1,\"view\":222,\"name\":\"name\",\"nodes\":[11,22],\"external_edges\":[1,2],\"internal_edges\":[3,4]}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

    @Test
    public void test() throws IOException {

        final CyGroupsElement e0 = new CyGroupsElement(1L, 222L, "name");

        e0.addExternalEdge(1L);
        e0.addExternalEdge(2L);
        e0.addInternalEdge(3L);
        e0.addInternalEdge(4L);

        e0.addNode(11L);
        e0.addNode(22L);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(e0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(CyGroupsFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end(true, "");
        assertEquals(CX_GROUPS_STR, out1.toString());

    }

}
