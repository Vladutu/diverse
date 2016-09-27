package ro.ucv.ace.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This class is used for Spring configuration and bean declarations.
 *
 * @author Georgian Vladutu
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ro.ucv.ace")
public class RestConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/webjars/");
    }

}