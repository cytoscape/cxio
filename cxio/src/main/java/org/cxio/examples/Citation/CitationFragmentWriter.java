package org.cxio.examples.Citation;

import java.io.IOException;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.tools.JsonWriter;
import org.ndexbio.model.object.SimplePropertyValuePair;
import org.ndexbio.model.object.network.Citation;

public class CitationFragmentWriter implements AspectFragmentWriter {

    @Override
    public String getAspectName() {
        return CitationElement.NAME;
    }

    public static CitationFragmentWriter createInstance() {
        return new CitationFragmentWriter();
    }

    private CitationFragmentWriter() {
    }

    private final void addCitationAspect(final CitationElement c, final JsonWriter w) throws IOException {
        final Citation cit = c.getCitation();
        w.writeStartObject();
        final List<String> cont = cit.getContributors();
        final long id = cit.getId();
        final String ids = cit.getIdentifier();
        final String id_type = cit.getIdType();
        final List<SimplePropertyValuePair> pp = cit.getPresentationProperties();
        final String title = cit.getTitle();
        final String type = cit.getType();
        w.writeEndObject();
    }

    @Override
    public void write(final List<AspectElement> citation_aspects, final JsonWriter w) throws IOException {
        if (citation_aspects == null) {
            return;
        }
        w.startArray(CitationElement.NAME);
        for (final AspectElement citation_aspect : citation_aspects) {
            addCitationAspect((CitationElement) citation_aspect, w);
        }
        w.endArray();
    }

}
