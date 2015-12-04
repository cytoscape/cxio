package org.cxio.test;

import static org.junit.Assert.assertTrue;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.junit.Test;

public class NetworkAttributesTest {

    @Test
    public void test() {

        final NetworkAttributesElement e = new NetworkAttributesElement(1L, "one", 12);
        assertTrue(e.getDataType() == ATTRIBUTE_DATA_TYPE.INTEGER);
    }

}
