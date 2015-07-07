package io.curly.commons.web.hateoas;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility class to resolve cascading mapping when using Zuul
 * this will enables to resolve the context path where the resource is mapped to zuul on ZullRoutes
 * prefixing the current path to the resource
 *
 * @author Joao Pedro Evangelista
 */
public class ZuulAwareMappingResolver {

    private static final String X_FORWARDED_HOST = "X-Forwarded-Host";

    private final String zuulRoute;

    private final String contextResourcePath;

    private final HttpServletRequest request;

    /**
     * Constructs a new resolver
     *
     * @param byPassRequest       the current request used by the HATEOAS to construct links
     * @param zuulRoute           the Zuul Route path, can it be the prefix for all Zuul routes such as '/api' or with
     *                            current resource context path such as '/api/resources'
     * @param contextResourcePath the resource path on lower tree e.g. '/resources'
     */
    public ZuulAwareMappingResolver(HttpServletRequest byPassRequest, String zuulRoute, String contextResourcePath) {
        this.request = byPassRequest;
        this.zuulRoute = zuulRoute;
        this.contextResourcePath = contextResourcePath;
    }

    /**
     * Constructs a new resolver
     *
     * @param byPassRequest the current request used by the HATEOAS to construct links
     * @param zuulRoute     the Zuul Route path, can it be the prefix for all Zuul routes such as '/api' or with
     *                      current resource context path such as '/api/resources'
     */
    public ZuulAwareMappingResolver(HttpServletRequest byPassRequest, String zuulRoute) {
        this(byPassRequest, zuulRoute, null);
    }

    /**
     * Slashs the fragments with when declared, they do not contains it already
     *
     * @param builder   current StringBuilder
     * @param fragments the fragments
     * @return slashed paths
     */
    private static StringBuilder resolveFragmentation(StringBuilder builder, String[] fragments) {
        for (String fragment : fragments) {
            if (fragment.charAt(0) != '/') {
                builder.append("/").append(fragment);
            } else {
                builder.append(fragment);
            }
        }
        return builder;
    }

    /**
     * Resolve the current host and fragments appending them and reconstructing the URL based on the X-Forwarded-Host
     * header value usually enabled by default on Zuul and Hateoas aware
     *
     * @param hostUri   the microservice host url, can be get by using load balancing or discovery client
     * @param fragments the rest of url such as ids
     * @return the reconstructed URI ready to make a request or compose a Link
     */
    public String resolve(String hostUri, String... fragments) {
        StringBuilder builder = new StringBuilder(hostUri);
        builder.append(zuulRoute);
        if (contextResourcePath != null) {
            builder.append(contextResourcePath);
        }
        if (fragments != null) {
            resolveFragmentation(builder, fragments);
        }
        return reconstructURI(extractHeader(), builder.toString());
    }

    public String resolve(URI hostUri, String... fragments) {
        return this.resolve(hostUri.toString(), fragments);
    }

    /**
     * <a href="https://github.com/spring-cloud-samples/customers-stores/blob/master/rest-microservices-customers/src/main/java/example/customers/integration/StoreIntegration.java#L89">Github Spring Cloud Sample implementation<a/>
     *
     * @param host the current request host
     * @param href the #resolve builder returns
     * @return reconstructed URI based on the current request host and port comparing them with the service host and port
     */
    public String reconstructURI(String host, String href) {
        URI original;
        try {
            original = new URI(href);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Cannot create URI from: " + href);
        }
        int port = 80;
        if ("https".equals(original.getScheme())) {
            port = 443;
        }
        if (host.contains(":")) {
            String[] pair = host.split(":");
            host = pair[0];
            port = Integer.valueOf(pair[1]);
        }
        if (host.equals(original.getHost()) && port == original.getPort()) {
            return href;
        }
        String scheme = original.getScheme();
        if (scheme == null) {
            scheme = port == 443 ? "https" : "http";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://");
        if (StringUtils.hasText(original.getRawUserInfo())) {
            sb.append(original.getRawUserInfo()).append("@");
        }
        sb.append(host);
        if (port >= 0) {
            sb.append(":").append(port);
        }
        sb.append(original.getPath());
        if (StringUtils.hasText(original.getRawQuery())) {
            sb.append("?").append(original.getRawQuery());
        }
        if (StringUtils.hasText(original.getRawFragment())) {
            sb.append("#").append(original.getRawFragment());
        }

        return sb.toString();
    }

    private String extractHeader() {
        String header = request.getHeader(X_FORWARDED_HOST);
        String contextPath = request.getContextPath();
        java.lang.System.out.println(contextPath);
        System.out.println(header);
        return header;
    }
}
