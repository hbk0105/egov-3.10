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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
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
            map.put("filePath",filePath.replace("/", File.separator).replace("\\", File.separator));
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
    public void fileDownload(String orgName, File f, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!f.exists()) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 원본 파일 크기 확인
        log.debug("원본 파일 크기: " + f.length());

        // 브라우저 구분 (User-Agent)
        String userAgent = req.getHeader("User-Agent");

        // 파일 이름 인코딩 (UTF-8 → ISO-8859-1 변환)
        String dwnFileName = URLEncoder.encode(orgName.trim(), "UTF-8").replaceAll("\\+", "%20");

        // Content-Disposition 설정 (파일 다운로드 방식)
        if (userAgent != null && (userAgent.contains("MSIE") || userAgent.contains("Trident"))) { // IE 브라우저
            res.setHeader("Content-Disposition", "attachment; filename=\"" + dwnFileName + "\";");
        } else { // 크롬, 파이어폭스, 사파리 등
            res.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + dwnFileName);
        }

        // 파일의 MIME 타입 감지 (Apache Tika 활용)
        String mimeType = new Tika().detect(f);
        res.setContentType(mimeType);

        // Content-Length 설정
        res.setContentLengthLong(f.length());

        // 파일을 직접 읽어서 전송 (OutputStream 사용)
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(f));
             OutputStream outputStream = res.getOutputStream()) {

            byte[] buffer = new byte[8192]; // 8KB 버퍼
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            log.debug("파일 다운로드 중 오류 발생: " + e.getMessage());
        }
    }

    public void getImage(String imagePath , HttpServletResponse res) throws IOException{

        File imgFile = new File(imagePath);

        // 파일 존재 여부 확인
        if (!imgFile.exists()) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 이미지 MIME 타입 감지
        String mimeType = new Tika().detect(imgFile);
        res.setContentType(mimeType);

        // 파일을 직접 읽어서 OutputStream으로 응답 전송
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(imgFile));
             OutputStream outputStream = res.getOutputStream()) {

            byte[] buffer = new byte[8192]; // 8KB 버퍼
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }

    }

    /**
     * 이미지 출력
     * @param f
     * @return ResponseEntity<byte[]>
     * @throws IOException
     */
    // TODO: 이미지 출력
    public ResponseEntity<byte[]> getImage(File f) throws IOException {

        try (InputStream inputImage = new FileInputStream(f);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputImage.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            HttpHeaders headers = new HttpHeaders();
            String mimeType = new Tika().detect(f);
            headers.setContentType(MediaType.valueOf(mimeType));

            headers.add("Content-Type", Files.probeContentType(f.toPath()));

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            // 파일이 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            // 기타 IO 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        /*
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
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);*/
    }

    /**
     * 첨부파일 삭제
     * @param fullPathAndNm [ 첨부파일 경로+파일명 ]
     * @return int [ 1 : 삭제 성공 , -99 : 삭제 실패 , 0 : 파일이 존재하지 않음 ]
     * @throws IOException
     */
    public int fileDelete(String fullPathAndNm){
        int result =  -99;

        log.debug("### fullPathAndNm --> " +fullPathAndNm);
        // 파일 객체 생성
        File file = new File(fullPathAndNm);

        log.debug(file.exists());
        log.debug(file.delete());

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