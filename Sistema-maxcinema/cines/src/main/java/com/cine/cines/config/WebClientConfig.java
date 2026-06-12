package com.cine.cines.config;

@Configuration
public class WebClientConfig {
    @Bean 
    public WebClientConfig.Builder WebClientConfig(){
        return WebClient.Builder();
    }
}
