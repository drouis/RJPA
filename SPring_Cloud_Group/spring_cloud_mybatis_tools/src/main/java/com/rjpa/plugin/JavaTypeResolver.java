package com.rjpa.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by tuo on 2016-06-29.
 */
public class JavaTypeResolver implements org.mybatis.generator.api.JavaTypeResolver {
    protected List<String> warnings;
    protected Properties properties = new Properties();
    protected Context context;
    protected boolean forceBigDecimals;
    protected Map<Integer, JdbcTypeInformation> typeMap = new HashMap();

    public JavaTypeResolver() {
        this.typeMap.put(Integer.valueOf(2003), new JavaTypeResolver.JdbcTypeInformation("ARRAY", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(-5), new JavaTypeResolver.JdbcTypeInformation("BIGINT", new FullyQualifiedJavaType(Long.class.getName())));
        this.typeMap.put(Integer.valueOf(-2), new JavaTypeResolver.JdbcTypeInformation("BINARY", new FullyQualifiedJavaType("byte[]")));
        this.typeMap.put(Integer.valueOf(-7), new JavaTypeResolver.JdbcTypeInformation("BIT", new FullyQualifiedJavaType(Integer.class.getName())));
        this.typeMap.put(Integer.valueOf(2004), new JavaTypeResolver.JdbcTypeInformation("BLOB", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(16), new JavaTypeResolver.JdbcTypeInformation("BOOLEAN", new FullyQualifiedJavaType(Boolean.class.getName())));
        this.typeMap.put(Integer.valueOf(1), new JavaTypeResolver.JdbcTypeInformation("CHAR", new FullyQualifiedJavaType(String.class.getName())));
        this.typeMap.put(Integer.valueOf(2005), new JavaTypeResolver.JdbcTypeInformation("CLOB", new FullyQualifiedJavaType(String.class.getName())));
        this.typeMap.put(Integer.valueOf(70), new JavaTypeResolver.JdbcTypeInformation("DATALINK", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(91), new JavaTypeResolver.JdbcTypeInformation("DATE", new FullyQualifiedJavaType(Date.class.getName())));
        this.typeMap.put(Integer.valueOf(2001), new JavaTypeResolver.JdbcTypeInformation("DISTINCT", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(8), new JavaTypeResolver.JdbcTypeInformation("DOUBLE", new FullyQualifiedJavaType(Double.class.getName())));
        this.typeMap.put(Integer.valueOf(6), new JavaTypeResolver.JdbcTypeInformation("FLOAT", new FullyQualifiedJavaType(Double.class.getName())));
        this.typeMap.put(Integer.valueOf(4), new JavaTypeResolver.JdbcTypeInformation("INTEGER", new FullyQualifiedJavaType(Integer.class.getName())));
        this.typeMap.put(Integer.valueOf(2000), new JavaTypeResolver.JdbcTypeInformation("JAVA_OBJECT", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(-4), new JavaTypeResolver.JdbcTypeInformation("LONGVARBINARY", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(-1), new JavaTypeResolver.JdbcTypeInformation("LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
        this.typeMap.put(Integer.valueOf(0), new JavaTypeResolver.JdbcTypeInformation("NULL", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(1111), new JavaTypeResolver.JdbcTypeInformation("OTHER", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(7), new JavaTypeResolver.JdbcTypeInformation("REAL", new FullyQualifiedJavaType(Float.class.getName())));
        this.typeMap.put(Integer.valueOf(2006), new JavaTypeResolver.JdbcTypeInformation("REF", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(5), new JavaTypeResolver.JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Integer.class.getName())));
        this.typeMap.put(Integer.valueOf(2002), new JavaTypeResolver.JdbcTypeInformation("STRUCT", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(92), new JavaTypeResolver.JdbcTypeInformation("TIME", new FullyQualifiedJavaType(Date.class.getName())));
        this.typeMap.put(Integer.valueOf(93), new JavaTypeResolver.JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(Date.class.getName())));
        this.typeMap.put(Integer.valueOf(-6), new JavaTypeResolver.JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        this.typeMap.put(Integer.valueOf(-3), new JavaTypeResolver.JdbcTypeInformation("VARBINARY", new FullyQualifiedJavaType(Object.class.getName())));
        this.typeMap.put(Integer.valueOf(12), new JavaTypeResolver.JdbcTypeInformation("VARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.forceBigDecimals = StringUtility.isTrue(properties.getProperty("forceBigDecimals"));
    }

    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        JavaTypeResolver.JdbcTypeInformation jdbcTypeInformation = this.typeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
        FullyQualifiedJavaType answer;
        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case 2:
                case 3:
                    if (introspectedColumn.getScale() <= 0 && introspectedColumn.getLength() <= 18 && !this.forceBigDecimals) {
                        if (introspectedColumn.getLength() > 9) {
                            answer = new FullyQualifiedJavaType(Long.class.getName());
                        } else if (introspectedColumn.getLength() > 4) {
                            answer = new FullyQualifiedJavaType(Integer.class.getName());
                        } else {
                            answer = new FullyQualifiedJavaType(Short.class.getName());
                        }
                    } else {
                        answer = new FullyQualifiedJavaType(BigDecimal.class.getName());
                    }
                    break;
                default:
                    answer = null;
            }
        } else {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }

        return answer;
    }

    public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
        JavaTypeResolver.JdbcTypeInformation jdbcTypeInformation = this.typeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
        String answer;
        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case 2:
                    answer = "NUMERIC";
                    break;
                case 3:
                    answer = "DECIMAL";
                    break;
                default:
                    answer = null;
            }
        } else {
            answer = jdbcTypeInformation.getJdbcTypeName();
        }

        return answer;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private static class JdbcTypeInformation {
        private String jdbcTypeName;
        private FullyQualifiedJavaType fullyQualifiedJavaType;

        public JdbcTypeInformation(String jdbcTypeName, FullyQualifiedJavaType fullyQualifiedJavaType) {
            this.jdbcTypeName = jdbcTypeName;
            this.fullyQualifiedJavaType = fullyQualifiedJavaType;
        }

        public String getJdbcTypeName() {
            return this.jdbcTypeName;
        }

        public FullyQualifiedJavaType getFullyQualifiedJavaType() {
            return this.fullyQualifiedJavaType;
        }
    }
}
