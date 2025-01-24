package com.astrapay.starter.configuration.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * ParamNameProcessor
 *
 * @author  Fawwaazrahman Arandhana W
 * @author  Astrapay
 * @version 1.12
 * @since   2021-09-01
 */
public class ParamNameProcessor extends ServletModelAttributeMethodProcessor {

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    public ParamNameProcessor(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        super(false);
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    /**
     * Check if ParamName annotation is declared on the field
     *
     * @param parameter
     * @return {@link Boolean} is annotation declared on the field
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return !BeanUtils.isSimpleProperty(parameter.getParameterType())
                && Arrays.stream(parameter.getParameterType().getDeclaredFields())
                .anyMatch(field -> field.getAnnotation(ParamName.class) != null);
    }

    /**
     * Bind request param to field
     *
     * @param nativeWebRequest incoming request
     * @param binder web binder
     */
    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest nativeWebRequest) {
        Object target = Objects.requireNonNull(binder.getTarget());
        Map<String, String> paramMappings = this.getParamMappings(target.getClass()); // get request param to field mapping
        ParamNameDataBinder paramNameDataBinder = new ParamNameDataBinder(target, binder.getObjectName(), paramMappings); // set mapping binder for the object
        Objects.requireNonNull(requestMappingHandlerAdapter.getWebBindingInitializer()).initBinder(paramNameDataBinder, nativeWebRequest);
        super.bindRequestParameters(paramNameDataBinder, nativeWebRequest);
    }

    /**
     * Get param mappings.
     *
     * @param targetClass
     * @return {@link Map<String, String>}
     */
    private Map<String, String> getParamMappings(Class<?> targetClass) {
        // get all declared fields from the target class
        Field[] fields = targetClass.getDeclaredFields();
        Map<String, String> paramMappings = new HashMap<>();
        for (Field field : fields) {
            ParamName paramName = field.getAnnotation(ParamName.class);
            if (paramName != null && !paramName.value().isEmpty()) {
                paramMappings.put(paramName.value(), field.getName()); // set field mapping from annotation param and field name
            }
        }
        return paramMappings;
    }
}
