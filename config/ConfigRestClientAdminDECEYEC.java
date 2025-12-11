package mx.ine.sustseycae.config;

import java.io.Serializable;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClient;

import mx.ine.sustseycae.repositories.RepoJPACEtiquetas;
import mx.ine.sustseycae.util.Constantes;

@Configuration
public class ConfigRestClientAdminDECEYEC implements Serializable {

    private static final Log logger = LogFactory.getLog(ConfigRestClientAdminDECEYEC.class);

    @Autowired
    @Qualifier("repoJPACEtiquetas")
    private transient RepoJPACEtiquetas repoJPACEtiquetas;

    @Bean
    public RestClient restClientAdminDECEYEC() {
        try {
            String host = repoJPACEtiquetas.obtieneEtiquetasRecursivamente(0, 0, 0, 0,
                    Constantes.ID_ETIQUETA_HOST_WS_ADMIN_DECEYEC);
            String header = repoJPACEtiquetas.obtieneEtiquetasRecursivamente(0, 0, 0, 0,
                    Constantes.ID_ETIQUETA_HEADER_WS_ADMIN_DECEYEC);

            if (header != null && !header.isEmpty() && host != null) {
                String[] headerWs = header.split(",");

                return RestClient.builder()
                        .messageConverters(
                                converters -> converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8)))
                        .baseUrl(host).defaultHeader(headerWs[0], headerWs[1]).build();
            }
        } catch (Exception e) {
            logger.error("ERROR ConfigRestClientAdminDECEYEC -restClientAdminDECEYEC: ", e);
        }
        return RestClient.builder()
                .messageConverters(converters -> converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8)))
                .build();

    }

    @Bean
    public RestClient restClientAUS() {
        String host = repoJPACEtiquetas.obtenerUrlWsAus(Constantes.ID_ETIQUETA_WS_CREACION_CTA);
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        JdkClientHttpRequestFactory jdkClientHttpRequestFactory = new JdkClientHttpRequestFactory(httpClient);
        return RestClient.builder().requestFactory(jdkClientHttpRequestFactory).baseUrl(host)
                .messageConverters(
                        converters -> converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8)))
                .build();
    }

}
