package org.cxio.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-x.x.x.jar org.cxio.cmd.GeneSymbol2EntrezPLocal infile map outfile counter-start
 *
 *
 */
public final class GeneSymbol2EntrezPLocal {

    public static void main(final String[] args) throws IOException {

        if ((args.length != 3) && (args.length != 4)) {
            System.out.println("Usage: GeneSymbol2EntrezPLocal <infile:comma-separated matrix> <mapping file:tab-separated> <outfile:comma-separated matrix> [counter start for missing mappings]");
            System.exit(-1);
        }

        final File infile = new File(args[0]);
        final File inmap = new File(args[1]);
        final File outfile = new File(args[2]);
        int counter_start = -1;
        if (args.length == 4) {
            counter_start = Integer.parseInt(args[3]);
        }

        if (!infile.exists()) {
            System.out.println("does not exist: " + infile);
            System.exit(-1);
        }
        if (!inmap.exists()) {
            System.out.println("does not exist: " + inmap);
            System.exit(-1);
        }
        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }
        if (counter_start >= 0) {
            System.out.println("counter start must be negative");
            System.exit(-1);
        }

        System.out.println("Infile       : " + infile);
        System.out.println("Map file     : " + inmap);
        System.out.println("Outfile      : " + outfile);
        System.out.println("Counter start: " + counter_start);
        System.out.println();

        final PrintWriter writer = new PrintWriter(outfile, "UTF-8");

        final Map<String, String> id_to_entrez = readMapping(inmap);

        boolean first = true;
        final List<String> missing = new ArrayList<String>();
        int counter = counter_start;
        try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    final String[] ids = line.split(",");
                    for (String id : ids) {
                        id = id.trim();
                        if (id.length() > 0) {
                            if (!id_to_entrez.containsKey(id)) {
                                missing.add(id + "\t" + counter);
                                --counter;
                            }
                            else {
                                writer.print(",");
                                writer.print(id_to_entrez.get(id));
                            }
                        }
                    }
                    writer.println();
                    writer.flush();
                }
                else {
                    writer.print(line);
                    writer.println();
                    writer.flush();
                }
            }
        }

        writer.close();

        if (counter != counter_start) {
            outfile.delete();
            System.out.println("encountered " + missing.size() + " unmapped symbols which need to be added to the map file:");

            for (final String s : missing) {
                System.out.println(s);
            }

            System.out.println("encountered " + missing.size() + " unmapped symbols which need to be added to the map file (see above), aborting");
            System.exit(0);
        }
        else {
            System.out.println("OK");
        }
    }

    private static Map<String, String> readMapping(final File inmap) throws IOException, FileNotFoundException {
        final Map<String, String> id_to_entrez = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(inmap))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ((line.length() > 0) && !line.startsWith("#")) {
                    final String[] x = line.split("\t");
                    if (x.length == 2) {
                        if (x[0].indexOf(", ") < 0) {
                            id_to_entrez.put(x[0], x[1]);
                        }
                        else {
                            final String[] y = x[0].split(", ");
                            for (final String mk : y) {
                                id_to_entrez.put(mk, x[1]);
                            }
                        }
                    }
                    else if (x.length != 1) {
                        System.out.println("format error: " + line);
                        System.exit(-1);
                    }
                }
            }
        }
        return id_to_entrez;
    }
}