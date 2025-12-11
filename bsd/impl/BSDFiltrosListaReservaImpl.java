package mx.ine.sustseycae.bsd.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASFiltrosListaReserva;
import mx.ine.sustseycae.bsd.BSDFiltrosListaReserva;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Service("bsdFiltros")
@Scope("prototype")
public class BSDFiltrosListaReservaImpl implements BSDFiltrosListaReserva {

    @Autowired
    private ASFiltrosListaReserva asFiltros;

    @Override
    public ModelGenericResponse obtenerMunicipios(Integer idEstado, Integer idDistrito, Integer idCorte) {
        return asFiltros.obtenerMunicipios(idEstado, idDistrito, idCorte);
    }

    @Override
    public ModelGenericResponse obtenerLocalidadesPorMunicipio(Integer idEstado, Integer idMunicipio, Integer idDistrito,
            Integer idCorte, Integer idProceso, Integer idDetalle, Integer idParticipacion) {
        return asFiltros.obtenerLocalidadesPorMunicipio(idEstado, idMunicipio, idDistrito, idCorte, idProceso,
                idDetalle, idParticipacion);
    }

    @Override
    public ModelGenericResponse obtenerSedes(Integer idProceso, Integer idDetalle, Integer idParticipacion) {
        return asFiltros.obtenerSedes(idProceso, idDetalle, idParticipacion);
    }

    @Override
    public ModelGenericResponse obtenerSecciones(Integer idProceso, Integer idDetalle, Integer idParticipacion) {
        return asFiltros.obtenerSecciones(idProceso, idDetalle, idParticipacion);
    }

}
