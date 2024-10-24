"use client"

import React, { useState } from 'react';
import axios from 'axios'; // Import axios
import './login.css';
import { useRouter } from 'next/navigation';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const router = useRouter();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/users/login', {
        email,
        password,
      });

      const token = response.data.token; // Adjust according to your API response structure
      if (rememberMe) {
        localStorage.setItem('authToken', token);
        localStorage.setItem('userEmail', email);
      } else {
        sessionStorage.setItem('authToken', token);
        sessionStorage.setItem('userEmail', email);
      }

      if (response.status === 200) {
        const userRole = response.data.role; // Assuming role is in the response data
        if (userRole === 'ADMIN') {
          alert("Admin login successful!");
          router.push('/admin');
        } else {
          alert("Login successful!");
          router.push('/home-loggedin');
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
        <div>
          <label>
            <input
              type="checkbox"
              checked={rememberMe}
              onChange={(e) => setRememberMe(e.target.checked)}
            />
            Remember Me
          </label>
        </div>
        <button type="submit">Login</button>
      </form>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <p>Don't have an account? <a href="/register">Register here</a></p>
      <a href="/">Back to Home</a>
    </div>
  );
}
