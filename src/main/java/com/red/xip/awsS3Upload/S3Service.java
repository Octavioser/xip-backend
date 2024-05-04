package com.red.xip.awsS3Upload;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;


@Service
public class S3Service {

    private final AmazonS3 s3Client;
   
    private final String bucketName;

    public S3Service(@Value("${aws.s3.access-key}") String accessKey, 
            @Value("${aws.s3.secret-key}") String secretKey,
            @Value("${aws.s3.bucket-name}") String bucketName) {
		this.s3Client = AmazonS3ClientBuilder.standard()
		       .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
		       .withRegion(Regions.AP_NORTHEAST_2)
		       .build();
		this.bucketName = bucketName;
	}

    public String uploadBase64Image(String base64Image, String filePath) {
        // Base64 인코딩된 데이터에서 데이터 URI 스키마를 제거하고 순수 데이터만 추출
        String base64Data = base64Image.split(",")[1];
        byte[] imageData = Base64.getDecoder().decode(base64Data);
        
        // 이미지를 S3에 업로드
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData)) {
        	
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageData.length);
            
            s3Client.putObject(bucketName, filePath, inputStream, metadata);
            
            return s3Client.getUrl(bucketName, filePath).toString(); // 업로드된 이미지 URL 반환
        } catch (Exception e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }
    }
    
    // 폴더 삭제
    public void deleteFolder(String folderPath) {
        ObjectListing objectListing = s3Client.listObjects(bucketName, folderPath);
        while (true) {
            for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
                s3Client.deleteObject(bucketName, os.getKey());
            } 

            if (objectListing.isTruncated()) {
                objectListing = s3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }
    
}