"use client";
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import './edit-profile.css';

export default function EditProfile() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [billingAddress, setBillingAddress] = useState('');
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [paymentCards, setPaymentCards] = useState([]);
  const [cardNumber, setCardNumber] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [cardHolderName, setCardHolderName] = useState('');
  const router = useRouter();

  // Fetch user data and payment cards on component mount
  useEffect(() => {
    const storedEmail = localStorage.getItem('userEmail');
    if (storedEmail) {
      // Fetch user data
      axios.get(`http://localhost:8080/api/users/by-email?email=${storedEmail}`)
        .then((response) => {
          const userData = response.data;
          setFirstName(userData.firstname || '');
          setLastName(userData.lastname || '');
          setEmail(userData.email || '');
          setBillingAddress(userData.billingAddress || '');
          setPaymentCards(userData.paymentCards || []); // Assuming the response includes payment cards
        })
        .catch((error) => {
          console.error('Error fetching user data:', error);
        });
    }
  }, []);

  // Handle profile update (including payment cards)
  const handleProfileUpdate = (event) => {
    event.preventDefault();
    const storedEmail = localStorage.getItem('userEmail');
    const updateData = {
      firstname: firstName,
      lastname: lastName,
      billingAddress,
      paymentCards: paymentCards.map(card => ({
        cardNumber: card.cardNumber,
        expiryDate: card.expiryDate,
        cardHolderName: card.cardHolderName,
        cardType: "VISA" // Replace with dynamic value if needed
      }))
    };

    if (newPassword) {
      updateData.password = newPassword;
    }

    axios.put(`http://localhost:8080/api/users/by-email?email=${storedEmail}`, updateData)
      .then(() => {
        alert('Profile updated successfully!');
      })
      .catch((error) => {
        console.error('Error updating profile:', error);
      });
  };

  // Handle adding a new payment card
  const handleAddCard = (event) => {
    event.preventDefault();
    const storedEmail = localStorage.getItem('userEmail');
    const newCard = {
      cardNumber,
      expiryDate,
      cardHolderName,
      cardType: "VISA" // Change this to match user input if necessary
    };

    axios.post(`http://localhost:8080/api/users/by-email/payment-cards`, newCard) // Use POST to add a new card
      .then((response) => {
        setPaymentCards([...paymentCards, response.data]); // Update state with the new card
        alert('Payment card added successfully!');
        // Clear input fields
        setCardNumber('');
        setExpiryDate('');
        setCardHolderName('');
      })
      .catch((error) => {
        console.error('Error adding payment card:', error);
      });
  };

  // Handle logout
  const handleLogout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userEmail');
    router.push('/login'); // Redirect to login page
  };

  return (
    <div className="edit-profile-container">
      <h1>Edit Profile</h1>
      
      <div className="current-user-info">
        <h2>Current User:</h2>
        <p><strong>Name:</strong> {firstName} {lastName}</p>
        <p><strong>Email:</strong> {email}</p>
      </div>

      <form onSubmit={handleProfileUpdate}>
        <h2>Change Profile Information</h2>
        <div className="form-group">
          <label>First Name:</label>
          <input
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Last Name:</label>
          <input
            type="text"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Billing Address:</label>
          <input
            type="text"
            value={billingAddress}
            onChange={(e) => setBillingAddress(e.target.value)}
            required
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
        <button type="submit">Update Profile</button>
      </form>

      <h2>Your Payment Cards</h2>
      <ul>
        {paymentCards.map((card, index) => (
          <li key={index}>
            <p>Card Number: {card.cardNumber}</p>
            <p>Expiry Date: {card.expiryDate}</p>
            <p>Card Holder Name: {card.cardHolderName}</p>
          </li>
        ))}
      </ul>

      <form onSubmit={handleAddCard}>
        <h2>Add Payment Card</h2>
        <div className="form-group">
          <label>Card Number:</label>
          <input
            type="text"
            value={cardNumber}
            onChange={(e) => setCardNumber(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Expiry Date:</label>
          <input
            type="text"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Card Holder Name:</label>
          <input
            type="text"
            value={cardHolderName}
            onChange={(e) => setCardHolderName(e.target.value)}
            required
          />
        </div>
        <button type="submit">Add Card</button>
      </form>

      <div className="logout-section">
        <button onClick={handleLogout}>Logout</button>
      </div>
    </div>
  );
}
