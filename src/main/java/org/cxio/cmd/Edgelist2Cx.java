package org.cxio.cmd;

import java.io.File;
import java.io.IOException;

import org.cxio.tools.BasicTable;
import org.cxio.tools.BasicTableParser;
import org.cxio.tools.Edgelist2CxTool;

/**
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-x.x.x.jar org.cxio.cmd.Edgelist2Cx -f infile outfile
 *
 *
 */
public final class Edgelist2Cx {

    private static final char  COLUMN_DELIMITER = '\t';
    public static final String WEIGHT           = "weight";

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

        final BasicTable<String> t = BasicTableParser.parse(infile, COLUMN_DELIMITER);

        if (t.getNumberOfRows() < 2) {
            System.out.println("edge-list seems ill-formatted");
            System.exit(-1);
        }
        if (t.getNumberOfColumns() != 3) {
            System.out.println("edge-list is ill-formatted, number of columns is: " + t.getNumberOfColumns());
            System.exit(-1);
        }

        try {
            Edgelist2CxTool.edgelist2Cx(outfile, force_symetrical, t);
        }
        catch (final Exception e) {
            System.out.println("error: " + e.getMessage());
            System.exit(-1);
        }

        System.out.println("OK");

    }

    private static void error() {
        System.out.println("Usage: Edgelist2Cx [-f] <infile: tab-separated edge-list> <outfile: network in cx-format>");
        System.exit(-1);
    }
}
