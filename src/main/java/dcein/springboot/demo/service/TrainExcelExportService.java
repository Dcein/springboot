package dcein.springboot.demo.service;

import dcein.springboot.demo.constants.ServiceConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: DingCong
 * @Description: Excel导出, apache开源组件POI3.9动态导出EXCEL文档
 * @@Date: Created in 16:52 2018/7/17
 */
@Slf4j
@Service
public class TrainExcelExportService<T> {


    public void exportExcel(Collection<T> dataSource, OutputStream out) throws InvocationTargetException {
        exportExcel("POI EXPORT EXCEL FUNCTION 1", null, dataSource, out, ServiceConstants.DATA_PARTNER);
    }

    public void exportExcel(String[] headers, Collection<T> dataSource, OutputStream out) throws InvocationTargetException {
        exportExcel("POI EXPORT EXCEL FUNCTION 2", headers, dataSource, out, ServiceConstants.DATA_PARTNER);
    }

    public void exportExcel(String[] headers, Collection<T> dataSource, OutputStream out, String pattern) throws InvocationTargetException {
        exportExcel("POI EXPORT EXCEL FUNCTION 3", headers, dataSource, out, pattern);
    }

    /**
     * excel导出核心方法
     * @param excelFileName 表格标题名
     * @param headers       表格属性列名数组
     * @param dataSource    需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out           与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern       如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
     * @throws InvocationTargetException
     */
    public void exportExcel(String excelFileName, String[] headers, Collection<T> dataSource, OutputStream out, String pattern) throws InvocationTargetException {

        //step1.新建一个工作空间
        HSSFWorkbook workbook = new HSSFWorkbook();

        //step2.开始创建Excel表格
        HSSFSheet sheet = workbook.createSheet(excelFileName);

        //step3.设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(ServiceConstants.DEFAULT_COLUMN_WIDE);

        //step4.设置创建表格头的一个样式
        HSSFCellStyle ExcelHeaderStyle = workbook.createCellStyle();

        //step5.设置表格头具体样式
        ExcelHeaderStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        ExcelHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        ExcelHeaderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        ExcelHeaderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        ExcelHeaderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        ExcelHeaderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        ExcelHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //step6.设置表格头字体
        HSSFFont ExcelHeaderFontStyle = workbook.createFont();
        ExcelHeaderFontStyle.setColor(HSSFColor.VIOLET.index);
        ExcelHeaderFontStyle.setFontHeightInPoints(ServiceConstants.FONT_HEIGHT_IN_POINTS);
        ExcelHeaderFontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        //step7.把设置的表格头字体应用到当前的表格头样式中
        ExcelHeaderStyle.setFont(ExcelHeaderFontStyle);

        //step8.创建一个数据行样式
        HSSFCellStyle ExcelDataStyle = workbook.createCellStyle();

        //step8.开始设置数据行样式
        ExcelDataStyle.setFillForegroundColor(HSSFColor.TAN.index);
        ExcelDataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        ExcelDataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        ExcelDataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        ExcelDataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        ExcelDataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        ExcelDataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        ExcelDataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //step9.设置数据行字体样式
        HSSFFont ExcelDataFontStyle = workbook.createFont();
        ExcelDataFontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        //step10.把数据行字体样式应用到当前的数据行样式中
        ExcelDataStyle.setFont(ExcelDataFontStyle);

        //step11.创建一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        //step12.定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));

        //step13.设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));//todo

        //step14.设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("test"); //todo

        //step15.产生表格标题行
        HSSFRow row = sheet.createRow(ServiceConstants.INDEX_ZERO);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(ExcelHeaderStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //step16.遍历数据集合,开始向每行填充数据
        Iterator<T> it = dataSource.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();

            //step17.利用反射机制，来获取对象中属性的值，调用getObject()
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                //得到本行数据的第i列
                Field field = fields[i];
                String fieldName = field.getName();
                if (fieldName.equals(ServiceConstants.SERIAL_VERSION_UID)) {
                    break;
                }
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(ExcelDataStyle);
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});

                    //step18.判断值的类型后进行强制类型转换
                    String textValue = null;

                    //step19.判断数据类型
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    } else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    }else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "TRUE";
                        if (!bValue) {
                            textValue = "FALSE";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);

                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (int) (35.7 * 80));

                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        //其它数据类型都当作字符串简单处理
                        textValue = (String) value;
                    }

                    //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            //是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("[The system print data number is : " + index + "]");
        try {
            workbook.write(out);
            log.info(ServiceConstants.DISPLAY_SUCCESS_TEXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
