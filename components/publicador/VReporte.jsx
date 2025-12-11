import React from 'react';
import { ArrowLeftOutlined } from '@ant-design/icons';
import VCotas from './VCotas';
import VExportar from './VExportar';
import VTabla from './VTabla';
import VCustomModal from '../commonComponents/VCustomModal';
import * as etiquetas from '../../utils/publicador/etiquetas';
import { obtieneEtiquetaNivel } from '../../utils/publicador/funciones';
import { ETQ_LISTADOS, NUM_EXPEDIENTE, EXP_NO_DISPONIBLE, DESCARGAR } from '../../utils/constantes';
import { descargaExpediente } from '../../utils/bitacoraDesempenio/funciones';

const handleLinkToNivelAddProps = (proceso, estado, distrito, municipio, handleLinkToNivel, record, nivel) => {
    handleLinkToNivel(nivel,
        record.idDetalleProceso ? {
            descripcionDetalle: record.descripcionDetalle,
            idDetalleProceso: record.idDetalleProceso
        } : proceso,
        record.idEstado ? {
            nombreEstado: record.nombreEstado,
            idEstado: record.idEstado
        } : estado,
        record.idDistrito ? {
            nombreDistrito: record.nombreDistrito !== undefined ? record.nombreDistrito : (record.cabeceraDistritalLocal != undefined ? record.cabeceraDistritalLocal : record.cabeceraDistritalFederal),
            idDistrito: record.idDistrito
        } : distrito,
        record.idMunicipio ? {
            nombreMunicipio: record.nombreMunicipio,
            idMunicipio: record.idMunicipio
        } : municipio);
}

const VReporte = ({ tipoReporte, reporte, nivel,
    proceso, estado, distrito, municipio,
    error, datosTabla, datosFiltrados, isVistaTemporal,
    handleChangeTabla, handleLinkToNivel, handleReturn,
    isOpenCotas, handleChangeCotas }) => {

    const [expedientesDisponibles, setExpedientesDisponibles] = React.useState({});
    const [openModal, setOpenModal] = React.useState(false);
    const [msjModal, setMsjModal] = React.useState("");
    const [tipoModal, setTipoModal] = React.useState(2);

    const columnasWeb = datosTabla?.header?.web ? [...datosTabla.header.web] : [];

    const cerrarModal = () => {
        setOpenModal(false);
        setMsjModal("");
    };

    if (tipoReporte === ETQ_LISTADOS && reporte?.label?.startsWith(NUM_EXPEDIENTE)) {
        columnasWeb.push({
            title: "Expediente",
            key: "expediente",
            dataIndex: "expediente",
            width: 100,
            align: "center",
            render: (_, record) => {
                return (
                    <a
                        href="#"
                        onClick={async (e) => {
                            e.preventDefault();

                            const requestExpediente = {
                                idDetalleProceso: record.idDetalleProceso || proceso?.idDetalleProceso,
                                idParticipacion: record.idParticipacion,
                                idAspirante: record.idAspirante,
                            };

                            const resultado = await descargaExpediente(requestExpediente, 'Expediente');

                            if (!resultado) {
                                setMsjModal(EXP_NO_DISPONIBLE);
                                setTipoModal(2);
                                setOpenModal(true);
                            }
                        }}
                    >
                        {DESCARGAR}
                    </a >
                );
            }
        });
    }

    return (
        <div className='publicador-reporte-container'>
            {
                datosTabla ?
                    <>
                        {datosTabla.cotas
                            && datosTabla.cotas.html
                            && datosTabla.cotas.html.length > 0 ?
                            <VCotas cotas={datosTabla.cotas.html}
                                isOpenCotas={isOpenCotas}
                                handleChange={handleChangeCotas} />
                            : ''
                        }
                        {isVistaTemporal ?
                            <div className='rc-regresar'
                                onClick={handleReturn}>
                                <ArrowLeftOutlined />{etiquetas.ACCION_REGRESAR}
                            </div>
                            : ''
                        }
                        <div className='rc-reporte'>
                            <span className='rc-reporte-title'>{reporte?.label}</span>
                            <span className='rc-reporte-nivel'>{obtieneEtiquetaNivel(estado, distrito, municipio, nivel)}</span>
                        </div>
                        <VExportar proceso={proceso}
                            estado={estado}
                            distrito={distrito}
                            municipio={municipio}
                            reporte={reporte}
                            nivel={nivel}
                            cotas={datosTabla.cotas && datosTabla.cotas.pdf ?
                                datosTabla.cotas.pdf
                                : []}
                            headerCSV={datosTabla.header.csv}
                            headerPDF={datosTabla.header.pdf}
                            datos={datosFiltrados && datosFiltrados.length > 0 ?
                                datosFiltrados
                                : datosTabla.datos} />
                        <VTabla tipoReporte={tipoReporte}
                            header={columnasWeb}
                            datos={datosTabla.datos}
                            handleChange={handleChangeTabla}
                            handleLinkToNivelAddProps={handleLinkToNivelAddProps.bind(null,
                                proceso,
                                estado,
                                distrito,
                                municipio,
                                handleLinkToNivel)} />
                    </>
                    : <div className='rc-error'>
                        <span className='rc-error-msg'>
                            {error ? error : etiquetas.NO_HAY_DATOS}
                        </span>
                    </div>
            }
            <VCustomModal
                title={null}
                mensaje={msjModal}
                footer={null}
                open={openModal}
                tipoModal={tipoModal}
                cerrarModal={cerrarModal}
            />
        </div>
    );
}

export default VReporte;