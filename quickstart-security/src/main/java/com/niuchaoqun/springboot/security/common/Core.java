package com.niuchaoqun.springboot.security.common;


import com.niuchaoqun.springboot.security.property.ConfigProperty;
import com.niuchaoqun.springboot.security.util.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
public class Core {
    private static final String TABLE_SQL_TEMPLATE = "SELECT * FROM %s";

    private static final String TABLE_CACHE_NAMESPACE = "table_cache:";

    private static final String TABLE_PRIMARY_KEY = "id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ConfigProperty configProperty;

    /**
     * 根据实体类和预定义的白名单，缓存优先查询全表数据（适用于变更很小的配置类数据表）
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> List<T> tableCache(Class<T> entityClass) {
        return tableCache(entityClass, true);
    }

    /**
     * 根据实体类和预定义的白名单，缓存优先查询全表数据（适用于变更很小的配置类数据表），支持强制刷新
     *
     * @param entityClass
     * @param cached
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> tableCache(Class<T> entityClass, boolean cached) {
        String entityName = entityClass.getSimpleName();
        String tableName = CamelCaseUtil.camelToUnder(entityName);
        Set<String> tableCache = configProperty.getTableCache();

        if (!tableCache.isEmpty() && tableCache.contains(tableName)) {
            String key = TABLE_CACHE_NAMESPACE + tableName;

            ValueOperations<String, List<T>> ops = redisTemplate.opsForValue();

            if (cached && BooleanUtils.isTrue(redisTemplate.hasKey(key))) {
                log.debug("TableCache [{}]", tableName);
                return ops.get(key);
            } else {
                List<T> datas = table(entityClass);
                if (datas != null) {
                    ops.set(key, datas);
                }
                return datas;
            }
        } else {
            return table(entityClass);
        }
    }

    /**
     * 根据实体类，缓存优先查询数据表单一记录
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public <T> T tableCache(Class<T> entityClass, Long id) {
        return tableCache(entityClass, id, true);
    }

    /**
     * 根据实体类，缓存优先查询数据表单一记录，支持强制更新
     *
     * @param entityClass
     * @param id
     * @param cached
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T tableCache(Class<T> entityClass, Long id, boolean cached) {
        String entityName = entityClass.getSimpleName();
        String tableName = CamelCaseUtil.camelToUnder(entityName);
        String idString = String.valueOf(id);

        String key = TABLE_CACHE_NAMESPACE + tableName + ":" + idString;
        ValueOperations<String, T> ops = redisTemplate.opsForValue();

        if (cached && BooleanUtils.isTrue(redisTemplate.hasKey(key))) {
            log.debug("TableCache [{}] Primary: {}", tableName, id);
            return ops.get(key);
        } else {
            // 更新缓存
            T data = table(entityClass, id);
            if (data != null) {
                ops.set(key, data);
                return data;
            }
        }

        return null;
    }

    /**
     * 缓存清空
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> tableClear(Class<T> entityClass, Long id) {
        String entityName = entityClass.getSimpleName();
        String tableName = CamelCaseUtil.camelToUnder(entityName);
        Set<String> tableCache = configProperty.getTableCache();

        if (id != null) {
            String idString = String.valueOf(id);
            String key = TABLE_CACHE_NAMESPACE + tableName + ":" + idString;
            if (BooleanUtils.isTrue(redisTemplate.hasKey(key))) {
                log.debug("TableClear [{}] Primary: {}", tableName, id);
                redisTemplate.delete(key);
            }
        }

        if (!tableCache.isEmpty() && tableCache.contains(tableName)) {
            String tableKey = TABLE_CACHE_NAMESPACE + tableName;
            if (BooleanUtils.isTrue(redisTemplate.hasKey(tableKey))) {
                log.debug("TableClear [{}]", tableName);
                redisTemplate.delete(tableKey);
            }
        }

        return null;
    }

    /**
     * 根据实体类，快捷查询全表数据
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> List<T> table(Class<T> entityClass) {
        String entityName = entityClass.getSimpleName();
        String tableName = CamelCaseUtil.camelToUnder(entityName);

        if (StringUtils.isNotBlank(tableName)) {
            String sql = String.format(TABLE_SQL_TEMPLATE, tableName);

            log.debug("Table [{}]", sql);
            List<T> datas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(entityClass));

            if (!CollectionUtils.isEmpty(datas)) {
                return datas;
            }
        }

        return Collections.emptyList();
    }

    /**
     * 根据实体类，快捷查询数据表单一记录，返回该行记录，必须传入主键
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public <T> T table(Class<T> entityClass, Long id) {
        String entityName = entityClass.getSimpleName();
        String tableName = CamelCaseUtil.camelToUnder(entityName);

        if (StringUtils.isNotBlank(tableName) && id > 0) {
            String sql = String.format(TABLE_SQL_TEMPLATE, tableName);
            sql += " WHERE " + TABLE_PRIMARY_KEY + " = ?";
            try {
                log.debug("Table [{}] Primary: {}", sql, id);
                T data = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(entityClass), id);

                if (data != null) {
                    return data;
                }
            } catch (EmptyResultDataAccessException e) {
                return null;
            }
        }

        return null;
    }
}

