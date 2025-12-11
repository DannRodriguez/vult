import React from 'react';
import { Modal, Button } from 'antd';
import iconoPregunta from '../../imgExmnLn/icono_pregunta.svg'


const ModalAcciones = ({ type ,message, handleOk, handleCancel, visible }) => {
    return(
        <Modal title={null} 
            footer={null}
            width={350}
            centered={true}
            closable={false}
            visible={visible} >
            <div className ='modales-generico'>
                <img src={iconoPregunta}
                    className='adm-image'
                    alt="Icono mensaje"/>
                <span style={{textAlign:'center'}}>{message}</span>
                <div className ='adm-buttons'>
                    <Button className='adm-buttons-cancel' 
                            onClick={handleCancel}>
                        Cancelar
                    </Button>
                    <Button className='adm-buttons-button'
                            onClick={handleOk}>
                        Aceptar
                    </Button>
                </div>
            </div>
        </Modal>
    );
}

export default ModalAcciones;