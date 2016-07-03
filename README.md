# hessian-rpc

It is a library for you to build multiple RPC service **EASY** and **QUICKLY** in Java. It's based on hessian.

It contains three modules,`hessian-rpc-core`、`hessian-rpc-server` and `hessian-rpc-client`；

## Quick Start

### Step 1

For server.

Add denpendency in your maven `pom.xml` file.

```
<dependency>
    <groupId>io.hessian.rpc</groupId>
    <artifactId>hessian-rpc-core</artifactId>
    <version>0.9.0</version>
</dependency>
<dependency>
    <groupId>io.hessian.rpc</groupId>
    <artifactId>hessian-rpc-server</artifactId>
    <version>0.9.0</version>
</dependency>
```

Add `component-scan` config in your `applicationContext.xml` file.

```
<context:component-scan base-package="your.company.service" scope-resolver="io.hessian.rpc.server.ScanAnnotationScopeMetadataResolver">
        <context:include-filter type="annotation" expression="io.hessian.rpc.core.annotation.HServiceNamespace" />
</context:component-scan>
```

> This `component-scan` can auto-proxy your service, 
> `io.hessian.rpc.server.ScanAnnotationScopeMetadataResolver` is fixed.

Add your Service Java.

```
package your.company;

public interface EquipmentService {

    Equipment find(Long id);
}
```

```
package your.company.service;

import your.company.EquipmentService;

import io.hessian.rpc.core.annotation.HServiceMapping;
import io.hessian.rpc.core.annotation.HServiceNamespace;
import org.springframework.beans.factory.annotation.Autowired;

@HServiceNamespace
public class EquipmentServiceImpl implements EquipmentService {

    @HServiceMapping
    public Equipment find(Integer id) {
        
//        Equipment entity = dao.findById(id);
        
        return entity;
    }
}
```

### Step 2

For client.

```
String url = "http://localhost:8080/contextPath";

EquipmentService service = null;

try {
    service = ClientBuilder.proxyBuilder(url).build(EquipmentService.class);
    System.out.println(service.find(100));
} catch (ProxyException e) {
    //log with exception
}

```


## Next TO DO

- Auto proxy with client Java interface
- Performance Test


## It's Alpha version, just for share, DO NOT USE in your production env

