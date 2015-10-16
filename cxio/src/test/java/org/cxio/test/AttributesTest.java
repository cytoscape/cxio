package org.cxio.test;

import static org.junit.Assert.*;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.junit.Test;

public class AttributesTest {

    @Test
    public void test() {
        HiddenAttributesElement a = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", null, ATTRIBUTE_DATA_TYPE.STRING);
        
        assertTrue(a.isSingleValue() == true);
        assertTrue(a.getValue()== null);
        
        HiddenAttributesElement b = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", "\"v\"", ATTRIBUTE_DATA_TYPE.STRING);
        
        assertTrue(b.isSingleValue() == true);
        assertTrue(b.getValue().equals("v"));
        
        HiddenAttributesElement c = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", null, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(c.isSingleValue() == false);
        assertTrue(c.getValues() == null);
        //assertTrue(c.getValue().equals("v"));
        
    }

}
