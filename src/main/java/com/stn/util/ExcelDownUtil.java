package com.stn.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelDownUtil {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${Globals.file.uploadDir}")
    private String uploadDir;

    public void excelDown(String[] headers , List<HashMap> dataList , String fileNm, HttpServletResponse response , HttpServletRequest request) throws Exception {

        // 워크북 생성
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet1");        //엑셀 시트 이름
        Row row = null;
        Cell cell = null;
        int rowNo = 0;

        XSSFCellStyle headStyle = headStyle((XSSFWorkbook) wb);
        // 데이터용 경계 스타일 테두리만 지정  (데이터)
        CellStyle bodyStyle = bodyStyle((XSSFWorkbook) wb);

        try{
            // 헤더 생성
            row = sheet.createRow(rowNo++);
            for(int i = 0; i < headers.length; i++){
                cell = row.createCell(i);
                cell.setCellStyle(headStyle);
                cell.setCellValue(headers[i]);
            }

            for(int i = 0; i < dataList.size(); i++){
                row = sheet.createRow(rowNo++);
                int celCnt = 0;
                HashMap map = dataList.get(i);
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry<String, Object> entry = (HashMap.Entry)it.next();
                    cell = row.createCell(celCnt++);
                    cell.setCellValue(StringUtil.nullToEmptyString((String) entry.getValue()));
                    cell.setCellStyle(bodyStyle);
                }
            }

            // 컨텐츠 타입과 파일명 지정
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileNm, "UTF-8")+".xlsx");
            response.setHeader("Content-Transfer-Encoding", "binary;");


            OutputStream os = response.getOutputStream();


            // 2023-06-02 개인정보 보안 취약점 조치
            // 1. 작성된 엑셀 파일 임의 경로에 저장
            String fileLocation = uploadDir + File.separator + fileNm +".xlsx";
            if (!new File(uploadDir).exists()) {
                new File(uploadDir).mkdirs();
            }

            ByteArrayOutputStream fileOut = new ByteArrayOutputStream(); //임시 데이터 저장을 위한 곳
            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            wb.write(fileOut); //엑셀파일에 한번 작성

            InputStream filein = new ByteArrayInputStream(fileOut.toByteArray());
            POIFSFileSystem filesSystem = new POIFSFileSystem();

            // 2.엑셀파일 암호걸기

            String password = "";
            if (request.getParameter("pswd") != null) {
                OPCPackage opc = OPCPackage.open(filein);
                password = request.getParameter("pswd");
                EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile);
                Encryptor encryptor = encryptionInfo.getEncryptor();
                encryptor.confirmPassword(password);

                opc.save(encryptor.getDataStream(filesSystem));
                filesSystem.writeFilesystem(fileOutputStream);
            }

            wb.write(fileOutputStream);//fileLocation에 작성한 경로에 파일 생성됨.
            wb.close();

            // 3. 위에서 만든 파일 읽어와서 사용자 PC에 출력
            byte[] fileByte = Files.readAllBytes(Paths.get(fileLocation));
            response.getOutputStream().write(fileByte);
            response.getOutputStream().flush();

            //OutputStream os = response.getOutputStream();

            //4.임시 엑셀 파일 삭제
            File deleteTempFile = new File(fileLocation);
            deleteTempFile.delete();

            if(wb != null) wb.close();
            if(os != null) os.close();

        }catch (Exception e){
            log.error("error : ", e);
            e.printStackTrace();
            printMessage(response, "엑셀다운로드 중 일시적인 오류가 발생하였습니다.");
        }
    }

    public XSSFCellStyle headStyle(XSSFWorkbook wb){
        XSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        // https://www.linkedin.com/pulse/color-palette-poi-indexedcolors-aniruddha-duttachowdhury
        headStyle.setFillForegroundColor(IndexedColors.TAN.index);
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        return headStyle;
    }

    public CellStyle bodyStyle(XSSFWorkbook wb){
        CellStyle bodyStyle = wb.createCellStyle();
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        return bodyStyle;
    }


    //정상적인 다운로드가 안될 경우 메시지 처리
    public static void printMessage(HttpServletResponse response, String msg) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter printwriter = response.getWriter();
        printwriter.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        printwriter.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        printwriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        printwriter.println("<script type=\"text/javascript\">");
        printwriter.println("alert('" + msg + "');");
        printwriter.println("history.back(-1);");
        printwriter.println("</script>");
        printwriter.println("</html>");
        printwriter.flush();
        printwriter.close();
    }
}
