package cn.cgt.service;

import cn.cgt.util.ReportFileUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 日更文件 生成服务
 *
 * @author HuangZh
 * @date 2022/09/26
 */
@Service
public class DailyOpsService {


    /**
     * 报告
     *
     * @param date             报告日期（yyyy-MM-dd,例如：2022-10-01）
     * @param templateFilePath 模板文件路径(例如：reportConfiguration.getTemplatePath() + "二、日常巡检/过程资料/巡检服务/服务器备份情况巡查表/报告月份/巡查服务-服务器备份情况巡查表(日期).doc";)
     */
    public void report(String date, String templateFilePath) {

        //加载文档
        Document doc = new Document();
        doc.loadFromFile(templateFilePath);

        DateTime dateTime = DateUtil.parseDate(date);
        int year = dateTime.year();
        int month = dateTime.monthBaseOne();
        //报告月份
        String reportMoth = year + "-" + month;
        Map<String, String> templatesParams = new HashMap<>();
        templatesParams.put("{日期}", date);

        Set<Map.Entry<String, String>> entries = templatesParams.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            //调用方法用新文本替换原文本内容
            String key = entry.getKey();
            String value = entry.getValue();
            doc.replace(key, value, false, true);
        }

        //保存文档
        String outFilePath = ReportFileUtil.outFilePathHandler(templateFilePath, reportMoth, date);
        doc.saveToFile(outFilePath, FileFormat.Docx_2013);
        doc.dispose();

        System.out.println("ok-->" + outFilePath);
    }


}
