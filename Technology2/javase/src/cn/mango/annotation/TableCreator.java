package cn.mango.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/3/9 17:36
 **/
public class TableCreator {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("arguments annotated classes");
            System.exit(0);
        }
        for (String className : args) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.out.println("class not find");
            }
            TableName annotation = clazz.getAnnotation(TableName.class);
            String tableName = annotation.value();
            if (tableName.length() < 1)
                tableName = clazz.getName().toUpperCase();
            List<String> columns = new ArrayList<>();
            for (Field field : clazz.getDeclaredFields()) {
                String columnName = null;
                TableField tableField = field.getDeclaredAnnotation(TableField.class);
                if (tableField == null)
                    continue;
                if (tableField.name().length() < 1)
                    columnName = field.getName();
                else
                    columnName = tableField.name();
                if (tableField.type().getVale().equals("datetime"))
                    columns.add(columnName + " " + tableField.type().getVale()
                            + getConstraint(tableField.constraints()));
                else
                columns.add(columnName + " " + tableField.type().getVale() + "(" +
                        tableField.value() + ")" + getConstraint(tableField.constraints()));
            }
            StringBuffer stringBuffer = new StringBuffer("CREATE TABLE " + tableName + "(");
            for (String column : columns)
                stringBuffer.append("\n" + column + ",");
            String tableCreate = stringBuffer.substring(0, stringBuffer.length()-1) + "\n);";
            System.out.println(tableCreate);

        }
    }

    private static String getConstraint(Constraints constraints) {
        String message = "";
        if (constraints.allowNull())
            message += " NOT NULL";
        if (constraints.primaryKey())
            message += " PRIMARY KEY";
        if (constraints.unique())
            message += " UNIQUE";
        return message;

    }



}
