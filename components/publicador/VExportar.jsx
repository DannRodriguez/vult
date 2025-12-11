import React, { useEffect, useState } from 'react';
import { FilePdfOutlined, FileExcelOutlined, FileTextOutlined } from '@ant-design/icons';
import { obtieneNombreArchivo, generaTxt, generaPDF, obtieneEtiquetaNivel } from '../../utils/publicador/funciones';
import ExcelJS from 'exceljs/dist/exceljs';
import logoINE from "../../img/ine24.jpg";


const VExportar = ({ proceso, estado, distrito, municipio, reporte, nivel, cotas, headerCSV, headerPDF, datos }) => {

    const [dataImg, setDataImg] = useState();


    useEffect(() => {
        convertImageToBase64(logoINE, callback);
    }, [])

    const callback = (data) => {
        setDataImg(data);
        console.log(dataImg);
    }

    function convertImageToBase64(imgUrl, callback) {
        const image = new Image();
        image.crossOrigin = 'anonymous';
        image.onload = () => {
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');
            canvas.height = image.naturalHeight;
            canvas.width = image.naturalWidth;
            ctx.drawImage(image, 0, 0);
            const dataUrl = canvas.toDataURL();
            callback && callback(dataUrl)
        }
        image.src = imgUrl;
    }

    const getDataSet = () => {

        convertImageToBase64(logoINE, callback);
        const headers = headerCSV.reduce((acc, header) => {
            acc.keys[header["key"]] = true;
            acc.labels.push(header["label"]);
            return acc;
        }, { keys: {}, labels: [] });

        const dataTable = datos.reduce((acc, fila) => {
            const row = [];
            for (const [key, value] of Object.entries(fila)) {
                if (headers.keys[key]) {
                    if (value === null || value === undefined || value === '' || value === false) {
                        row.push("-");
                    } else if (typeof value === 'string' && value.trim() === '') {
                        row.push("-");
                    } else if (typeof value === 'number' && isNaN(value)) {
                        row.push("-");
                    } else {
                        row.push(value);
                    }
                }
            }
            acc.push(row);
            return acc;
        }, []);

        if (datos) {
            var fileName = obtieneNombreArchivo(reporte.key, nivel.label);
            let nombreListado = reporte.label;
            let nivelListado = obtieneEtiquetaNivel(estado, distrito, municipio, nivel);
            let fechaHora = "Fecha de impresión: " + getFechaHora();
            var nombreProceso = proceso.nombreProceso;

            var workbook = new ExcelJS.Workbook();
            var logo = dataImg;
            console.log(logoINE.src);
            const imageId2 = workbook.addImage({
                extension: 'png',
                base64: logo
            });

            let worksheet = workbook.addWorksheet(fileName);
            worksheet.addImage(imageId2, 'A1:C4');

            const rowListado = worksheet.getRow(1);
            rowListado.getCell(5).value = nombreListado;
            rowListado.getCell(5).style = { font: { bold: true, size: 12 } };

            const rowProceso = worksheet.getRow(2);
            rowProceso.getCell(5).value = nombreProceso;
            rowProceso.getCell(5).style = { font: { bold: true, size: 12 } };

            const rowNivel = worksheet.getRow(3);
            rowNivel.getCell(5).value = nivelListado;
            rowNivel.getCell(5).style = { font: { bold: true, size: 12 } };

            const rowFecha = worksheet.getRow(4);
            rowFecha.getCell(5).value = fechaHora;
            rowFecha.getCell(5).style = { font: { bold: true, size: 12 } }

            const rowHeader = worksheet.getRow(6);
            rowHeader.style = { font: { bold: true } }
            rowHeader.values = headers.labels;

            worksheet.addRows(dataTable);

            worksheet.eachRow((row, rowNumber) => {
                if (rowNumber > 6) {
                    row.eachCell((cell) => {
                        if (cell.value === "-") {
                            cell.alignment = { vertical: 'middle', horizontal: 'center' };
                        }
                    });
                }
            });

            workbook.xlsx.writeBuffer().then((buf) => {
                const blob = new Blob([buf], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
                const element = document.createElement("a");
                element.href = URL.createObjectURL(blob);
                element.download = fileName + ".xlsx";
                document.body.appendChild(element);
                element.click();

            });
        }
    }

    const getFechaHora = () => {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1;

        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = "0" + dd;
        }
        if (mm < 10) {
            mm = "0" + mm;
        }
        var HH = today.getHours();
        var MM = today.getMinutes();
        if (HH < 10) {
            HH = "0" + HH;
        }
        if (MM < 10) {
            MM = "0" + MM;
        }
        var todayFormat = dd + "/" + mm + "/" + yyyy + " " + HH + ":" + MM;
        return todayFormat;
    }

    return (<div className='publicador-exportar-container'>
        {
            headerCSV && headerCSV.length ?

                <div className='pec-option'
                    onClick={getDataSet}>
                    <FileExcelOutlined />
                    <span className='pec-option-label'>XLSX</span>
                </div>
                : null
        }
        {
            headerPDF && headerPDF.length ?
                <div className='pec-option'
                    onClick={() => generaPDF(proceso, estado, distrito, municipio, reporte, nivel, cotas, headerPDF, datos)}>
                    <FilePdfOutlined />
                    <span className='pec-option-label'>PDF</span>
                </div>
                : null
        }

        {
            headerCSV && headerCSV.length ?
                <div className='pec-option'
                    onClick={() => generaTxt(headerCSV, datos, reporte.key, nivel.label)}>
                    <FileTextOutlined />
                    <span className='pec-option-label'>TXT</span>
                </div>
                : null
        }
    </div>);
}

export default (VExportar);