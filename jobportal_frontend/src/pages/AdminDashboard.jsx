import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

function AdminDashboard() {
  const [info, setInfo] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const token = localStorage.getItem("token");
  const [isApproved, setApproved] = useState("APPROVED");

  // const handleLoggedInUser = async () => {
  //   const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
  //     headers: {
  //       Authorization: `Bearer ${token}`,
  //     },
  //   });
  //   setCompanyId(res.data.id);
  // };

  // useEffect(() => {
  //   handleLoggedInUser();
  // }, []);

  useEffect(() => {
    //const token = localStorage.getItem("token");

    axios
      .get("http://localhost:8080/api/jobpost/findall", {
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

  const handleJobUpdate = async (id) => {
  try {
    const res = await axios.put(`http://localhost:8080/api/jobpost/updatestatus/${id}`, {
      isApproved
    }, {
      headers: { Authorization: `Bearer ${token}` }
    });

    alert("Approved successfully");
  } catch (error) {
    console.error("Error:", error);
  }
};


  return (
    <div>
      <Link to="/asetting">Go to setting</Link>

      <ul>
        {Array.isArray(info) && info.length > 0 ? (
          info.map((index, i) => (
            <li key={i}>
              {index.title || "No Title"} - {index.isApproved || "Pending"}
              <button onClick={() => handleJobUpdate(index.id)}>Approve</button>
            </li>
          ))
        ) : (
          <li>No job posts found.</li>
        )}
      </ul>
    </div>
  );
}

export default AdminDashboard;
