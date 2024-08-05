package com.reine.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author reine
 * 2024/8/5 18:23
 */
@Slf4j
public class PdfUtils {

    /**
     * 将指定文件夹下的所有图片，转换成一个 pdf 文件
     *
     * @param filePath 图片路径
     * @param pdfPath  生成 pdf 的路径
     * @return 是否成功
     */
    public static boolean convertToPdf(Path filePath, Path pdfPath) throws IOException {
        // 创建 pdf 文件
        if (!pdfPath.toFile().exists()) {
            Files.createFile(pdfPath);
        }
        log.info("开始将图片文件夹转换成 pdf 文件");
        // 创建一个新的PDDocument对象
        try (
                PDDocument document = new PDDocument();
                DirectoryStream<Path> files = Files.newDirectoryStream(filePath)
        ) {
            // 获取图片文件夹中的所有图片文件
            for (Path path : files) {
                // 创建一个新的页面
                PDPage page = new PDPage();
                document.addPage(page);
                // 加载图片
                PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(path.toFile(), document);
                // 将图片添加到页面
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                contentStream.close();
            }
            document.save(pdfPath.toString());
        }
        return true;
    }

}
