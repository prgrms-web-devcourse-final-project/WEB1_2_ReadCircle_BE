package org.prgrms.devcourse.readcircle.common.upload.controller;

//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/s3")
//@RequiredArgsConstructor
//public class S3Controller {
//
//    private final S3Service s3Service;
//
//    // 파일 업로드
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileUrl = s3Service.uploadFile(file);
//            return ResponseEntity.ok(fileUrl);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
//        }
//    }
//
//    // 파일 다운로드
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
//        byte[] fileData = s3Service.downloadFile(fileName);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/octet-stream");
//        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//
//        return ResponseEntity.ok().headers(headers).body(fileData);
//    }
//}
