"use client";

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import '../styles/book-tickets.css';

const BookTickets = () => {
  const router = useRouter();
  const [selectedMovie, setSelectedMovie] = useState('');
  const [showTime, setShowTime] = useState('');
  const [seats, setSeats] = useState([]);
  const [ages, setAges] = useState({});
  const [movies, setMovies] = useState([]);
  const [showTimes, setShowTimes] = useState([]);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [paymentMethod, setPaymentMethod] = useState(''); // No default payment method yet
  const [paymentCards, setPaymentCards] = useState([]); // Store user's payment cards

  const ticketPrices = {
    Child: 9,
    Adult: 15,
    Senior: 5,
  };

  // Fetch movies
  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/movies/get-movies");
        setMovies(response.data);
      } catch (error) {
        console.error("Error fetching movies:", error);
      }
    };

    fetchMovies();
  }, []);

  // Fetch showtimes
  useEffect(() => {
    if (selectedMovie) {
      const fetchShowtimes = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/api/showtimes/get-showtimes/${selectedMovie}`);
          const formattedShowTimes = response.data.map(showtime => ({
            ...showtime,
            formattedTime: new Date(showtime.dateTime).toLocaleString(),
          }));
          setShowTimes(formattedShowTimes);
        } catch (error) {
          console.error("Error fetching showtimes:", error);
        }
      };

      fetchShowtimes();
    }
  }, [selectedMovie]);

  // Fetch seats
  useEffect(() => {
    if (showTime) {
      const fetchSeats = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/api/seats/${showTime}`);
          setSeats(response.data);
        } catch (error) {
          console.error("Error fetching seats:", error);
        }
      };

      fetchSeats();
    }
  }, [showTime]);

  // Fetch payment cards for the user
  useEffect(() => {
    const fetchPaymentCards = async () => {
      try {
        const storedEmail = localStorage.getItem("userEmail");
        
        // Fetch the user by email
        const userResponse = await axios.get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`);
        const userId = userResponse.data.id; 
  
        // Fetch payment cards for the user
        const paymentCardsResponse = await axios.get(`http://localhost:8080/api/payment-cards/user/${userId}`);
        setPaymentCards(paymentCardsResponse.data);
      } catch (error) {
        console.error("Error fetching payment cards:", error);
      }
    };
  
    fetchPaymentCards();
  }, []);

  // Calculate total price
  useEffect(() => {
    let newTotal = selectedSeats.reduce((sum, seatId) => {
      const ageGroup = ages[seatId];
      return sum + (ticketPrices[ageGroup] || 0);
    }, 0);
    setTotalPrice(newTotal);
  }, [selectedSeats, ages]);

  const handleSeatSelection = (seatId) => {
    setSelectedSeats(prev => 
      prev.includes(seatId) ? prev.filter(seat => seat !== seatId) : [...prev, seatId]
    );
  };

  const handleAgeChange = (event, seatId) => {
    setAges({ ...ages, [seatId]: event.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Assuming `storedEmail` is available and used to get `userId`
    const storedEmail = localStorage.getItem("userEmail");
    const userResponse = await axios.get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`);
    const userId = userResponse.data.id; 
    
    // Map selected seats to tickets with corresponding ages
    const tickets = selectedSeats.map(seatId => {
      const seat = seats.find(seat => seat.id === seatId); 
      const ticketPrice = ticketPrices[ages[seatId]] || 0;
      const ticketType = ages[seatId]; 

      return {
        userId: userId,
        showtimeId: showTime,
        seatId: seat.id,
        ticketPrice: ticketPrice,
        ticketType: ticketType
      };
    });

    const bookingData = {
      userId: userId,
      movieId: selectedMovie,
      orderPrice: totalPrice, 
      tickets: tickets 
    };

     // Log the booking data to the console
  console.log("Booking Data:", bookingData);

    try {
      const response = await axios.post("http://localhost:8080/api/orders/create-order", bookingData);
      alert("Booking confirmed!");

      router.push('/confirmation');
    } catch (error) {
      console.error("Error booking tickets:", error);
      alert("Error booking tickets. Please try again.");
      console.log(bookingData);
    }
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
              <option key={movie.id} value={movie.id}>{movie.title}</option>
            ))}
          </select>
        </div>

        {/* Select Show Time */}
        <div>
          <label htmlFor="show-time">Select Show Time:</label>
          <select id="show-time" value={showTime} onChange={(e) => setShowTime(e.target.value)}>
            <option value="">Select a time</option>
            {showTimes.map(time => (
              <option key={time.id} value={time.id}>{time.formattedTime}</option>
            ))}
          </select>
        </div>

        {/* Seat Selection */}
        <div>
  <h2>Select Seats:</h2>
  <div className="seats">
    {seats.map(seat => (
      <div
        key={seat.id}
        className={`seat ${selectedSeats.includes(seat.id) ? 'selected' : ''} 
          ${seat.seatStatus === 'AVAILABLE' ? 'available' : 'taken'}`}
        onClick={() => handleSeatSelection(seat.id)}
      >
        <span>Seat {seat.seatNumber}</span>
      </div>
    ))}
  </div>
</div>



        {/* Age Selection */}
        <div>
          <h2>Select Ages for Your Seats:</h2>
          {selectedSeats.map(seatId => (
            <div key={seatId}>
              <span>Seat {seatId}:</span>
              <select value={ages[seatId] || ''} onChange={(e) => handleAgeChange(e, seatId)}>
                <option value="">Select Age Group</option>
                <option value="Child">Child</option>
                <option value="Adult">Adult</option>
                <option value="Senior">Senior</option>
              </select>
            </div>
          ))}
        </div>

        {/* Payment Method Selection */}
        <div>
          <h2>Select Payment Method:</h2>
          <select value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
            <option value="">Select Payment Method</option>
            {paymentCards.map(card => (
              <option key={card.id} value={card.id}>
                {card.cardType} ending in {card.cardNumber.slice(-4)}
              </option>
            ))}
          </select>
        </div>

        {/* Display Total Price */}
        <div className="price-display">
          <h2>Total Price: ${totalPrice}</h2>
        </div>

        <button type="submit">Confirm Booking</button>
      </form>
      <a href="/" className="back-to-home">Back to Home</a>
    </div>
  );
};

export default BookTickets;
