// src/app/register/page.js

"use client"; 

import React, { useState } from 'react';
import './register.css'; 

export default function Register() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phone, setPhone] = useState('');
    const [promotions, setPromotions] = useState(false);
    
    const [cardType, setCardType] = useState('');
    const [cardNumber, setCardNumber] = useState('');
    const [expirationDate, setExpirationDate] = useState('');

    // Billing address fields
    const [billingAddress1, setBillingAddress1] = useState('');
    const [billingAddress2, setBillingAddress2] = useState('');
    const [billingCity, setBillingCity] = useState('');
    const [billingState, setBillingState] = useState('');
    const [billingZipCode, setBillingZipCode] = useState('');

    // Home address fields
    const [homeAddress1, setHomeAddress1] = useState('');
    const [homeAddress2, setHomeAddress2] = useState('');
    const [homeCity, setHomeCity] = useState('');
    const [homeState, setHomeState] = useState('');
    const [homeZipCode, setHomeZipCode] = useState('');

    const handleRegister = (e) => {
        e.preventDefault();
        // registration logic 
        console.log('Registering:', { 
            firstName, 
            lastName, 
            email, 
            phone, 
            password, 
            promotions, 
            cardType, 
            cardNumber,
            expirationDate,
            billingAddress1,
            billingAddress2,
            billingCity,
            billingState,
            billingZipCode,
            homeAddress1,
            homeAddress2,
            homeCity,
            homeState,
            homeZipCode,
        });
    };

    return (
        <div className="register-container">
            <h1>Register</h1>
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
                        type="text"
                        placeholder="Phone Number"  
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
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
                
                <div className="checkbox-container">
                    <input
                        type="checkbox"
                        id="promotions"
                        checked={promotions}
                        onChange={(e) => setPromotions(e.target.checked)}
                    />
                    <label htmlFor="promotions">Sign up for promotional emails</label>
                </div>

                <h3>Optional Payment Information</h3>
                <div>
                    <input
                        type="text"
                        placeholder="Card Type (e.g., Visa)"
                        value={cardType}
                        onChange={(e) => setCardType(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Card Number"
                        value={cardNumber}
                        onChange={(e) => setCardNumber(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Expiration Date (MM/YY)"
                        value={expirationDate}
                        onChange={(e) => setExpirationDate(e.target.value)}
                    />
                </div>

                <h3>Billing Address</h3>
                <div>
                    <input
                        type="text"
                        placeholder="Billing Address 1"
                        value={billingAddress1}
                        onChange={(e) => setBillingAddress1(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Billing Address 2 (Apt/Unit)"
                        value={billingAddress2}
                        onChange={(e) => setBillingAddress2(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Billing City"
                        value={billingCity}
                        onChange={(e) => setBillingCity(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Billing State"
                        value={billingState}
                        onChange={(e) => setBillingState(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Billing Zip Code"
                        value={billingZipCode}
                        onChange={(e) => setBillingZipCode(e.target.value)}
                    />
                </div>

                <h3>Home Address</h3>
                <div>
                    <input
                        type="text"
                        placeholder="Home Address 1"
                        value={homeAddress1}
                        onChange={(e) => setHomeAddress1(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Home Address 2 (Apt/Unit)"
                        value={homeAddress2}
                        onChange={(e) => setHomeAddress2(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Home City"
                        value={homeCity}
                        onChange={(e) => setHomeCity(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Home State"
                        value={homeState}
                        onChange={(e) => setHomeState(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Home Zip Code"
                        value={homeZipCode}
                        onChange={(e) => setHomeZipCode(e.target.value)}
                    />
                </div>

                <button type="submit">Register</button>
            </form>
            <p>Already have an account? <a href="/login">Login here</a></p>
            <a href="/">Back to Home</a> 
        </div>
    );
}
