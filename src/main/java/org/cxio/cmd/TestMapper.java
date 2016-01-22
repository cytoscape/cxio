package org.cxio.cmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.cxio.tools.GeneSymbolMapper;
import org.cxio.tools.MappingServiceTools;

public final class TestMapper {

    public static void main(final String[] args) throws IOException {

        final List<String> ids = new ArrayList<String>();

        ids.add("A1BG");
        ids.add("A2M");
        ids.add("A2MP1");
        ids.add("AADAC");
        ids.add("AADACL2");
        ids.add("NOP9");
        ids.add("idonotexist1");
        ids.add("idonotexist2");

        final SortedMap<String, SortedSet<String>> map = new TreeMap<String, SortedSet<String>>();
        final SortedSet<String> unmatched_ids = new TreeSet<String>();

        final String res = MappingServiceTools.runQuery(ids, GeneSymbolMapper.MAP_SERVICE_URL_STR);
        System.out.println(res);
        final SortedSet<String> in_types = new TreeSet<String>();
        in_types.add(MappingServiceTools.SYNONYMS);
        in_types.add(MappingServiceTools.SYMBOL);
        MappingServiceTools.parseResponse(res, in_types, "human", "GeneID", map, unmatched_ids);
        System.out.println("mappings  = " +map);
        System.out.println("unmatched = "+unmatched_ids);
       

        System.out.println("OK");

    }

}