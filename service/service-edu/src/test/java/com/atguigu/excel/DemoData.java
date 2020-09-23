package com.atguigu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Project: guli-parent
 * @Describe: 设置表头和添加的数据字段
 * @Author: Jan
 * @Date: 2020-09-10 10:49
 */
@Data
public class DemoData {

    // index代表表的第几列 从0开始 用于读操作
    // 设置表头名称 设置列对应的属性
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;

    // 设置表头名称 设置列对应的属性
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}
