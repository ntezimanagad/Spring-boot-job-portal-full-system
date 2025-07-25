import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function ApplicantSetting() {
  const token = localStorage.getItem("token");
  const [userId, setUserId] = useState("");
  const [fullName, setFullName] = useState("");
  const [phone, setPhone] = useState("");
  const [location, setLocation] = useState("");
  const [education, setEducation] = useState("");
  const [experience, setExperience] = useState("");
  const [skills, setSkills] = useState("");
  const [resumePath, setResumePath] = useState("");

  const handleLoggedInUser = async () => {
    const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setUserId(res.data.id);
  };

  useEffect(() => {
    handleLoggedInUser();
  });

  useEffect(() => {
  const fetchUserAndApplicant = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const id = res.data.id;
      setUserId(id);

      const applicantRes = await axios.get(
        `http://localhost:8080/api/applicant/getapplicantbyid/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const data = applicantRes.data;
      setFullName(data.fullName || "");
      setPhone(data.phone || "");
      setLocation(data.location || "");
      setEducation(data.education || "");
      setExperience(data.experience || "");
      setSkills(data.skills || "");
      setResumePath(data.resumePath || "");
    } catch (err) {
      console.error("Failed to fetch user/applicant:", err);
    }
  };

  fetchUserAndApplicant();
}, []);


  

  const handleApplicantUpdate = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.put(
        `http://localhost:8080/api/applicant/update/${userId}`,
        {
          fullName,
          phone,
          location,
          education,
          experience,
          skills,
          resumePath,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Applicant profile Updated Successfull");
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <div>
      <Link to="/adashboard">Go to Home</Link>
      <Link to="/viewapplication">My Application</Link>
      <h2>Update Applicant</h2>
      <form>
        <div>
          <label>Full Name</label>
          <input
            value={fullName}
            onChange={(e) => setFullName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Phone</label>
          <input
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
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
        <div>
          <label>Education</label>
          <input
            value={education}
            onChange={(e) => setEducation(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Experience</label>
          <input
            value={experience}
            onChange={(e) => setExperience(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Skills</label>
          <input
            value={skills}
            onChange={(e) => setSkills(e.target.value)}
            required
          />
        </div>
        <div>
          <label>ResumePath</label>
          <input
            value={resumePath}
            onChange={(e) => setResumePath(e.target.value)}
            required
          />
        </div>

        {/* <div>
          <label>User Id</label>
          <input value={userId} readOnly />
        </div> */}
        <button onClick={handleApplicantUpdate}>Update</button>
      </form>
    </div>
  );
}

export default ApplicantSetting;
