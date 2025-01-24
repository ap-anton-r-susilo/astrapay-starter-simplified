package com.astrapay.starter.configuration.mapper.converter;

import com.astrapay.starter.dto.AdditionalDataDto;
import com.astrapay.starter.service.model.SourceChildObjectAdditionalData;
import com.astrapay.starter.service.model.SourceObjectAdditionalData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-15
 */
@ExtendWith(SpringExtension.class)
public class AdditionalDataToListConverterTest {

    @InjectMocks
    private AdditionalDataToListConverter additionalDataToListConverter;

    @Test
    public void TestConvert() throws IllegalAccessException {
        SourceObjectAdditionalData sourceObjectAdditionalData = SourceObjectAdditionalData.builder()
                .name("Budi")
                .amount("10000")
                .id("0")
                .build();

        List<AdditionalDataDto> expectedAdditionalDataList = new ArrayList<>();
        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("0")
                .label("ID")
                .key("id")
                .isCopyable(false)
                .build());

        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("10000")
                .label("Harga")
                .key("amount")
                .isCopyable(false)
                .build());

        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("Budi")
                .label("Nama")
                .key("name")
                .isCopyable(true)
                .build());

        Collection<AdditionalDataDto> additionalDataDtoList = additionalDataToListConverter.convert(sourceObjectAdditionalData, null, null);
        Assertions.assertEquals(expectedAdditionalDataList, additionalDataDtoList);
    }

    @Test
    public void TestConvert_withInheritance() {
        SourceChildObjectAdditionalData sourceChildObjectAdditionalData = SourceChildObjectAdditionalData.builder()
                .id("0")
                .name("Budi")
                .amount("10000")
                .address("surabaya")
                .build();

        List<AdditionalDataDto> expectedAdditionalDataList = new ArrayList<>();
        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("0")
                .label("ID")
                .key("id")
                .isCopyable(false)
                .build());

        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("10000")
                .label("Harga")
                .key("amount")
                .isCopyable(false)
                .build());

        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("Budi")
                .label("Nama")
                .key("name")
                .isCopyable(true)
                .build());

        expectedAdditionalDataList.add(AdditionalDataDto.builder()
                .value("surabaya")
                .label("Alamat")
                .key("address")
                .isCopyable(false)
                .build());

        Collection<AdditionalDataDto> additionalDataDtoList = additionalDataToListConverter.convert(sourceChildObjectAdditionalData, null, null);
        Assertions.assertEquals(expectedAdditionalDataList, additionalDataDtoList);
    }

}
