package model.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * TODO 批量导入工具
 * @author dongqi
 * @date 2019/5/16 20:03
 * @params
 * @return
 */
public class ImportUtils {

    /**
     * TODO 获取excel的workbook对象
     * @author dongqi
     * @date 2019/5/16 20:03
     * @params [file]
     * @return org.apache.poi.ss.usermodel.Workbook
     */
    public static Workbook getWorkbook(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if(!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$"))
            throw new IOException("文件格式不正确");

        boolean isExcel2003 = true;

        if (fileName.matches("^.+\\.(?i)(xlsx)$"))
            isExcel2003 = false;

        //创建HSSFWorkbook对象
        Workbook workbook;
        if (isExcel2003) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        return workbook;
    }


}
