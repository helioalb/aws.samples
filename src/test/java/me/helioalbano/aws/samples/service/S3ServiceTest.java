package me.helioalbano.aws.samples.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import me.helioalbano.aws.samples.exception.CreateBucketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

class S3ServiceTest {

    private S3Client s3Client;

    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        s3Client = mock(S3Client.class);
        s3Service = new S3Service(s3Client);
    }

    @Test
    void givenAValidBucketNameWhenCreateBucketThenAValidRequestIsSent() {
        // Arrange
        when(s3Client.createBucket((CreateBucketRequest) any()))
            .thenReturn(CreateBucketResponse.builder().build());
        // Act
        s3Service.createBucket("bucket-name");
        // Assert
        var expectedRequest = CreateBucketRequest.builder().bucket("bucket-name").build();
        verify(s3Client).createBucket(expectedRequest);
    }

    @Test
    void givenABucketNameInvalidToSDKWhenCreateBucketThenThrowsSdkClientException() {
        // Arrange
        when(s3Client.createBucket((CreateBucketRequest) any()))
            .thenThrow(SdkClientException.class);
        // Act
        var exception = assertThrows(CreateBucketException.class, () -> s3Service.createBucket("bucket-name"));
        // Assert
        assertEquals("Error creating bucket bucket-name", exception.getMessage());
    }

    @Test
    void givenABucketNameInvalidToAWSServiceWhenCreateBucketThenThrowsAwsServiceException() {
        // Arrange
        when(s3Client.createBucket((CreateBucketRequest) any()))
            .thenThrow(AwsServiceException.class);
        // Act
        var exception = assertThrows(CreateBucketException.class, () -> s3Service.createBucket("bucket-name"));
        // Assert
        assertEquals("Error creating bucket bucket-name", exception.getMessage());
    }
}
