import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function AppliedApplicant() {
  const [info, setInfo] = useState([]);
  const [jobInfo, setJobInfo] = useState([]);
  const [applicantInfo, setApplicantInfo] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const token = localStorage.getItem("token");
  const [companyId, setCompanyId] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [requirements, setRequirement] = useState("");
  const [location, setLocation] = useState("");
  const [status, setStatus] = useState("");

  const handleLoggedInUser = async () => {
    const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setCompanyId(res.data.id);
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

  const updateStatus = async (id, applicantId, newStatus) => {
    try {
      const res = await axios.put(
        `http://localhost:8080/api/application/updatestatus/${id}`,
        {
          status: newStatus,
        },
        {
          params: {
            applicantId,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Status Updated");
      axios
      .get("http://localhost:8080/api/application/appliedjob", {
        params: { page, size },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setInfo(res.data.content))
      .catch((err) => {
        console.error("Error fetching company posts:", err);
      });
    } catch (err) {
      console.error("Error updating status:", err);
      alert("Failed to update status");
    }
  };

  useEffect(() => {
    handleLoggedInUser();
  }, []);

  const deleteApplication = async (jobId) => {
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/application/delete/${jobId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      alert("Application Deleted successfully");
      axios
      .get("http://localhost:8080/api/application/appliedjob", {
        params: { page, size },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setInfo(res.data.content))
      .catch((err) => {
        console.error("Error fetching company posts:", err);
      });
    } catch (error) {
      console.error("Error:", error);
    }
  };

  useEffect(() => {
    //const token = localStorage.getItem("token");

    axios
      .get("http://localhost:8080/api/application/appliedjob", {
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

  const handleJobPost = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(
        "http://localhost:8080/api/jobpost/create",
        {
          companyId,
          title,
          description,
          requirements,
          location,
          isApproved,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Job created Successfull");
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <div>
      <Link to="/csetting">Go to setting</Link>
      <Link to="/cdashboard">Go to Home</Link>
      <div>
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
                <button
                  onClick={() =>
                    updateStatus(index.id, index.applicantId, "APPROVED")
                  }
                >
                  Approve
                </button>
                <button
                  onClick={() =>
                    updateStatus(index.id, index.applicantId, "ACCEPTED")
                  }
                >
                  Accept
                </button>
                <button
                  onClick={() =>
                    updateStatus(index.id, index.applicantId, "REJECTED")
                  }
                >
                  Reject
                </button>
                <button onClick={() => deleteApplication(index.id)}>
                  Delete
                </button>
              </li>
            ))
          ) : (
            <li>No job posts found.</li>
          )}
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
        </ul>
      </div>
    </div>
  );
}

export default AppliedApplicant;
