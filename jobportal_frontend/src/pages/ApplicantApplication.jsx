import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function ApplicantApplication() {
  const [info, setInfo] = useState([]);
  const [jobInfo, setJobInfo] = useState([]);
  const [applicantInfo, setApplicantInfo] = useState([]);
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

  const getApplicantById = async (id) => {
    const res = await axios(
      `http://localhost:8080/api/applicant/getapplicantbyid/${id}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    setApplicantInfo([res.data]);
  };

  const getJobById = async (id) => {
    const res = await axios(
      `http://localhost:8080/api/jobpost/getjobbyid/${id}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    setJobInfo([res.data]);
  };

  const deleteApplication = async (jobId) => {
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/application/delete/${jobid}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      alert("Application Deleted successfully");
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
              {index.applicantId} - {index.jobPostId} - {index.appliedAt} -{" "}
              {index.status}
              <button onClick={() => getApplicantById(index.applicantId)}>
                Applicant Info
              </button>{" "}
              -
              <button onClick={() => getJobById(index.jobPostId)}>
                Job Info
              </button>
              
            </li>
          ))
        ) : (
          <li>No job posts found.</li>
        )}
      </ul>
      <ul>
        {Array.isArray(jobInfo) && info.length > 0 ? (
          jobInfo.map((index, i) => (
            <li key={i}>
              {index.title} - {index.description} - {index.requirements} -{" "}
              {index.location}
            </li>
          ))
        ) : (
          <li>No job posts found.</li>
        )}
      </ul>
      <ul>
        {Array.isArray(applicantInfo) && info.length > 0 ? (
          applicantInfo.map((index, i) => (
            <li key={i}>
              {index.fullName} - {index.phone} - {index.location} -{" "}
              {index.education}- {index.experience} - {index.skills} -{" "}
              {index.resumePath}
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
