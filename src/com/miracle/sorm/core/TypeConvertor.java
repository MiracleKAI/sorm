package com.miracle.sorm.core;

/**
 * 类型转换器，将数据库类型转换为java类型
 */
public class TypeConvertor {

    //java对应java中的类型

    private String javaType;

    /**
     *
     * @param mySqlType  mySql中的数据类型
     * @return           java中的类型
     *
     */
    public String mySqlType2JavaType(String mySqlType) {
        switch (mySqlType) {
            case  "INT":
                javaType="Integer";
                break;
            case "CHAR":
                javaType = "String";
                break;
            case "VARCHAR":
                javaType = "String";
                break;
            case "BLOB":
                javaType = "byte[]";
                break;
            case "TEXT":
                javaType = "String";
                break;
            case "INTEGER":
                javaType = "Integer";
                break;
            case "SMALLINT":
                javaType = "Integer";
                break;
            case "TINYINT":
                javaType = "Integer";
                break;
            case "MEDIUMINT":
                javaType = "Integer";
                break;
            case "BIT":
                javaType = "Boolean";
                break;
            case "BIGINT":
                javaType = "Long";
                break;
            case "FLOAT":
                javaType = "Float";
                break;
            case "DOUBLE":
                javaType = "Double";
                break;
            case "DECIMAL":
                javaType = "java.math.BigDecimal";
                break;
            case "DATE":
                javaType = "java.util.Date";
                break;
            case "TIME":
                javaType = "java.util.Time";
                break;
            case "DATETIME":
                javaType = "java.util.Date";
                break;
            case "TIMESTAMP":
                javaType = "java.util.Date";
                break;
            case "ENUM":
                javaType = "Enum";
                break;
            case "SMALLINT UNSIGNED":
                javaType = "Integer";
                break;
            default:
                System.out.println(mySqlType);
                throw new RuntimeException("亲,你要转换的mysql类型[" + mySqlType + "]不存在!");
        }
        return javaType;
    }
}
