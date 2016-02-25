package org.cxio.aspects.datamodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

final class DatamodelsUtil {

    final static String removeParenthesis(final String string, final ATTRIBUTE_DATA_TYPE type) {
        if (string == null) {
            return null;
        }
        String substring = string.trim();
        if (substring.equals("null")) {
            return null;
        }
        if (substring.startsWith("\"") && substring.endsWith("\"")) {
            substring = substring.substring(1, substring.length() - 1);
        }
        final boolean allow_empty_string = (type == ATTRIBUTE_DATA_TYPE.STRING);
        if (!allow_empty_string && (substring.trim().length() < 1)) {
            throw new IllegalArgumentException("illegal format, empty strings not allowed: " + string);
        }
        return substring;
    }

    private final static List<String> split(final String json_array) throws IOException {

        final List<String> splittedJsonElements = new ArrayList<String>();
        final ObjectMapper m = new ObjectMapper();
        final JsonNode n = m.readTree(json_array);

        if (n.isArray()) {
            final ArrayNode arrayNode = (ArrayNode) n;
            for (int i = 0; i < arrayNode.size(); i++) {
                final JsonNode individualElement = arrayNode.get(i);
                splittedJsonElements.add(individualElement.toString());
            }
        }
        return splittedJsonElements;
    }

    final static List<String> parseStringToStringList(final String string, final ATTRIBUTE_DATA_TYPE type) throws IOException {
        final List<String> l = new ArrayList<String>();
        if (string == null) {
            return null;
        }
        final boolean allow_empty_string = (type == ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        final String str = string.trim();
        if (str.startsWith("[") && str.endsWith("]")) {
            // str = str.substring(1, str.length() - 1).trim();
            // if (str.length() == 0) {
            // return l;
            // }
            final List<String> str_split = split(str);
            for (String s : str_split) {
                s = s.trim();
                if (s.equals("null")) {
                    l.add(null);
                }
                else if (s.startsWith("\"") && s.endsWith("\"")) {
                    final String substring = s.substring(1, s.length() - 1);
                    if (!allow_empty_string && (substring.trim().length() < 1)) {
                        throw new IllegalArgumentException("parsing string to string list: illegal format, empty strings not allowed: " + str);
                    }
                    l.add(substring);
                }
                else {
                    throw new IllegalArgumentException("parsing string to string list: illegal format: expected to be \"null\" or begin and end with quotation marks, instead got: " + s + " from: "
                            + str);
                }
            }
        }
        else {
            throw new IllegalArgumentException("parsing string to string list: illegal format: expected to begin and end with square brackets, instead got : " + str);
        }
        return l;
    }

}
