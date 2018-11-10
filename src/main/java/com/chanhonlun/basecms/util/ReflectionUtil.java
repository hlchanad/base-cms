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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

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
        else if (Boolean.class.equals(clazz)) {
            fieldBuilder.type(FieldType.RADIO);
            fieldBuilder.options(Arrays.asList(
                    FieldOption.builder()
                            .id(property.getName() + "_" + Boolean.TRUE.toString())
                            .title("Yes")
                            .value(Boolean.TRUE.toString())
                            .build(),
                    FieldOption.builder()
                            .id(property.getName() + "_" + Boolean.FALSE.toString())
                            .title("No")
                            .value(Boolean.FALSE.toString())
                            .build()
            ));
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
     * clone a new instance of fieldMap with Gson
     *
     * @param fieldMap the map used by create/ edit/ detail service
     * @return new fieldMap
     */
    public static Map<String, Field> cloneFieldMap(Map<String, Field> fieldMap) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(fieldMap), new TypeToken<Map<String, Field>>(){}.getType());
    }

    /**
     * clone a new instance of fieldDetailMap with Gson
     *
     * @param fieldDetailMap the map used by create/ edit/ detail service
     * @return new fieldDetailMap
     */
    public static Map<String, Map<Language, Field>> cloneFieldDetailMap(Map<String, Map<Language, Field>> fieldDetailMap) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(fieldDetailMap), new TypeToken<Map<String, Map<Language, Field>>>(){}.getType());
    }

    /**
     * Replace value from {@code pojo} into {@code fieldMap}
     *
     * @param fieldMap map for describing the Fields
     * @param pojo     the pojo
     * @param <Pojo>   extending {@link BasePojo}
     * @param <PojoPK> Primary Key of Pojo, extending {@link Serializable}
     * @return new {@code fieldMap} with pojo's value
     */
    public static <Pojo extends BasePojo<PojoPK>, PojoPK extends Serializable>
    void updateFieldMapWithValues(Map<String, Field> fieldMap, Pojo pojo) {

        ReflectionUtil.getClassFields(pojo.getClass())
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .forEach(property -> {
                    try {
                        Method getter = pojo.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                        String value = Optional.ofNullable(getter.invoke(pojo))
                                .map(Object::toString)
                                .map(string -> string.replaceAll("\\n", "<br/>"))
                                .orElse(null);
                        fieldMap.get(property.getName()).setValue(value);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        logger.error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                    }
                });

    }

    /**
     * Replace value from {@code detail pojo} into {@code fieldDetailMap}
     *
     * @param fieldDetailMap     map for describing the Fields Detail
     * @param pojo               the pojo
     * @param findByRefIdAndLang a {@code BiFunction} for finding a PojoDetail with RefId and Lang
     * @param <Pojo>             extending {@link BasePojo}
     * @param <PojoPK>           Primary Key of Pojo, extending {@link Serializable}
     * @param <PojoDetail>       extending {@link BaseDetailPojo}
     * @param <PojoDetailPK>     Primary Key of DetailPojo, extending {@link Serializable}
     * @return new {@code fieldDetailMap} with detail pojo's value
     */
    public static <
            Pojo extends BasePojo<PojoPK>,
            PojoPK extends Serializable,
            PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
            PojoDetailPK extends Serializable>
    void updateFieldDetailMapWithValues(Map<String, Map<Language, Field>> fieldDetailMap,
                                        Pojo pojo,
                                        BiFunction<PojoPK, Language, PojoDetail> findByRefIdAndLang) {

        Stream.of(Language.values())
                .map(language -> findByRefIdAndLang.apply(pojo.getId(), language))
                .forEach((PojoDetail pojoDetail) -> ReflectionUtil.getClassFields(pojoDetail.getClass())
                        .stream()
                        .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                        .forEach(property -> {
                            try {
                                Method getter = pojoDetail.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                                String value = Optional.ofNullable(getter.invoke(pojoDetail))
                                        .map(Object::toString)
                                        .map(string -> string.replaceAll("\\n", "<br/>"))
                                        .orElse(null);
                                fieldDetailMap.get(property.getName()).get(pojoDetail.getLang()).setValue(value);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                logger.error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                            }
                        }));

    }

    /**
     * Find the List of RequestMapping values for declared from Controller
     *
     * @param requestMappingHandlerMapping autowired from any spring bean
     * @return List of request mapping of controller
     */
    public static List<String> getControllerMappings(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        return Optional.ofNullable(requestMappingHandlerMapping.getApplicationContext())
                .map(applicationContext -> applicationContext.getBeansWithAnnotation(Controller.class))
                .orElse(Collections.emptyMap())
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().getClass().getPackage().getName().startsWith("org.springframework"))
                .map(entry -> entry.getValue().getClass().getAnnotation(RequestMapping.class))
                .map(RequestMapping::value)
                .map(values -> values[0])
                .filter(StringUtils::isNotBlank)
                .sorted()
                .collect(Collectors.toList());
    }

    public static Map<String, Field> getFieldMap(Class<? extends BasePojo> clazz) {
        Map<String, Field> fieldMap = new LinkedHashMap<>();

        ReflectionUtil.getClassFields(clazz)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));

        return fieldMap;
    }

    public static Map<String, Map<Language, Field>> getFieldDetailMap(Class<? extends BaseDetailPojo> clazz) {
        Map<String, Map<Language, Field>> fieldDetailMap = new LinkedHashMap<>();

        ReflectionUtil.getClassFields(clazz)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> {
                    Map<Language, Field> languageFieldMap = new LinkedHashMap<>();

                    for (Language language : Language.values()) {
                        languageFieldMap.put(language, ReflectionUtil.getFieldFromProperty(property, language));
                    }

                    return new ImmutablePair<>(property.getName(), languageFieldMap);
                })
                .forEach(pair -> fieldDetailMap.put(pair.getKey(), pair.getValue()));

        return fieldDetailMap;
    }
}