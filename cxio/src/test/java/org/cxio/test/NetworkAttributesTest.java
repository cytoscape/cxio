package org.cxio.test;

import static org.junit.Assert.*;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.junit.Test;

public class NetworkAttributesTest {

    @Test
    public void test() {
        
        NetworkAttributesElement e = new NetworkAttributesElement("1", "one",  12 );
        assertTrue(e.getDataType() == ATTRIBUTE_TYPE.INTEGER);
    }

}
