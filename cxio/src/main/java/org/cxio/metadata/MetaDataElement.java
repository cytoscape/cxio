package org.cxio.metadata;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.util.Util;
import org.cxio.core.interfaces.AspectElement;

public class MetaDataElement {

    public final static String              CONSISTENCY_GROUP = "consistencyGroup";
    public final static String              ELEMENT_COUNT     = "elementCount";
    public final static String              ID_COUNTER        = "idCounter";
    public final static String              LAST_UPDATE       = "lastUpdate";
    public final static String              NAME              = "name";
    public final static String              PROPERTIES        = "properties";
    public final static String              VERSION           = "version";

    final private SortedMap<String, Object> _data;

    public MetaDataElement() {
        _data = new TreeMap<String, Object>();
    }

    public MetaDataElement(final SortedMap<String, Object> data) {
        _data = data;
    }

    public final void addProperty(final Map<String, String> property) {
        if (!_data.containsKey(PROPERTIES)) {
            _data.put(PROPERTIES, new ArrayList<Map<String, String>>());
        }
        getProperties().add(property);
    }

    public final Object get(final String key) {
        return _data.get(key);
    }

    public final void getConsistencyGroup() {
        _data.get(CONSISTENCY_GROUP);
    }

    public final SortedMap<String, Object> getData() {
        return _data;
    }

    public final void getElementCount() {
        _data.get(ELEMENT_COUNT);
    }

    public final void getIdCounter() {
        _data.get(ID_COUNTER);
    }

    public final void getLastUpdate() {
        _data.get(LAST_UPDATE);
    }

    public final void getName() {
        _data.get(NAME);
    }

    @SuppressWarnings("unchecked")
    public final ArrayList<Map<String, String>> getProperties() {
        return ((ArrayList<Map<String, String>>) _data.get(PROPERTIES));
    }

    public final void getVersion() {
        _data.get(VERSION);
    }

    public final Set<String> keySet() {
        return _data.keySet();
    }

    public final void put(final String key, final Object value) {
        _data.put(key, value);
    }

    public final void setConsistencyGroup(final Long c) {
        _data.put(CONSISTENCY_GROUP, c);
    }

    public final void setElementCount(final Long c) {
        _data.put(ELEMENT_COUNT, c);
    }

    public final void setIdCounter(final Long c) {
        _data.put(ID_COUNTER, c);
    }

    public final void setLastUpdate(final String last_update) {
        _data.put(LAST_UPDATE, last_update);
    }

    public final void setName(final String name) {
        _data.put(NAME, name);
    }
    
    public final void setName(final AspectElement e) {
        _data.put(NAME, e.getAspectName());
    }

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
