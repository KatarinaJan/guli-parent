package com.atguigu.commonutils.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-02 13:38
 */
public class ExceptionUtil {

    public static String getMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (null != sw) {
                try {
                    sw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
