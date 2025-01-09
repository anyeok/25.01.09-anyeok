package com.example.anyeok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @CreateDate, @LastModifiedDate를 사용하기 위해 필요
public class AnyeokApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnyeokApplication.class, args);
	}

}
