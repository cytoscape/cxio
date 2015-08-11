package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.core.CxConstants;
import org.cxio.core.JsonWriter;
import org.cxio.util.Util;

final class WriterUtils {

    final static void writeTimeStamp(final String time_stamp, final JsonWriter w) throws IOException {
        if (!Util.isEmpty(time_stamp)) {
            w.writeStartObject();
            w.writeStringField(CxConstants.TIME_STAMP_LABEL, time_stamp);
            w.writeEndObject();
        }
    }

}
