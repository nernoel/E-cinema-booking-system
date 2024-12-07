"use client";

import { useState, useEffect } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import "../styles/edit-profile.css";

export default function EditProfile() {
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    billingAddress: "",
    paymentCards: [],
  });

  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [cardName, setCardName] = useState("");
  const [number, setNumber] = useState("");
  const [securityCode, setSecurityCode] = useState("");
  const [expiryDate, setExpiryDate] = useState("");
  const [cardType, setCardType] = useState("");

  const router = useRouter();

  // Fetch user data on component mount
  useEffect(() => {
    const storedEmail = localStorage.getItem("userEmail");
    if (storedEmail) {
      axios
        .get(`http://localhost:8080/api/users/get-user?email=${storedEmail}`)
  
        .then((response) => {
          const userData = response.data;
          setUser({
            firstName: userData.firstname || "",
            lastName: userData.lastname || "",
            email: userData.email || "",
            billingAddress: userData.billingAddress || "",
            paymentCards: userData.paymentCards || [],
          });
        })
        .catch((error) => {
          console.error("Error fetching user data:", error);
        });
    }
  }, []);

  // Validate old password function
  const validateOldPassword = () => {
    const storedEmail = localStorage.getItem("userEmail");
    return axios
      .post(`http://localhost:8080/api/users/validate-password`, { email: storedEmail, password: oldPassword })
      .then((response) => response.data.valid);
  };

  // Handle profile update
  const handleProfileInfoUpdate = (event) => {
    event.preventDefault();
    const storedEmail = localStorage.getItem("userEmail");
  
    if (!storedEmail) {
      console.error("No email found in local storage.");
      return;
    }
  
    const updateData = {
      firstname: user.firstName,
      lastname: user.lastName,
      billingAddress: user.billingAddress,
    };
  
    axios
      .put(`http://localhost:8080/api/users/edit-profile/by-email?email=${storedEmail}`, updateData)
      .then(() => {
        alert("Profile information updated successfully!");
        //router.push("/profile"); // Redirect back to profile or another page
      })
      .catch((error) => {
        console.error("Error updating profile information:", error);
      });
  };
  

  // Handle password update
  const handlePasswordUpdate = async (event) => {
    event.preventDefault();

    const isOldPasswordValid = await validateOldPassword();
    if (!isOldPasswordValid) {
      alert("Old password is incorrect.");
      return;
    }

    if (newPassword !== confirmPassword) {
      alert("New password and confirm password do not match.");
      return;
    }

    const storedEmail = localStorage.getItem("userEmail");
    axios
      .put(`http://localhost:8080/api/users/by-email?email=${storedEmail}`, { password: newPassword })
      .then(() => {
        alert("Password updated successfully!");
        //router.push("/profile"); // Redirect back to profile or another page
      })
      .catch((error) => {
        console.error("Error updating password:", error);
      });
  };

  const handleAddCard = (event) => {
    event.preventDefault();
  
    // Prevent adding more than 4 payment cards
    if (user.paymentCards.length >= 4) {
      alert("You can only add up to 4 payment cards.");
      return;
    }
  
    // Create a new card object
    const newCard = {
      cardHolder: cardName,
      cardNumber: number,
      securityCode,
      expiryDate,
      cardType,
      userId: user.id, // Assume `user.id` holds the current user's ID
    };
  
    // Send the new payment card to the backend
    axios
      .post(`http://localhost:8080/api/payment-cards/add`, newCard)
      .then((response) => {
        // Get the newly added card from the response
        const addedCard = response.data;
  
        // Update the local user state with the new card
        setUser((prevState) => ({
          ...prevState,
          paymentCards: [...prevState.paymentCards, addedCard],
        }));
  
        alert("Payment card added successfully!");
      })
      .catch((error) => {
        console.error("Error adding payment card:", error);
      });
  
    // Clear input fields
    setCardName("");
    setNumber("");
    setSecurityCode("");
    setExpiryDate("");
    setCardType("");
  };
  
  

  // Handle logout
  const handleLogout = () => {
    if (window.confirm("Are you sure you want to logout?")) {
      localStorage.removeItem("authToken"); // Remove the auth token
      localStorage.removeItem("userEmail");  // Remove user email if stored
      router.push("/login");                 // Redirect to login page
    }
  };

  return (
    <div className="edit-profile-container">
      <h1>Edit Profile</h1>

      <div className="current-user-info">
        <h2 className="current-user-text">Current User:</h2>
        <p><strong>Name:</strong> {user.firstName} {user.lastName}</p>
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Billing Address:</strong> {user.billingAddress}</p>
      </div>

      {/* Form to update name and billing address */}
      <form onSubmit={handleProfileInfoUpdate}>
        <h2>Update Name and Billing Address</h2>
        <div className="form-group">
          <label>First Name:</label>
          <input
            type="text"
            value={user.firstName}
            onChange={(e) => setUser({ ...user, firstName: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label>Last Name:</label>
          <input
            type="text"
            value={user.lastName}
            onChange={(e) => setUser({ ...user, lastName: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label>Billing Address:</label>
          <input
            type="text"
            value={user.billingAddress}
            onChange={(e) => setUser({ ...user, billingAddress: e.target.value })}
          />
        </div>
        <button type="submit">Update Information</button>
      </form>

      {/* Form to change password */}
      <form onSubmit={handlePasswordUpdate}>
        <h2>Change Password</h2>
        <div className="form-group">
          <label>Old Password:</label>
          <input
            type="password"
            value={oldPassword}
            onChange={(e) => setOldPassword(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>New Password:</label>
          <input
            type="password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Confirm New Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
        </div>
        <button type="submit">Change Password</button>
      </form>

      <h2>Your Payment Cards</h2>
      <ul>
        {user.paymentCards.length > 0 ? (
          user.paymentCards.map((card, index) => (
            <li key={index}>
              <p className="payment-card-info">Card Name: {card.cardHolder}</p>
              <p className="payment-card-info">Card Number: **** **** **** {card.cardNumber.slice(-4)}</p>
              <p className="payment-card-info">Expiry Date: {card.expiryDate}</p>
              <p className="payment-card-info">Security Code: {card.securityCode}</p>
              <p className="payment-card-info">Card Type: {card.cardType}</p>
            </li>
          ))
        ) : (
          <p>No payment cards stored.</p>
        )}
      </ul>

      <form onSubmit={handleAddCard}>
        <h2>Add Payment Card</h2>
        <div className="form-group">
          <label>Card Name:</label>
          <input
            type="text"
            value={cardName}
            onChange={(e) => setCardName(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Card Number:</label>
          <input
            type="text"
            value={number}
            onChange={(e) => setNumber(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Security Code:</label>
          <input
            type="text"
            value={securityCode}
            onChange={(e) => setSecurityCode(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Expiry Date:</label>
          <input
            type="text"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Card Type:</label>
          <input
            type="text"
            value={cardType}
            onChange={(e) => setCardType(e.target.value)}
          />
        </div>
        <button type="submit">Add Card</button>
      </form>

      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}
