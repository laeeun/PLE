package com.springmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration       // 설정 클래스임을 나타냄
@EnableScheduling
@EnableAsync
public class AppConfig {

}