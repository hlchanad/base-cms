package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.response.vo.DetailField;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.FieldOption;
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

    /**
     * With the given any class, get the list of properties with Java reflection methods
     *
     * @param clazz any class
     * @return list of reflected {@code java.lang.reflect.Field}
     */
    public static List<java.lang.reflect.Field> getClassFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    /**
     * Pre-set some type (property of {@code Field} with given {@code java.lang.reflect.Field}
     *
     * @param property {@code java.lang.reflect.Field} from the {@link #getClassFields}
     * @return my {@code Field} class for Thymeleaf usage
     */
    public static Field getFieldFromProperty(java.lang.reflect.Field property) {

        return getFieldFromProperty(property, null);
    }

    /**
     * Pre-set some type (property of {@code Field} with given {@code java.lang.reflect.Field} + language
     * only for Detail Pojo
     *
     * @param property {@code java.lang.reflect.Field} from the {@link #getClassFields}
     * @param language the Language of the Pojo
     * @return my {@code Field} class for Thymeleaf usage
     */
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

    /**
     * change from the map which is easier for developer to a list which is easier for Thymeleaf
     *
     * @param fieldMap property that a PageService should have
     * @return a list of {@code Field} for Thymeleaf usage
     */
    public static List<Field> getFields(Map<String, Field> fieldMap) {
        return new ArrayList<>(fieldMap.values());
    }

    /**
     * change from the map which is easier for developer to a list which is easier for Thymeleaf
     *
     * @param fieldDetailMap property that a PageService should have
     * @return a list of {@code DetailField} for Thymeleaf usage
     */
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

    /**
     * Replace value from {@code pojo} into {@code fieldMap}
     *
     * @param fieldMap map for describing the Fields
     * @param pojo the pojo
     * @param <Pojo> extending {@link BasePojo}
     * @param <PojoPK> Primary Key of Pojo, extending {@link Serializable}
     * @return new {@code fieldMap} with pojo's value
     */
    public static <Pojo extends BasePojo<PojoPK>, PojoPK extends Serializable>
    Map<String, Field> updateFieldMapWithValues(Map<String, Field> fieldMap, Pojo pojo) {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(fieldMap), new TypeToken<Map<String, Field>>(){}.getType());

        ReflectionUtil.getClassFields(pojo.getClass())
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .forEach(property -> {
                    try {
                        Method getter = pojo.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                        Object object = getter.invoke(pojo);
                        String value = object == null ? null : object.toString().replaceAll("\\n", "<br/>");
                        fieldMapClone.get(property.getName()).setValue(value);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        logger.error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                    }
                });

        return fieldMapClone;
    }

    /**
     * Replace value from {@code detail pojo} into {@code fieldDetailMap}
     *
     * @param fieldDetailMap map for describing the Fields Detail
     * @param pojo the pojo
     * @param findByRefIdAndLang a {@code BiFunction} for finding a PojoDetail with RefId and Lang
     * @param <Pojo> extending {@link BasePojo}
     * @param <PojoPK> Primary Key of Pojo, extending {@link Serializable}
     * @param <PojoDetail> extending {@link BaseDetailPojo}
     * @param <PojoDetailPK> Primary Key of DetailPojo, extending {@link Serializable}
     * @return new {@code fieldDetailMap} with detail pojo's value
     */
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
                .forEach((PojoDetail pojoDetail) -> ReflectionUtil.getClassFields(pojoDetail.getClass())
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