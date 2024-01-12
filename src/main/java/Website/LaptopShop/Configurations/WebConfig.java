package Website.LaptopShop.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("/resources/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/static/js/");
        registry.addResourceHandler("/Frontend/img/**").addResourceLocations("/resources/Frontend/images/");
        registry.addResourceHandler("/Frontend/css/**").addResourceLocations("/resources/Frontend/css/");
        registry.addResourceHandler("/Frontend/js/**").addResourceLocations("/resources/Frontend/js/");
    }
}
