package org.cxio.metadata;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

/**
 * This is to hold meta data.
 * It is intended to be used together with class MetaData.
 *
 * @author cmzmasek
 *
 */
public class MetaDataElement {

    public final static String              CONSISTENCY_GROUP = "consistencyGroup";
    public final static String              ELEMENT_COUNT     = "elementCount";
    public final static String              ID_COUNTER        = "idCounter";
    public final static String              LAST_UPDATE       = "lastUpdate";
    public final static String              NAME              = "name";
    public final static String              PROPERTIES        = "properties";
    public final static String              VERSION           = "version";

    final private SortedMap<String, Object> _data;

    /**
     * Constructor to create an empty MetaDataElement.
     *
     */
    public MetaDataElement() {
        _data = new TreeMap<String, Object>();
    }

    /**
     * Constructor to create a MetaDataElement containing the
     * data provided in a SortedMap (String to Object).
     *
     */
    public MetaDataElement(final SortedMap<String, Object> data) {
        _data = data;
    }

    /**
     * Convenience method to add a "property" (a map mapping String keys
     * to String values).
     *
     * @param property a map mapping String keys to String values to add
     */
    public final void addProperty(final Map<String, String> property) {
        if (!_data.containsKey(PROPERTIES)) {
            _data.put(PROPERTIES, new ArrayList<Map<String, String>>());
        }
        getProperties().add(property);
    }

    /**
     * The get the Object value corresponding to a given key.
     *
     * @param key
     * @return
     */
    public final Object get(final String key) {
        return _data.get(key);
    }

    /**
     * Convenience method to get the consistency group.
     *
     * @return the consistency group
     */
    public final Long getConsistencyGroup() {
        return Long.valueOf((String) _data.get(CONSISTENCY_GROUP));
    }

    /**
     * This allows to get all he data in this object as a SortedMap (String to Object).
     *
     * @return a SortedMap (String to Object)
     */
    public final SortedMap<String, Object> getData() {
        return _data;
    }

    /**
     * Convenience method to get the element count.
     *
     * @return the element count
     */
    public final Long getElementCount() {
        return Long.valueOf((String) _data.get(ELEMENT_COUNT));
    }

    /**
     * Convenience method to get the id counter.
     *
     * @return the id counter
     */
    public final Long getIdCounter() {
        return Long.valueOf((String) _data.get(ID_COUNTER));
    }

    /**
     * Convenience method to get the last update value.
     *
     * @return the last update value
     */
    public final Long getLastUpdate() {
        return Long.valueOf((String) _data.get(LAST_UPDATE));
    }

    /**
     * Convenience method to get the name (of the corresponding aspect).
     *
     * @return the name (of the corresponding aspect)
     */
    public final String getName() {
        return (String) _data.get(NAME);
    }

    @SuppressWarnings("unchecked")
    /**
     * Convenience method to get all "properties" (maps mapping String keys
     * to String values).
     *
     * @return  all "properties"
     */
    public final ArrayList<Map<String, String>> getProperties() {
        return ((ArrayList<Map<String, String>>) _data.get(PROPERTIES));
    }

    /**
     * Convenience method to get the (corresponding aspect) version.
     *
     * @return the (corresponding aspect) version
     */
    public final String getVersion() {
        return (String) _data.get(VERSION);
    }

    /**
     * This returns the keys for all the data fields.
     *
     * @return the keys for all the data fields
     */
    public final Set<String> keySet() {
        return _data.keySet();
    }

    /**
     * This is to put an arbitrary Object as value (data field), mapped to a key.
     *
     * @param key the key
     * @param value an Object
     */
    public final void put(final String key, final Object value) {
        _data.put(key, value);
    }

    /**
     * Convenience method to set the consistency group.
     *
     * @param c
     */
    public final void setConsistencyGroup(final Long c) {
        _data.put(CONSISTENCY_GROUP, String.valueOf(c));
    }

    /**
     * Convenience method to set the element count.
     *
     * @param c
     */
    public final void setElementCount(final Long c) {
        _data.put(ELEMENT_COUNT, String.valueOf(c));
    }

    /**
     * Convenience method to set the id counter.
     *
     * @param c
     */
    public final void setIdCounter(final Long c) {
        _data.put(ID_COUNTER, String.valueOf(c));
    }

    /**
     * Convenience method to set the last update value.
     *
     * @param last_update
     */
    public final void setLastUpdate(final Long last_update) {
        _data.put(LAST_UPDATE, String.valueOf(last_update));
    }

    /**
     * Convenience method to set the name (of the corresponding aspect).
     *
     * @param name the name (of the corresponding aspect)
     */
    public final void setName(final String name) {
        _data.put(NAME, name);
    }

    /**
     * Convenience method to set the name from an AspectElement.
     *
     * @param e an AspectElement (to get the name from)
     */
    public final void setName(final AspectElement e) {
        _data.put(NAME, e.getAspectName());
    }

    /**
     * Convenience method to set the (corresponding aspect) version.
     *
     * @param version the (corresponding aspect) version
     */
    public final void setVersion(final String version) {
        _data.put(VERSION, version);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Entry<String, Object> e : _data.entrySet()) {
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append(Util.LINE_SEPARATOR);
        }
        return sb.toString();
    }

}
