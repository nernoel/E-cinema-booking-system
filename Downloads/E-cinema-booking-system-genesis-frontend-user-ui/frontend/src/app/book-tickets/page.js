// src/app/book-tickets/page.js

"use client"; 

import React, { useState } from 'react';
import { useRouter } from 'next/navigation'; 
import './book-tickets.css'; // import the css 

// dummy data 
const BookTickets = () => {
  const router = useRouter(); // get router for navigation
  const [selectedMovie, setSelectedMovie] = useState('');
  const [showTime, setShowTime] = useState('');
  const [seats, setSeats] = useState(Array(10).fill(false));
  const [ages, setAges] = useState(Array(10).fill(''));

  const movies = [
    { title: "Fantastic Mr. Fox", id: 1 },
    { title: "9", id: 2 }
  ];

  const showTimes = [
    "10:00 AM",
    "1:00 PM",
    "4:00 PM",
    "7:00 PM"
  ];

  const handleSeatSelection = (index) => {
    const newSeats = [...seats];
    newSeats[index] = !newSeats[index];
    setSeats(newSeats);
  };

  const handleAgeChange = (event, index) => {
    const newAges = [...ages];
    newAges[index] = event.target.value;
    setAges(newAges);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("Selected Movie:", selectedMovie);
    console.log("Show Time:", showTime);
    console.log("Selected Seats:", seats);
    console.log("Ages:", ages);
  };

  return (
    <div className="book-tickets-container">
      <h1>Book Tickets</h1>
      <form onSubmit={handleSubmit}>
        {/* Select Movie */}
        <div>
          <label htmlFor="movie">Select Movie:</label>
          <select id="movie" value={selectedMovie} onChange={(e) => setSelectedMovie(e.target.value)}>
            <option value="">Select a movie</option>
            {movies.map(movie => (
              <option key={movie.id} value={movie.title}>{movie.title}</option>
            ))}
          </select>
        </div>

        {/* Select Show Time */}
        <div>
          <label htmlFor="show-time">Select Show Time:</label>
          <select
            id="show-time"
            value={showTime}
            onChange={(e) => setShowTime(e.target.value)}
          >
            <option value="">Select a time</option>
            {showTimes.map((time, index) => (
              <option key={index} value={time}>{time}</option>
            ))}
          </select>
        </div>

        {/* Seat Selection */}
        <div>
          <h2>Select Seats:</h2>
          <div className="seats">
            {seats.map((isSelected, index) => (
              <div key={index} className={`seat ${isSelected ? 'selected' : ''}`} onClick={() => handleSeatSelection(index)}>
                <span>Seat {index + 1}</span>
              </div>
            ))}
          </div>
        </div>

        {/* Age Selection for Selected Seats */}
        <div>
          <h2>Select Ages for Your Seats:</h2>
          {seats.map((isSelected, index) => (
            isSelected && (
              <div key={index}>
                <span>Seat {index + 1}:</span>
                <select
                  value={ages[index]}
                  onChange={(e) => handleAgeChange(e, index)}
                  className="age-select"
                >
                  <option value="">Select Age Group</option>
                  <option value="Child">Child</option>
                  <option value="Adult">Adult</option>
                  <option value="Senior">Senior</option>
                </select>
              </div>
            )
          ))}
          {seats.every(seat => !seat) && <p>Please select at least one seat to choose an age.</p>}
        </div>

        <button type="submit">Confirm Booking</button>
      </form>
      <a href="/" className="back-to-home">Back to Home</a> {/* Link back to home */}
    </div>
  );
};

export default BookTickets;
