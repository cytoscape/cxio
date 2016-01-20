package org.cxio.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MappingServiceTools {
    public static final String   HUMAN     = "human";
    public static final String   GENE_ID   = "GeneID";
    public static final String   SYMBOL    = "Symbol";
    public static final String   SYNONYMS  = "Synonyms";

    private static final String  UNMATCHED = "unmatched";
    private static final String  MATCHED   = "matched";
    private static final String  MATCHES   = "matches";
    private static final String  IN        = "in";
    private static final String  IN_TYPE   = "inType";
    private static final String  SPECIES   = "species";

    private static final boolean DEBUG     = false;

    public final static String runQuery(final List<String> ids, final String url) throws IOException {
        final String json_query = MappingServiceTools.makeQuery(ids);
        return MappingServiceTools.post(url, json_query);
    }

    public final static void parseResponse(final String json_str,
                                           final Set<String> in_types,
                                           final String target_species,
                                           final String target_type,
                                           final Map<String, SortedSet<String>> matched_ids,
                                           final Set<String> unmatched_ids) throws IOException, JsonProcessingException {
        if (DEBUG) {
            System.out.println("str =" + json_str);
        }
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode root = mapper.readTree(json_str);
        if (DEBUG) {
            System.out.println("root=" + root);
        }

        final JsonNode unmatched = root.path(UNMATCHED);

        final Iterator<JsonNode> unmatched_it = unmatched.elements();
        while (unmatched_it.hasNext()) {
            unmatched_ids.add(unmatched_it.next().asText());
        }
        if (!root.has(MATCHED)) {
            throw new IOException("no " + MATCHED + " field");
        }

        final JsonNode matched = root.path(MATCHED);

        final Iterator<JsonNode> matched_it = matched.elements();
        while (matched_it.hasNext()) {
            final JsonNode n = matched_it.next();
            if (n.has(SPECIES)) {
                if (target_species.equals(n.get(SPECIES).asText())) {
                    if (in_types.contains(n.get(IN_TYPE).asText())) {
                        final String in = n.get(IN).asText();
                        if (n.has(MATCHES)) {
                            final JsonNode m = n.get(MATCHES);
                            if (m.has(target_type)) {
                                final String g = m.get(target_type).asText();
                                if (!matched_ids.containsKey(in)) {
                                    matched_ids.put(in, new TreeSet<String>());
                                }
                                matched_ids.get(in).add(g);
                            }
                            else {
                                throw new IOException("no target type:" + target_type);
                            }
                        }
                        else {
                            throw new IOException("no " + MATCHES + " field");
                        }
                    }
                }
            }
            else {
                throw new IOException("no species: " + target_species);
            }
        }
    }

    private static final String makeQuery(final List<String> gene_symbols) {
        final StringBuilder sb = new StringBuilder();
        sb.append("{\"ids\": [");
        sb.append(listToString(gene_symbols));
        sb.append("], \"idTypes\":[\"GeneID\"] }");
        return sb.toString();
    }

    private final static StringBuilder listToString(final List<String> l) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (final String s : l) {
            if (first) {
                first = false;
            }
            else {
                sb.append(",");
            }
            sb.append("\"");
            sb.append(s);
            sb.append("\"");
        }
        return sb;
    }

    private static final String post(final String url_str, final String json_query) throws IOException {
        final URL url = new URL(url_str);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (DEBUG) {
            System.out.println(json_query);
        }
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
}
