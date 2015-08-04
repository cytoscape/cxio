package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

public class SubNetworkFragmentWriter extends AbstractAspectFragmentWriter {

    public static SubNetworkFragmentWriter createInstance() {
        return new SubNetworkFragmentWriter();
    }

    private SubNetworkFragmentWriter() {
    }
    
    @Override
    protected void writeElement(AspectElement element, JsonWriter w) throws IOException {
        final EdgesElement e = (EdgesElement) element;
        w.writeStartObject();
        final String edge_id = e.getId();
        if (!Util.isEmpty(edge_id)) {
            w.writeStringField(EdgesElement.ID, edge_id);
        }
        w.writeStringField(EdgesElement.SOURCE_NODE_ID, e.getSource());
        w.writeStringField(EdgesElement.TARGET_NODE_ID, e.getTarget());
        w.writeEndObject();
        
    }

    @Override
    public String getAspectName() {
        return SubNetworkElement.NAME;
    }

}
