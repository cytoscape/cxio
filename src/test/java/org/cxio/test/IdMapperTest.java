package org.cxio.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.cxio.tools.MappingServiceTools;

public final class IdMapperTest {

    public static void main(final String[] args) {
        final IdMapperTest t = new IdMapperTest();
        System.out.println("Using: " + MappingServiceTools.DEFAULT_MAP_SERVICE_URL_STR);
        System.out.println();
        try {
            if (t.test1() && t.test2() && t.test3() && MappingServiceTools.testMappingService()) {
                System.out.println();
                System.out.println("OK");
            }
            else {
                System.out.println();
                System.out.println("Not OK");
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
            System.out.println("Not OK");
        }
    }

    private final boolean test1() throws IOException {

        final List<String> ids = new ArrayList<String>();
        ids.add("A1BG");

        final SortedMap<String, SortedSet<String>> map = new TreeMap<String, SortedSet<String>>();
        final SortedSet<String> unmatched_ids = new TreeSet<String>();

        final String res = MappingServiceTools.runQuery(ids, MappingServiceTools.DEFAULT_MAP_SERVICE_URL_STR);

        final SortedSet<String> in_types = new TreeSet<String>();
        in_types.add(MappingServiceTools.SYNONYMS);
        in_types.add(MappingServiceTools.SYMBOL);
        MappingServiceTools.parseResponse(res, in_types, "human", "GeneID", map, unmatched_ids);

        boolean success = true;

        if (map.size() == 1) {
            System.out.println("Success: mapped " + map.size() + " gene symbols (from " + ids + ")");
        }
        else {
            System.out.println("Error:   mapped " + map.size() + " gene symbols, expected to map 1");
            System.out.println("         mappings: " + map);
            success = false;
        }

        if (unmatched_ids.size() == 0) {
            System.out.println("Success: could not map " + unmatched_ids.size() + " gene symbols");
        }
        else {
            System.out.println("Error:   could not map " + unmatched_ids.size() + " gene symbols, expected to not map 0");
            System.out.println("         unmatched: " + unmatched_ids);
            success = false;
        }
        System.out.println();
        if (!success) {
            System.out.println("Response was: " + res);

        }
        return success;

    }

    private final boolean test2() throws IOException {

        final List<String> ids = new ArrayList<String>();
        ids.add("A1BG");
        ids.add("A2M");
        ids.add("A2MP1");
        ids.add("AADAC");
        ids.add("AADACL2");
        ids.add("NOP9");
        ids.add("doesnotmap1");
        ids.add("doesnotmap2");

        final SortedMap<String, SortedSet<String>> map = new TreeMap<String, SortedSet<String>>();
        final SortedSet<String> unmatched_ids = new TreeSet<String>();

        final String res = MappingServiceTools.runQuery(ids, MappingServiceTools.DEFAULT_MAP_SERVICE_URL_STR);

        final SortedSet<String> in_types = new TreeSet<String>();
        in_types.add(MappingServiceTools.SYNONYMS);
        in_types.add(MappingServiceTools.SYMBOL);
        MappingServiceTools.parseResponse(res, in_types, "human", "GeneID", map, unmatched_ids);

        boolean success = true;

        if (map.size() == 6) {
            System.out.println("Success: mapped " + map.size() + " gene symbols (from " + ids + ")");
        }
        else {
            System.out.println("Error:   mapped " + map.size() + " gene symbols, expected to map 6");
            System.out.println("         mappings: " + map);
            success = false;
        }

        if (unmatched_ids.size() == 2) {
            System.out.println("Success: could not map " + unmatched_ids.size() + " gene symbols");
        }
        else {
            System.out.println("Error:   could not map " + unmatched_ids.size() + " gene symbols, expected to not map 2");
            System.out.println("         unmatched: " + unmatched_ids);
            success = false;
        }
        System.out.println();
        if (!success) {
            System.out.println("Response was: " + res);
        }
        return success;

    }

    private final boolean test3() throws IOException {

        final List<String> ids = getIds("human_gene_symbols.txt");

        final SortedMap<String, SortedSet<String>> map = new TreeMap<String, SortedSet<String>>();
        final SortedSet<String> unmatched_ids = new TreeSet<String>();

        final long t0 = System.currentTimeMillis();
        final String res = MappingServiceTools.runQuery(ids, MappingServiceTools.DEFAULT_MAP_SERVICE_URL_STR);
        final long t = System.currentTimeMillis() - t0;
        final SortedSet<String> in_types = new TreeSet<String>();
        in_types.add(MappingServiceTools.SYNONYMS);
        in_types.add(MappingServiceTools.SYMBOL);

        boolean success = true;
        MappingServiceTools.parseResponse(res, in_types, "human", "GeneID", map, unmatched_ids);
        if (map.size() > 4000) {
            System.out.println("Success: mapped " + map.size() + " gene symbols (from " + ids.size() + " queries)");
        }
        else {
            System.out.println("Error:   mapped " + map.size() + " gene symbols, expected to map more than 4000");
            System.out.println("         mappings: " + map);
            success = false;
        }
        if (unmatched_ids.size() < 1000) {
            System.out.println("Success: could not map " + unmatched_ids.size() + " gene symbols");
            double tpi = ((1000 * t) / ((double) ids.size()));
            System.out.printf("Speed  : %.2f μs/id", tpi);
            System.out.println();
        }
        else {
            System.out.println("Error:   could not map " + unmatched_ids.size() + " gene symbols, expected to not map less than 1000");
            System.out.println("         unmatched: " + unmatched_ids);
            success = false;
        }
        return success;

    }

    private List<String> getIds(final String infile) {
        final List<String> ids = new ArrayList<String>();
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(infile).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                ids.add(line.trim());
            }
            scanner.close();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
