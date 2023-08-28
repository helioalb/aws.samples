package me.helioalbano.aws.samples.entrypoint.rest.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.helioalbano.aws.samples.exception.CreateBucketException;
import me.helioalbano.aws.samples.service.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(S3Controller.class)
class S3ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3Service s3Service;

    @Test
    void givenAValidBucketNameWhenCreateBucketThenReturnCreated() throws Exception {
        var json = """
            {
                "bucketName": "my-bucket"
            }
            """;

        doNothing().when(s3Service).createBucket("my-bucket");

        var request = MockMvcRequestBuilders
            .post("/s3/create-bucket")
            .contentType("application/json")
            .content(json);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/s3/my-bucket"));
    }

    @Test
    void givenAInvalidBucketNameWhenCreateBucketThenReturnCreated() throws Exception {
        var json = """
            {
                "bucketName": "invalid-bucket-name"
            }
            """;

        doThrow(new CreateBucketException("invalid-bucket-name", new RuntimeException()))
            .when(s3Service).createBucket("invalid-bucket-name");

        var request = MockMvcRequestBuilders
            .post("/s3/create-bucket")
            .contentType("application/json")
            .content(json);

        mockMvc.perform(request)
            .andExpect(status().isBadGateway())
            .andExpect(jsonPath("$.message").value("Error creating bucket invalid-bucket-name"));
    }
}
