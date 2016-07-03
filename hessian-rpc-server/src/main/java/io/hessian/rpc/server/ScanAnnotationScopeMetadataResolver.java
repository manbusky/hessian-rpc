package io.hessian.rpc.server;

import io.hessian.rpc.core.annotation.HServiceMapping;
import io.hessian.rpc.core.annotation.HServiceNamespace;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScopeMetadata;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/3/16.
 */
public class ScanAnnotationScopeMetadataResolver extends AnnotationScopeMetadataResolver {

    private final static Logger logger = LoggerFactory.getLogger(ScanAnnotationScopeMetadataResolver.class);

    @Override
    public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {

        ScopeMetadata scopeMetadata = super.resolveScopeMetadata(definition);

        String className = definition.getBeanClassName();

        try {

            Class<?> cls = Class.forName(className);

            HServiceNamespace namespaceAnnotation = cls.getAnnotation(HServiceNamespace.class);

            String namespace = namespaceAnnotation.value();

            if(StringUtils.isEmpty(namespace)) {
                namespace = className;
            }

            Method[] methods = cls.getDeclaredMethods();

            for (Method method: methods) {

                HServiceMapping mappingAnnotation = method.getAnnotation(HServiceMapping.class);

                if(mappingAnnotation != null) {

                    String name = mappingAnnotation.value();

                    if(StringUtils.isEmpty(name)) {
                        name = method.getName();
                    }

                    HessianServiceHolder.registerService(namespace, name, cls, method);

                } else {

                    HessianServiceHolder.registerService(namespace, method.getName(), cls, method);
                }
            }

        } catch (ClassNotFoundException e) {
            logger.error("service class: '{}' not found!", className);
            logger.error("service class load error.", e);
        }

        return scopeMetadata;

    }

}
