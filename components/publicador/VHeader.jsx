import React from 'react';
import VFiltros from './VFiltros';
import * as etiquetas from '../../utils/publicador/etiquetas';
import boleta from '../../img/boleta.svg';
import "../../css/publicador.scss"
import VCotas from './VCotas';
import { BarInfo } from "../commonComponents/AccessoriesComponents";
const VHeader = ({ tipoReporte, reporte, nivelSeleccionado,
    corte, listaReportes,
    seleccionaReporte, seleccionaNivelReporte,
    datosTabla, isOpenCotas, handleChangeCotas }) => {
    return (
        <div className='publicador-header-container'>
            <div className='phc-text'>
                <div className='phc-text-title'>
                    <span className='span_Header_Light'>{"Inicio " + " / "}</span>
                    <span className='span_Header_Medium'>{etiquetas.TITLE_REPORTES + " - "}</span>
                    <span className='span_Header_Medium'>{tipoReporte === etiquetas.FOLDER_LISTADOS ?
                        etiquetas.LABEL_LISTADOS
                        : etiquetas.LABEL_CEDULAS}</span>
                </div>
                <div className='div_publ_title'>
                    <img src={boleta} style={{ width: "45px" }} />
                    <span>{"  " + etiquetas.TITLE_REPORTES}</span>
                </div>
                {datosTabla?.cotas?.html?.length > 0 && (
                    <>
                        <BarInfo text={etiquetas.ACCESIBILIDAD_COTAS} />
                        <VCotas cotas={datosTabla.cotas.html}
                            isOpenCotas={isOpenCotas}
                            handleChange={handleChangeCotas} />
                    </>
                )}
                {
                    corte ?
                        <div className='phc-text-corte'>
                            <span className='phc-text-corte-label'>{etiquetas.ULTIMO_CORTE}</span>
                            <span className='phc-text-corte-value'>{corte.fecha}</span>
                        </div>
                        : ''
                }
            </div>
            <VFiltros tipoReporte={tipoReporte}
                listaReportes={listaReportes}
                reporteSeleccionado={reporte?.key}
                nivelSeleccionado={nivelSeleccionado}
                seleccionaReporte={seleccionaReporte}
                seleccionaNivelReporte={seleccionaNivelReporte} />
        </div>
    );
}

export default VHeader;