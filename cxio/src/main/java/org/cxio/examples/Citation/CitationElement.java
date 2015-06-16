package org.cxio.examples.Citation;

import java.util.List;
import java.util.Map;

import org.cxio.core.interfaces.AspectElement;
import org.ndexbio.model.object.network.Citation;

public class CitationElement implements AspectElement {

    
    protected static final String CONTRIBUTORS              = "contributors";
    protected static final String IDENTIFIER                = "identifier";
    protected static final String IDENTIFIER_TYPE           = "identifierType";
    protected static final String PROPERTIES                = "properties";
    protected static final String PRESENTATION_PROPERTIES = "presentationProperties";

    protected static final String PROPERTY_DATA_TYPE        = "dataType";
    protected static final String PROPERTY_IDENTIFIER       = "identifier";
    protected static final String PROPERTY_IDENTIFIER_TYPE  = "identifierType";
    protected static final String PROPERTY_NAME             = "name";
    protected static final String PROPERTY_PREDICATE_ID     = "predicateId";
    protected static final String PROPERTY_PREDICATE_STRING = "predicateString";
    protected static final String PROPERTY_TYPE             = "type";
    protected static final String PROPERTY_VALUE            = "value";
    protected static final String PROPERTY_VALUE_ID         = "valueId";
    protected static final String TITLE                     = "title";
    protected static final String TYPE                      = "type";
    public final static String NAME = "Citations";
    private final Citation     _citation;
    
    public final static CitationElement createInstance(final Citation citation) {
        return new CitationElement(citation);
    }
    
    public final static CitationElement createInstance() {
        return new CitationElement(new Citation() );
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
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append( _citation.getId() );
        sb.append("\n");
        sb.append("identifier: ");
        sb.append( _citation.getIdentifier() );
        sb.append("\n");
        sb.append("id type: ");
        sb.append( _citation.getIdType() );
        sb.append("\n");
        sb.append("title: ");
        sb.append( _citation.getTitle() );
        sb.append("\n");
        sb.append("type: ");
        sb.append( _citation.getType() );
        sb.append("\n");
        sb.append("contributors: ");
        sb.append( _citation.getContributors() );
        sb.append("\n");
        sb.append("presentation properties: ");
        sb.append( _citation.getPresentationProperties() );
        sb.append("\n");
        sb.append("properties: ");
        sb.append( _citation.getProperties() );
       
        return sb.toString();
    }

}
