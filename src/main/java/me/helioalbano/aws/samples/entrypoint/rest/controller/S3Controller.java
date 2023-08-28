package me.helioalbano.aws.samples.entrypoint.rest.controller;

import java.net.URI;
import me.helioalbano.aws.samples.entrypoint.rest.dto.CreateBucketRequest;
import me.helioalbano.aws.samples.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/create-bucket")
    public ResponseEntity<Void> createBucket(@RequestBody CreateBucketRequest request) {
        s3Service.createBucket(request.bucketName());
        return ResponseEntity.created(buildResourcePath(request.bucketName())).build();
    }

    private URI buildResourcePath(String bucketName) {
        return URI.create("/s3/" + bucketName);
    }
}
