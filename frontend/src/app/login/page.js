// src/app/login/page.js

"use client"; 

import React, { useState } from 'react';
<<<<<<< HEAD
import { useRouter } from 'next/navigation'; 
import './login.css';
=======
import './login.css'; // import the css
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe

export default function Login() {
  const [email, setEmail] = useState(''); 
  const [password, setPassword] = useState('');
<<<<<<< HEAD
  const router = useRouter();

  const handleLogin = async (e) => {
    e.preventDefault();
    console.log('Logging in:', { email, password });
    const response = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    });

    if (response.ok) {
      router.push('/home-loggedin');
    } else {
      console.error('Login failed');
    }
=======

  const handleLogin = (e) => {
    e.preventDefault();
    // login logic 
    console.log('Logging in:', { email, password });
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe
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
<<<<<<< HEAD
      <a href="/">Back to Home</a>
    </div>
  );
}
=======
      <a href="/">Back to Home</a> {/* Link back to home */}
    </div>
  );
}
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe
