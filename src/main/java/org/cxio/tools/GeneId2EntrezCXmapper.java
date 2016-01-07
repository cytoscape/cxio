package org.cxio.tools;

import java.io.File;
import java.io.IOException;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar:path/to/jackson-databind-2.5.0.jar:path/to/jackson
 * -core-2.5.0.jar:path/to/jackson-annotations-2.5.0.jar org.cxio.tools.GeneId2EntrezCXmapper
 *
 *
 */
public final class GeneId2EntrezCXmapper {

    public static void main(final String[] args) throws IOException {

        // if (args.length != 0) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }
        final File infile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim.cx");
        final File outfile = new File("/Users/cmzmasek/Desktop/one.cx");

        System.out.println("Infile: " + infile);
        System.out.println("Outfile: " + outfile);
        System.out.println();

        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            // System.exit(-1);
        }

        CxIdMapping.run(infile, outfile, CxIdMapping.FIELD_NODES_NAME);

        System.out.println();
        System.out.println("OK");

    }
}