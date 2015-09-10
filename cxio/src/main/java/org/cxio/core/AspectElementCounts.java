package org.cxio.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

public final class AspectElementCounts {

    private final SortedMap<String, Integer> _count_data;
    private long                             _sum;

    public final static AspectElementCounts createInstance() {
        return new AspectElementCounts();
    }

    public final Set<String> getAllAspectNames() {
        return _count_data.keySet();
    }

    public final int getAspectElementCount(final AspectElement element) {
        return getAspectElementCount(element.getAspectName());
    }

    public final int getAspectElementCount(final String aspect_name) {
        if (_count_data.containsKey(aspect_name)) {
            return _count_data.get(aspect_name);
        }
        return 0;
    }

    public final long getSum() {
        return _sum;
    }

    public final boolean isCountsAreEqual(final AspectElementCounts o) {
        if (getAllAspectNames().size() != o.getAllAspectNames().size()) {
            return false;
        }
        if (!getAllAspectNames().containsAll(o.getAllAspectNames())) {
            return false;
        }
        for (final Map.Entry<String, Integer> e : _count_data.entrySet()) {
            if (e.getValue() != o.getAspectElementCount(e.getKey())) {
                return false;
            }
        }
        return true;
    }

    public final boolean isSumsAreEqual(final AspectElementCounts o) {
        return getSum() == o.getSum();
    }

    public final void setAspectElementCount(final AspectElement element, final int count) {
        increaseAspectElementCount(element.getAspectName(), count);
    }

    public final void setAspectElementCount(final String aspect_name, final int count) {
        _count_data.put(aspect_name, count);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, Integer> e : _count_data.entrySet()) {
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append(Util.LINE_SEPARATOR);
        }
        sb.append("sum : ");
        sb.append(getSum());
        return sb.toString();
    }

    final void processAspectElement(final AspectElement element) {
        increaseAspectElementCount(element.getAspectName());
        _sum += element.getSum();
    }

    final void processAspectElements(final List<AspectElement> elements) {
        increaseAspectElementCount(elements.get(0).getAspectName(), elements.size());
        for (final AspectElement element : elements) {
            _sum += element.getSum();
        }
    }

    final void processAnonymousAspectElements(final List<AnonymousElement> elements) {
        increaseAspectElementCount(elements.get(0).getAspectName(), elements.size());
        for (final AspectElement element : elements) {
            _sum += element.getSum();
        }
    }

    private final void increaseAspectElementCount(final String aspect_name) {
        if (_count_data.containsKey(aspect_name)) {
            _count_data.put(aspect_name, _count_data.get(aspect_name) + 1);
        }
        else {
            _count_data.put(aspect_name, 1);
        }
    }

    private final void increaseAspectElementCount(final String aspect_name, final int count) {
        if (_count_data.containsKey(aspect_name)) {
            _count_data.put(aspect_name, _count_data.get(aspect_name) + count);
        }
        else {
            _count_data.put(aspect_name, count);
        }
    }

    private AspectElementCounts() {
        _count_data = new TreeMap<String, Integer>();
        _sum = 0;
    }

}
