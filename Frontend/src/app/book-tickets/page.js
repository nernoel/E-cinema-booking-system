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
  const [paymentMethod, setPaymentMethod] = useState('');
  const [paymentCards, setPaymentCards] = useState([]);
  const [promoCode, setPromoCode] = useState('');
  const [promoDiscount, setPromoDiscount] = useState(0);
  const [loading, setLoading] = useState(false);
  const [promoError, setPromoError] = useState('');
  const [promoUsed, setPromoUsed] = useState(false);

  const ticketPrices = {
    Child: 9.99,
    Adult: 15.99,
    Senior: 5.99,
  };

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

  useEffect(() => {
    const fetchPaymentCards = async () => {
      try {
        const storedEmail = localStorage.getItem("userEmail");
        const userResponse = await axios.get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`);
        const userId = userResponse.data.id;
        const paymentCardsResponse = await axios.get(`http://localhost:8080/api/payment-cards/user/${userId}`);
        setPaymentCards(paymentCardsResponse.data);
      } catch (error) {
        console.error("Error fetching payment cards:", error);
      }
    };
    fetchPaymentCards();
  }, []);

  useEffect(() => {
    let newTotal = selectedSeats.reduce((sum, seatId) => {
      const ageGroup = ages[seatId];
      return sum + (ticketPrices[ageGroup] || 0);
    }, 0);

    // Apply discount if promoDiscount exists
    const discountedPrice = promoDiscount > 0 ? newTotal * (1 - promoDiscount / 100) : newTotal;
    setTotalPrice(discountedPrice.toFixed(2));  // Update the total price with discount applied
  }, [selectedSeats, ages, promoDiscount]); // Recalculate when any of these change

  const handleSeatSelection = (seatId) => {
    setSelectedSeats(prev =>
      prev.includes(seatId) ? prev.filter(seat => seat !== seatId) : [...prev, seatId]
    );
  };

  const handleAgeChange = (event, seatId) => {
    setAges({ ...ages, [seatId]: event.target.value });
  };

  const handleApplyPromo = async () => {
  try {
    const storedEmail = localStorage.getItem("userEmail");
    const userResponse = await axios.get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`);
    const userId = userResponse.data.id;

    // Get the promo code details
    const promoResponse = await axios.get(`http://localhost:8080/api/promos/get-promo/${promoCode}`);
    if (promoResponse.status !== 200) {
      setPromoError("Promo code not found.");
      return;
    }

    const promo = promoResponse.data;
    
    if (promo.isActive !== "ACTIVE") {
      setPromoError("Promo code is expired.");
      return;
    }

    // Check if the user has already used this promo code
    const usageResponse = await axios.get(`http://localhost:8080/api/promos/usage/${userId}/${promo.id}`);
    
    if (usageResponse.status === 200) {
      const usage = usageResponse.data;

      if (usage.usageStatus === "USED") {
        setPromoUsed(true);
        setPromoError("You have already used this promo code.");
        return;
      }
    }

    if (usageResponse.status === 404 || usageResponse.data.usageStatus === "NOT_USED") {
      setPromoUsed(false);
      setPromoDiscount(promo.discountPercentage);
      setPromoError('');
      alert('Promo code applied successfully!');
      setPromoCode('');
    } else {
      setPromoError("Error verifying promo code usage.");
    }
  } catch (error) {
    console.error("Error applying promo code:", error);
    setPromoError("Error applying promo code.");
  }
};


  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
  
    try {
      const storedEmail = localStorage.getItem("userEmail");
      const userResponse = await axios.get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`);
      const userId = userResponse.data.id;
  
      const tickets = selectedSeats.map(seatId => {
        const seat = seats.find(seat => seat.id === seatId);
        const ticketPrice = ticketPrices[ages[seatId]] || 0;
        const ticketType = ages[seatId];
  
        return {
          userId,
          showtimeId: showTime,
          seatId: seat.id,
          ticketPrice,
          ticketType,
        };
      });
  
      const bookingData = {
        userId,
        movieId: selectedMovie,
        orderPrice: totalPrice,
        tickets,
        paymentCardId: paymentMethod,
        //promoCode,
      };
  
      // Create order
      const response = await axios.post("http://localhost:8080/api/orders/create-order", bookingData);
      const orderId = response.data.id;  // Get the order ID from the response
      alert("Booking confirmed!");
      console.log(bookingData);
  
      // Fetch the order with matching details (Optional if needed)
      const orderResponse = await axios.get(`http://localhost:8080/api/orders/get-order/${orderId}`);
      if (orderResponse.status === 200) {
        console.log("Fetched order details:", orderResponse.data);
      }
  
      // Push to order confirmation with the order ID
      router.push(`/order-confirmation?orderId=${orderId}`);
  
    } catch (error) {
      console.error("Error booking tickets:", error);
      alert("Error booking tickets. Please try again.");
    } finally {
      setLoading(false);
    }
  };
  

  return (
    <div className="book-tickets-container">
      <h1>Book Tickets</h1>
      <form onSubmit={handleSubmit}>
        {/* Movie Selection */}
        <div>
          <label htmlFor="movie">Select Movie:</label>
          <select id="movie" value={selectedMovie} onChange={(e) => setSelectedMovie(e.target.value)}>
            <option value="">Select a movie</option>
            {movies.map(movie => (
              <option key={movie.id} value={movie.id}>{movie.title}</option>
            ))}
          </select>
        </div>

        {/* Show Time Selection */}
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

        {/* Promo Code */}
        {!promoDiscount && !promoUsed && (
          <div>
            <h2>Apply Promo Code:</h2>
            <input
              type="text"
              value={promoCode}
              onChange={(e) => setPromoCode(e.target.value)}
              placeholder="Enter promo code"
            />
            <button type="button" onClick={handleApplyPromo}>
              Apply
            </button>
            {promoError && <p className="error">{promoError}</p>}
          </div>
        )}
        {promoUsed && <p className="error">You have already used this promo code.</p>}

        {/* Payment Method */}
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

        {/* Total Price */}
        <div className="price-display">
          <h2>Total Price: ${totalPrice}</h2>
        </div>

        <button type="submit" disabled={loading}>
          {loading ? "Booking..." : "Confirm Booking"}
        </button>
      </form>
    </div>
  );
};

export default BookTickets;