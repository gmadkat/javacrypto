package mil.osd.avds.decryptor.decryptor;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.Assert;

import com.diffplug.common.base.Errors;


import jdk.nashorn.internal.objects.annotations.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class DefaultingPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    private List<Resource> defaultsResources = new ArrayList<>();

    private List<EncryptablePropertiesPropertySource> defaultsPropertySources = new ArrayList<>();


    /**
     * Creates a new instance of this class, given the location of the default properties file
     *
     * @param defaultsFileLocations The location of the default properties file.  Presumed to be non-null.
     */
    public DefaultingPropertySourcesPlaceholderConfigurer(List<PropertyDefaultFileWrapper> defaultsFileLocations) throws Exception {
        super();
        Assert.notNull(defaultsFileLocations, "Defaults file location cannot be null");

        // Collect the file names as ClassPathResource instances
        List<ClassPathResource> resources = defaultsFileLocations.stream()
                .map(PropertyDefaultFileWrapper::getFilePath)
                .map(ClassPathResource::new)
                .collect(Collectors.toList());


        // Throw an Exception if any of the resources don't exist
        resources.stream()
                .filter(resource -> !resource.exists())
                .findAny()
                .ifPresent(resource -> {
                    throw new RuntimeException("Defaults File does not exist: %s" + resource.getFilename());
                });


        // And if we make it here, add them all (because all must exist in order to get here)
        defaultsResources.addAll(resources);

        List<EncryptablePropertiesPropertySource> propertySources = new ArrayList<>();
        for (PropertyDefaultFileWrapper files : defaultsFileLocations) {
            ResourcePropertySource resourcePropertySource = new ResourcePropertySource(defaultsResources.get(0).getFilename(), defaultsResources.get(0));
            Properties p = new Properties();
            for (Object k : resourcePropertySource.getSource().keySet()) {
                //TODO: Do we want to partition the ENC and non ENC here or do it in EncryptableProperties as I have now?
//                if (resourcePropertySource.getSource().get(k).toString().contains("ENC(")) {
                    p.setProperty(k.toString(), resourcePropertySource.getSource().get(k).toString());
//                }
            }
            EncryptablePropertiesPropertySource encryptablePropertiesPropertySource = new EncryptablePropertiesPropertySource(files.getFilePath(), p, new StringEncryptor());
            propertySources.add(encryptablePropertiesPropertySource);
        }
        defaultsPropertySources.addAll(propertySources);

    }


        /**
         * Set the {@code Environment} that this object runs in.
         * <p>{@code PropertySources} from this environment will be searched when replacing ${...} placeholders.
         *
         * @param environment The environment in question
         */
        @Override
        public void setEnvironment(Environment environment) {
            // Delegate to the superclass, so we actually TRACK the environment
            super.setEnvironment(environment);

            // Get the ordered collection of all property sources known to the application.
            MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();


            defaultsPropertySources
                    .forEach(resource -> propertySources.addLast(resource));

            //TODO: Do we want to partition the ENC and non ENC here or do it in EncryptableProperties as I have now?

            // Wrap all of our defaults files in a property source and add it to the bottom of the collection.
            // Since Spring starts at the top of the collection and works its way down if a match isn't found, this
            // ensures that defaults will get used ONLY if all else fails
            // Note: the following will throw an AvdsGeneralException if any of the property sources could not be added.
//            defaultsResources
//                    .forEach(resource -> propertySources.addLast(
//                            Errors.createRethrowing(error ->
//                                    new RuntimeException("Could not create property source for defaults file due to exception"))
//                                    .get(() -> new ResourcePropertySource(resource.getFilename(), resource))));
       }
}
