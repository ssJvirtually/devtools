package dev.ssjvirtually.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class UrlParserUtil {

    private UrlParserUtil() {
    }
    
    public static Map<String, String> parse(String urlString) {
        if (urlString == null || urlString.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }
        
        try {
            URI uri = new URI(urlString.trim());
            Map<String, String> map = new LinkedHashMap<>();
            map.put("Scheme", uri.getScheme() != null ? uri.getScheme() : "");
            map.put("Host", uri.getHost() != null ? uri.getHost() : "");
            map.put("Port", uri.getPort() == -1 ? "" : String.valueOf(uri.getPort()));
            map.put("Path", uri.getPath() != null ? uri.getPath() : "");
            map.put("Query", uri.getQuery() != null ? uri.getQuery() : "");
            map.put("Fragment", uri.getFragment() != null ? uri.getFragment() : "");
            return map;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format: " + e.getMessage(), e);
        }
    }
}
