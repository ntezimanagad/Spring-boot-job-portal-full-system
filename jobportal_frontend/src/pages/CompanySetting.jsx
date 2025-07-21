import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'


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

  const handleCompany = async (e)=>{
    e.preventDefault()
    try {
        const res = await axios.post("http://localhost:8080/api/company/create",
            {
                userId,
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
        alert("Company profile created Successfull")
    } catch (error) {
        console.log("error",error)
    }
  }

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
        <button onClick={handleCompany}>create</button>
      </form>

      
    </div>
  )
}

export default CompanySetting