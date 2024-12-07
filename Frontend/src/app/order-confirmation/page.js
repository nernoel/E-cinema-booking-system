"use client";
import React from "react";
import { useRouter } from "next/navigation";
import "../styles/confirmation.css";

const Confirmation = () => {
  const router = useRouter();

  const handleGoHome = () => {
    router.push("/");
  };

  return (
    <div className="confirmation-container">
      <h1>Booking Confirmed!</h1>
      <p>Thank you for your purchase. Your booking has been successfully processed.</p>
      <p>A confirmation email has been sent to your registered email address.</p>

      <button onClick={handleGoHome} className="home-button">
        Back to Home
      </button>
    </div>
  );
};

export default Confirmation;
