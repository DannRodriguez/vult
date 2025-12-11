package mx.ine.sustseycae.security;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SpringSecurityHeadersFilter extends OncePerRequestFilter {

    @Resource(lookup = "java:/util/origenesPermitidosSustSEyCAE")
    private String origenesPermitidosSustSEyCAE;

    private final String filterDenyMode = "DENY";
    private final String filterSameOriginMode = "SAMEORIGIN";
    private final String filterCacheMode = "no-cache";
    private final String filterPragmaMode = "no-cache";
    private final String filterContentTypeMode = "nosniff";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestOrigin = StringEscapeUtils.escapeJava(request.getHeader("Origin"));
        if (requestOrigin != null
                && Arrays.asList(origenesPermitidosSustSEyCAE.split(",")).contains(requestOrigin.trim())) {
            response.setHeader("Access-Control-Allow-Origin", requestOrigin);
        }

        response.setHeader("X-Frame-Options", filterDenyMode);
        response.setHeader("X-Frame-Options", filterSameOriginMode);
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        response.setHeader("Cache-Control", filterCacheMode);
        response.setHeader("Pragma", filterPragmaMode);
        response.setHeader("Expires", "0");
        response.setHeader("X-Content-Type-Options", filterContentTypeMode);

        filterChain.doFilter(request, response);
    }
}