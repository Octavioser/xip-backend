package com.red.xip.awsSesEmail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Component
public class AwsSesService {

  @Autowired
  public AmazonSimpleEmailService amazonSimpleEmailService;

  public void sendEmail( String content, String sender, String receiver, String subject) {

    try {
      SendEmailRequest sendEmailRequest = new SendEmailRequest()
          .withDestination(new Destination().withToAddresses(receiver))
          .withMessage(new Message()
              .withBody(
                  new Body().withHtml(new Content().withCharset("UTF-8").withData(content)))
              .withSubject(new Content().withCharset("UTF-8").withData(subject)))
          .withSource(sender);
      amazonSimpleEmailService.sendEmail(sendEmailRequest);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
