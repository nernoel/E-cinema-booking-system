"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import axios from "axios";
import "../styles/order-confirmation.css";

const OrderConfirmation = () => {
  const router = useRouter();
  const [bookingData, setBookingData] = useState(null);
  const [movie, setMovie] = useState(null); // State to store movie details
  const [paymentCard, setPaymentCard] = useState(null); // State to store payment card details

  useEffect(() => {
    // Retrieve the booking data from localStorage
    const storedBookingData = localStorage.getItem("bookingData");
    if (storedBookingData) {
      const parsedBookingData = JSON.parse(storedBookingData);
      setBookingData(parsedBookingData);

      // Fetch the movie and payment card details
      fetchMovieDetails(parsedBookingData.movieId);
      fetchPaymentCardDetails(parsedBookingData.paymentCardId);
    } else {
      router.push("/home-authenticated"); 
    }
  }, [router]);

  const fetchMovieDetails = async (movieId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/movies/${movieId}`);
      setMovie(response.data); // Set the movie details
    } catch (error) {
      console.error("Error fetching movie:", error);
    }
  };

  const fetchPaymentCardDetails = async (paymentCardId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/payment-cards/${paymentCardId}`);
      setPaymentCard(response.data); // Set the payment card details
    } catch (error) {
      console.error("Error fetching payment card:", error);
    }
  };

  if (!bookingData) {
    return <div>Loading...</div>;
  }

  return (
    <div className="order-confirmation-container">
      <h1>Order Confirmation</h1>
      <div className="confirmation-details">
        <h2>Booking Summary</h2>
        <p><strong>Movie:</strong> {movie ? movie.title : "Loading movie details..."}</p>
        <p><strong>Total Price:</strong> ${bookingData.orderPrice}</p>

        <h3>Tickets</h3>
        <ul>
          {bookingData.tickets.map(ticket => (
            <li key={ticket.seatId}>
              <p>Seat: {ticket.seatId}</p>
              <p>Ticket Type: {ticket.ticketType}</p>
              <p>Price: ${ticket.ticketPrice}</p>
            </li>
          ))}
        </ul>

        <h3>Payment Method</h3>
        <p>
          {paymentCard 
            ? `Card ending in ${paymentCard.cardNumber.slice(-4)}`
            : "No payment method selected"}
        </p>

        <button onClick={() => router.push("/")}>Back to Home</button>
      </div>
    </div>
  );
};

export default OrderConfirmation;
