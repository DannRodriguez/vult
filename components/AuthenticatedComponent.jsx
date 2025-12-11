import { useEffect } from "react";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { useNavigate } from "react-router-dom";

function AuthenticatedComponent({ children }) {
  const user = useSelector((store) => store.loginUser.currentUser);
  const navigate = useNavigate();

  useEffect(() => {
    const currentTime = Date.now() / 1000;

    if (
      user?.tknA == undefined ||
      user?.tknA == null ||
      user?.tknA?.exp < currentTime
    ) {
      navigate("/");
    }
  }, []);

  useEffect(() => {
    if (user == null) {
      navigate("/");
    }
  }, [user]);

  return <>{children}</>;
}

export default AuthenticatedComponent;
