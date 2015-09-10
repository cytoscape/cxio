package org.cxio.core;

import java.util.HashMap;
import java.util.Map;

import org.cxio.core.interfaces.AspectElement;

public final class ElementCounts {
    
    private final Map<String, Integer> _data;

    public final static ElementCounts createInstance() {
        return new ElementCounts();
    }

    public final int getCount(final AspectElement element) {
        return getCount(element.getAspectName());
    }

    public final int getCount(final String aspect_name) {
        if (_data.containsKey(aspect_name)) {
            return _data.get(aspect_name);
        }
        return 0;
    }

    public final void increaseCount(final AspectElement element, final int count) {
        increaseCount(element.getAspectName(), count);
    }

    public final void increaseCount(final String aspect_name, final int count) {
        if (_data.containsKey(aspect_name)) {
            _data.put(aspect_name, _data.get(aspect_name) + count);
        }
        else {
            _data.put(aspect_name, count);
        }
    }
    
    private ElementCounts() {
        _data = new HashMap<String, Integer>();
    }

}
