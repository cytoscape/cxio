package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

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

    final static List<String> parseStringToStringList(final String string, final ATTRIBUTE_DATA_TYPE type) {
        final List<String> l = new ArrayList<String>();
        if (string == null) {
            return null;
        }
        final boolean allow_empty_string = (type == ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        String str = string.trim();
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1).trim();
            if (str.length() == 0) {
                return l;
            }
            for (String s : str.split(",", -1)) {
                s = s.trim();
                if (s.equals("null")) {
                    l.add(null);
                }
                else if (s.startsWith("\"") && s.endsWith("\"")) {
                    final String substring = s.substring(1, s.length() - 1);
                    if (!allow_empty_string && (substring.trim().length() < 1)) {
                        throw new IllegalArgumentException("illegal format, empty strings not allowed: " + str);
                    }
                    l.add(substring);
                }
                else {
                    throw new IllegalArgumentException("illegal format: " + str);
                }
            }
        }
        else {
            throw new IllegalArgumentException("illegal format: " + str);
        }
        return l;
    }

}
