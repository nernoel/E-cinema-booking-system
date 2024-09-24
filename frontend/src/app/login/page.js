// src/app/login/page.js

"use client"; 

import React, { useState } from 'react';
import './login.css'; // import the css

export default function Login() {
  const [email, setEmail] = useState(''); 
  const [password, setPassword] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    // login logic 
    console.log('Logging in:', { email, password });
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
      <p>Don't have an account? <a href="/register">Register here</a></p>
      <a href="/">Back to Home</a> {/* Link back to home */}
    </div>
  );
}