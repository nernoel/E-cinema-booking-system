// src/app/checkout/page.js

"use client"; 

import React, { useState } from 'react';
import './checkout.css'; // import the css

const Checkout = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [cardNumber, setCardNumber] = useState('');
  const [expDate, setExpDate] = useState('');
  const [cvv, setCvv] = useState('');
  const [billingAddress, setBillingAddress] = useState('');

  const handleConfirm = (event) => {
    event.preventDefault();
    console.log("Checkout details submitted:", { name, email, cardNumber, expDate, cvv, billingAddress });
    // logic for confirmation later
  };

  const handleCancel = () => {
    console.log("Checkout cancelled.");
    // redirect to home page
  };

  return (
    <div className="checkout-container">
      <h1>Checkout</h1>
      <form onSubmit={handleConfirm}>
        <div className="form-group">
          <label htmlFor="name">Name on Card:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="cardNumber">Card Number:</label>
          <input
            type="text"
            id="cardNumber"
            value={cardNumber}
            onChange={(e) => setCardNumber(e.target.value)}
            required
            maxLength={16}
            placeholder="1234 5678 1234 5678"
          />
        </div>

        <div className="form-group">
          <label htmlFor="expDate">Expiration Date:</label>
          <input
            type="text"
            id="expDate"
            value={expDate}
            onChange={(e) => setExpDate(e.target.value)}
            required
            placeholder="MM/YY"
          />
        </div>

        <div className="form-group">
          <label htmlFor="cvv">CVV:</label>
          <input
            type="password"
            id="cvv"
            value={cvv}
            onChange={(e) => setCvv(e.target.value)}
            required
            maxLength={3}
            placeholder="123"
          />
        </div>

        <div className="form-group">
          <label htmlFor="billingAddress">Billing Address:</label>
          <input
            type="text"
            id="billingAddress"
            value={billingAddress}
            onChange={(e) => setBillingAddress(e.target.value)}
            required
          />
        </div>

        <div className="checkout-buttons">
          <button type="button" className="cancel-button" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="confirm-button">Confirm</button>
        </div>
      </form>
    </div>
  );
};

export default Checkout;
