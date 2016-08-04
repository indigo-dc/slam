package pl.cyfronet.ltos.security;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by km on 04.08.16.
 */
@Configuration
@EnableAutoConfiguration
public class SslConfig {

    @Value("${keystore.file}")
    private String keystoreFile;

    @Value("${keystore.pass}")
    private String keystorePass;

    @Value("${keystore.tomcat.alias}")
    private String keystoreAlias;

    @Value("${server.port}")
    private Integer serverPort;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addConnectorCustomizers((Connector connector) -> {
            connector.setPort(serverPort);
            connector.setSecure(true);
            connector.setScheme("https");
            Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
            proto.setClientAuth("want");
            proto.setSSLEnabled(true);
            proto.setKeystoreFile(keystoreFile);
            proto.setKeystorePass(keystorePass);
            proto.setKeystoreType("JKS");
            proto.setKeyAlias(keystoreAlias);
        });

        return factory;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            System.setProperty("javax.net.ssl.keyStore", keystoreFile);
            System.setProperty("javax.net.ssl.keyStorePassword", keystorePass);
            System.setProperty("javax.net.ssl.keyStoreType", "jks");
            System.setProperty("javax.net.ssl.trustStore", keystoreFile);
            System.setProperty("javax.net.ssl.trustStorePassword", keystorePass);
            System.setProperty("javax.net.ssl.trustStoreType", "jks");

            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
            container.addErrorPages(error401Page, error404Page, error500Page);
        };
    }
}