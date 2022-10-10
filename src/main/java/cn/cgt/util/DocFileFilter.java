package cn.cgt.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.FileFilter;

/**
 * 过滤文件
 * 仅接收 doc扩展名的文件
 *
 *
 * @author HuangZh
 * @date 2022/10/09
 */
public class DocFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {

        return "doc".equals(FileUtil.getSuffix(pathname));
    }
}
