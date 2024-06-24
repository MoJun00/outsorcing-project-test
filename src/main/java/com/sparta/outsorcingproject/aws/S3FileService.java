package com.sparta.outsorcingproject.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.UserRepository;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {
    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadProfile(MultipartFile file, UserDetailsImpl userDetails) {
        String originalFilename=file.getOriginalFilename(); // 원본 파일 명
        //String extention = fileName.substring(fileName.lastIndexOf(".")); //확장자 명
        //String fileUrl= "https://" + bucket + "/test" +fileName;
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; // 변경된 파일 명 (같은 이름의 파일 방지)
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        String profileURL;
        try {
            amazonS3Client.putObject(bucket,s3FileName,file.getInputStream(),metadata); // 파일 업로드

            profileURL = amazonS3Client.getUrl(bucket, s3FileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("업로드 중 오류가 발생했습니다.");
        }
        User user = userDetails.getUser();

        if(user.getProfileUrl() != null)
            s3delete(user.getProfileUrl());
        //이미 프로필 사진이 있다면 이 전에 있던 사진 삭제

        user.updateProfileUrl(profileURL);
        userRepository.save(user);

        return "프로필 업로드 완료";
    }

    public String s3delete(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
            log.error("key : " + key);
            return "삭제 완료";
        }catch (Exception e){
            throw new IllegalArgumentException("이미지 삭제 도중 오류가 발생했습니다.");
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new IllegalArgumentException("URL 변환 실패");
        }
    }
}
