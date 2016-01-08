package org.cxio.cmd;

import java.io.File;
import java.io.IOException;

import org.cxio.tools.GeneSymbolMapper;

/**
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar org.cxio.cmd.GeneSymbol2Entrez infile outfile
 *
 *
 */
public final class GeneSymbol2Entrez {

    public static void main(final String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: GeneSymbol2Entrez <infile in CX format> <outfile in CX format>");
            System.exit(-1);
        }

        final File infile = new File(args[0]);
        final File outfile = new File(args[1]);

        if (!infile.exists()) {
            System.out.println("does not exist: " + infile);
            System.exit(-1);
        }
        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        System.out.println("Infile : " + infile);
        System.out.println("Outfile: " + outfile);
        System.out.println();

        GeneSymbolMapper.run(infile, outfile, GeneSymbolMapper.FIELD_NODES_NAME);

        System.out.println();
        System.out.println("OK");

    }
}