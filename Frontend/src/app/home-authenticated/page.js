"use client"

import React, { useEffect, useState } from "react";
import "../styles/home-authenticated.css";
import { useRouter } from "next/navigation";
import Link from "next/link";
import axios from "axios";

export default function Home() {
    const [movies, setMovies] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedMovie, setSelectedMovie] = useState(null);
    const [filteredMovies, setFilteredMovies] = useState([]);
    const [isLoggedIn, setIsLoggedIn] = useState(false); // Track if user is logged in
    const router = useRouter();

    useEffect(() => {
        const token = localStorage.getItem("authToken");
        if (token) {
            setIsLoggedIn(true); // User is logged in
        } else {
            router.push("/login"); // Redirect to login if not logged in
        }

        axios
            .get("http://localhost:8080/api/movies/get-movies")
            .then((response) => {
                setMovies(response.data);
                setFilteredMovies(response.data);
            })
            .catch((error) => console.error("Error fetching movies:", error));
    }, [router]);

    const handleSearch = (event) => {
        const term = event.target.value.toLowerCase();
        setSearchTerm(term);
        setFilteredMovies(
            term === ""
                ? movies
                : movies.filter((movie) =>
                      movie.title.toLowerCase().includes(term)
                  )
        );
    };

    const handleLogout = () => {
        if (window.confirm("Are you sure you want to logout?")) {
            localStorage.removeItem("authToken");
            localStorage.removeItem("userEmail");
            router.push("/login");
        }
    };

    const isCurrentlyRunning = (releaseDate) => {
        const now = new Date();
        return new Date(releaseDate) <= now;
    };

    const currentlyRunningMovies = filteredMovies.filter((movie) =>
        isCurrentlyRunning(movie.releaseDate)
    );
    const comingSoonMovies = filteredMovies.filter(
        (movie) => !isCurrentlyRunning(movie.releaseDate)
    );

    // Extracts the YouTube video ID and returns the embed URL
    const getEmbedUrl = (url) => {
        let videoId;
        if (url.includes('youtu.be')) {
            videoId = url.split('youtu.be/')[1]?.split('?')[0];
        } else if (url.includes('v=')) {
            videoId = url.split('v=')[1]?.split('&')[0];
        }
        return videoId ? `https://www.youtube.com/embed/${videoId}` : '';
    };

    return (
        <div className="home-container">
            <div className="container">
                <nav className="navbar">
                    <h1 className="logo">E-Cinema</h1>
                    <div className="nav-links">
                        <Link href="/">Home</Link>
                        <Link href="/book-tickets">Book Tickets</Link>
                        <Link href="/edit-profile">Edit Profile</Link>
                        <button onClick={handleLogout} className="logout-button">
                            Logout
                        </button>
                    </div>
                </nav>

                <header className="welcome-header">
                    <h1>Welcome to Ecinema</h1>
                </header>

                <div className="search-bar">
                    <input
                        type="text"
                        placeholder="Search for a movie..."
                        value={searchTerm}
                        onChange={handleSearch}
                        className="search-input"
                    />
                </div>

                <main className="movies-section">
                    {filteredMovies.length > 0 ? (
                        <>
                            <section className="movie-category">
                                <h2>Currently Running</h2>
                                <div className="movie-list">
                                    {currentlyRunningMovies.map((movie) => (
                                        <div
                                            key={movie.id}
                                            className="movie-card"
                                            onClick={() => setSelectedMovie(movie)}
                                        >
                                            <h3>{movie.title}</h3>
                                            <img
                                                src={movie.moviePosterLink}
                                                alt={movie.title}
                                                className="movie-image"
                                            />
                                        </div>
                                    ))}
                                </div>
                            </section>

                            <section className="movie-category">
                                <h2>Coming Soon</h2>
                                <div className="movie-list">
                                    {comingSoonMovies.map((movie) => (
                                        <div
                                            key={movie.id}
                                            className="movie-card"
                                            onClick={() => setSelectedMovie(movie)}
                                        >
                                            <h3>{movie.title}</h3>
                                            <img
                                                src={movie.imageUrl}
                                                alt={movie.title}
                                                className="movie-image"
                                            />
                                        </div>
                                    ))}
                                </div>
                            </section>
                        </>
                    ) : (
                        <p className="no-movies-message">
                            No movies found for the search term "{searchTerm}".
                        </p>
                    )}
                </main>

                {selectedMovie && (
                    <div className="movie-details-modal">
                        <div className="movie-details">
                            <h2>{selectedMovie.title}</h2>
                            <p>
                                <strong>Genre:</strong> {selectedMovie.genre}
                            </p>
                            <p>
                                <strong>Runtime:</strong> {selectedMovie.runtime} minutes
                            </p>
                            <p>
                                <strong>Description:</strong> {selectedMovie.description}
                            </p>
                            <p>
                                <strong>Rating:</strong> {selectedMovie.rating}
                            </p>
                            <p>
                                <strong>Release Date:</strong> {selectedMovie.releaseDate}
                            </p>

                            <iframe
                                width="560"
                                height="315"
                                src={getEmbedUrl(selectedMovie.trailer)}
                                title={selectedMovie.title}
                                allowFullScreen
                                className="movie-trailer"
                            ></iframe>

                            <button
                                className="close-modal-button"
                                onClick={() => setSelectedMovie(null)}
                            >
                                Close
                            </button>

                            <div className="modal-footer">
                                {!isLoggedIn ? (
                                    <p className="login-note">
                                        Please login to purchase tickets.
                                    </p>
                                ) : (
                                    <button
                                        className="new-action-button"
                                        onClick={() =>
                                            router.push(
                                                `/book-tickets?movieId=${selectedMovie.id}`
                                            )
                                        }
                                    >
                                        Purchase Tickets
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}
