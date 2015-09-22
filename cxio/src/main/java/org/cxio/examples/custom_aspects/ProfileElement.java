package org.cxio.examples.custom_aspects;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

public final class ProfileElement implements AspectElement {

    public final static String NAME         = "profile";
    public final static String PROFILE_NAME = "name";
    public final static String PROFILE_DESC = "desc";
    private final String       _name;
    private final String       _desc;

    @Override
    public String getAspectName() {
        return NAME;
    }

    public ProfileElement(final String name, final String description) {
        if (Util.isEmpty(name)) {
            throw new IllegalArgumentException("profile name must not be null or empty");
        }
        if (Util.isEmpty(description)) {
            throw new IllegalArgumentException("profile description must not be null or empty");
        }
        _name = name;
        _desc = description;
    }

    public final String getName() {
        return _name;
    }

    public final String getDescription() {
        return _desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("name: ");
        sb.append(_name);
        sb.append("\n");
        sb.append("description: ");
        sb.append(_desc);
        return sb.toString();
    }

}
