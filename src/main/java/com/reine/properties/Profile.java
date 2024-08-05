package com.reine.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 读取app.yml配置文件配置类
 *
 * @author reine
 * 2024/7/18 16:19
 */
@Data
@ConfigurationProperties(prefix = "download.config")
public class Profile {
    /**
     * 文件后缀
     */
    private String suffix = "jpg";
    /**
     * 是否压缩
     */
    private Boolean compress = false;
    /**
     * 压缩密码,为空即不进行加密，反之亦然
     */
    private String password = "";
    /**
     * 语言
     */
    private String language = "Chinese";
    /**
     * 压缩文件目录
     */
    private String compressDir = "7z";
    /**
     * 下载过程中，如果文件已存在，是否替换原有的文件
     */
    private Boolean replaceFile = false;
    /**
     * 下载过程重试次数
     */
    private Integer retryTime = 5;
    /**
     * 下载根路径
     */
    private String rootDir = ".";
    /**
     * 1:ZIP_STANDARD
     * 2:ZIP_STANDARD_VARIANT_STRONG
     * 3:AES_128
     * 4:AES_192
     * 5:AES_256
     */
    private byte encryptionMethod = 3;
    /***
     *  压缩文件分片大小，单位MB，0表示不分片
     *
     */
    private int compressSplitSize = 0;

}
