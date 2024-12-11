"use client";

import React, { useState, useEffect } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import axios from "axios";
import "../styles/order-confirmation.css";

const OrderConfirmation = () => {
  const [orderDetails, setOrderDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const router = useRouter();
  const searchParams = useSearchParams();
  const orderId = searchParams.get("orderId");

  useEffect(() => {
    if (!orderId) {
      setError("Order ID is missing.");
      setLoading(false);
      return;
    }

    const fetchOrderDetails = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/orders/get-order/${orderId}`
        );
        setOrderDetails(response.data);
      } catch (error) {
        console.error("Error fetching order details:", error);
        setError("Failed to fetch order details.");
      } finally {
        setLoading(false);
      }
    };

    fetchOrderDetails();
  }, [orderId]);

  const handleBackToHome = () => {
    router.push("/home-authenticated");
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p className="error">{error}</p>;

  return (
    <div className="order-confirmation-container">
      <h1>Order Confirmation</h1>
      {orderDetails && (
        <>
          <h2>Thank you for your purchase!</h2>
          <p><strong>Order ID:</strong> {orderDetails.id}</p>
          <p>
            {/*<strong>Order Date:</strong>{" "}
            {new Date(...orderDetails.orderDate).toLocaleString()*/}
          </p>
          <p><strong>Total Price:</strong> ${orderDetails.orderPrice.toFixed(2)}</p>
          {/*<p><strong>Payment Method ID:</strong> {orderDetails.paymentCardId}</p>*/}
          <h3>Tickets</h3>
          <ul className="tickets-text">
            {orderDetails.tickets.map((ticket) => (
              <li key={ticket.id}>
                <strong>Seat:</strong> {ticket.seatId}, <strong>Type:</strong>{" "}
                {ticket.ticketType}, <strong>Price:</strong> ${ticket.ticketPrice.toFixed(2)}
              </li>
            ))}
          </ul>
          <button className="back-to-home-button" onClick={handleBackToHome}>
            Back to Home
          </button>
        </>
      )}
    </div>
  );
};

export default OrderConfirmation;
