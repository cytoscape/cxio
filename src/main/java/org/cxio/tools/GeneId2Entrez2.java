package org.cxio.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar org.cxio.tools.GeneId2Entrez
 *
 *
 */
public final class GeneId2Entrez2 {

    private static final char    COLUMN_DELIMITER   = ',';
    private static final boolean ALLOW_UNMAPPED_IDS = false;

    public static void main(final String[] args) throws IOException {

        final List<String> ids = new ArrayList<String>();

        ids.add("A1BG");
        ids.add("A2M");
        ids.add("A2MP1");
        ids.add("AADAC");
        ids.add("AADACL2");
        ids.add("AADACL3");

        final String url = "http://54.200.201.85:3000/map";

        final SortedMap<String,  SortedSet<String>> map = new TreeMap<String, SortedSet<String>>();

        for (final String id : ids) {
            final String json_query = makeQuery(id);
            final String res = query(url, json_query);
            System.out.println(res);

            parseResponse(res, "Symbol", "human", "GeneID", true, -1, map);
        }
        System.out.println(map);
        System.exit(0);

        // final File infile = new File("/Users/cmzmasek/Desktop/test.csv");
        final File infile = new File("/Users/cmzmasek/WORK/datafiles/PC_smData_labelled.csv");
        final File outfile = new File("/Users/cmzmasek/Desktop/patient_files/PC_smData_labelled_entrez.csv");

        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        final File inmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez_CLEAN_UP.txt");

        PrintWriter outmap_writer = null;
        File outmap = null;
        if (ALLOW_UNMAPPED_IDS) {
            outmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez_TO_ADD2.txt");
            outmap_writer = new PrintWriter(outmap, "UTF-8");
        }
        int counter = -10000;

        final PrintWriter writer = new PrintWriter(outfile, "UTF-8");

        final Map<String, String> id_to_entrz = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(inmap))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ((line.length() > 0) && !line.startsWith("#")) {
                    final String[] x = line.split("\t");
                    if (x.length == 2) {
                        if (x[0].indexOf(", ") < 0) {
                            id_to_entrz.put(x[0], x[1]);
                        }
                        else {
                            final String[] y = x[0].split(", ");
                            for (final String mk : y) {
                                id_to_entrz.put(mk, x[1]);
                            }
                        }

                    }
                    else if (x.length != 1) {
                        System.out.println("error");
                        System.exit(-1);
                    }
                }
            }
        }

        final BasicTable<String> t = BasicTableParser.parse(infile, COLUMN_DELIMITER);

        for (int c = 0; c < (t.getNumberOfColumns() - 1); ++c) {
            final String val = t.getValue(c, 0);
            if (!id_to_entrz.containsKey(val)) {
                if (!ALLOW_UNMAPPED_IDS) {
                    System.out.println("not found: " + val);
                    System.exit(-1);
                }
                else {
                    outmap_writer.println(val + "\t" + counter);
                    --counter;
                }
            }
            else {
                writer.print(",");
                writer.print(id_to_entrz.get(val));
            }
        }
        writer.println();
        writer.flush();

        for (int r = 1; r < t.getNumberOfRows(); ++r) {
            for (int c = 0; c < t.getNumberOfColumns(); ++c) {
                if (c > 0) {
                    writer.print(",");
                }
                writer.print(t.getValue(c, r));
            }
            writer.println();
            writer.flush();
        }

        if (ALLOW_UNMAPPED_IDS) {
            outmap_writer.close();
        }
        writer.close();
        System.out.println("OK");

    }

    private static int parseResponse(final String res,
                                      final String in_type,
                                      final String target_species,
                                      final String target_type,
                                      final boolean keep_non_matches,
                                      int no_match_counter,
                                      final SortedMap<String,  SortedSet<String>> map) throws IOException, JsonProcessingException {
        if ( no_match_counter >= 0) {
            throw new IllegalArgumentException("no match counter must be negative");
        }
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode root = mapper.readTree(res);
        final JsonNode matched = root.path("matched");
        final Iterator<JsonNode> matched_it = matched.elements();
        while (matched_it.hasNext()) {
            final JsonNode n = matched_it.next();
            if (n.has("species")) {
                final String species = n.get("species").asText();
                if (target_species.equals(species)) {
                    if (in_type.equals(n.get("inType").asText())) {
                        final String in = n.get("in").asText();
                        if (n.has("matches")) {
                            final JsonNode m = n.get("matches");
                            if (m.has(target_type)) {
                                final String g = m.get(target_type).asText();
                                System.out.println(g);
                                if (!map.containsKey(in)) {
                                    map.put(in, new TreeSet<String>());
                                }
                                map.get(in).add(g);
                            }
                            else {
                                if (keep_non_matches) {
                                    map.put(in, new TreeSet<String>());
                                    map.get(in).add(String.valueOf(no_match_counter));
                                    --no_match_counter;
                                }
                            }
                        }
                        else {
                            if (keep_non_matches) {
                                map.put(in, new TreeSet<String>());
                                map.get(in).add(String.valueOf(no_match_counter));
                                --no_match_counter;
                            }
                        }
                    }

                }
            }
            else {
                throw new IOException("no species in: " + res);
            }
            
        }
        return no_match_counter;
    }

    public static String makeQuery() {
        final String json_query = "{\"ids\": [\"P53_HUMAN\", \"TP53\", \"P04637\", \"7157\", \"p53\"], \"idTypes\":[\"GeneID\", \"Synonyms\", \"Symbol\"]}";
        return json_query;
    }

    public static String makeQuery(final String gene_symbol) {
        final String json_query = "{\"ids\": [\"" + gene_symbol + "\"], \"idTypes\":[\"GeneID\"] }";
        return json_query;
    }

    public static String query(final String url_str, final String json_query) throws IOException {

        final URL url = new URL(url_str);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        final OutputStream os = conn.getOutputStream();
        os.write(json_query.getBytes());
        os.flush();

        // if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
        // throw new RuntimeException("Failed : HTTP error code : " +
        // conn.getResponseCode());
        // }

        final BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();
        return sb.toString();
    }

    public static String httpGet(final String url_str) throws IOException {
        final URL url = new URL(url_str);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }
}