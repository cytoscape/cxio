package org.cxio.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public final class MetaDataCollection implements Serializable {

    private static final long                     serialVersionUID = 7233278148613095352L;

    /**
     * The name of the MetaDataElement list when serialized to json.
     *
     */
    public final static String                    NAME             = "metaData";
    final private List<SortedMap<String, Object>> _data;

    /**
     * Constructor, to create an empty MetaData object.
     *
     */
    public MetaDataCollection() {
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
    public final Collection<MetaDataElement> asCollectionOfMetaDataElements() {
        final ArrayList<MetaDataElement> l = new ArrayList<MetaDataElement>();
        for (int i = 0; i < size(); i++) {
            l.add(new MetaDataElement(_data.get(i)));
        }
        return l;
    }

    /**
     * This is to get the meta data as list of sorted maps (String to Object).
     *
     * @return a list of sorted maps (String to Object)
     */
    public final Collection<SortedMap<String, Object>> getMetaData() {
        return _data;
    }

    /**
     * This method returns the MetaDataElement with a given name
     * (getName() returns name).
     * Return null if not found.
     * Throws a IllegalArgumentException if more than two elements with the same name.
     *
     * @param name the name of the MetaDataElement to find
     * @return a MetaDataElement with a given name, null if no such element
     */
    public final MetaDataElement getMetaDataElement(final String name) {
        MetaDataElement res = null;
        for (final MetaDataElement e : asCollectionOfMetaDataElements()) {
            if (e.getName().equals(name)) {
                if (res == null) {
                    res = e;
                }
                else {
                    throw new IllegalArgumentException("more than one meta data element with name '" + name + "'");
                }
            }
        }
        return res;
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
        for (final MetaDataElement e : asCollectionOfMetaDataElements()) {
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
    public final static MetaDataCollection createInstanceFromJson(final InputStream is) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(is, MetaDataCollection.class);
    }

    /**
     * This is to create a MetaData object from a JsonParser.
     *
     * @param jp a JsonParser
     * @return a MetaData object
     * @throws IOException
     */
    public static MetaDataCollection createInstanceFromJson(final JsonParser jp) throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(jp, MetaDataCollection.class);
    }

    /**
     * This is to create a MetaData object from a json formatted String.
     *
     * @param str a json formatted String
     * @return a MetaData object
     * @throws IOException
     */
    public final static MetaDataCollection createInstanceFromJson(final String str) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(str, MetaDataCollection.class);
    }

    // ///

    /**
     * Convenience method to get the consistency group of the meta data element with
     * a give name.
     *
     * @param name
     * @return
     */
    public final Long getConsistencyGroup(final String name) {
        final MetaDataElement e = getMetaDataElement(name);
        if (e != null) {
            return e.getConsistencyGroup();
        }
        return null;
    }

    /**
     * Convenience method to get the element count of the meta data element with
     * a give name.
     *
     * @param name
     * @return the element count
     */
    public final Long getElementCount(final String name) {
        final MetaDataElement e = getMetaDataElement(name);
        if (e != null) {
            return e.getElementCount();
        }
        return null;
    }

    /**
     * Convenience method to get the id counter of the meta data element with
     * a give name.
     *
     * @param name
     * @return the id counter
     */
    public final Long getIdCounter(final String name) {
        final MetaDataElement e = getMetaDataElement(name);
        if (e != null) {
            return e.getIdCounter();
        }
        return null;
    }

    /**
     * Convenience method to get the last update value of the meta data element with
     * a give name.
     *
     * @param name
     * @return the last update value
     */
    public final Long getLastUpdate(final String name) {
        final MetaDataElement e = getMetaDataElement(name);
        if (e != null) {
            return e.getLastUpdate();
        }
        return null;
    }

    /**
     * Convenience method to get the (corresponding aspect) version of the meta data element with
     * a give name.
     *
     * @param name
     * @return the (corresponding aspect) version
     */
    public final String getVersion(final String name) {
        final MetaDataElement e = getMetaDataElement(name);
        if (e != null) {
            return e.getVersion();
        }
        return null;
    }

    /**
     * Convenience method to set the consistency group for the meta data element with
     * a give name.
     * If no such element exist, a new one will be created.
     *
     * @param name
     * @param c
     */
    public final void setConsistencyGroup(final String name, final Long c) {
        final MetaDataElement e = checkIfElementPresent(name);
        e.setConsistencyGroup(c);
    }

    /**
     * Convenience method to set the element count for the meta data element with
     * a give name.
     * If no such element exist, a new one will be created.
     *
     * @param name
     * @param c
     */
    public final void setElementCount(final String name, final Long c) {
        final MetaDataElement e = checkIfElementPresent(name);
        e.setElementCount(c);
    }

    /**
     * Convenience method to set the id counter for the meta data element with
     * a give name.
     * If no such element exist, a new one will be created.
     *
     * @param name
     * @param c
     */
    public final void setIdCounter(final String name, final Long c) {
        final MetaDataElement e = checkIfElementPresent(name);
        e.setIdCounter(c);
    }

    /**
     * Convenience method to set the last update value for the meta data element with
     * a give name.
     * If no such element exist, a new one will be created.
     *
     * @param name
     * @param last_update
     */
    public final void setLastUpdate(final String name, final Long last_update) {
        final MetaDataElement e = checkIfElementPresent(name);
        e.setLastUpdate(last_update);
    }

    /**
     * Convenience method to set the (corresponding aspect) version for the meta data element with
     * a give name.
     * If no such element exist, a new one will be created.
     *
     * @param name
     * @param version the (corresponding aspect) version
     */
    public final void setVersion(final String name, final String version) {
        final MetaDataElement e = checkIfElementPresent(name);
        e.setVersion(version);
    }

    private MetaDataElement checkIfElementPresent(final String name) {
        MetaDataElement e = getMetaDataElement(name);
        if (e == null) {
            e = new MetaDataElement();
            e.setName(name);
            addMetaDataElement(e);
        }
        return e;
    }

}
