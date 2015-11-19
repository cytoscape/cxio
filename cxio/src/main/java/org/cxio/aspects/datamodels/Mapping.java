package org.cxio.aspects.datamodels;

import org.cxio.util.CxioUtil;

public final class Mapping {

    public final static String TYPE       = "type";
    public final static String DEFINITION = "definition";

    private final String       _type;
    private final String       _definition;

    public Mapping(final String type, final String definition) {
        if (CxioUtil.isEmpty(type)) {
            throw new IllegalArgumentException("mappping type must not be null or empty");
        }
        if (CxioUtil.isEmpty(definition)) {
            throw new IllegalArgumentException("mappping definition must not be null or empty");
        }
        _type = type;
        _definition = definition;
    }

    public final String getType() {
        return _type;
    }

    public final String getDefintion() {
        return _definition;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(_type);
        sb.append(": ");
        sb.append(_definition);
        return sb.toString();
    }
}
