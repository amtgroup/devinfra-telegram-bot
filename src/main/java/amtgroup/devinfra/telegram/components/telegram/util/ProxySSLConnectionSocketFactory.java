package amtgroup.devinfra.telegram.components.telegram.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Objects;

/**
 * @author Vitaly Ogoltsov
 */
public class ProxySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

    private final Proxy proxy;

    public ProxySSLConnectionSocketFactory(SSLContext sslContext, Proxy proxy) {
        super(sslContext);
        this.proxy = Objects.requireNonNull(proxy);
    }

    @Override
    public Socket createSocket(final HttpContext context) {
        return new Socket(proxy);
    }

}
