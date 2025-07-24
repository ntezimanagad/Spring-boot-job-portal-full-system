import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CreateApplicantInfo() {
  const token = localStorage.getItem("token");
  const [userId, setUserId] = useState("");
  const [fullName, setFullName] = useState("");
  const [phone, setPhone] = useState("");
  const [location, setLocation] = useState("");
  const [education, setEducation] = useState("");
  const [experience, setExperience] = useState("");
  const [skills, setSkills] = useState("");
  const [resumePath, setResumePath] = useState("");
  const navigate = useNavigate()

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

  const handleApplicant = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(
        "http://localhost:8080/api/applicant/create",
        {
          userId,
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
      alert("Applicant profile created Successfull");
      navigate("/login")
    } catch (error) {
      console.log("error", error);
    }
  };

  

  return (
    <div>
      <h2>Applicant Information</h2>
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
        <button onClick={handleApplicant}>create</button>
      </form>
    </div>
  );
}

export default CreateApplicantInfo;
