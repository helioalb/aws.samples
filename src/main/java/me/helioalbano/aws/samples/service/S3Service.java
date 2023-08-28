package me.helioalbano.aws.samples.service;

import me.helioalbano.aws.samples.exception.CreateBucketException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void createBucket(String bucketName) {
        try {
            s3Client.createBucket(
                CreateBucketRequest
                    .builder()
                    .bucket(bucketName)
                    .build());
        } catch (AwsServiceException | SdkClientException e) {
            throw new CreateBucketException(bucketName, e);
        }
    }
}
