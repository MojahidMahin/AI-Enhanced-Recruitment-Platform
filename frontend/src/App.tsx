import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useStore } from './store/useStore';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import CandidatesList from './pages/CandidatesList';
import CandidateDetail from './pages/CandidateDetail';
import JobsList from './pages/JobsList';
import JobDetail from './pages/JobDetail';
import './styles/index.css';

function App() {
  const { isAuthenticated } = useStore();

  useEffect(() => {
    // Initialize app - check for existing auth token
    const token = localStorage.getItem('token');
    if (token) {
      useStore.setState({ isAuthenticated: true });
    }
  }, []);

  return (
    <Router>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
      {isAuthenticated && <Navbar />}
      <Routes>
        <Route path="/" element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" />} />
        <Route path="/candidates" element={isAuthenticated ? <CandidatesList /> : <Navigate to="/login" />} />
        <Route path="/candidates/:id" element={isAuthenticated ? <CandidateDetail /> : <Navigate to="/login" />} />
        <Route path="/jobs" element={isAuthenticated ? <JobsList /> : <Navigate to="/login" />} />
        <Route path="/jobs/:id" element={isAuthenticated ? <JobDetail /> : <Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

export default App;
