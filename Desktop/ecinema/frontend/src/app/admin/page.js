"use client";
import React, { useState } from "react";
import '../styles/admin.css';

const AdminDashboard = () => {
  return (
    <div className="adminDashboard">
      <h1>Admin Dashboard</h1>
      <button onClick={() => window.location.href = '/manageMovies'}>Manage Movies</button>
      <button onClick={() => window.location.href = '/managePromotions'}>Manage Promotions</button>
      <button onClick={() => window.location.href = '/manageUsers'}>Manage Users</button>
    </div>
  );
};

const ManageMovies = () => {
  const [movies, setMovies] = useState([]);
  const [movieTitle, setMovieTitle] = useState("");
  const [releaseDate, setReleaseDate] = useState("");
  const [schedule, setSchedule] = useState("");

  const handleAddMovie = (e) => {
    e.preventDefault();
    const newMovie = { movieTitle, releaseDate, schedule };
    setMovies([...movies, newMovie]);
    setMovieTitle("");
    setReleaseDate("");
    setSchedule("");
  };

  return (
    <div className="manageMovies">
      <h1>Manage Movies</h1>
      <form onSubmit={handleAddMovie}>
        <label htmlFor="movieTitle">Movie Title: </label>
        <input
          type="text"
          id="movieTitle"
          value={movieTitle}
          onChange={(e) => setMovieTitle(e.target.value)}
          required
        />

        <label htmlFor="releaseDate">Release Date: </label>
        <input
          type="date"
          id="releaseDate"
          value={releaseDate}
          onChange={(e) => setReleaseDate(e.target.value)}
          required
        />

        <label htmlFor="schedule">Schedule: </label>
        <input
          type="text"
          id="schedule"
          value={schedule}
          onChange={(e) => setSchedule(e.target.value)}
          required
          placeholder="Showtime Schedule"
        />

        <button type="submit">Add Movie</button>
      </form>

      <h2>Existing Movies</h2>
      <ul id="movieList">
        {movies.map((movie, index) => (
          <li key={index}>{`${movie.movieTitle} - ${movie.releaseDate} - ${movie.schedule}`}</li>
        ))}
      </ul>
    </div>
  );
};

const ManagePromotions = () => {
  const [promotions, setPromotions] = useState([]);
  const [promoName, setPromoName] = useState("");
  const [promoDiscount, setPromoDiscount] = useState("");

  const handleAddPromotion = (e) => {
    e.preventDefault();
    const newPromotion = { promoName, promoDiscount };
    setPromotions([...promotions, newPromotion]);
    setPromoName("");
    setPromoDiscount("");
  };

  return (
    <div className="managePromotions">
      <h1>Manage Promotions</h1>
      <form onSubmit={handleAddPromotion}>
        <label htmlFor="promoName">Promotion Name: </label>
        <input
          type="text"
          id="promoName"
          value={promoName}
          onChange={(e) => setPromoName(e.target.value)}
          required
        />
        <label htmlFor="promoDiscount">Discount Percent: </label>
        <input
          type="number"
          id="promoDiscount"
          value={promoDiscount}
          onChange={(e) => setPromoDiscount(e.target.value)}
          min="0"
          max="100"
          required
        />
        <button type="submit">Add Promotion</button>
      </form>

      <h2>Existing Promotions</h2>
      <ul id="promotionList">
        {promotions.map((promotion, index) => (
          <li key={index}>{`${promotion.promoName} - ${promotion.promoDiscount}%`}</li>
        ))}
      </ul>
    </div>
  );
};

const AdminMainMenu = () => {
  const handleLogout = () => {
    // Clear the local session token (or other state indicating the user is logged in)
    localStorage.removeItem('authToken');
    localStorage.removeItem('userEmail');
    // Optionally, you can clear any other session data as needed
    window.location.href = "/login"; // Redirect to the login page
  };

  return (
    <div>
      <AdminDashboard />
      <ManageMovies />
      <ManagePromotions />
      <button className="logout-button" onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default AdminMainMenu;
