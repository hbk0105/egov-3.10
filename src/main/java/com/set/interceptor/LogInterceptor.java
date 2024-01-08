package com.set.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Intercepts({@Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
public class LogInterceptor implements Interceptor {

    //private static Logger sqlLog = LoggerFactory.getLogger("MYBATIS_SQL_LOG");

    private org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        String tmpsql = handler.getBoundSql().getSql();
        String sql = "";

        try {
            sql = bindSql(handler); // SQL추출

            log.debug("=====================================================================");
            log.debug(sql);
            log.debug("=====================================================================");

        } catch (Exception e){
            log.error("LogInterceptor - intercept" + e.getMessage());
        }

        return invocation.proceed();
    }

    /**
     * <pre>
     * bindSql
     *
     * <pre>
     *
     * @param handler
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    private String bindSql(StatementHandler handler) throws NoSuchFieldException, IllegalAccessException {
        BoundSql boundSql = handler.getBoundSql();

        // 쿼리실행시 맵핑되는 파라미터를 구한다
        Object param = handler.getParameterHandler().getParameterObject();
        // 쿼리문을 가져온다(이 상태에서의 쿼리는 값이 들어갈 부분에 ?가 있다)
        String sql = boundSql.getSql();

        // 바인딩 파라미터가 없으면
        if (param == null) {
            sql = sql.replaceFirst("\\?", "''");
            return sql;
        }

        // 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
        if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
            sql = sql.replaceFirst("\\?", param.toString());
        }
        // 해당 파라미터의 클래스가 String인 경우
        else if (param instanceof String) {
            sql = sql.replaceFirst("\\?", "'" + param + "'");
        }
        // 해당 파라미터의 클래스가 Map인 경우
        else if (param instanceof Map) {
            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
            for (ParameterMapping mapping : paramMapping) {
                String propValue = mapping.getProperty();
                Object value = ((Map) param).get(propValue);
                String getParam = "##";
                if (value == null) {
//                    continue;
                    value = getParam;
                }

                if (value instanceof String) {
                    sql = sql.replaceFirst("\\?", "'" + value + "'");
                } else {
                    sql = sql.replaceFirst("\\?", value.toString());
                }
            }
        }
        // 해당 파라미터의 클래스가 사용자 정의 클래스인 경우
        else {
            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
            Class<? extends Object> paramClass = param.getClass();

            for (ParameterMapping mapping : paramMapping) {
                String propValue = mapping.getProperty();
                //Field field = paramClass.getDeclaredField(propValue);
                Field field = findUnderlying(paramClass, propValue);
                if (field != null) {
                    field.setAccessible(true);
                    Class<?> javaType = mapping.getJavaType();
                    String getParam = "##";
                    if (field.get(param) != null) {
                        getParam = field.get(param).toString();
                    }
                    if (String.class == javaType) {
                        sql = sql.replaceFirst("\\?", "'" + getParam + "'");
                    } else {
                        sql = sql.replaceFirst("\\?", getParam);
                    }
                }
//                field.setAccessible(true);
//                Class<?> javaType = mapping.getJavaType();
//                if (String.class == javaType) {
//                    sql = sql.replaceFirst("\\?", "'" + field.get(param) + "'");
//                } else {
//                    sql = sql.replaceFirst("\\?", field.get(param).toString());
//                }
            }
        }

        // return sql
        return sql;
    }

    // class.getDeclaredField의 경우 부모 필드까지 검사하지 않아서 검사하는 메소드
    // 출처: https://stackoverflow.com/questions/12485351/java-reflection-field-value-in-extends-class
    private Field findUnderlying(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        } while ((current = current.getSuperclass()) != null);
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}