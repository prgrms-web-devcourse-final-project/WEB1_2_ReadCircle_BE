package org.prgrms.devcourse.readcircle.common.upload.service;

//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

//@Service
//@RequiredArgsConstructor
//public class S3Service {
//    private final S3Client s3Client;
//    private final String bucketName = "your-s3-bucket-name";
//
//    // 파일 업로드
//    public String uploadFile(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//
//        // PutObject 요청 생성 및 실행
//        s3Client.putObject(PutObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(fileName)
//                        .contentType(file.getContentType())
//                        .build(),
//                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
//
//        // 업로드된 파일 URL 반환
//        return getFileUrl(fileName);
//    }
//
//    // 파일 다운로드
//    public byte[] downloadFile(String fileName) {
//        // GetObject 요청 생성 및 실행
//        GetObjectResponse response = s3Client.getObject(GetObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(fileName)
//                        .build(),
//                software.amazon.awssdk.core.sync.ResponseTransformer.toBytes());
//        return response.asByteArray();
//    }
//
//    // 파일 URL 생성
//    public String getFileUrl(String fileName) {
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3Client.region().id(), fileName);
//    }
//}
