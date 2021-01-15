package kr.mikuwallets.djyurika400.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({@PropertySource(value = {"classpath:/ddj400.properties"}, encoding = "UTF-8")})
public class AppConfig {
}
