import React from "react";
import { Modal } from "antd";
import {
  ExclamationCircleFilled,
  CheckCircleOutlined,
} from "@ant-design/icons";

const VCustomModal = ({
  title,
  mensaje,
  footer,
  open,
  tipoModal,
  cerrarModal,
}) => {
  return (
    <Modal
      title={title}
      className="modal-util-opc"
      open={open}
      centered
      footer={footer}
      onCancel={cerrarModal}
      onOk={cerrarModal}
      maskClosable={false}
    >
      <div className="divCenterUtil">
        {tipoModal == 1 ? (
          <CheckCircleOutlined className="icono_antd_ok" id="ok" />
        ) : (
          <ExclamationCircleFilled className="icono_antd_warn" id="warning" />
        )}
      </div>
      <br />
      <div className="divCenterUtil">
        <p className="msj-modal-gen">{mensaje}</p>
      </div>
    </Modal>
  );
};

export default VCustomModal;
