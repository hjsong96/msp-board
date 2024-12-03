package kr.msp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public BeanNameViewResolver beanNameViewResolver(){
        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }

    @Bean(name="defaultJsonView")
    public MappingJackson2JsonView mappingJackson2JsonView(){
        return new MappingJackson2JsonView();
    }

    @Bean
    public UrlFilenameViewController urlFilenameViewController(){
        return new UrlFilenameViewController();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:18080")
//                .allowedMethods("GET", "HEAD", "POST", "OPTIONS")
//                .allowedHeaders("*")
//                .exposedHeaders("X-Test-1", "X-Test-2")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }

}
