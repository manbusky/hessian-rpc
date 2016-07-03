package io.hessian.rpc.client;

import io.hessian.rpc.core.CoreService;
import io.hessian.rpc.core.SerializeUtils;
import io.hessian.rpc.core.annotation.HServiceMapping;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/3/16.
 */
public class ProxyExecutor implements MethodInterceptor {

    private CoreService coreService;

    private String namespace;

    public ProxyExecutor(CoreService coreService, String namespace) {
        this.coreService = coreService;
        this.namespace = namespace;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] parameters, MethodProxy methodProxy) throws Throwable {

        int length = parameters.length;

        byte[][] parameterData = new byte[length][];

        for (int i = 0; i < length; i++) {
            parameterData[i] = SerializeUtils.encode(parameters[i]);
        }

        String name = "";

        HServiceMapping mapping = method.getAnnotation(HServiceMapping.class);
        if(mapping != null) {
            name = mapping.value();
        }

        if(StringUtils.isEmpty(name)) {
            name = method.getName();
        }

        byte[] rtv = coreService.call(namespace, name, parameterData);

        return SerializeUtils.decode(rtv, method.getReturnType());

    }
}
