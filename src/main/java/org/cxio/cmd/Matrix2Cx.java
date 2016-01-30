package org.cxio.cmd;

import java.io.File;
import java.io.IOException;

import org.cxio.tools.Matrix2CxTool;

/**
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-x.x.x.jar org.cxio.cmd.Matrix2Cx infile outfile
 *
 *
 *
 */
public final class Matrix2Cx {

    public static final String COLUMN_DELIMITER = "\\s+";
    public static final String EDGE_NAME        = "weight";

    public static void main(final String[] args) throws IOException {

        File infile = null;
        File outfile = null;
        boolean force_symetrical = false;
        if (args.length == 2) {
            infile = new File(args[0]);
            outfile = new File(args[1]);
        }
        else if (args.length == 3) {
            infile = new File(args[1]);
            outfile = new File(args[2]);
            if (args[0].equals("-f")) {
                force_symetrical = true;
            }
            else {
                error();
            }
        }
        else {
            error();
        }

        if (!infile.exists()) {
            System.out.println("does not exist: " + infile);
            System.exit(-1);
        }
        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        System.out.println("Infile           : " + infile);
        System.out.println("Outfile          : " + outfile);
        System.out.println("Force symmetrical: " + force_symetrical);
        System.out.println();

        try {
            Matrix2CxTool.matrix2Cx(infile, outfile, force_symetrical);
        }
        catch (final Exception e) {
            System.out.println("error: " + e.getMessage());
            System.exit(-1);
        }
        System.out.println();
        System.out.println("OK");

    }

    private static void error() {
        System.out.println("Usage: Matrix2Cx [-f] <infile: tab-separated matrix> <outfile: network in cx-format>");
        System.exit(-1);
    }
}
