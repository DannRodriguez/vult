import { useState } from "react"
import { Layout, Row,Col, Spin} from "antd"
import * as constantes from '../../utils/constantes'
import icono_mod_cerrado from '../../img/modulo-cerrado.png'
import logoLoader from '../../img/loader.gif'
import AuthenticatedComponent from "../AuthenticatedComponent"
import Template from "../interfaz/Template"

export default function ModuloCerrado(){
    const [loading, setLoading] = useState(false)

    return(
        <AuthenticatedComponent>
            <Template>
                <div id="divContentSustSEyCAE" style={{marginTop:'0px'}}>
                    <Spin spinning={loading} indicator={<img src={logoLoader} style={{width: '200px', height:'200px'}}/>}>
                        <Layout id="content-modulo-cerrado">
                            <Row style={{marginBottom: '22px', textAlign:'center', marginTop:'42px', fontFamily:'Roboto-Bold'}}>
                                <Col xs={24} md={24} xl={6}/>
                                <Col xs={24} md={24} xl={12}>
                                    <h2 style={{fontFamily: 'Roboto-Bold'}}>{constantes.TITULO_MODULO_CERRADO}</h2>
                                </Col>
                                <Col xs={24} md={24} xl={6}/>
                            </Row>
                            <Row style={{marginBottom: '22px', textAlign:'center'}}>
                                <Col xs={24} md={24} xl={3}/>
                                <Col xs={24} md={24} xl={18}>
                                    <h4 style={{fontFamily:'Roboto-regular'}}>
                                        {constantes.MSJ_MODULO_CERRADO}
                                    </h4>
                                </Col>
                                <Col xs={24} md={24} xl={3}/>
                            </Row>
                            <Row style={{marginBottom: '22px', textAlign:'center'}}>
                                <Col xs={24} md={24} xl={6}/>
                                <Col xs={24} md={24} xl={12}>
                                    <img className="align-boleta-header" src={icono_mod_cerrado} />
                                </Col>
                                <Col xs={24} md={24} xl={6}/>
                            </Row>
                        </Layout>
                    </Spin>
                </div>
            </Template>
        </AuthenticatedComponent>
    )
}