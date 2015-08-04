package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.TimeStamp;
import org.cxio.core.JsonWriter;

final class WriterUtils {

    final static void writeTimeStamp(final String time_stamp, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(TimeStamp.NAME, time_stamp);
        w.writeEndObject();
    }

}
