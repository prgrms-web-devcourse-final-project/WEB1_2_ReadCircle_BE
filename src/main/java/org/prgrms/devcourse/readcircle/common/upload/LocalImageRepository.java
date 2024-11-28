package org.prgrms.devcourse.readcircle.common.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
@Primary
public class LocalImageRepository implements ImageRepository {

    @Value("${file.local.upload.path}")
    private String uploadPath;

    @Override
    public String upload(MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        final String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        final Path savePath = Paths.get(uploadPath, uniqueFilename);

        try {
            Files.createDirectories(savePath.getParent());
            file.transferTo(savePath);
        } catch (Exception e) {
            throw new IllegalArgumentException("[Error] 이미지 업로드에 실패했습니다.");
        }

        return uniqueFilename;  // 고유한 파일명 반환
    }
}
