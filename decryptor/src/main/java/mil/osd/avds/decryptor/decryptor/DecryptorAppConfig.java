package mil.osd.avds.decryptor.decryptor;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DecryptorAppConfig {

    @Bean
    public static DefaultingPropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer (
            List<PropertyDefaultFileWrapper> propertyDefaultFileWrappers)  throws Exception {
        return new DefaultingPropertySourcesPlaceholderConfigurer(propertyDefaultFileWrappers);
    }

    @Bean
    public static PropertyDefaultFileWrapper avdsProperties() {
        return new PropertyDefaultFileWrapper("decryptor.properties");
    }
}
