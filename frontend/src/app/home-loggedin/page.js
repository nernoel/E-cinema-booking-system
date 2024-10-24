// src/app/home-loggedin/page.js

"use client"; 

import React, { useEffect, useState } from 'react';
<<<<<<< HEAD
import { useRouter } from 'next/navigation'; 
import './home-loggedin.css'; // import the css

export default function Home() {
  const router = useRouter(); 
  
=======
import './home-loggedin.css'; // import the css

export default function Home() {
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe
  // state for movies and search term
  const [movies, setMovies] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  // dummy movie data with valid links
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
    // set movies to dummy data
    setMovies(dummyMovies);
  }, []);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  const performSearch = () => {
    console.log("Searching for:", searchTerm);
  };

<<<<<<< HEAD
  // logout function
  const handleLogout = () => {
    // clear user session
    localStorage.removeItem('userToken'); 

    // redirect to home-loggedout page
    router.push('/home-loggedout'); 
  };

=======
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe
  return (
    <div>
      {/* navigation bar */}
      <nav className="navbar">
        <h1 className="logo">E-Cinema</h1>
        <div className="nav-links">
          <a href="/">Home</a>
          <a href="/book-tickets">Book Tickets</a> {/* Add Book Tickets tab */}
          <a href="/edit-profile">Edit Profile</a> {/* Change here */}
<<<<<<< HEAD
          <button onClick={handleLogout}>Logout</button> {/* Logout button */}
=======
>>>>>>> f9678918d058ed6352f97f7b7b6b86906ce01bbe
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

      {/* movie categories */}
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
              <button onClick={() => window.location.href = "/book-tickets"}>Book Movie</button> {/* Redirect to Book Tickets */}
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}
