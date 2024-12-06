"use client";

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios'; 

import '../styles/login.css';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (token) {
      // If token exists, user is logged in, redirect to homepage
      router.push('/home-authenticated');  // Adjust the path as needed
    }
  }, [router]);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // Make API call for login
      const response = await axios.post('http://localhost:8080/api/users/login', {
        email,
        password,
      });
      const token = response.data.token; 
      
      // Save the token based on successful login
      if (response.status === 200) {
        localStorage.setItem('authToken', token);
        localStorage.setItem('userEmail', email);

        
        // Get the user role based on the response data
        const userRole = response.data.role;
        
        if (userRole === 'ADMIN') {
          alert("Admin login successful!");
          router.push('/admin');
        } else {
          alert("Login successful!");
          router.push('/home-authenticated');
        }
      } else {
        setErrorMessage('Invalid email or password');
      }
    } catch (error) {
      console.error('Error during login:', error);
      setErrorMessage('An error occurred while trying to log in.');
    }
  };

  return (
    <div className="login-container">
      <h1>Login</h1>
      <form onSubmit={handleLogin}>
        <div>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <p>Don't have an account? <a href="/register">Register here</a></p>
      <a href="/">Back to Home</a>
    </div>
  );
}