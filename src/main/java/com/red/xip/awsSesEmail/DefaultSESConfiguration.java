package com.red.xip.awsSesEmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class DefaultSESConfiguration {

	@Value("${aws.ses.access-key}")
    private String accessKey;
 
    @Value("${aws.ses.secret-key}")
    private String secretKey;
    
    public AWSStaticCredentialsProvider awsCredentials() {
        BasicAWSCredentials credentials =
            new BasicAWSCredentials(accessKey, secretKey);
        return new AWSStaticCredentialsProvider(credentials);
      }

  @Bean
  public AmazonSimpleEmailService getAmazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(awsCredentials())
        .withRegion(Regions.AP_NORTHEAST_1).build();
  }
}
