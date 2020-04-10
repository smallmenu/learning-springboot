package com.niuchaoqun.springboot.security.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    public static List<String> stringToList(String idString) {
        List<String> lists = new ArrayList<>();
        if (StringUtils.isNotBlank(idString)) {
            String[] idStrings = StringUtils.split(idString, ",");
            for (String idstr : idStrings) {
                String id = StringUtils.trimToEmpty(idstr);
                if (StringUtils.isNotBlank(id)) {
                    if (!lists.contains(id)) {
                        lists.add(id);
                    }
                }
            }
        }

        return lists;
    }
}
