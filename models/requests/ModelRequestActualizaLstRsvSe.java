package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ModelRequestActualizaLstRsvSe implements Serializable {

    private static final long serialVersionUID = 2885769816799333011L;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 5, fraction = 0)
    private Integer idProceso;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 7, fraction = 0)
    private Integer idDetalle;

    @PositiveOrZero
    @Digits(integer = 3, fraction = 0)
    private Integer idParticipacion;

    @PositiveOrZero
    @Digits(integer = 6, fraction = 0)
    private Integer idAspirante;

    private Integer estatus;

    private String ip;

    private String user;

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public Integer getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(Integer idAspirante) {
        this.idAspirante = idAspirante;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
