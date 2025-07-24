import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function CompanyDashboard() {
  const [info, setInfo] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const token = localStorage.getItem("token");
  const [companyId, setCompanyId] = useState("");
  const [id, setJobPost] = useState("")
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [requirements, setRequirement] = useState("");
  const [location, setLocation] = useState("");
  const [isApproved, setApproved] = useState("PENDING");

  const handleLoggedInUser = async () => {
    const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setCompanyId(res.data.id);
  };

  useEffect(() => {
    handleLoggedInUser();
  }, []);

  useEffect(() => {
    //const token = localStorage.getItem("token");

    axios
      .get("http://localhost:8080/api/jobpost/company_post", {
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

  const handleJobPostDelete = async (id) => {
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/jobpost/delete/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Job Deleted Successfull");
      axios
      .get("http://localhost:8080/api/jobpost/company_post", {
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
      console.log("error", error);
    }
  };

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
      axios
      .get("http://localhost:8080/api/jobpost/company_post", {
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
      console.log("error", error);
    }
  };

  const handleJobPostUpdate = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.put(
        `http://localhost:8080/api/jobpost/update/${id}`,
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
      alert("Job Updated Successfull");
      axios
      .get("http://localhost:8080/api/jobpost/company_post", {
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
      console.log("error", error);
    }
  };

  return (
    <div>
      <Link to="/csetting">Go to setting</Link>
      <Link to="/appliedJob">Applied Job</Link>
      <h2>Update Suggestion</h2>
      <form>
        <div>
          <label>title</label>
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Description</label>
          <input
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Requirement</label>
          <input
            value={requirements}
            onChange={(e) => setRequirement(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Location</label>
          <input
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            required
          />
        </div>

        {/* <div>
          <label>User Id</label>
          <input value={userId} readOnly />
        </div> */}
        <button onClick={handleJobPostUpdate}>Update</button>
        <button onClick={handleJobPost}>create</button>
      </form>
      <div>
        <ul>
          {Array.isArray(info) && info.length > 0 ? (
            info.map((index, i) => (
              <li key={i}>
                {index.title || "No Title"} - {index.description || "No description"} -{" "}
                {index.requirements || "No requirement"} -{" "}
                {index.isApproved || "Pending"}
                <button onClick={() =>{
                  setTitle(index.title)
                  setDescription(index.description)
                  setRequirement(index.requirements)
                  setLocation(index.location)
                  setJobPost(index.id)}
                } >
                  Update
                </button>
                <button onClick={() => handleJobPostDelete(index.id)} >
                  delete
                </button>
              </li>
            ))
          ) : (
            <li>No job posts found.</li>
          )}
        </ul>
      </div>
    </div>
  );
}

export default CompanyDashboard;
