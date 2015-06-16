package org.cxio.examples.Citation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.tools.Util;
import org.ndexbio.model.object.NdexPropertyValuePair;
import org.ndexbio.model.object.SimplePropertyValuePair;
import org.ndexbio.model.object.network.Citation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class CitationFragmentReader implements AspectFragmentReader {

    private static final boolean STRICT = false;

    public static CitationFragmentReader createInstance() {
        return new CitationFragmentReader();
    }

    @Override
    public String getAspectName() {
        return CitationElement.NAME;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {

        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + CitationElement.NAME + "'");
        }
        final List<AspectElement> na_aspects = new ArrayList<AspectElement>();
        System.out.println("_____  start name is:" + jp.getCurrentName());
        while ((t != JsonToken.END_ARRAY) || !jp.getCurrentName().equals(CitationElement.NAME)) {
            if (t == JsonToken.START_OBJECT) {
                final CitationElement nae = CitationElement.createInstance();
                final Citation cit = nae.getCitation();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (CxConstants.ID.equals(namefield)) {
                        cit.setId(Long.valueOf(jp.getText()));
                    }
                    else if (CitationElement.IDENTIFIER.equals(namefield)) {
                        cit.setIdentifier(jp.getText());
                    }
                    else if (CitationElement.IDENTIFIER_TYPE.equals(namefield)) {
                        cit.setIdType(jp.getText());
                    }
                    else if (CitationElement.TITLE.equals(namefield)) {
                        cit.setTitle(jp.getText());
                    }
                    else if (CitationElement.TYPE.equals(namefield)) {
                        cit.setType(jp.getText());
                    }
                    else if (CitationElement.CONTRIBUTORS.equals(namefield)) {
                        cit.setContributors(Util.parseSimpleStringList(jp, t));
                    }
                    else if (CitationElement.PROPERTIES.equals(namefield)) {
                        final List<NdexPropertyValuePair> pvps = new ArrayList<NdexPropertyValuePair>();
                        while (jp.nextToken() != JsonToken.END_ARRAY) {
                            final NdexPropertyValuePair pvp = new NdexPropertyValuePair();
                            if (t == JsonToken.START_OBJECT) {
                                while (jp.nextToken() != JsonToken.END_OBJECT) {
                                    final String namefield2 = jp.getCurrentName();
                                    jp.nextToken();
                                    if (CitationElement.PROPERTY_DATA_TYPE.equals(namefield2)) {
                                        pvp.setDataType(jp.getText());
                                    }
                                    else if (CitationElement.PROPERTY_PREDICATE_ID.equals(namefield2)) {
                                        pvp.setPredicateId(Long.valueOf(jp.getText()));
                                    }
                                    else if (CitationElement.PROPERTY_PREDICATE_STRING.equals(namefield2)) {
                                        pvp.setPredicateString(jp.getText());
                                    }
                                    else if (CitationElement.PROPERTY_TYPE.equals(namefield2)) {
                                        pvp.setType(jp.getText());
                                    }
                                    else if (CitationElement.PROPERTY_VALUE.equals(namefield2)) {
                                        pvp.setValue(jp.getText());
                                    }
                                    else if (CitationElement.PROPERTY_VALUE_ID.equals(namefield2)) {
                                        pvp.setValueId(Long.valueOf(jp.getText()));
                                    }
                                }
                            }
                            pvps.add(pvp);
                        }
                        cit.setProperties(pvps);
                    }
                    else if (CitationElement.PRESENTATION_PROPERTIES.equals(namefield)) {
                        final List<SimplePropertyValuePair> properties = new ArrayList<SimplePropertyValuePair>();
                        while (jp.nextToken() != JsonToken.END_ARRAY) {
                            final SimplePropertyValuePair pvp = new SimplePropertyValuePair();
                            if (t == JsonToken.START_OBJECT) {
                                while (jp.nextToken() != JsonToken.END_OBJECT) {
                                    final String namefield2 = jp.getCurrentName();
                                    jp.nextToken();
                                    if (CitationElement.PROPERTY_NAME.equals(namefield2)) {
                                        pvp.setName(jp.getText());
                                    }
                                    else if (CitationElement.PROPERTY_TYPE.equals(namefield2)) {
                                        pvp.setType(jp.getText());
                                    }
                                    else if (CitationElement.PROPERTY_VALUE.equals(namefield2)) {
                                        pvp.setValue(jp.getText());
                                    }
                                }
                            }
                            properties.add(pvp);
                        }
                        cit.setPresentationProperties(properties);
                    }

                    else if (STRICT) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }
                }

                na_aspects.add(nae);
            }
            t = jp.nextToken();
        }
        System.out.println("_____  final name is:" + jp.getCurrentName());
        return na_aspects;
    }

}
