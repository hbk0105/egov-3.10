package com.web.common.cntrl;

import com.stn.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gogo")
public class FileController {


    @Autowired
    private FileUtil fileUtil;

/*

    @GetMapping(value ="/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        try {

            Path path = Paths.get("C:\\upload\\files\\").resolve(fileName);
            String contentType = Files.probeContentType(path);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(Files.readAllBytes(path), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/
    // https://okky.kr/questions/680429
    @GetMapping("/sample")
    public void downloadImageV2( HttpServletResponse response ) throws IOException {
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("C:\\upload\\files\\1.png");
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            out.flush();
            out.close();
        }
    }

    /**
     * 이미지 조회
     * @param req
     * @return ResponseEntity<byte[]>
     * @throws Exception
     */
    // TODO: 이미지 조회
    @RequestMapping("/img.do")
    public ResponseEntity<byte[]> getImage(HttpServletRequest req) throws Exception {
        File f = new File("C:\\upload\\files\\test.png");
/*
        if (!f.exists()) {
            // 파일이 존재하지 않는 경우 404 오류 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("### f :: " + f.getAbsolutePath());

        return  fileUtil.getImage(new File(f.getAbsolutePath()));*/

        HttpHeaders headers = new HttpHeaders();
        InputStream in = new FileInputStream(f.getAbsolutePath());
        byte[] media = IOUtils.toByteArray(in);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/pictures", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(HttpServletRequest request) throws IOException {

        String check_id = request.getParameter("id");
        Map<String, Object> param = new HashMap<String, Object>();

        String res = "C:\\upload\\files\\test.png";
        InputStream in = new FileInputStream(res);

        return IOUtils.toByteArray(in);
    }
}