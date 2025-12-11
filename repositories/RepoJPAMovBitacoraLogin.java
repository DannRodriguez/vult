package mx.ine.sustseycae.repositories;

import mx.ine.sustseycae.dto.db.DTOMovBitacoraLogin;
import mx.ine.sustseycae.dto.db.DTOMovBitacoraLoginId;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RepoJPAMovBitacoraLogin extends JpaRepository<DTOMovBitacoraLogin, DTOMovBitacoraLoginId> {

        @Query(value = """
                        SELECT SUPYCAP.S_MOV_BITACORA_LOGIN.NEXTVAL
                        FROM DUAL""", nativeQuery = true)
        public Integer getNextValSequenceMovBitacoraLogin();

        @Query(value = """
                        SELECT CASE WHEN MVL.minutos+(MVL.horas*60)+(MVL.dias*60*24) >= :tiempoReLogin THEN 1
                                   WHEN MVL.minutos < :tiempoReLogin THEN 0
                               END AS puedeAcceder
                        FROM (SELECT    EXTRACT (MINUTE FROM (:fechaLogin - (MAX(FECHA_LOGUEO)))) AS minutos,
                                        EXTRACT (HOUR FROM (:fechaLogin - (MAX(FECHA_LOGUEO)))) AS horas,
                                        EXTRACT (DAY FROM (:fechaLogin - (MAX(FECHA_LOGUEO)))) AS dias
                           FROM SUPYCAP.MOV_BITACORA_LOGIN
                           WHERE ID_SISTEMA = :idSistema
                             AND USUARIO = :usuario
                             AND FECHA_CIERRE_SESION IS NULL) MVL """, nativeQuery = true)
        public Integer validaLoginHoraAcceso(
                        @Param("idSistema") Integer idSistema,
                        @Param("usuario") String usuario,
                        @Param("fechaLogin") Date fechaLogin,
                        @Param("tiempoReLogin") Integer tiempoReLogin);

        @Query(value = """
                        SELECT NVL(ESTATUS, 0) AS estatus
                        FROM SUPYCAP.MOV_BITACORA_LOGIN
                        WHERE ID_SISTEMA = :idSistema
                                AND USUARIO = :usuario
                                AND ID_TOKEN_ACCESO = :idTokenAcceso
                                AND FECHA_CIERRE_SESION IS NULL """, nativeQuery = true)
        public Integer validarTokenAcceso(Integer idSistema, String usuario, String idTokenAcceso);

        @Modifying(flushAutomatically = true, clearAutomatically = true)
        @Transactional
        @Query(value = """
                        UPDATE SUPYCAP.MOV_BITACORA_LOGIN
                        SET FECHA_CIERRE_SESION = :fechaCierreSesion,
                                ESTATUS = 0
                        WHERE ID_SISTEMA = :idSistema
                                AND USUARIO = :usuario
                                AND ESTATUS = 1 """, nativeQuery = true)
        public void actualizaSesionAEstatusInactivo(
                        @Param("idSistema") Integer idSistema,
                        @Param("usuario") String usuario,
                        @Param("fechaCierreSesion") Date fechaCierreSesion);

        public boolean existsByUsuarioAndIdIdSistemaAndEstatus(String usuario, Integer idSistema, Integer status);

}
