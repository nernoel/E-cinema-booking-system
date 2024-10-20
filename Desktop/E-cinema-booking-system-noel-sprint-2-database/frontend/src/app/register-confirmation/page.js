// src/app/register-confirmation/page.js

"use client";

import React from 'react';

import './register-confirmation.css'; // import the css

export default function RegisterConfirmation() {
    return (
        <div className="confirmation-container">
            <h1>Registration Successful</h1>
            <p>Thank you for registering. You can now log in to your account.</p>
            <a href="/login">Go to Login</a>
        </div>
    );
}

