package com.stn.util;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 *
 * Description : 파일 관련 클래스
 *
 * Modification Information
 * 수정일			 수정자						수정내용
 * -----------	-----------------------------  -------
 * 2021. 3.  22.    MICHAEL						최초작성
 *
 */
@Component
@PropertySource("classpath:/egovframework/egovProps/globals.properties")
public class FileUtil {

    private Logger log = LogManager.getLogger(this.getClass());
    @Value("${Globals.file.uploadDir}")
    private String uploadDir;

    public List<Map<String,Object>> fileUpload(MultipartHttpServletRequest request,String folder) throws Exception {
        List<Map<String,Object>> fileList = new ArrayList<>();
        //MultipartHttpServletRequest mRequest = request;
        Iterator<String> iter = request.getFileNames();
        while (iter.hasNext()) {
            MultipartFile item = request.getFile(iter.next());
            String fieldName = item.getName();
            if(item.getSize() == 0) continue;
            String path = folder == null || "".equals(folder) ? "file"  : folder;
            fileList.add(fileUpload(item,path));
        }
        return fileList;
    }

    /**
     * 파일 업로드
     * @param file
     * @return Files
     * @throws Exception
     */
    // TODO: 파일 업로드
    public Map<String,Object> fileUpload(MultipartFile file,String path) throws Exception {
        Map<String,Object> map = new HashMap<>();
        try {

            String origFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = FilenameUtils.getExtension(origFilename).toLowerCase();

            // 동일한 파일 업로드 안됨
            String fileName =  RandomStringUtils.randomAlphanumeric(32)  + "." + fileExtension;

            if(fileName.contains("..")){
                throw new Exception("invalid path : "+fileName );
            }
            String savePath = uploadDir + "\\"+path;
            if(path.equalsIgnoreCase("smartEditor") && !isPermisionFileMimeType(file.getContentType())){
                throw new Exception("invalid img : "+fileName );
            }

            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if(new File(savePath).exists() == false){ new File(savePath).mkdirs(); }

            String filePath = savePath + "\\" + fileName;
            File destinationFile =  new File(filePath);
            file.transferTo(destinationFile);

            map.put("origFilename",origFilename);
            map.put("fileName",fileName);
            map.put("filePath",filePath);
            map.put("size",file.getSize());
            map.put("savePath",savePath);
            map.put("mimeType",new Tika().detect(destinationFile));

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private boolean isPermisionFileMimeType( String contentType ) throws Exception {

        String originalFileExtensionBack = null;
        if(contentType.contains("image/jpeg")){
            originalFileExtensionBack = ".jpg";
            return true;
        }
        else if(contentType.contains("image/png")){
            originalFileExtensionBack = ".png";
            return true;
        }
        else if(contentType.contains("image/gif")){
            originalFileExtensionBack = ".gif";
            return true;
        }
        else if(contentType.contains("image/bmp")){
            originalFileExtensionBack = ".bmp";
            return true;
        } else {
            return false;
        }

    }

    /**
     * 파일 다운로드
     * @param orgName
     * @param f
     * @param req
     * @return ResponseEntity<byte[]>
     * @throws Exception
     */
    // TODO: 파일 다운로드
    public ResponseEntity<byte[]> fileDownload(String orgName , File f , HttpServletRequest req) throws Exception{

        InputStream inputImage = new FileInputStream(f.getAbsolutePath());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int l = inputImage.read(buffer);
        while (l >= 0) {
            outputStream.write(buffer, 0, l);
            l = inputImage.read(buffer);
        }

        HttpHeaders header = new HttpHeaders();
        String strClient = req.getHeader("User-Agent");

        String dwnFileName = new String(orgName.trim().getBytes("EUC-KR"),"8859_1");   //수정사항

        // https://m.blog.naver.com/PostView.nhn?blogId=kimgungoo&logNo=90045379130&proxyReferer=https:%2F%2Fwww.google.com%2F
        if(strClient.indexOf("MSIE 5.5")>-1) {
            header.add("Content-Type", "doesn/matter;");
            header.add("Content-Disposition", "filename=" + dwnFileName + ";");
        }
        else {
            header.add("Content-Type", "application/octet-stream;");
            header.add("Content-Disposition", "attachment; filename=" + dwnFileName + ";");
        }

        // 파일의 mediaType를 알아내기 위한 api
        //String mediaType = new Tika().detect(new File(f.getAbsolutePath()));
        header.setContentType(MediaType.valueOf(new Tika().detect(new File(f.getAbsolutePath()))));
        header.add("Content-Type","text/html; charset=EUC_KR");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return new ResponseEntity<byte[]>(outputStream.toByteArray(), header, HttpStatus.OK);
    }

    /**
     * 이미지 출력
     * @param f
     * @return ResponseEntity<byte[]>
     * @throws IOException
     */
    // TODO: 이미지 출력
    public ResponseEntity<byte[]> getImage(File f) throws IOException {
        InputStream inputImage = new FileInputStream(f.getAbsolutePath());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int l = inputImage.read(buffer);
        while(l >= 0) {
            outputStream.write(buffer, 0, l);
            l = inputImage.read(buffer);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", String.valueOf(MediaType.valueOf(new Tika().detect(new File(f.getAbsolutePath())))));
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 첨부파일 삭제
     * @param fullPathAndNm [ 첨부파일 경로+파일명 ]
     * @return int [ 1 : 삭제 성공 , -99 : 삭제 실패 , 0 : 파일이 존재하지 않음 ]
     * @throws IOException
     */
    public int fileDelete(String fullPathAndNm){
        int result =  -99;

        System.out.println("### fullPathAndNm --> " +fullPathAndNm);
        // 파일 객체 생성
        File file = new File(fullPathAndNm);

        System.out.println(file.exists());
        System.out.println(file.delete());

        // 파일이 존재하는지 확인 후 삭제
        if (file.exists()) {
            if (file.delete()) {
                result = 1;
                log.info("DELETE SUCCESS");
            }else{
                log.error("DELETE FAIL");
            }
        } else {
            log.error("NOT FILE");
            result =  1;
        }
        return result;

    }

}