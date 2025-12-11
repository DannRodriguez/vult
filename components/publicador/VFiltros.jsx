import React from 'react';
import { Row, Select, Col } from 'antd';
import * as etiquetas from '../../utils/publicador/etiquetas';

const { Option } = Select;
const VFiltros = ({ tipoReporte, listaReportes, reporteSeleccionado,
    nivelSeleccionado, seleccionaReporte, seleccionaNivelReporte }) => {
    return (
        <div className='publicador-filtros-container'>
            <div className='pfc-combo-reportes'>
                <div className='div_selecc_tipoRepo'>
                    <span>{etiquetas.SELECCIONA}</span>
                    <span>{tipoReporte === etiquetas.FOLDER_LISTADOS ? etiquetas.EL_LISTADO : etiquetas.LA_CEDULA}</span>
                </div>
                <Select showArrow={true}
                    placeholder={etiquetas.SELECCIONA}
                    value={reporteSeleccionado}
                    onSelect={seleccionaReporte}
                >
                    {
                        listaReportes
                        && Object.values(listaReportes).map((reporte) => (
                            <Option key={reporte.key}
                                value={reporte.key}>
                                {reporte.label}
                            </Option>
                        ))
                    }
                </Select>
            </div>
            {
                tipoReporte === etiquetas.FOLDER_CEDULAS && reporteSeleccionado ?
                    <div className='pfc-combo-niveles'>
                        <div className='div_selecc_niveles'>
                            <span>{etiquetas.NIVEL}</span>
                        </div>
                        <Select showArrow={true}
                            placeholder={etiquetas.SELECCIONE_NIVEL}
                            value={nivelSeleccionado}
                            onSelect={seleccionaNivelReporte}
                        >
                            {
                                listaReportes
                                && listaReportes[reporteSeleccionado]
                                && listaReportes[reporteSeleccionado].niveles
                                && Object.values(listaReportes[reporteSeleccionado].niveles).map((nivel) => (
                                    <Option key={nivel.key}
                                        value={nivel.key}>
                                        {nivel.label}
                                    </Option>
                                ))
                            }
                        </Select>
                    </div>
                    : null
            }
        </div>
    );
}
export default VFiltros;