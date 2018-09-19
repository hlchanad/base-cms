package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.response.DetailField;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.FieldOption;
import com.google.common.base.CaseFormat;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<Field> getFields(Map<String, Field> fieldMap) {
        return new ArrayList<>(fieldMap.values());
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

    public static <Pojo extends BasePojo<PojoPK>, PojoPK extends Serializable>
    Map<String, Field> updateFieldMapWithValues(Map<String, Field> fieldMap, Pojo pojo) {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(fieldMap), new TypeToken<Map<String, Field>>(){}.getType());

        ReflectionUtil.getPojoFields(pojo.getClass())
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .forEach(property -> {
                    try {
                        Method getter = pojo.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                        String value = getter.invoke(pojo).toString().replaceAll("\\n", "<br/>");
                        fieldMapClone.get(property.getName()).setValue(value);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        logger.error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                    }
                });

        return fieldMapClone;
    }

    public static <
            Pojo extends BasePojo<PojoPK>,
            PojoPK extends Serializable,
            PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
            PojoDetailPK extends Serializable>
    Map<String, Map<Language, Field>> updateFieldDetailMapWithValues(Map<String,Map<Language,Field>> fieldDetailMap,
                                                                     Pojo pojo,
                                                                     BiFunction<PojoPK, Language, PojoDetail> findByRefIdAndLang) {
        Gson gson = new Gson();

        Map<String, Map<Language, Field>> fieldDetailMapClone =
                gson.fromJson(gson.toJson(fieldDetailMap), new TypeToken<Map<String, Map<Language, Field>>>(){}.getType());

        Stream.of(Language.values())
                .map(language -> findByRefIdAndLang.apply(pojo.getId(), language))
                .forEach((PojoDetail pojoDetail) -> ReflectionUtil.getPojoFields(pojoDetail.getClass())
                        .stream()
                        .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                        .forEach(property -> {
                            try {
                                Method getter = pojoDetail.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                                String value  = getter.invoke(pojoDetail).toString().replaceAll("\\n", "<br/>");
                                fieldDetailMapClone.get(property.getName()).get(pojoDetail.getLang()).setValue(value);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                logger.error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                            }
                        }));

        return fieldDetailMapClone;
    }
}