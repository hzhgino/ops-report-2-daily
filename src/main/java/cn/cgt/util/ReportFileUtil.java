package cn.cgt.util;

import cn.cgt.config.ReportConfiguration;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 报告文件工具类
 *
 * @author HuangZh
 * @date 2022/09/26
 */
@Component
public class ReportFileUtil {


    private static ReportConfiguration reportConfiguration;

    @Autowired
    public void setReportConfiguration(ReportConfiguration reportConfiguration) {
        ReportFileUtil.reportConfiguration = reportConfiguration;
    }


    /**
     * 传入模板文件路径， 返回报告文件生成路径
     *
     * @param templatePath 模板文件路径
     * @param reportMoth   报告月份(例如：2022-10)
     * @param date         日期（例如：2022-10-09）
     *
     * @return {@link String}
     */
    public static String outFilePathHandler(String templatePath, String reportMoth, String date) {
        String outFilePath = templatePath.replace(reportConfiguration.getTemplatePath(), reportConfiguration.getOutPath())
                .replace("报告月份", reportMoth)
                .replace("日期", date);


        //检查输出目录是否存在
        File file = FileUtil.file(outFilePath);

        String name = FileUtil.getName(file);
        String outPath = file.getAbsolutePath().replace(name, "");
        if (!FileUtil.newFile(outPath).exists()) {
            FileUtil.mkdir(outPath);
        }
        return outFilePath;
    }


}
