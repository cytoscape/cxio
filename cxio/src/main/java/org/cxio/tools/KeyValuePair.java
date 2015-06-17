package org.cxio.tools;

public final class KeyValuePair {
    final private String _key;
    final private String _value;

    public KeyValuePair(final String key, final String value) {
        _key = key;
        _value = value;
    }

    public final String getKey() {
        return _key;
    }

    public final String getValue() {
        return _value;
    }

}
