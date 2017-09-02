package com.example.demo.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class CustomCommentGenerator extends DefaultCommentGenerator {

    /** The suppress all comments. */
    private boolean suppressChineseAllComments;

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressChineseAllComments || introspectedColumn.getRemarks() == null) {
            return;
        }

        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

}
