package util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author kz
 * @date 2017-12-05
 * jxl读取excel文件内容
 */
public class JxlReadExcelUtil {


    /**
     * 读取xls文件内容
     *
     * @param excelPath 想要读取的文件路径
     * @return 返回文件内容
     */
    public void xls2String(String excelPath) {

        try {
            if (StringUtils.isBlank(excelPath)) {
                return;
            }
            File file = new File(excelPath);
            FileInputStream fis = new FileInputStream(file);

            Workbook rwb = Workbook.getWorkbook(fis);
            //获取excel工作表
            Sheet[] sheet = rwb.getSheets();
            for (int i = 0; i < sheet.length; i++) {
                Sheet rs = rwb.getSheet(i);
                for (int j = 0; j < rs.getRows(); j++) {
                    //获取行
                    Cell[] cells = rs.getRow(j);
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < cells.length; k++) {
                        //获取每行的列
                        sb.append(cells[k].getContents()).append("   ");
                    }
                    //输出每行数据
                    System.out.println(sb);
                }
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
