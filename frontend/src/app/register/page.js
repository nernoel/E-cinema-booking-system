// src/app/register/page.js

"use client"; 

import React, { useState } from 'react';
<<<<<<< HEAD
import { useRouter } from 'next/navigation'; 
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
    
    const [cardholderName, setCardholderName] = useState(''); 
    const [billingAddress1, setBillingAddress1] = useState('');
    const [billingAddress2, setBillingAddress2] = useState('');
    const [billingCity, setBillingCity] = useState('');
    const [billingState, setBillingState] = useState('');
    const [billingZipCode, setBillingZipCode] = useState('');

    
    const [homeAddress1, setHomeAddress1] = useState('');
    const [homeAddress2, setHomeAddress2] = useState('');
    const [homeCity, setHomeCity] = useState('');
    const [homeState, setHomeState] = useState('');
    const [homeZipCode, setHomeZipCode] = useState('');

    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const router = useRouter(); 

    const validateEmail = (email) => {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    };

    const validatePhoneNumber = (phone) => {
        const regex = /^[0-9]{10,15}$/;
        return regex.test(phone);
    };

    const validatePassword = (password) => {
        const regex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        return regex.test(password);
    };

    const validateCardNumber = (number) => {
        const regex = /^[0-9]{13,19}$/;
        return regex.test(number);
    };

    const validateExpirationDate = (date) => {
        const regex = /^(0[1-9]|1[0-2])\/?([0-9]{2}|[0-9]{4})$/;
        return regex.test(date);
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        setErrorMessage('');
        
        if (!firstName || !lastName || !email || !password || !phone) {
            setErrorMessage('Please fill in all required fields.');
            return;
        }

        if (!validateEmail(email)) {
            setErrorMessage('Please enter a valid email address.');
            return;
        }

        if (!validatePhoneNumber(phone)) {
            setErrorMessage('Please enter a valid phone number with 10-15 digits.');
            return;
        }

        if (!validatePassword(password)) {
            setErrorMessage('Password must be at least 8 characters long, with at least one capital letter and one special character.');
            return;
        }

        // Validate billing info only if at least one billing field is provided
        if (cardholderName || cardType || cardNumber || expirationDate || billingAddress1 || billingCity || billingState || billingZipCode) {
            if (!cardholderName) {
                setErrorMessage('Please enter the cardholder name.');
                return;
            }

            if (!cardType) {
                setErrorMessage('Please enter a card type.');
                return;
            }

            if (!validateCardNumber(cardNumber)) {
                setErrorMessage('Please enter a valid card number (13-19 digits).');
                return;
            }

            if (!validateExpirationDate(expirationDate)) {
                setErrorMessage('Please enter a valid expiration date (MM/YY).');
                return;
            }

            if (!billingAddress1 || !billingCity || !billingState || !billingZipCode) {
                setErrorMessage('Please fill in all billing address fields.');
                return;
            }
        }

        // Validate home address info only if at least one home address field is provided
        if (homeAddress1 || homeCity || homeState || homeZipCode) {
            if (!homeAddress1 || !homeCity || !homeState || !homeZipCode) {
                setErrorMessage('Please fill in all home address fields.');
                return;
            }
        }

        setLoading(true);
        
        try {
            const response = await fetch('/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ 
                    firstName, 
                    lastName, 
                    email, 
                    phone, 
                    password, 
                    promotions, 
                    cardType, 
                    cardNumber,
                    expirationDate,
                    cardholderName, 
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
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                setErrorMessage(errorData.message || 'Registration failed. Please try again.');
            } else {
                router.push('/register-confirmation'); 
            }
        } catch (error) {
            setErrorMessage('An error occurred. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-container">
            <h1>Register</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
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
                        placeholder="Cardholder Name"
                        value={cardholderName}
                        onChange={(e) => setCardholderName(e.target.value)}
                    />
                </div>
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

                <div>
                    <button type="submit" disabled={loading}>
                        {loading ? 'Registering...' : 'Register'}
                    </button>
                </div>
            </form>
            <div className="register-links">
                <a href="/login">Already have an account? Log in</a>
                <br />
                <a href="/">Back to Home</a>
            </div>
        </div>
    );
=======
import './register.css'; // import the css 

export default function Register() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = (e) => {
    e.preventDefault();
    // registration logic 
    console.log('Registering:', { firstName, lastName, email, password });
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
      <a href="/">Back to Home</a> {/* Link back to home */}
    </div>
  );
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe
}
