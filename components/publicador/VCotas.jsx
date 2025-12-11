import React from 'react';
import { InfoCircleOutlined, RightOutlined } from '@ant-design/icons'
import * as etiquetas from '../../utils/publicador/etiquetas';
import "../../css/publicador.scss";
import { Tooltip } from 'antd';

const VCotas = ({ cotas, isOpenCotas, handleChange }) => {
    return (
        <div className='publicador-cotas-container'>
            <div className='pcc-icon'
                style={{ display: `${isOpenCotas ? 'none' : 'flex'}` }}
                onClick={handleChange} >
                <Tooltip title={etiquetas.ACCESIBILIDAD} placement="left">
                    <InfoCircleOutlined />
                </Tooltip>
            </div>
            <div className='pcc-icon'
                style={{ display: `${isOpenCotas ? 'flex' : 'none'}` }}
                onClick={handleChange}>
                <RightOutlined />
            </div>
            <div className='pcc-data'
                style={{
                    display: `${isOpenCotas ? 'flex' : 'none'}`,
                    maxHeight: '400px',
                    overflowY: 'auto'
                }}>
                <div className='pcc-data-title'>
                    <InfoCircleOutlined />
                    <span className='pcc-data-title-text'>{etiquetas.COTAS_TITLE}</span>
                </div>
                {
                    cotas.map((cotaGroup, index) => {
                        return (
                            <div className='pcc-data-group' key={index}>
                                <span
                                    className={cotaGroup.className}
                                    style={{ backgroundColor: cotaGroup.backgroundColor }}
                                >
                                    {cotaGroup.title}
                                </span>
                                {
                                    cotaGroup.content.map((cota, index) => {
                                        return (
                                            <div className='pcc-data-group-content' key={index}>
                                                <span>{cota.label}</span>
                                                <span>{cota.value}</span>
                                            </div>
                                        );
                                    })
                                }
                            </div>
                        );
                    })
                }
            </div>
        </div>
    );
}

export default VCotas;