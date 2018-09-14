package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.response.FieldOption;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static List<Field> getPojoFields(Class<?> pojoClass) {
        return Arrays.asList(pojoClass.getDeclaredFields());
    }

    public static com.chanhonlun.basecms.response.Field getFieldFromProperty(Field property) {

        return getFieldFromProperty(property, null);
    }

    public static com.chanhonlun.basecms.response.Field getFieldFromProperty(Field property, Language language) {

        com.chanhonlun.basecms.response.Field.FieldBuilder fieldBuilder = com.chanhonlun.basecms.response.Field.builder();

        String languageSuffix = language != null ? "_" + language.name() : "";

        // --- id --------------
        fieldBuilder.id(property.getName() + languageSuffix);

        // --- title ------------
        String lowerUnderscored = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, property.getName());
        String lowerSpaced      = lowerUnderscored.replaceAll("_", " ");
        fieldBuilder.title(StringUtils.capitalize(lowerSpaced) + languageSuffix);


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
}