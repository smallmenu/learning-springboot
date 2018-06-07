package com.niuchaoqun.springboot.gmapper;

import com.niuchaoqun.springboot.gentity.User;
import com.niuchaoqun.springboot.gentity.UserExample.Criteria;
import com.niuchaoqun.springboot.gentity.UserExample.Criterion;
import com.niuchaoqun.springboot.gentity.UserExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {

    public String countByExample(UserExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(UserExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=TINYINT}");
        }
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getSalt() != null) {
            sql.VALUES("salt", "#{salt,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("birthday", "#{birthday,jdbcType=DATE}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=CHAR}");
        }
        
        if (record.getAccess() != null) {
            sql.VALUES("access", "#{access,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAccessTime() != null) {
            sql.VALUES("access_time", "#{accessTime,jdbcType=TIME}");
        }
        
        if (record.getCreated() != null) {
            sql.VALUES("created", "#{created,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdated() != null) {
            sql.VALUES("updated", "#{updated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            sql.VALUES("state", "#{state,jdbcType=BIT}");
        }
        
        return sql.toString();
    }

    public String selectByExample(UserExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("role_id");
        sql.SELECT("username");
        sql.SELECT("password");
        sql.SELECT("salt");
        sql.SELECT("name");
        sql.SELECT("birthday");
        sql.SELECT("sex");
        sql.SELECT("access");
        sql.SELECT("access_time");
        sql.SELECT("created");
        sql.SELECT("updated");
        sql.SELECT("state");
        sql.FROM("user");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        User record = (User) parameter.get("record");
        UserExample example = (UserExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("user");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getRoleId() != null) {
            sql.SET("role_id = #{record.roleId,jdbcType=TINYINT}");
        }
        
        if (record.getUsername() != null) {
            sql.SET("username = #{record.username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        
        if (record.getSalt() != null) {
            sql.SET("salt = #{record.salt,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("birthday = #{record.birthday,jdbcType=DATE}");
        }
        
        if (record.getSex() != null) {
            sql.SET("sex = #{record.sex,jdbcType=CHAR}");
        }
        
        if (record.getAccess() != null) {
            sql.SET("access = #{record.access,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAccessTime() != null) {
            sql.SET("access_time = #{record.accessTime,jdbcType=TIME}");
        }
        
        if (record.getCreated() != null) {
            sql.SET("created = #{record.created,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdated() != null) {
            sql.SET("updated = #{record.updated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            sql.SET("state = #{record.state,jdbcType=BIT}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("user");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("role_id = #{record.roleId,jdbcType=TINYINT}");
        sql.SET("username = #{record.username,jdbcType=VARCHAR}");
        sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        sql.SET("salt = #{record.salt,jdbcType=VARCHAR}");
        sql.SET("name = #{record.name,jdbcType=VARCHAR}");
        sql.SET("birthday = #{record.birthday,jdbcType=DATE}");
        sql.SET("sex = #{record.sex,jdbcType=CHAR}");
        sql.SET("access = #{record.access,jdbcType=TIMESTAMP}");
        sql.SET("access_time = #{record.accessTime,jdbcType=TIME}");
        sql.SET("created = #{record.created,jdbcType=TIMESTAMP}");
        sql.SET("updated = #{record.updated,jdbcType=TIMESTAMP}");
        sql.SET("state = #{record.state,jdbcType=BIT}");
        
        UserExample example = (UserExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        SQL sql = new SQL();
        sql.UPDATE("user");
        
        if (record.getRoleId() != null) {
            sql.SET("role_id = #{roleId,jdbcType=TINYINT}");
        }
        
        if (record.getUsername() != null) {
            sql.SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getSalt() != null) {
            sql.SET("salt = #{salt,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday,jdbcType=DATE}");
        }
        
        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=CHAR}");
        }
        
        if (record.getAccess() != null) {
            sql.SET("access = #{access,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAccessTime() != null) {
            sql.SET("access_time = #{accessTime,jdbcType=TIME}");
        }
        
        if (record.getCreated() != null) {
            sql.SET("created = #{created,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdated() != null) {
            sql.SET("updated = #{updated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            sql.SET("state = #{state,jdbcType=BIT}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, UserExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}