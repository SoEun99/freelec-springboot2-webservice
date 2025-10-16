//Application : 프로젝트 메인 클래스

package com.soeun.book.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// ┖ 스프링 부트 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
// @SpringBootApplication 위치부터 설정 읽음, 때문에 항상 최상단에 위치

//@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //내장 WAS 사용, 스프링 부트 Jar 파일 실행만 하면 됨
        //외장 WAS 종류와 버전, 설정 일치 필요 없이 언제 어디서나 같은 환경에서 스프링 부트 배포 가능
    }
    @Bean
    CommandLineRunner showEffectiveClientId(ClientRegistrationRepository repo) {
        return args -> {
            var reg = ((InMemoryClientRegistrationRepository) repo)
                    .findByRegistrationId("google");
            if (reg == null) {
                System.out.println(">>> [WARN] google ClientRegistration not found!");
            } else {
                System.out.println(">>> EFFECTIVE GOOGLE CLIENT_ID = " + reg.getClientId());
            }
        };
    }
}
