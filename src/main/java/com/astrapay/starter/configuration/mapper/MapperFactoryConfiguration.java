package com.astrapay.starter.configuration.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MapperFactoryConfiguration
 *
 * @author Arthur Purnama
 */
@Component
public class MapperFactoryConfiguration {
    public MapperFactoryConfiguration(MapperFactory mapperFactory, List<MapperConfiguration> mapperConfigurations) {
        mapperConfigurations.forEach(mapperConfiguration -> mapperConfiguration.configure(mapperFactory));
    }
}
