package seoultech.gdsc.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import seoultech.gdsc.web.interceptor.AuthInterceptor;
import seoultech.gdsc.web.interceptor.LogInterceptor;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final List<String> notAuthUrlList =
            Arrays.asList("/api/user/login","/api/user","/api/user");
    private static final List<String> AuthUrlList =
            Arrays.asList("/api/user/logout","/api/user","/api/board/comment","/api/board");

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns(AuthUrlList);
    }
}
