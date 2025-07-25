import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { Link, useNavigate } from 'react-router-dom'


function CompanySetting() {
  const token = localStorage.getItem("token")
  const[userId, setUserId] = useState("")
  const[companyName, setCompanyName] = useState("")
  const[logoPath, setLogoPath] = useState("")
  const[description, setDescription] = useState("")
  const[website, setWebsite] = useState("")
  const[approved, setApproved] = useState("PENDING")

  const handleLoggedInUser = async ()=>{
    const res = await axios.get("http://localhost:8080/api/user/getUserInfo",{
        headers:{
            Authorization: `Bearer ${token}`
        }
    })
    setUserId(res.data.id);
  }

  useEffect(()=>{
    handleLoggedInUser()
  })

  useEffect(() => {
  const fetchUserAndCompany = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/user/getUserInfo", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const id = res.data.id;
      setUserId(id);

      const applicantRes = await axios.get(
        `http://localhost:8080/api/company/getcampanybyid/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const data = applicantRes.data;
      setCompanyName(data.companyName || "");
      setLogoPath(data.logoPath || "");
      setDescription(data.description || "");
      setWebsite(data.website || "");
      
    } catch (err) {
      console.error("Failed to fetch user/Company:", err);
    }
  };

  fetchUserAndCompany();
}, []);


  const handleCompanyUpdate = async (e)=>{
    e.preventDefault()
    try {
        const res = await axios.put(`http://localhost:8080/api/company/update/${userId}`,
            {
                companyName,
                logoPath,
                description,
                website,
                approved
            },
            {
                headers:{
                    Authorization: `Bearer ${token}`
                }
            }
        )
        alert("Company profile Updated Successfull")
    } catch (error) {
        console.log("error",error)
    }
  }

    
    
  return (
    <div>
      <Link to="/cdashboard">Go to home</Link>
      <Link to="/appliedJob">Applied Job</Link>
      <h2>Update Suggestion</h2>
      <form>
        <div>
          <label>Company Name</label>
          <input value={companyName} onChange={e => setCompanyName(e.target.value)} required />
        </div>
        <div>
          <label>Logo Path</label>
          <input value={logoPath} onChange={e => setLogoPath(e.target.value)} required />
        </div>
        <div>
          <label>Description</label>
          <input value={description} onChange={e => setDescription(e.target.value)} required />
        </div>
        <div>
          <label>website</label>
          <input value={website} onChange={e => setWebsite(e.target.value)} required />
        </div>
        
        {/* <div>
          <label>User Id</label>
          <input value={userId} readOnly />
        </div> */}
        <button onClick={handleCompanyUpdate}>Update</button>
      </form>

      
    </div>
  )
}

export default CompanySetting