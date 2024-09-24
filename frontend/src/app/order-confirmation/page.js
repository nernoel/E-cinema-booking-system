// src/app/order-confirmation/page.js

"use client"; 

import React from 'react';
import './order-confirmation.css'; // import the css

const OrderConfirmation = () => {
  return (
    <div className="confirmation-container">
      <h1>Order Confirmed!</h1>
      <div className="order-summary">
        <p>Thank you for your purchase. Your order has been successfully processed.</p>
        <p><strong>Order Number:</strong> 123456789</p>
        <p><strong>Tickets:</strong> 2 x Adult, 1 x Child</p>
        <p><strong>Total Price:</strong> $45.00</p>
      </div>
      <div className="confirmation-buttons">
        <button className="home-button" onClick={() => window.location.href = "/"}>Back to Home</button>
      </div>
    </div>
  );
};

export default OrderConfirmation;
