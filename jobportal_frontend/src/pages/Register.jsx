import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [cpassword, setCPassword] = useState("");
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
  }, [otpSent, timer]);

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      if (cpassword != password) {
        alert("password mismatch");
        return false;
      }
      setLoading(true);
      const res = await axios.post("http://localhost:8080/api/auth/register", {
        name,
        email,
        password,
      });
      alert("Otp sent check your email");
      setOtpSent(true);
    } catch (error) {
      console.log("failed", error);
    } finally {
      setLoading(false);
    }
  };

  const handleRegisterValidation = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      const res = await axios.post(
        `http://localhost:8080/api/auth/confirm-registration?otpCode=${otpCode}`,
        {
          name,
          email,
          password,
        }
      );
      const token = res.data;
      localStorage.setItem("token", token);

      const decoded = jwtDecode(token);
      const userRole = decoded.role;

      if (userRole === "COMPANY") {
        navigate("/companyinfo");
      } else if (userRole === "APPLICANT") {
        alert("Successfully Registered In as User");
        navigate("/applicantinfo");
      } else {
        navigate("/admin");
      }
    } catch (error) {
      console.log("failed", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      {!otpSent ? (
        <>
          <div>
            <label htmlFor="">Name</label>
            <div>
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>
          </div>
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
            <label htmlFor="">Confirm Password</label>
            <div>
              <input
                type="text"
                value={cpassword}
                onChange={(e) => setCPassword(e.target.value)}
              />
            </div>
          </div>
          <div>
            <button onClick={handleRegister} disabled={loading}>
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
                onChange={(e) => setOtp(e.target.value)}
              />
            </div>
          </div>
          <div>
            <button onClick={handleRegisterValidation} disabled={loading}>
              {loading ? "Verfying OTP....." : "Verfy"}
            </button>
            {timer > 0 ? (
              <p>resent OTP in {timer} second</p>
            ) : (
              <button onClick={handleResentOtp}>Resend</button>
            )}
          </div>
        </>
      )}
    </div>
  );
}

export default Register;
