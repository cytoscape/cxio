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
 * This class is to hold various meta data and to serialize this
 * data to json, and the de-serialize from json.
 *
 * @author cmzmasek
 *
 */
public final class MetaData {

    public final static String                    NAME = "metaData";
    final private List<SortedMap<String, Object>> _data;

    public MetaData() {
        _data = new ArrayList<SortedMap<String, Object>>();
    }

    public final void addMetaDataElement(final MetaDataElement e) {
        _data.add(e.getData());
    }

    public final List<MetaDataElement> asListOfMetaDataElements() {
        final ArrayList<MetaDataElement> l = new ArrayList<MetaDataElement>();
        for (int i = 0; i < size(); i++) {
            l.add(getMetaDataElement(i));
        }
        return l;
    }

    public final List<SortedMap<String, Object>> getMetaData() {
        return _data;
    }

    public final MetaDataElement getMetaDataElement(final int index) {
        return new MetaDataElement(_data.get(index));
    }

    public final int size() {
        return _data.size();
    }

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

    public final static MetaData createInstanceFromJson(final InputStream is) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(is, MetaData.class);
    }

    public static MetaData createInstanceFromJson(final JsonParser jp) throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(jp, MetaData.class);
    }

    public final static MetaData createInstanceFromJson(final String str) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(str, MetaData.class);
    }

}
