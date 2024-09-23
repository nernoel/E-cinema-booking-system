// src/app/page.js
"use client"; // Mark this component as a Client Component

import React, { useEffect, useState } from 'react';
import './home.css'; // Import the CSS file

export default function Home() {
  // State for movies and search term
  const [movies, setMovies] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  // Dummy movie data with valid links
  const dummyMovies = [
    {
      title: "Fantastic Mr. Fox",
      genre: "Animation",
      runtime: 87,
      description: "A family of foxes tries to outwit three mean farmers.",
      releaseDate: "2009-11-13",
      trailer: "https://www.youtube.com/embed/n2igjYFojUo",
      moviePosterLink: "https://upload.wikimedia.org/wikipedia/en/a/af/Fantastic_mr_fox.jpg",
      category: "Currently Running"
    },
    {
      title: "9",
      genre: "Animation, Action, Adventure",
      runtime: 79,
      description: "A group of sentient rag dolls must fight for their survival against machines that have wiped out humanity.",
      releaseDate: "2009-09-09",
      trailer: "https://www.youtube.com/embed/RSDlFmVo330",
      moviePosterLink: "https://upload.wikimedia.org/wikipedia/en/c/c9/9posterfinal.jpg",
      category: "Coming Soon"
    }
  ];

  useEffect(() => {
    // Set movies to dummy data
    setMovies(dummyMovies);
  }, []);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  const performSearch = () => {
    console.log("Searching for:", searchTerm);
  };

  return (
    <div>
      {/* Navigation Bar */}
      <nav className="navbar">
        <h1 className="logo">E-Cinema</h1>
        <div className="nav-links">
          <a href="/">Home</a>
          <a href="/login">Login/Register</a>
        </div>
      </nav>

      <h1>Welcome to the E-Cinema Booking System</h1>

      {/* Search Bar */}
      <div>
        <input
          type="text"
          placeholder="Search for a movie"
          onChange={handleSearch}
          value={searchTerm}
        />
        <button onClick={performSearch}>Search</button>
      </div>

      {/* Movie Categories */}
      {['Currently Running', 'Coming Soon'].map(category => (
        <div key={category}>
          <h2>{category}</h2>
          {movies.filter(movie => movie.category === category && movie.title.toLowerCase().includes(searchTerm))
                 .map((movie, index) => (
            <div key={index}>
              <h3>{movie.title}</h3>
              <img src={movie.moviePosterLink} alt={movie.title} style={{ width: '200px', height: 'auto' }} />
              <iframe 
                width="560" 
                height="315" 
                src={movie.trailer} 
                title={movie.title} 
                allowFullScreen
              ></iframe>
              <button>Book Movie</button>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}
