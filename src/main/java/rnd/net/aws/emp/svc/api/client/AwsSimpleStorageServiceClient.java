package rnd.net.aws.emp.svc.api.client;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AwsSimpleStorageServiceClient {

    private static final String AWS_S3_BUCKET_NAME_EMP_DIR_SERVICE = "emp-dir-svc-bucket";
    private static S3Client s3Client;
    static {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        s3Client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .build();
    }

    public void uploadFileIntoS3Bucket(MultipartFile empPhoto, String fileName){
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .bucket(AWS_S3_BUCKET_NAME_EMP_DIR_SERVICE)
                .key("emp-" + fileName)
                .build();
        try {
            s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(empPhoto.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
