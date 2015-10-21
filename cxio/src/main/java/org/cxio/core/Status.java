package org.cxio.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Status implements Serializable {

    private static final String                   ERROR_MESSAGE    = "error_message";
    private static final String                   HAS_ERROR        = "has_error";
    public final static String                    NAME             = "status";
    private static final long                     serialVersionUID = 6558992873250381652L;

    private final List<SortedMap<String, String>> _data            = new ArrayList<SortedMap<String, String>>();

    public final static Status createInstanceFromJson(final InputStream is) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(is, Status.class);
    }

    public final static Status createInstanceFromJson(final JsonParser jp) throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(jp, Status.class);
    }

    public final static Status createInstanceFromJson(final String str) throws IOException {
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(str, Status.class);
    }

    public Status() {
        init();
        _data.get(0).put(ERROR_MESSAGE, "");
        _data.get(0).put(HAS_ERROR, String.valueOf(false));
    }

    public Status(final boolean has_error) {
        init();
        _data.get(0).put(ERROR_MESSAGE, "");
        _data.get(0).put(HAS_ERROR, String.valueOf(has_error));
    }

    public Status(final String error_message, final boolean has_error) {
        init();
        _data.get(0).put(ERROR_MESSAGE, error_message == null ? "" : error_message);
        _data.get(0).put(HAS_ERROR, String.valueOf(has_error));
    }

    @JsonIgnore
    public final String getErrorMessage() {
        return _data.get(0).get(ERROR_MESSAGE);
    }

    public List<SortedMap<String, String>> getStatus() {
        return _data;
    }

    private final void init() {
        _data.add(new TreeMap<String, String>());
    }

    @JsonIgnore
    public final boolean isHasError() {
        return Boolean.valueOf(_data.get(0).get(HAS_ERROR));
    }

    public final void toJson(final JsonWriter w) throws IOException {
        w.writeObject(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("has error    : " + isHasError());
        if (!Util.isEmpty(getErrorMessage())) {
            sb.append("error message: " + getErrorMessage());
        }
        return sb.toString();
    }

}
