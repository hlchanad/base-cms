package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.response.DetailField;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.FieldOption;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static List<java.lang.reflect.Field> getPojoFields(Class<?> pojoClass) {
        return Arrays.asList(pojoClass.getDeclaredFields());
    }

    public static Field getFieldFromProperty(java.lang.reflect.Field property) {

        return getFieldFromProperty(property, null);
    }

    public static Field getFieldFromProperty(java.lang.reflect.Field property, Language language) {

       Field.FieldBuilder fieldBuilder = Field.builder();

        String languageSuffix = language == null ? "" : CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, language.name());
        String languagePrefix = language == null ? "" : "detail" + languageSuffix + ".";

        // --- id --------------
        fieldBuilder.id(languagePrefix + property.getName());

        // --- title ------------
        String lowerUnderscored = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, property.getName());
        String lowerSpaced      = lowerUnderscored.replaceAll("_", " ");
        fieldBuilder.title(StringUtils.capitalize(lowerSpaced));


        // --- type -------------
        Class<?> clazz = property.getType();
        if (Date.class.equals(clazz)) {
            fieldBuilder.type(FieldType.DATE);
        }
        else if (Short.class.equals(clazz) || Integer.class.equals(clazz) || Long.class.equals(clazz)) {
            fieldBuilder.type(FieldType.NUMBER);
        }
        else if (Float.class.equals(clazz) || Double.class.equals(clazz)) {
            fieldBuilder.type(FieldType.NUMBER);
            fieldBuilder.numberStep(0.1f);
        }
        else if (clazz.isEnum()) {
            @SuppressWarnings("unchecked")
            List<? extends Enum> enums = (List<? extends Enum>) Arrays.asList(clazz.getEnumConstants());

            fieldBuilder.type(enums.size() > 5 ? FieldType.DROPDOWN : FieldType.RADIO);

            List<FieldOption> fieldOptions = enums.stream().map((Enum anEnum) ->
                    FieldOption.builder()
                            .id(property.getName() + "_" + anEnum.name().toLowerCase())
                            .title(StringUtils.capitalize(anEnum.name().replaceAll("_", " ").toLowerCase()))
                            .value(anEnum.name())
                            .build())
                    .collect(Collectors.toList());

            fieldBuilder.options(fieldOptions);
        }
        else {
            fieldBuilder.type(FieldType.TEXT);
        }

        return fieldBuilder.build();
    }

    public static List<DetailField> getDetailFields(Map<String, Map<Language, Field>> fieldDetailMap) {
        List<DetailField> detailFields = new ArrayList<>();

        fieldDetailMap.values().stream()
                .flatMap(languageFieldMap -> languageFieldMap.entrySet().stream())
                .forEach(languageFieldEntry -> {

                    DetailField search = detailFields.stream()
                            .filter(detailField -> detailField.getLanguage().equals(languageFieldEntry.getKey()))
                            .findAny()
                            .orElse(null);

                    if (search == null) {
                        search = DetailField.builder()
                                .language(languageFieldEntry.getKey())
                                .fields(new ArrayList<>())
                                .build();
                        detailFields.add(search);
                    }

                    search.getFields().add(languageFieldEntry.getValue());
                });

        return detailFields;
    }
}