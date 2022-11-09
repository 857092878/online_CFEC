//package com.rui.online.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @Author 蒲锐
// * @CreateTme 2022/11/6 19:55
// * Version1.0.0
// */
//
//
////跨域设置
//@Configuration
//public class Corsconfig implements WebMvcConfigurer {
//    //当前跨域请求最大有效时长。这里默认1天
//    private static final long MAX_AGE = 24*60*60;
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration ();
//        corsConfiguration.addAllowedOrigin("*");//设置访问源地址
//        corsConfiguration.addAllowedHeader("*");//设置访问源请求头
//        corsConfiguration.addAllowedMethod ("*");//设置访问源请求方法
//        corsConfiguration.setMaxAge(MAX_AGE);
//        return corsConfiguration ;
//    }
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration ( " /**",buildConfig());//对接口配否跨域设西
//        return new CorsFilter(source);
//    }
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        //本应用的所有方法都会去处理跨域请求
////        registry.addMapping("/**")
////                //允许远端访问的域名
////                .allowedOrigins("http://localhost:8000")
////                //允许请求的方法("POST", "GET", "PUT", "OPTIONS", "DELETE")
////                .allowedMethods("*")
////                //允许请求头
////                .allowedHeaders("*");
////    }
//}
