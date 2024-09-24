"use client"; 

import axios from 'axios';
import React, { useEffect, useState } from 'react';
import './home-loggedout.css'; 

export default function Home() {
   // Movies array is initially empty
  const [movies, setMovies] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

    // Defining API URL
  const API_URL = "http://localhost:8080/api/movies";

  // Defining function to fetch API
  const fetchData = async () => {
    try {
      const { data } = await axios.get(API_URL);
      setMovies(data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData(); 
  }, []);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  const performSearch = () => {
    console.log("Searching for:", searchTerm);
  };

  return (
    <div>
      {/* navigation bar */}
      <nav className="navbar">
        <h1 className="logo">E-Cinema</h1>
        <div className="nav-links">
          <a href="/">Home</a>
          <a href="/login">Book Tickets</a>
          <a href="/login">Login/Register</a>
        </div>
      </nav>

      <h1>Welcome to the E-Cinema Booking System</h1>

      {/* search bar */}
      <div>
        <input
          type="text"
          placeholder="Search for a movie"
          onChange={handleSearch}
          value={searchTerm}
        />
        <button onClick={performSearch}>Search</button>
      </div>

      {/* Display movies */}
      <h2>Movies</h2>
      {movies.filter(movie => movie.title.toLowerCase().includes(searchTerm))
             .map((movie, index) => (
        <div key={index}>
          <h3>{movie.title}</h3>
          <img src={movie.moviePosterLink} alt={movie.title} style={{ width: '200px', height: 'auto' }} />
          <iframe width="560" height="315" src={movie.trailer} title={movie.title} allowFullScreen></iframe>
          <button onClick={() => window.location.href = '/login'}>Book Movie</button>
        </div>
      ))}
    </div>
  );
}
