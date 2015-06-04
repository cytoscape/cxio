package cxio;

import java.io.OutputStream;

public final class BasicCxContainer implements CxContainer {
    // TODO implement me

    @Override
    public final Status getStatus() {
        return Status.OK;
    }

    @Override
    public final OutputStream getCx() {
        // This will return a ByteArrayOutputStream first.
        // Later it will return a PipedOutputStream.
        return null;
    }

}
