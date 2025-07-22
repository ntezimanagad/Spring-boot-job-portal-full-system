import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function ApplicantApplication() {
  const [info, setInfo] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const token = localStorage.getItem("token");
  const [applicantId, setApplicant] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [requirements, setRequirement] = useState("");
  const [location, setLocation] = useState("");
  const [status, setStatus] = useState("PENDING");

  const handleLoggedInUser = async () => {
    const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setApplicant(res.data.id);
  };

  const handleJobCreation = async (jobId) => {
    try {
      const res = await axios.post(
        `http://localhost:8080/api/application/create`,
        {
          applicantId,
          jobPostId: jobId,
          status,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      alert("Application Sent successfully");
    } catch (error) {
      console.error("Error:", error);
    }
  };

  useEffect(() => {
    handleLoggedInUser();
  }, []);

  useEffect(() => {

    axios
      .get("http://localhost:8080/api/application/myapplication", {
        params: { page, size },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setInfo(res.data.content))
      .catch((err) => {
        console.error("Error fetching company posts:", err);
      });
  }, [page, size]);

  return (
    <div>
        <Link to="/asetting">Go to setting</Link>
        <Link to="/adashboard">Home</Link>
      <ul>
        {Array.isArray(info) && info.length > 0 ? (
          info.map((index, i) => (
            <li key={i}>
              {index.applicantId || "No Title"} - {index.jobPostId || "Pending"} - {index.status || "Pending"}
            </li>
          ))
        ) : (
          <li>No job posts found.</li>
        )}
      </ul>
    </div>
  );
}

export default ApplicantApplication;
