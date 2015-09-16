package org.cxio.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is to hold a list of MetaDataElements to serialize this
 * data to json, and to de-serialize from json.
 *
 * @author cmzmasek
 *
 */
public final class MetaData {

    /**
     * The name of the MetaDataElement list when serialized to json.
     *
     */
    public final static String                    NAME = "metaData";
    final private List<SortedMap<String, Object>> _data;

    /**
     * Constructor, to create an empty MetaData object.
     *
     */
    public MetaData() {
        _data = new ArrayList<SortedMap<String, Object>>();
    }

    /**
     * This method id to add a MetaDataElement
     *
     * @param e a MetaDataElement
     */
    public final void addMetaDataElement(final MetaDataElement e) {
        _data.add(e.getData());
    }

    /**
     * This is to get the meta data as list of MetaDataElements
     *
     * @return a list of MetaDataElements
     */
    public final List<MetaDataElement> asListOfMetaDataElements() {
        final ArrayList<MetaDataElement> l = new ArrayList<MetaDataElement>();
        for (int i = 0; i < size(); i++) {
            l.add(getMetaDataElement(i));
        }
        return l;
    }

    /**
     * This is to get the meta data as list of sorted maps (String to Object).
     *
     * @return a list of sorted maps (String to Object)
     */
    public final List<SortedMap<String, Object>> getMetaData() {
        return _data;
    }

    /**
     * This is to get a MetaDataElement.
     *
     *
     * @param index the index of the MetaDataElement
     * @return a MetaDataElement
     */
    public final MetaDataElement getMetaDataElement(final int index) {
        return new MetaDataElement(_data.get(index));
    }

    /**
     * This returns the number of MetaDataElements.
     *
     * @return the number of MetaDataElements
     */
    public final int size() {
        return _data.size();
    }

    /**
     * This is the serialize this MetaData object to json.
     *
     * @param w a JsonWriter
     * @throws IOException
     */
    public final void toJson(final JsonWriter w) throws IOException {
        w.writeObject(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final MetaDataElement e : asListOfMetaDataElements()) {
            if ((e != null) && !e.getData().isEmpty()) {
                sb.append(e);
                sb.append(Util.LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

    /**
     * This is to create a MetaData object from a json formatted InputStream.
     *
     * @param is a json formatted stream
     * @return a MetaData object
     * @throws IOException
     */
    public final static MetaData createInstanceFromJson(final InputStream is) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(is, MetaData.class);
    }

    /**
     * This is to create a MetaData object from a JsonParser.
     *
     * @param is a JsonParser
     * @return a MetaData object
     * @throws IOException
     */
    public static MetaData createInstanceFromJson(final JsonParser jp) throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(jp, MetaData.class);
    }

    /**
     * This is to create a MetaData object from a json formatted String.
     *
     * @param str a json formatted String
     * @return a MetaData object
     * @throws IOException
     */
    public final static MetaData createInstanceFromJson(final String str) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(str, MetaData.class);
    }

}
