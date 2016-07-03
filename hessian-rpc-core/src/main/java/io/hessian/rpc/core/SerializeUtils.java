package io.hessian.rpc.core;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by manbu on 7/2/16.
 */
public class SerializeUtils {

    private static ConcurrentMap<String, RuntimeSchema> runtimeSchemaMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static byte[] encode(Object object) {

        RuntimeSchema schema = getRuntimeSchema(object.getClass());

        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        return ProtobufIOUtil.toByteArray(object, schema,  buffer);
    }

    @SuppressWarnings("unchecked")
    public static <T> T decode(byte[] data, Class<T> clazz) {

        RuntimeSchema<T> schema = getRuntimeSchema(clazz);

        try {
            T m = newInstance(clazz);

            ProtobufIOUtil.mergeFrom(data, m, schema);

            return m;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {

            throw new SerializeException("encode error.", e);

        }
    }

    @SuppressWarnings("unchecked")
    private static RuntimeSchema getRuntimeSchema(Class<?> clazz) {

        String name = clazz.getName();

        if(! runtimeSchemaMap.containsKey(name)) {

            runtimeSchemaMap.put(name, RuntimeSchema.createFrom(clazz));
        }

        return runtimeSchemaMap.get(name);
    }

    @SuppressWarnings("unchecked")
    private static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        if(Number.class.isAssignableFrom(clazz)) {

            Method method = clazz.getMethod("valueOf", String.class);

            if(method != null) {

                return (T) method.invoke(null, "0");
            }
        }

        return clazz.newInstance();
    }

}
