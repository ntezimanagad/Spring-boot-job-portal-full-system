import React from "react";
import { jwtDecode } from "jwt-decode";
import { Navigate } from "react-router-dom";

function PrivateRoute({ children, role }) {
  const token = localStorage.getItem("token");
  if (!token) {
    return <Navigate to="/login" />;
  }

  try {
    const now = Date.now();
    const decode = jwtDecode(token);
    if (decode.exp && decode.exp < now / 1000) {
      localStorage.removeItem("token");
      return <Navigate to="/login" />;
    }

    const roles = decode.role ? [decode.role] : [];
    if (role && !roles.includes(role)) {
      localStorage.removeItem("token");
      return <Navigate to="/login" />;
    }
    return children;
  } catch (error) {
    localStorage.removeItem("token");
    return <Navigate to="/login" />;
  }
}

export default PrivateRoute;
