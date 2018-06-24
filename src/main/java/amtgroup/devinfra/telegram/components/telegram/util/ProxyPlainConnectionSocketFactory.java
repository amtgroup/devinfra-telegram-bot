package amtgroup.devinfra.telegram.components.telegram.util;

import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Objects;

/**
 * @author Vitaly Ogoltsov
 */
public class ProxyPlainConnectionSocketFactory extends PlainConnectionSocketFactory {

    private final Proxy proxy;

    public ProxyPlainConnectionSocketFactory(Proxy proxy) {
        this.proxy = Objects.requireNonNull(proxy);
    }

    @Override
    public Socket createSocket(HttpContext context) {
        return new Socket(proxy);
    }

}
