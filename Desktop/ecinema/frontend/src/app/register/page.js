"use client"; 

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';  // import useRouter for redirect
import './register.css'; // import the css 

export default function Register() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const router = useRouter();  // Initialize router for redirect

  const handleRegister = async (e) => {
    e.preventDefault();
    
    // Reset messages before submission
    setErrorMessage('');
    setSuccessMessage('');

    const userData = {
      firstname: firstName,
      lastname: lastName,
      email: email,
      password: password,
    };

    try {
      // Make POST request to register the user
      const response = await fetch('http://localhost:8080/api/users', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      const result = await response.json();

      if (response.ok) {
        // If successful, display success message and redirect to confirmation page
        setSuccessMessage('Registration successful! Redirecting to confirmation page...');
        setTimeout(() => {
          router.push('/register-confirmation'); // Redirect to confirmation page
        }, 2000); // Redirect after 2 seconds
      } else {
        // If email is already taken, show an error message
        setErrorMessage(result.message || 'Something went wrong. Please try again.');
      }
    } catch (error) {
      console.error('Error during registration:', error);
      setErrorMessage('Something went wrong. Please try again.');
    }
  };

  return (
    <div className="register-container">
      <h1>Register</h1>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      
      <form onSubmit={handleRegister}>
        <div>
          <input
            type="text"
            placeholder="First Name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
        </div>
        <div>
          <input
            type="text"
            placeholder="Last Name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </div>
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
        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <a href="/login">Login here</a></p>
      <a href="/">Back to Home</a>
    </div>
  );
}
