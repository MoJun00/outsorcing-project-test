package com.sparta.outsorcingproject.aws;

import com.sparta.outsorcingproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3FileController  {

    private final S3FileService s3FileService;

    @PostMapping("/user/profile")
    public ResponseEntity<String> uploadProfile(@RequestParam("image") MultipartFile file,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = s3FileService.uploadProfile(file,userDetails);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/s3/delete")
    public ResponseEntity<String> s3delete(@RequestParam String imageAddress){
        String response = s3FileService.s3delete(imageAddress);

        return ResponseEntity.ok(response);
    }
}
