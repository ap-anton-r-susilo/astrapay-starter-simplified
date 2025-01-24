package com.astrapay.starter.configuration.mapper;

import ma.glasnost.orika.MapperFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class MapperFactoryConfigurationTest {

    @Test
    void configure(){
        List<MapperConfiguration> mapperConfigurations = new ArrayList<>();
        MapperFactory mapperFactoryMock = Mockito.mock(MapperFactory.class);
        MapperConfiguration mapperConfigurationMock = Mockito.mock(MapperConfiguration.class);
        mapperConfigurations.add(mapperConfigurationMock);
        MapperFactoryConfiguration mapperFactoryConfiguration = new MapperFactoryConfiguration(mapperFactoryMock, mapperConfigurations);
        Mockito.verify(mapperConfigurationMock, Mockito.times(1)).configure(mapperFactoryMock);
    }
}
