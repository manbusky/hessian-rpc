package io.hessian.rpc;

import io.hessian.rpc.core.SerializeUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SerializeTest extends TestCase {

    public SerializeTest(String testName ) {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( SerializeTest.class );
    }

    public void testApp() {

        String hello = "china";

        byte[] data = SerializeUtils.encode(hello);

        String string = SerializeUtils.decode(data, String.class);

        assertEquals(hello, string);
    }
}
