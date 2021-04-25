package co.com.hexagonal.infrastructure.adapter.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Properties;

@Component
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    @NonNull
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());

        Properties properties = factoryBean.getObject();

        return new PropertiesPropertySource(
                Objects.requireNonNull(resource.getResource().getFilename(), "No existe el archivo"),
                Objects.requireNonNull(properties, "No se pueden leer las properties"));
    }
}
