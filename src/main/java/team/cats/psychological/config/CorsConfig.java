package team.cats.psychological.config;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * @Author AnHui_XiaoYang
 * @Email 939209948@qq.com
 * @Date 2021/5/10 17:49
 * @Description
 */
@SpringBootConfiguration
@EnableSwagger2
public class CorsConfig implements WebMvcConfigurer {
    @Resource
    CorsInterceptor corsInterceptor;

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 跨域拦截器需放在最上面
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");

        // 注册Sa-Token的路由拦截器，并排除登录接口或其他可匿名访问的接口地址 (与注解拦截器无关)
        registry.addInterceptor(new SaRouteInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/upload/**","/LoginAdmin","/getSchoolListNoLogin","/loginForAdmin","/register","/upload/avatar","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //添加映射路径
//        registry.addMapping("/**")
//                //是否发送Cookie
//                .allowCredentials(true)
//                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
//                .allowedOriginPatterns("*")
//                //放行哪些请求方式
//                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
//                //.allowedMethods("*") //或者放行全部
//                //放行哪些原始请求头部信息
//                .allowedHeaders("*")
//                //暴露哪些原始请求头部信息
//                .exposedHeaders("*");
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        //本地存放上传文件的真实地址
        String realPathDir = System.getProperty("user.dir")+"/upload/";

        //配置上传文件的映射关系
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+realPathDir);


        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
