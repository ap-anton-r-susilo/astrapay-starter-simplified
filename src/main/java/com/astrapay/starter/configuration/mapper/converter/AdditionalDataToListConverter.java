package com.astrapay.starter.configuration.mapper.converter;

import com.astrapay.starter.annotations.AdditionalData;
import com.astrapay.starter.configuration.aspect.LogRequestResponse;
import com.astrapay.starter.dto.AdditionalDataDto;
import com.astrapay.starter.enums.LogStarterModule;
import com.astrapay.starter.service.model.DetailAdditionalData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-14
 */
@Component
@Slf4j
public class AdditionalDataToListConverter extends CustomConverter<DetailAdditionalData, Collection<AdditionalDataDto>> {

    @Override
    @LogRequestResponse
    public Collection<AdditionalDataDto> convert(DetailAdditionalData detailAdditionalData, Type<? extends Collection<AdditionalDataDto>> type, MappingContext mappingContext) {
        try {
            return this.convert(detailAdditionalData);
        } catch (IllegalAccessException e) {
            log.error("{} failed convert additional data to list {}", LogStarterModule.OrikaConverterAdditionalData, detailAdditionalData, e);
        }
        return new ArrayList<>();
    }

    public Collection<AdditionalDataDto> convert(DetailAdditionalData detailAdditionalData) throws IllegalAccessException {
        Class<?> clazz = detailAdditionalData.getClass();
        List<AdditionalDataDto> additionalDataDtoList = new ArrayList<>();
        for (Field field : this.getAllFields(clazz)) {
            if (field.isAnnotationPresent(AdditionalData.class)) {
                field.setAccessible(true);
                AdditionalData additionalData = field.getAnnotation(AdditionalData.class);
                if (!Objects.isNull(field.get(detailAdditionalData))) {
                    AdditionalDataDto additionalDataDto = AdditionalDataDto.builder()
                            .key(additionalData.value().isEmpty() ? field.getName() : additionalData.value())
                            .label(additionalData.label())
                            .isCopyable(additionalData.isCopyable())
                            .value(String.valueOf(field.get(detailAdditionalData)))
                            .build();
                    additionalDataDtoList.add(additionalDataDto);
                }
            }
        }
        return additionalDataDtoList;
    }

    List<Field> getAllFields(Class clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }
        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        result.addAll(Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        return result;
    }

}
