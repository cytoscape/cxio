package cxio;

import java.io.OutputStream;

public interface CxContainer {

    public Status getStatus();

    public OutputStream getCx();

    public enum Status {
        OK, NOT_OK;
    }

}
