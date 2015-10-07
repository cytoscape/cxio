package org.cxio.core.interfaces;

/**
 * The interface for all (named) AspectElements.
 *
 * @author cmzmasek
 *
 */
public interface AspectElement extends Comparable<AspectElement> {

    /**
     * This returns the name of the aspect.
     *
     * @return the name of the aspect
     */
    public String getAspectName();

}
