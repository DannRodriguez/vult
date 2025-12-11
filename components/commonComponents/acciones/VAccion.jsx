import React from 'react';

const VAccion = ({ accion: { title, img } })  => {
    return(
        <div className='bandeja-acciones-accion'>
            <img src={img} alt="Icono de la accion" />
            <span className='baa-title'>{title}</span>
        </div>
    );
}

export default VAccion;