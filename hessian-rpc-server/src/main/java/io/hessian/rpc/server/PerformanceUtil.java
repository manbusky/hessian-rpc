package io.hessian.rpc.server;

import io.hessian.rpc.core.SerializeUtils;
import io.hessian.rpc.core.ServiceInvokeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by manbu on 7/3/16.
 */
public class PerformanceUtil {

    public static byte[] invoke(Object bean, Method method, byte[][] parameterData) {

        int parameterCount = parameterData.length;

        if(method.getParameterCount() != parameterData.length) {

            throw new ServiceInvokeException("parameter is not match.");
        }

        Parameter[] parameters = method.getParameters();

        Object[] parameter = new Object[parameterCount];

        for (int i = 0; i < parameterCount; i++) {

            Class<?> cls = parameters[i].getType();

            Object object = SerializeUtils.decode(parameterData[i], cls);

            parameter[i] = object;
        }

        try {

            Object rtv = method.invoke(bean, parameter);

            return SerializeUtils.encode(rtv);

        } catch (IllegalAccessException | InvocationTargetException e) {

            throw new ServiceInvokeException("service invoke error.", e);
        }

    }

}
