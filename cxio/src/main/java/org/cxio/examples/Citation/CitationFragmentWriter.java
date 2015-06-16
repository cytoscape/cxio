package org.cxio.examples.Citation;

import java.io.IOException;
import java.util.List;

import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.tools.JsonWriter;
import org.cxio.tools.Util;
import org.ndexbio.model.object.NdexPropertyValuePair;
import org.ndexbio.model.object.SimplePropertyValuePair;
import org.ndexbio.model.object.network.Citation;

public class CitationFragmentWriter implements AspectFragmentWriter {

   

    
    public static CitationFragmentWriter createInstance() {
        return new CitationFragmentWriter();
    }

    private CitationFragmentWriter() {
    }

    private final void addElement(final CitationElement c, final JsonWriter w) throws IOException {
        final Citation cit = c.getCitation();
        w.writeStartObject();
        w.writeStringField(CxConstants.ID, String.valueOf(cit.getId()));
        w.writeStringFieldIfNotEmpty(CitationElement.TITLE, cit.getTitle());
        w.writeStringFieldIfNotEmpty(CitationElement.TYPE, cit.getType());
        w.writeStringFieldIfNotEmpty(CitationElement.IDENTIFIER, cit.getIdentifier());
        w.writeStringFieldIfNotEmpty(CitationElement.IDENTIFIER_TYPE, cit.getIdType());
        w.writeList(CitationElement.CONTRIBUTORS, cit.getContributors());

        final List<NdexPropertyValuePair> ps = cit.getProperties();
        if ((ps != null) && !ps.isEmpty()) {
            w.writeStartArray(CitationElement.PROPERTIES);
            for (final NdexPropertyValuePair p : ps) {
                if (p != null) {
                    w.writeStartObject();
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_DATA_TYPE, p.getDataType());
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_PREDICATE_STRING, p.getPredicateString());
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_TYPE, p.getType());
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_VALUE, p.getValue());
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_PREDICATE_ID, String.valueOf(p.getPredicateId()));
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_VALUE_ID, String.valueOf(p.getValueId()));
                    w.writeEndObject();
                }
            }
            w.writeEndArray();
        }

        final List<SimplePropertyValuePair> pp = cit.getPresentationProperties();
        if ((pp != null) && !pp.isEmpty()) {
            w.writeStartArray(CitationElement.PRESENTATION_PROPERTIES);
            for (final SimplePropertyValuePair p : pp) {
                if ((p != null) && !Util.isEmpty(p.getValue()) && !Util.isEmpty(p.getName())) {
                    w.writeStartObject();
                    w.writeStringField(CitationElement.PROPERTY_NAME, p.getName());
                    w.writeStringFieldIfNotEmpty(CitationElement.PROPERTY_TYPE, p.getType());
                    w.writeStringField(CitationElement.PROPERTY_VALUE, p.getValue());
                    w.writeEndObject();
                }
            }
            w.writeEndArray();
        }
        w.writeEndObject();
    }

    @Override
    public String getAspectName() {
        return CitationElement.NAME;
    }

    @Override
    public void write(final List<AspectElement> citation_elements, final JsonWriter w) throws IOException {
        if (citation_elements == null) {
            return;
        }
        w.startArray(CitationElement.NAME);
        for (final AspectElement citation_aspect_element : citation_elements) {
            addElement((CitationElement) citation_aspect_element, w);
        }
        w.endArray();
    }

}
