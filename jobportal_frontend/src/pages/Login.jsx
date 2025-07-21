import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Login() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [otpCode, setOtp] = useState("");
  const [otpSent, setOtpSent] = useState(false);
  const [loading, setLoading] = useState(false);
  const [timer, setTimer] = useState(60);
  const navigate = useNavigate();

  useEffect(() => {
    if (otpSent && timer) {
      const interval = setInterval(() => {
        setTimer((prev) => prev - 1);
      }, 1000);
      return () => clearInterval(interval);
    }
  }, []);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      const res = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        password,
      });
      alert("Otp sent check your email");
      setOtpSent(true);
    } catch (error) {
        console.log("failed",error)
    } finally{
        setLoading(false)
    }
  };

  const handleLoginValidation = async (e) => {
  e.preventDefault();
  try {
    setLoading(true);
    const res = await axios.post(
      `http://localhost:8080/api/auth/confirm-login?otpCode=${otpCode}`,
      { email }
    );

    const token = res.data; // ✅ extract only JWT
    localStorage.setItem("token", token);

    const decoded = jwtDecode(token);
    const userRole = decoded.role;

    if (userRole === "COMPANY") {
      navigate("/cdashboard");
    } else {
      navigate("/adashboard");
      alert("Successfully Logged In as User");
    }
  } catch (error) {
    console.log("Login failed", error);
  } finally {
    setLoading(false);
  }
};


  return (
    <div>
      {!otpSent ? (
        <>
          <div>
            <label htmlFor="">Email</label>
            <div>
              <input
                type="text"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label htmlFor="">Password</label>
            <div>
              <input
                type="text"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>
          <div>
            <button onClick={handleLogin} disabled={loading}>
              {loading ? "Sending OTP....." : "Register"}
            </button>
            
          </div>
        </>
      ) : (
        <>
          <div>
            <label htmlFor="">OTP</label>
            <div>
              <input
                type="text"
                value={otpCode}
                onChange={(e) =>
                  setOtp(e.target.value)
                }
              />
            </div>
          </div>
          <div>
            <button onClick={handleLoginValidation} disabled={loading}>
              {loading ? "Verfying OTP....." : "Verfy"}
            </button>
            {
                timer > 0 ? (
                    <p>resent OTP in {timer} second</p>
                ) : (
                    <button onClick={handleResentOtp}>Resend</button>
                )
            }
          </div>
        </>
      )}
    </div>
  )
}

export default Login;
