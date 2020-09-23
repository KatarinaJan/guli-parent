package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-10 10:52
 */
public class ExcelTest {

    private static List<DemoData> getData() {
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三" + i);
            list.add(data);
        }
        return list;
    }

    private static String getFile() {
        String fileName = "01.xlsx";
        String filePath = "E:\\jan\\excel\\output";
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return file.toString();
    }


    @Test
    public void writeTest01() {
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(getFile(), DemoData.class).sheet("写入数据1").doWrite(getData());
    }

    @Test
    public void writeTest02() {
        // 这里 需要指定用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(getFile(), DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(1,"写入数据2").build();
        excelWriter.write(getData(), writeSheet);
        // finish帮忙关流
        excelWriter.finish();
    }

    @Test
    public void readTest01() {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(getFile(), DemoData.class, new ExcelListener()).sheet().doRead();
    }

    @Test
    public void readTest02() {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ExcelReader excelReader = EasyExcel.read(bis, DemoData.class, new ExcelListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet().build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
}
