import AuthenticatedComponent from "../AuthenticatedComponent";
import Template from "../interfaz/Template";
import logo from "../../img/grafico_home_int.svg";

function MenuPrincipal() {
  return (
    <AuthenticatedComponent>
      <Template>
        <div style={{ margin: "20px 50px" }} className="divCenterUtil">
          <img style={{ width: "100%" }} src={logo} alt="" />
        </div>
      </Template>
    </AuthenticatedComponent>
  );
}

export default MenuPrincipal;
