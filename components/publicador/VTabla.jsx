import React from 'react';
import { Table, Input, DatePicker, Button, Space } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import * as etiquetas from '../../utils/publicador/etiquetas';
import { parseHeaderFunctions, removeTimeFromMoment, isRecordTotal } from '../../utils/publicador/funciones';
import { config } from 'exceljs';

class VTabla extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      searchText: '',
      searchedColumn: ''
    }
  }

  getColumnSearchPropsTxt = dataIndex => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
      <div className='publicador-tabla-filtro-texto'>
        <Input className='publicador-tabla-filtro-texto-input'
          ref={node => {
            this.searchInput = node;
          }}
          placeholder={`${etiquetas.ACCION_BUSCAR}...`}
          value={selectedKeys[0]}
          onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
          onPressEnter={() => this.handleSearch(selectedKeys, confirm, dataIndex)} />
        <Space>
          <Button className='publicador-tabla-filtro-texto-button-inverted'
            onClick={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
            icon={<SearchOutlined />}
            size="small">
            {etiquetas.ACCION_BUSCAR}
          </Button>
          <Button className='publicador-tabla-filtro-texto-button'
            onClick={() => this.handleReset(clearFilters, confirm)}
            size="small">
            {etiquetas.ACCION_LIMPIAR}
          </Button>
        </Space>
      </div>
    ),
    filterIcon: filtered => <SearchOutlined
      style={{
        backgroundColor: filtered ? '#a9a9a9ff' : undefined,
        padding: filtered ? '2px 4px' : undefined,
        borderRadius: filtered ? '4px' : undefined
      }} />,
    onFilter: (value, record) => {
      if (!record[dataIndex]) return '';

      const normalizeText = (text) => {
        return text.toString()
          .toLowerCase()
          .normalize('NFD')
          .replace(/[\u0300-\u036f]/g, '');
      };
      return normalizeText(record[dataIndex]).includes(normalizeText(value));
    },
    onFilterDropdownVisibleChange: visible => {
      if (visible) {
        setTimeout(() => this.searchInput.select(), 100);
      }
    },
    render: text =>
      this.state.searchedColumn === dataIndex ? (
        <Highlighter
          highlightStyle={{
            backgroundColor: '#d3c1e7',
            padding: 0
          }}
          searchWords={[this.state.searchText]}
          autoEscape
          textToHighlight={text ? text.toString() : ''} />
      )
        : (
          text
        ),
  });

  getColumnSearchPropsDate = dataIndex => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
      <div id='publicador-tabla-filtro-fecha'>
        <DatePicker
          placeholder={`${etiquetas.ACCION_BUSCAR}...`}
          onChange={(date, dateString) => {
            if (date) {
              setSelectedKeys([date]);
              this.handleSearch(selectedKeys, confirm, dataIndex)
            } else {
              setSelectedKeys([]);
              this.handleReset(clearFilters, confirm)
            }
          }}
          format="DD/MM/YYYY"
          value={selectedKeys[0]} />
      </div>
    ),
    onFilter: (value, record) =>
      record[dataIndex] ?
        record[dataIndex] === removeTimeFromMoment(value)
        : ''
  });

  handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm();
    this.setState({
      searchText: selectedKeys[0],
      searchedColumn: dataIndex,
    });
  };

  handleReset = (clearFilters, confirm) => {
    clearFilters();
    confirm();
    this.setState({
      searchText: ''
    });
  };

  render() {
    const { tipoReporte, header, datos, handleChange, handleLinkToNivelAddProps } = this.props;
    const headerFunctions = parseHeaderFunctions(header,
      datos,
      this.getColumnSearchPropsTxt,
      this.getColumnSearchPropsDate,
      handleLinkToNivelAddProps);

    const headerConNumeracion = headerFunctions.map(column => {
      let processedColumn = { ...column };

      return processedColumn;
    });
    return (<Table id='publicador-tabla'
      dataSource={datos}
      columns={headerConNumeracion}
      className='table-layout'
      rowClassName={(record, index) => {
        return isRecordTotal(record) ? 'table-total' : 'table-row';
      }
      }
      pagination={tipoReporte === etiquetas.FOLDER_LISTADOS ?
        {
          defaultPageSize: 10,
          showQuickJumper: false
        } : false}
      scroll={{ x: '1100px', y: '300px' }}
      onChange={handleChange} />);
  }
}

export default VTabla;