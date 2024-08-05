package com.reine.cmd;

import com.reine.properties.Profile;
import com.reine.site.SiteAction;
import com.reine.utils.PdfUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.nio.file.Path;


/**
 * @author reine
 * 2024/8/4 9:20
 */
@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class Downloader {

    private final SiteAction action;

    private final Profile profile;

    /**
     * 下载hentai
     *
     * @param name hentai名称
     */
    @ShellMethod(key = "download", value = "download hentai")
    public void download(@ShellOption(help = "hentai name") String name) {
        action.search(name);
        action.download().forEach(fail -> log.error("{} 下载失败, 原因 {}", fail.fileName(), fail.reason()));
        if (profile.getCompress()) {
            try {
                action.packageTo7z();
            } catch (IOException e) {
                log.error("打包失败");
            }
        }
        if (profile.getConvertPdf()) {
            try {
                action.convertToPdf();
            } catch (IOException e) {
                log.error("转换失败");
            }
        }
    }

    /**
     * 将一组图片转换成 pdf
     *
     * @param path 图片路径
     * @param name pdf 名称
     */
    @ShellMethod(key = "convert", value = "convert hentai to pdf")
    public void convert(@ShellOption(help = "hentai images path") String path,
                        @ShellOption(help = "pdf name") String name) {
        log.info(path);
        try {
            PdfUtils.convertToPdf(Path.of(path), Path.of(profile.getPdfDir(), name + ".pdf"));
        } catch (IOException e) {
            log.error("转换失败");
        }
    }


    /**
     * 设置下载路径
     *
     * @param dir 下载路径
     * @return 更新成功
     */
    @ShellMethod(key = "cd", value = "change directory of download")
    public String setRootDir(@ShellOption(help = "download directory") String dir) {
        profile.setRootDir(dir);
        return "Update Directory Success";
    }

    /**
     * 设置压缩密码
     *
     * @param password 压缩密码
     * @return 更新成功
     */
    @ShellMethod(key = "password", value = "package password")
    public String setPassword(@ShellOption(help = "password") String password) {
        profile.setPassword(password);
        return "Update Password Success";
    }
}
