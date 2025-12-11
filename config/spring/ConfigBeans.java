package mx.ine.sustseycae.config.spring;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;

import mx.ine.sustseycae.dto.DTOCorreoServicio;

@Configuration
public class ConfigBeans {

    @Autowired
    private Environment environment;

    private final JndiTemplate jndiTemplate;

    public ConfigBeans() {
        this.jndiTemplate = new JndiTemplate();
    }

    @Bean
    public String pathGlusterSistDECEYEC() throws NamingException {
        String path_GlusterFotos = environment.getProperty("spring.path.glusterSistDECEYEC");
        return jndiTemplate.lookup(path_GlusterFotos, String.class);
    }

    @Bean
    public String hostWSIntegrantesSesiones() throws NamingException {
        String path_GlusterFotos = environment.getProperty("spring.path.hostWSIntegrantesSesiones");
        return jndiTemplate.lookup(path_GlusterFotos, String.class);
    }

    @Bean
    public DTOCorreoServicio cuentaCorreo() throws NamingException {
        String appCorreoCuenta = environment.getProperty("application.correo.cuenta");
        String appCorreoPass = environment.getProperty("application.correo.password");

        String correoCuenta = jndiTemplate.lookup(appCorreoCuenta == null ? "" : appCorreoCuenta, String.class);
        String correoPass = jndiTemplate.lookup(appCorreoPass == null ? "" : appCorreoPass, String.class);

        DTOCorreoServicio dtoCorreoServicio = new DTOCorreoServicio();
        dtoCorreoServicio.setCuentaDeEnvio(correoCuenta);
        dtoCorreoServicio.setUsernameFrom(correoCuenta);
        dtoCorreoServicio.setPasswordFrom(correoPass);
        return dtoCorreoServicio;
    }

}
