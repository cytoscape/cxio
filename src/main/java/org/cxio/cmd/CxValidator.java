package org.cxio.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.cxio.tools.ValidatorTools;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp
 * path/to/cxio-0.0.1.jar:path/to/jackson-databind-2.5.0.jar:path/to/jackson
 * -core-2.5.0.jar:path/to/jackson-annotations-2.5.0.jar
 * org.cxio.cmd.CxValidator
 *
 *
 */
public class CxValidator {

    public static void main(final String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: ValidatorTools [CX formatted infile]");
            System.exit(-1);
        }
        final String infile = args[0];
        final ValidatorTools val = ValidatorTools.getInstance();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(infile));
        }
        catch (final FileNotFoundException e) {
            System.out.println("Could not create input stream from '" + infile + "'");
            System.exit(-1);
        }

        final boolean valid = val.validate(in);

        if (valid) {
            System.out.println("Valid");
            System.out.println(String.format("%-32s %7d", "Total time for parsing [ms]:", val.getTotalTimeMillis()));
            System.out.println(String.format("%-32s %7d", "Number of aspects:", val.getNumberOfAspects()));
            final String[] names = val.getAspectNames();
            for (final String name : names) {
                System.out.println(String.format("  %-30s %7d", name + ":", val.getAspectElementCounts().get(name)));
            }
            System.out.println(String.format("%-32s %7d", "Pre-metadata elements:", val.getPreMetaDataElementCount()));
            System.out.println(String.format("%-32s %7d", "Post-metadata elements:", val.getPostMetaDataElementCount()));
            if ((val.getStatus() != null) && !val.getStatus().isSuccess()) {
                System.out.println("No success: " + val.getStatus().getError());
            }

        }
        else {
            System.out.println("Not valid");
            System.out.println("Error:\t" + val.getError());
        }
    }
}
