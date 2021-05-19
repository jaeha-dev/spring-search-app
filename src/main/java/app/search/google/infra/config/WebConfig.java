package app.search.google.infra.config;

import app.search.google.infra.utility.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(new StringToEnumConverter.SortOptionConverter());
        formatterRegistry.addConverter(new StringToEnumConverter.UserSearchOptionConverter());
        formatterRegistry.addConverter(new StringToEnumConverter.KeywordSearchOptionConverter());
    }
}