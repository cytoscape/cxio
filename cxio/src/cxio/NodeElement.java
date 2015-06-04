package cxio;

public final class NodeElement implements AspectElement {

    private final String id;

    public NodeElement(final String id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return ((o instanceof NodeElement) && id.equals(((NodeElement) o).getId()));

    }

    @Override
    final public String getAspectName() {
        return Cx.NODES;
    }

    final public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }

}
