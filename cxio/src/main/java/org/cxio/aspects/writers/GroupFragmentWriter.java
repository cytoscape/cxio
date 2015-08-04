package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.GroupElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class GroupFragmentWriter extends AbstractAspectFragmentWriter {

    public static GroupFragmentWriter createInstance() {
        return new GroupFragmentWriter();
    }
    
    private GroupFragmentWriter() {
    }
    
    @Override
    protected void writeElement(AspectElement element, JsonWriter w) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getAspectName() {
        return GroupElement.NAME;
    }

}
