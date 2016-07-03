package io.hessian.rpc.client;

import com.caucho.hessian.client.HessianProxyFactory;
import io.hessian.rpc.core.Constant;
import io.hessian.rpc.core.CoreService;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;

/**
 * Created by manbu on 7/2/16.
 */
public class ClientBuilder {

    public static ObjProxyBuilder proxyBuilder(String baseUrl){
        return new ObjProxyBuilder(baseUrl);
    }

    public static class ObjProxyBuilder {
        private String url;
        private String namespace;

        public ObjProxyBuilder(String baseUrl) {

            String url;

            if(StringUtils.endsWith(baseUrl, "/")) {
                url = baseUrl + Constant.CORE_INTERNAL_SERVICE_PATH;
            } else {
                url = baseUrl + "/" + Constant.CORE_INTERNAL_SERVICE_PATH;
            }

            this.url = url;
        }

        public ObjProxyBuilder of(String namespace) {

            this.namespace = namespace;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T build(Class<T> clazz) throws ProxyException {

            HessianProxyFactory factory = new HessianProxyFactory();

            try {

                CoreService coreService = (CoreService) factory.create(CoreService.class, url);

                if(StringUtils.isEmpty(namespace)) {
                    namespace = clazz.getName();
                }

                Object proxy = Enhancer.create(clazz, new ProxyExecutor(coreService, namespace));

                return (T) proxy;

            } catch (MalformedURLException e) {

                throw new ProxyException("proxy error", e);
            }
        }
    }
}
