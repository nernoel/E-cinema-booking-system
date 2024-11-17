'use client';

import { useState } from "react";
import "../styles/admin.css";

const AdminDashboard = ({ setActiveSection }) => {
return (
    <div className="adminDashboard">
    <h1>Admin Dashboard</h1>
    <button onClick={() => setActiveSection("manageMovies")}>Manage Movies</button>
    <button onClick={() => setActiveSection("managePromotions")}>Manage Promotions</button>
    <button onClick={() => setActiveSection("scheduleMovie")}>Schedule Movie</button>
    </div>
);
};

const ManageMovies = ({ movies, setMovies }) => {
const [movieTitle, setMovieTitle] = useState("");
const [releaseDate, setReleaseDate] = useState("");
const [schedule, setSchedule] = useState("");

const handleAddMovie = (e) => {
    e.preventDefault();
    if (!movieTitle || !releaseDate || !schedule) {
    alert("All fields are required.");
    return;
    }

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

const ManagePromotions = ({ promotions, setPromotions, users }) => {
const [promoName, setPromoName] = useState("");
const [promoDiscount, setPromoDiscount] = useState("");

const handleAddPromotion = (e) => {
    e.preventDefault();
    if (!promoName || !promoDiscount) {
    alert("All fields are required.");
    return;
    }

    const newPromotion = { promoName, promoDiscount, sent: false };
    setPromotions([...promotions, newPromotion]);
    setPromoName("");
    setPromoDiscount("");
};

const handleSendPromotion = (index) => {
    const updatedPromotions = [...promotions];
    if (updatedPromotions[index].sent) {
    alert("This promotion has already been sent and cannot be modified.");
    return;
    }

    updatedPromotions[index].sent = true;
    setPromotions(updatedPromotions);

    // Simulate sending promotion to subscribed users
    const subscribedUsers = users.filter((user) => user.subscribed);
    alert(
    `Promotion "${updatedPromotions[index].promoName}" sent to ${subscribedUsers.length} subscribed users.`
    );
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
        <li key={index}>
            {`${promotion.promoName} - ${promotion.promoDiscount}%`}
            {promotion.sent ? (
            <span> (Sent)</span>
            ) : (
            <button onClick={() => handleSendPromotion(index)}>Send Promotion</button>
            )}
        </li>
        ))}
    </ul>
    </div>
);
};

const ScheduleMovie = ({ schedules, setSchedules }) => {
const [movieId, setMovieId] = useState("");
const [showroom, setShowroom] = useState("");
const [dateTime, setDateTime] = useState("");

const handleScheduleMovie = (e) => {
    e.preventDefault();
    if (!movieId || !showroom || !dateTime) {
    alert("All fields are required.");
    return;
    }

    const conflict = schedules.some(
    (schedule) => schedule.showroom === showroom && schedule.dateTime === dateTime
    );
    if (conflict) {
    alert("Scheduling conflict detected!");
    return;
    }

    const newSchedule = { movieId, showroom, dateTime };
    setSchedules([...schedules, newSchedule]);
    setMovieId("");
    setShowroom("");
    setDateTime("");
};

return (
    <div className="scheduleMovie">
    <h1>Schedule a Movie</h1>
    <form onSubmit={handleScheduleMovie}>
        <label htmlFor="movieId">Movie ID: </label>
        <input
        type="text"
        id="movieId"
        value={movieId}
        onChange={(e) => setMovieId(e.target.value)}
        required
        />

        <label htmlFor="showroom">Showroom: </label>
        <input
        type="text"
        id="showroom"
        value={showroom}
        onChange={(e) => setShowroom(e.target.value)}
        required
        />

        <label htmlFor="dateTime">Date and Time: </label>
        <input
        type="datetime-local"
        id="dateTime"
        value={dateTime}
        onChange={(e) => setDateTime(e.target.value)}
        required
        />

        <button type="submit">Schedule Movie</button>
    </form>

    <h2>Existing Schedules</h2>
    <ul id="scheduleList">
        {schedules.map((schedule, index) => (
        <li key={index}>{`Movie ID: ${schedule.movieId}, Showroom: ${schedule.showroom}, Date & Time: ${schedule.dateTime}`}</li>
        ))}
    </ul>
    </div>
);
};

const AdminMainMenu = () => {
const [activeSection, setActiveSection] = useState("dashboard");
const [movies, setMovies] = useState([]);
const [promotions, setPromotions] = useState([]);
const [schedules, setSchedules] = useState([]);
const users = [
    { name: "User1", subscribed: true },
    { name: "User2", subscribed: false },
    { name: "User3", subscribed: true },
];

return (
    <div>
    {activeSection === "dashboard" && <AdminDashboard setActiveSection={setActiveSection} />}
    {activeSection === "manageMovies" && <ManageMovies movies={movies} setMovies={setMovies} />}
    {activeSection === "managePromotions" && (
        <ManagePromotions
        promotions={promotions}
        setPromotions={setPromotions}
        users={users}
        />
    )}
    {activeSection === "scheduleMovie" && <ScheduleMovie schedules={schedules} setSchedules={setSchedules} />}
    </div>
);
};

export default AdminMainMenu;
