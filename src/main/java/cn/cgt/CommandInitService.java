package cn.cgt;

import cn.cgt.config.ReportConfiguration;
import cn.cgt.service.DailyOpsService;
import cn.cgt.util.DocFileFilter;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令初始化服务
 *
 * @author HuangZh
 * @date 2022/09/27
 */
@Service
public class CommandInitService implements CommandLineRunner {
    @Autowired
    private DailyOpsService dailyOpsService;
    @Autowired
    private ReportConfiguration reportConfiguration;


    @Override
    public void run(String... args) {
        //成城月份
        String start = new DateTime().toDateStr();
        String end = start;
        if (args.length > 0) {
            start = args[0];
            end = args.length == 1 ? start : args[1];
        }


        List<String> reportDateList = new ArrayList<>();

        DateTime startDate = DateUtil.parseDate(start);
        DateTime endDate = DateUtil.parseDate(end);
        reportDateList.add(startDate.toDateStr());
        while (startDate.isBefore(endDate)) {
            startDate.offset(DateField.DAY_OF_MONTH, 1);
            reportDateList.add(startDate.toDateStr());
        }
        for (String date : reportDateList) {
            List<File> templateFilePaths = FileUtil.loopFiles(reportConfiguration.getTemplatePath(), new DocFileFilter());
            for (File templateFilePath : templateFilePaths) {
                dailyOpsService.report(date, templateFilePath.getAbsolutePath());
            }
        }
    }


}
