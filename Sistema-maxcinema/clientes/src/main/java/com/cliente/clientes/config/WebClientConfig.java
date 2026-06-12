package com.cliente.clientes.config;


@Configuration
public class WebClientConfig {
    @Bean 
    public WebClientConfig.Builder WebClientConfig(){
        return WebClient.Builder();
    }
}
