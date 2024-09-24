// src/app/order-summary/page.js

"use client";

import React, { useState } from 'react';
import './order-summary.css'; // import the css

const OrderSummary = () => {
  // sample data for the order summary
  const [order, setOrder] = useState([
    { seat: 1, age: 'Adult', price: 10.00 },
    { seat: 2, age: 'Child', price: 5.00 },
  ]);

  const handleDeleteTicket = (index) => {
    const newOrder = order.filter((_, i) => i !== index);
    setOrder(newOrder);
  };

  const handleUpdateOrder = () => {
    // will update logic later 
    alert("Update functionality not implemented yet.");
  };

  const handleConfirmBooking = () => {
    // will change this later 
    alert("Confirm functionality not implemented yet.");
  };

  // calculate total price
  const totalPrice = order.reduce((total, item) => total + item.price, 0).toFixed(2);

  return (
    <div className="order-summary-container">
      <h1>Order Summary</h1>
      <table>
        <thead>
          <tr>
            <th>Seat</th>
            <th>Age</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {order.map((item, index) => (
            <tr key={index}>
              <td>{item.seat}</td>
              <td>{item.age}</td>
              <td>${item.price.toFixed(2)}</td>
              <td>
                <button onClick={() => handleDeleteTicket(index)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <h2>Total Price: ${totalPrice}</h2>
      <button onClick={handleUpdateOrder}>Update Order</button>
      <button onClick={handleConfirmBooking}>Confirm Booking</button>
      <a href="/">Back to Home</a> {/* Link back to home, centered */}
    </div>
  );
};

export default OrderSummary;
