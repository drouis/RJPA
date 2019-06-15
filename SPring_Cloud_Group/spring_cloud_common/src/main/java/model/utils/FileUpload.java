package model.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片上传工具
 */
public class FileUpload {


    /**
     * \图片批量上传
     * @param files
     * @param realpath
     * @param module
     * @return
     */
    public static List<String> writeUploadFile(MultipartFile[] files, String realpath, String module) {

        List<String> stringList = new LinkedList<>();

        for (MultipartFile file : files){

            String filename = file.getOriginalFilename();
            File fileDir = new File(realpath);
            if (!fileDir.exists())
                fileDir.mkdirs();

            String extname = FilenameUtils.getExtension(filename);
            String allowImgFormat = "git,jpg,jpeg,png";
            if (!allowImgFormat.contains(extname.toLowerCase()))
                throw new NumberFormatException("NOT_IMAGE");

            filename = module + Math.abs(file.getOriginalFilename().hashCode()) + RandomUtils.createRandomString(4) + "." + extname;

            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;

            try {
                inputStream = file.getInputStream();
                fileOutputStream = new FileOutputStream(realpath + "/" + filename);
                IOUtils.copy(inputStream, fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(fileOutputStream);
            }
            stringList.add(filename);
        }
        return stringList;

    }

}
