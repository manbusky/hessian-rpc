package io.hessian.rpc.core;

/**
 * Created by manbu on 7/2/16.
 */
public class SerializeException extends RuntimeException {

    public SerializeException(String message, Exception exception) {
        super(message, exception);
    }

}
