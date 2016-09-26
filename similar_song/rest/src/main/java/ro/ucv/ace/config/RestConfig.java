package ro.ucv.ace.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ro.ucv.ace.model.impl.AudioFeatures;
import ro.ucv.ace.model.impl.Song;
import ro.ucv.ace.model.impl.SongDetails;

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

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(Song.class, AudioFeatures.class, SongDetails.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public Jaxb2RootElementHttpMessageConverter jaxbMessageConverter() {
        return new Jaxb2RootElementHttpMessageConverter();
    }
}