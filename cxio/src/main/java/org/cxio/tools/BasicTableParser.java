package org.cxio.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.cxio.util.Util;

public class BasicTableParser {

    private final static String START_OF_COMMENT_LINE_DEFAULT = "#";

    private BasicTableParser() {
    }

    public static BasicTable<String> parse(final Object source, final char column_delimiter) throws IOException {
        return BasicTableParser.parse(source, column_delimiter, false, false, START_OF_COMMENT_LINE_DEFAULT, false).get(0);
    }

    public static BasicTable<String> parse(final Object source, final char column_delimiter, final boolean use_first_separator_only, final boolean use_last_separator_only) throws IOException {
        return BasicTableParser.parse(source, column_delimiter, use_first_separator_only, use_last_separator_only, START_OF_COMMENT_LINE_DEFAULT, false).get(0);
    }

    public static List<BasicTable<String>> parse(final Object source,
                                                 final char column_delimiter,
                                                 final boolean use_first_separator_only,
                                                 final boolean use_last_separator_only,
                                                 final String start_of_comment_line,
                                                 final boolean tables_separated_by_single_string_line) throws IOException {
        if (use_first_separator_only && use_last_separator_only) {
            throw new IllegalArgumentException();
        }
        final BufferedReader reader = Util.obtainReader(source);
        final List<BasicTable<String>> tables = new ArrayList<BasicTable<String>>();
        BasicTable<String> table = new BasicTable<String>();
        int row = 0;
        String line;
        boolean saw_first_table = false;
        final boolean use_start_of_comment_line = !(Util.isEmpty(start_of_comment_line));
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!Util.isEmpty(line)
                    && (((line.charAt(0) == '"') && (line.charAt(line.length() - 1) == '"') && (Util.countChars(line, '"') == 2)) || ((line.charAt(0) == '\'')
                            && (line.charAt(line.length() - 1) == '\'') && (Util.countChars(line, '\'') == 2)))) {
                line = line.substring(1, line.length() - 1).trim();
            }
            if (saw_first_table && (Util.isEmpty(line) || (tables_separated_by_single_string_line && (line.indexOf(column_delimiter) < 0)))) {
                if (!table.isEmpty()) {
                    tables.add(table);
                }
                table = new BasicTable<String>();
                row = 0;
            }
            else if (!Util.isEmpty(line) && (!use_start_of_comment_line || !line.startsWith(start_of_comment_line))) {
                saw_first_table = true;
                if (use_last_separator_only) {
                    final String e[] = line.split(column_delimiter + "");
                    final StringBuffer rest = new StringBuffer();
                    for (int i = 0; i < (e.length - 1); ++i) {
                        rest.append(e[i].trim());
                    }
                    table.setValue(0, row, rest.toString());
                    table.setValue(1, row, e[e.length - 1]);
                }
                else {
                    final StringTokenizer st = new StringTokenizer(line, column_delimiter + "");
                    int col = 0;
                    if (st.hasMoreTokens()) {
                        table.setValue(col++, row, st.nextToken().trim());
                    }
                    if (use_first_separator_only) {
                        final StringBuffer rest = new StringBuffer();
                        while (st.hasMoreTokens()) {
                            rest.append(st.nextToken());
                        }
                        table.setValue(col++, row, rest.toString());
                    }
                    else {
                        while (st.hasMoreTokens()) {
                            table.setValue(col++, row, st.nextToken().trim());
                        }
                    }
                }
                ++row;
            }
        }
        if (!table.isEmpty()) {
            tables.add(table);
        }
        reader.close();
        return tables;
    }
}
