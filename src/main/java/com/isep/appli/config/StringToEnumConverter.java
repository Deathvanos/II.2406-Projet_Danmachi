package com.isep.appli.config;

import com.isep.appli.models.enums.ItemCategory;
import org.springframework.core.convert.converter.Converter;


public class StringToEnumConverter implements Converter<String, ItemCategory> {
    @Override
    public ItemCategory convert(String source) {
        try {
            return ItemCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
