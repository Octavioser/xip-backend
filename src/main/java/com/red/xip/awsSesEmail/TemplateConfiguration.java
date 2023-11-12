package com.red.xip.awsSesEmail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class TemplateConfiguration { // HTML 템플릿으로 메일을 전송하기 위해 템플릿 관련 설정
	
    @Bean
    public TemplateEngine htmlTemplateEngine(SpringResourceTemplateResolver springResourceTemplateResolver) {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(springResourceTemplateResolver);
 
        return templateEngine;
    }
}
