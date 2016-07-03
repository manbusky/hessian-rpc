package io.hessian.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manbu on 7/3/16.
 */
public class HessianServiceHolder {

    private final static Logger logger = LoggerFactory.getLogger(HessianServiceHolder.class);

    private static Map<String, ServiceNamespaceDefinition> serviceNamespaceDefinitions;

    static {
        serviceNamespaceDefinitions = new HashMap<>();
    }

    public static void registerService(String namespace, String name, Class<?> cls, Method method) {

        ServiceNamespaceDefinition namespaceDefinition = serviceNamespaceDefinitions.get(namespace);

        if(namespaceDefinition == null) {

            namespaceDefinition = new ServiceNamespaceDefinition();
            namespaceDefinition.setNamespace(namespace);
            namespaceDefinition.setServiceClass(cls);

            namespaceDefinition.registerServiceMapping(new ServiceDefinition(namespace, name, cls, method));

        } else {

            namespaceDefinition.registerServiceMapping(new ServiceDefinition(namespace, name, cls, method));
        }

        serviceNamespaceDefinitions.put(namespace, namespaceDefinition);

        logger.debug("register service: namespace[{}], name[{}], className[{}], method[{}]", namespace, name, cls, method);

    }

    public static ServiceDefinition getServiceDefinition(String namespace, String name) {

        ServiceNamespaceDefinition namespaceDefinition = serviceNamespaceDefinitions.get(namespace);

        if(namespaceDefinition == null) { return null; }

        return namespaceDefinition.getServiceDefinition(name);
    }

}
