import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function ApplicantDashboard() {
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

  const handleJobCreation = async (jobId, companyId) => {
    console.log(companyId)
    try {
      const res = await axios.post(
        `http://localhost:8080/api/application/create`,
        {
          applicantId,
          jobPostId: jobId,
          campanyId: companyId,
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
    //const token = localStorage.getItem("token");

    axios
      .get("http://localhost:8080/api/jobpost/application", {
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
        <Link to="/viewapplication">My Application</Link>
      <ul>
        {Array.isArray(info) && info.length > 0 ? (
          info.map((index, i) => (
            <li key={i}>
              {index.title || "No Title"} - {index.companyId} - {index.isApproved || "Pending"}
              <button onClick={() => handleJobCreation(index.id, index.companyId)}>Apply</button>
            </li>
          ))
        ) : (
          <li>No job posts found.</li>
        )}
      </ul>
    </div>
  );
}

export default ApplicantDashboard;
