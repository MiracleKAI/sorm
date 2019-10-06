package com.miracle.sorm.Util;

import com.miracle.sorm.bean.ColumnInfo;
import com.miracle.sorm.bean.JavaFieldGetSet;
import com.miracle.sorm.bean.TableInfo;
import com.miracle.sorm.core.DBManager;
import com.miracle.sorm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表对应的bean类源代码，有属性fields，对应的get,set方法
 */
public class JavaFileUtils {
    /**
     * 生成属性和getset方法源码
     * @param columnInfo 字段信息
     * @param typeConvertor 类型转换器
     * @return 属性和getset方法源码
     */
    public static JavaFieldGetSet createJavaFiledGetSet(ColumnInfo columnInfo , TypeConvertor typeConvertor){
        JavaFieldGetSet javaFieldGetSet = new JavaFieldGetSet();
        String type = typeConvertor.mySqlType2JavaType(columnInfo.getColumnType());
        String fieldName = columnInfo.getColumnName();
        /*
         *  private String studentId;
         */
        javaFieldGetSet.setFieldInfo("\tprivate "+ type +" "+fieldName+";\n");

        /*
         *  public int setStudentId(int studentId){this.studentId = studentId;};
         */
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\tpublic ");
        stringBuffer.append("void");
        stringBuffer.append(" set"+StringUtils.firstChar2UpperCase(fieldName)+"(");
        stringBuffer.append(type +" "+fieldName+"){");
        stringBuffer.append("\n\n\t\tthis."+fieldName+" = " + fieldName+";\n\n\t}");

        javaFieldGetSet.setGeterInfo(stringBuffer.toString());

        /*
         *  public String getStudentId(){return this.student;}
         */
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("\tpublic ");
        stringBuffer2.append(type);
        stringBuffer2.append(" get"+StringUtils.firstChar2UpperCase(fieldName)+"(");
        stringBuffer2.append(" ){");
        stringBuffer2.append("\n\n\t\treturn this."+fieldName+";\n\n\t}");
        javaFieldGetSet.setSeterInfo(stringBuffer2.toString());
        return javaFieldGetSet;
    }

    /**
     * 根据表信息生成java源代码
     * @param tableInfo 表信息
     * @param typeConvertor 类型转换器
     * @return 表信息生成java源代码
     */
    public static String creatJavaClassSrc(TableInfo tableInfo, TypeConvertor typeConvertor){
        StringBuilder stringBuilder = new StringBuilder();

        Map<String,ColumnInfo> columns = tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields = new ArrayList<>();

        for(ColumnInfo columnInfo : columns.values()){
            javaFields.add(createJavaFiledGetSet(columnInfo,typeConvertor));
        }

        //package 语句
        stringBuilder.append("package "+ DBManager.getConfiguration().getPoPackage()+";\n\n");
        //import 语句
        stringBuilder.append("import java.util.*;\n");
        stringBuilder.append("import java.sql.*;\n\n");
        //类声明语句
        stringBuilder.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTableName())+"{\n\n");
        //属性列表
        for(JavaFieldGetSet javaFieldGetSet : javaFields){
            stringBuilder.append(javaFieldGetSet.getFieldInfo());
        }
        stringBuilder.append("\n\n");
        //get set方法
        for(JavaFieldGetSet javaFieldGetSet : javaFields){
            stringBuilder.append(javaFieldGetSet.getSeterInfo()+"\n");
            stringBuilder.append(javaFieldGetSet.getGeterInfo()+"\n");
        }
        //结束
        stringBuilder.append("}\n");
       // System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 根据表信息生成Java类
     * @param tableInfo 表信息
     * @param typeConvertor 类型转换器
     */
    public static void createJavaClassFile(TableInfo tableInfo,TypeConvertor typeConvertor)  {
        String src = creatJavaClassSrc(tableInfo,typeConvertor);
        BufferedWriter bufferedWriter = null;

        String srcPath =DBManager.getConfiguration().getSrcPath();

        String packagePath=DBManager.getConfiguration().getPoPackage().replaceAll("\\.","/");

        String classPath = srcPath + packagePath;

        File file = new File(classPath);

        if(!file.exists()){
            file.mkdirs();
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(file.getAbsolutePath()+"/"+StringUtils.firstChar2UpperCase(tableInfo.getTableName())+".java")));
            bufferedWriter.write(src);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bufferedWriter){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
