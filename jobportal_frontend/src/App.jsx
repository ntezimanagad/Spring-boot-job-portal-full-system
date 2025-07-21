import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import PrivateRoute from './components/PrivateRoute'
import CompanySetting from './pages/CompanySetting'
import CompanyDashboard from './pages/CompanyDashboard'
import ApplicantSetting from './pages/ApplicantSetting'
import ApplicantDashboard from './pages/ApplicantDashboard'

function App() {

  return (
    <>
      <Router>
        <Routes>
          <Route path="/login" element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/csetting" element={<PrivateRoute role="COMPANY"><CompanySetting/></PrivateRoute>}/>
          <Route path="/cdashboard" element={<PrivateRoute role="COMPANY"><CompanyDashboard/></PrivateRoute>}/>
          <Route path="/asetting" element={<PrivateRoute role="APPLICANT"><ApplicantSetting/></PrivateRoute>}/>
          <Route path="/adashboard" element={<PrivateRoute role="APPLICANT"><ApplicantDashboard/></PrivateRoute>}/>
        </Routes>
      </Router>
    </>
  )
}

export default App
