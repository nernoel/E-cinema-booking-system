"use client";
import moment from "moment";
import React, { useEffect, useState } from "react";
import axios from "axios";
import "../styles/order-history.css";

const OrderHistory = () => {
  const [orders, setOrders] = useState([]);
  const [movieTitles, setMovieTitles] = useState({});

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const storedEmail = localStorage.getItem("userEmail");
        
        // Fetch the user by email
        const userResponse = await axios.get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`);
        const id = userResponse.data.id;
        
        // Fetch orders based on user ID
        const response = await axios.get(`http://localhost:8080/api/orders/user/${id}`);
        setOrders(response.data);
        
        // Fetch movie titles for all orders
        const movieData = {};
        for (let order of response.data) {
          const movieResponse = await axios.get(`http://localhost:8080/api/movies/${order.movieId}`);
          movieData[order.id] = movieResponse.data.title; 
        }
        setMovieTitles(movieData);
      } catch (error) {
        console.error("Error fetching order history:", error);
      }
    };

    fetchOrders();
  }, []);

  return (
    <div className="order-history-container">
      <h1>Order History</h1>
      {orders.length > 0 ? (
        <table className="order-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Movie</th>
              <th>Date</th>
              <th>Tickets</th>
              <th>Total Amount</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.id}>
                <td>{order.id}</td>
                <td>{movieTitles[order.id] || 'Loading...'}</td>
                <td>{moment(order.orderDate, "YYYYMMDDHHmm").format("YYYY-MM-DD HH:mm")}</td>
                <td>
                  {/* Display ticket details */}
                  {order.tickets ? order.tickets.map((ticket, index) => (
                    <div key={index}>
                      <p>Seat: {ticket.seatId} | Type: {ticket.ticketType} | Price: ${ticket.ticketPrice}</p>
                    </div>
                  )) : 'No tickets'}
                </td>
                <td>${order.orderPrice}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No orders found.</p>
      )}
    </div>
  );
};

export default OrderHistory;
