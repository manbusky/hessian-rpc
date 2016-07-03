package io.hessian.rpc.server;

import io.hessian.rpc.core.CoreService;
import io.hessian.rpc.core.ServiceInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/1/16.
 */
public class HessianCoreService implements CoreService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(HessianCoreService.class);

    @Override
    public byte[] call(String namespace, String name, byte[][] parameterData) {

        ServiceDefinition serviceDefinition
                = HessianServiceHolder.getServiceDefinition(namespace, name);

        if(serviceDefinition != null) {

            Class<?> cls = serviceDefinition.getServiceClass();

            Method method = serviceDefinition.getMethod();

            Object bean = applicationContext.getBean(cls);

            return PerformanceUtil.invoke(bean, method, parameterData);

        } else {

            throw new ServiceInvokeException("service namespace:["+namespace+"] name:["+name+"] not found!");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
