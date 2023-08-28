package me.helioalbano.aws.samples.exception;

public class CreateBucketException extends RuntimeException {
    public CreateBucketException(String bucketName, Throwable cause) {
        super("Error creating bucket " + bucketName, cause);
    }
}
