package org.cxio.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar org.cxio.tools.GeneId2EntrezMapPreprocess
 *
 *
 */
public final class GeneId2EntrezMapPreprocess {

    public static void main(final String[] args) throws IOException {

        // if (args.length != 2) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }

        final File inmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez.txt");
        final File outmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez_CLEAN_UP.txt");

        final PrintWriter writer = new PrintWriter(outmap, "UTF-8");

        final Set<String> keys = new HashSet<String>();

        int counter = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(inmap))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("\t")) {
                    continue;
                }

                line = line.trim();
                if ((line.length() > 0) && !line.startsWith("#")) {
                    final String[] x = line.split("\t");
                    final String key = x[0].trim();
                    if (x.length == 2) {
                        final String val = x[1].trim();
                        if (key.indexOf(",") < 0) {
                            if (!keys.contains(key)) {
                                keys.add(key);
                                writer.print(key);
                                writer.print("\t");
                                writer.print(val);
                                writer.println();
                            }
                        }
                        else {
                            final String[] y = key.split(",");
                            for (String mk : y) {
                                mk = mk.trim();
                                if (!keys.contains(mk)) {
                                    keys.add(mk);
                                    writer.print(mk);
                                    writer.print("\t");
                                    writer.print(val);
                                    writer.println();
                                }
                            }
                        }
                    }
                    else if (x.length == 1) {
                        String my_key = key;
                        if (my_key.endsWith("~withdrawn")) {
                            my_key = my_key.substring(0, key.length() - 10);
                        }
                        if (!keys.contains(my_key)) {
                            keys.add(my_key);
                            writer.print(my_key);
                            writer.print("\t");
                            writer.print(counter);
                            writer.println();
                            --counter;
                        }
                    }
                    else {
                        System.out.println("error: " + line);
                        System.exit(-1);
                    }
                }
            }
        }
        writer.close();
        System.out.println(counter);
        System.out.println("OK");

    }
}