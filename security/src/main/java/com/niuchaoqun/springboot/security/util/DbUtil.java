package com.niuchaoqun.springboot.security.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    /**
     * 根据英文逗号分隔的ID，返回唯一的列表
     *
     * @param idString
     * @return
     */
    public static List<String> idStringToList(String idString) {
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
