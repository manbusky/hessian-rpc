package io.hessian.rpc.client;

/**
 * Created by manbu on 7/3/16.
 */
public class ProxyException extends Exception {

    public ProxyException(String message, Exception e) {
        super(message, e);
    }

}
