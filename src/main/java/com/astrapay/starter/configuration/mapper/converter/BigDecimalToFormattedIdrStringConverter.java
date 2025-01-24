package com.astrapay.starter.configuration.mapper.converter;

import com.astrapay.starter.common.AmountFormatter;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BigDecimalToFormattedIdrStringConverter extends CustomConverter<BigDecimal, String> {
 
    @Autowired
    private AmountFormatter amountFormatter;
    
    @Override
    public String convert(BigDecimal value, Type<? extends String> type, MappingContext mappingContext) {
        return amountFormatter.getIdrString(value);
    }
}
