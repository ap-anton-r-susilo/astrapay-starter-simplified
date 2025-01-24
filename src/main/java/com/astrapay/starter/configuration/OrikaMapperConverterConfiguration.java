package com.astrapay.starter.configuration;

import com.astrapay.starter.configuration.mapper.converter.AdditionalDataToListConverter;
import com.astrapay.starter.configuration.mapper.converter.BigDecimalToFormattedIdrStringConverter;
import com.astrapay.starter.enums.StarterOrikaConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-15
 */
@Component
public class OrikaMapperConverterConfiguration implements OrikaMapperFactoryConfigurer {
    
    @Autowired
    BigDecimalToFormattedIdrStringConverter bigDecimalToFormattedIdrStringConverter;

    @Override
    public void configure(MapperFactory orikaMapperFactory) {
        ConverterFactory converterFactory = orikaMapperFactory.getConverterFactory();
        converterFactory.registerConverter(StarterOrikaConverter.AdditionalDataToListConverter.getName(), new AdditionalDataToListConverter());
        converterFactory.registerConverter(StarterOrikaConverter.BigDecimalToFormattedIdrStringConverter.getName(), bigDecimalToFormattedIdrStringConverter);
    }
}
