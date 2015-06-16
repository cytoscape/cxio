package org.cxio.examples.Citation;

import org.cxio.core.interfaces.AspectElement;
import org.ndexbio.model.object.network.Citation;

public class CitationElement implements AspectElement {

    public final static String NAME = "Citation";
    private final Citation     _citation;

    public final static CitationElement createInstance(final Citation citation) {
        return new CitationElement(citation);
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    private CitationElement(final Citation citation) {
        _citation = citation;
    }

    public Citation getCitation() {

        return _citation;
    }

}
