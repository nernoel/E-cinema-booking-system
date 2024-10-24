// src/app/edit-profile/page.js

"use client"; 

import { useEffect, useState } from 'react';
import './edit-profile.css';

export default function EditProfile() {

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [billingAddress, setBillingAddress] = useState('');
  const [paymentCards, setPaymentCards] = useState([]);
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [promotions, setPromotions] = useState(false);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    async function fetchUserData() {
      setLoading(true);
      try {
        const response = await fetch('/api/user');
        if (response.ok) {
          const userData = await response.json();
          setFirstName(userData.firstName);
          setLastName(userData.lastName);
          setBillingAddress(userData.billingAddress);
          setPaymentCards(userData.paymentCards);
          setPromotions(userData.promotions);
        } else {
          setError('Failed to load user data.');
        }
      } catch (err) {
        setError('An error occurred while fetching user data.');
      } finally {
        setLoading(false);
      }
    }
    fetchUserData();
  }, []);

  const handleNameChange = async (event) => {
    event.preventDefault();
    setLoading(true);
    try {
      const response = await fetch('/api/user/updateName', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ firstName, lastName }),
      });
      if (!response.ok) throw new Error('Failed to update name');
      alert('Name updated successfully!');
    } catch (err) {
      setError('Failed to update name.');
    } finally {
      setLoading(false);
    }
  };

  const handleAddressChange = async (event) => {
    event.preventDefault();
    setLoading(true);
    try {
      const response = await fetch('/api/user/updateAddress', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ billingAddress }),
      });
      if (!response.ok) throw new Error('Failed to update address');
      alert('Address updated successfully!');
    } catch (err) {
      setError('Failed to update address.');
    } finally {
      setLoading(false);
    }
  };

  const handlePasswordChange = async (event) => {
    event.preventDefault();
    if (newPassword !== confirmPassword) {
      setError('Passwords do not match!');
      return;
    }
    setLoading(true);
    try {
      const response = await fetch('/api/user/updatePassword', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ oldPassword, newPassword }),
      });
      if (!response.ok) throw new Error('Failed to update password');
      alert('Password updated successfully!');
    } catch (err) {
      setError('Failed to update password.');
    } finally {
      setLoading(false);
    }
  };

  const handlePromotionsChange = async () => {
    setLoading(true);
    try {
      const response = await fetch('/api/user/updatePromotions', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ promotions: !promotions }),
      });
      if (!response.ok) throw new Error('Failed to update promotions');
      setPromotions(!promotions);
    } catch (err) {
      setError('Failed to update promotions.');
    } finally {
      setLoading(false);
    }
  };

  const handleCardUpdate = async (index, cardData) => {
    console.log(`Updating card ${index}:`, cardData);
  };

  return (
    <div className="edit-profile-container">
      <h1>Edit Profile</h1>

      {loading && <p>Loading...</p>}
      {error && <p className="error">{error}</p>}

      <form onSubmit={handleNameChange}>
        <h2>Change Name</h2>
        <input 
          type="text" 
          value={firstName} 
          onChange={(e) => setFirstName(e.target.value)} 
          required 
        />
        <input 
          type="text" 
          value={lastName} 
          onChange={(e) => setLastName(e.target.value)} 
          required 
        />
        <button type="submit">Update Name</button>
      </form>

      <form onSubmit={handleAddressChange}>
        <h2>Change Billing Address</h2>
        <input 
          type="text" 
          value={billingAddress} 
          onChange={(e) => setBillingAddress(e.target.value)} 
          required 
        />
        <button type="submit">Update Address</button>
      </form>

      <form onSubmit={handlePasswordChange}>
        <h2>Change Password</h2>
        <input 
          type="password" 
          value={oldPassword} 
          onChange={(e) => setOldPassword(e.target.value)} 
          required 
        />
        <input 
          type="password" 
          value={newPassword} 
          onChange={(e) => setNewPassword(e.target.value)} 
          required 
        />
        <input 
          type="password" 
          value={confirmPassword} 
          onChange={(e) => setConfirmPassword(e.target.value)} 
          required 
        />
        <button type="submit">Update Password</button>
      </form>

      <div>
        <h2>Promotions</h2>
        <label>
          <input 
            type="checkbox" 
            checked={promotions} 
            onChange={handlePromotionsChange} 
          />
          Receive Promotions
        </label>
      </div>

      <div>
        <h2>Manage Payment Cards</h2>
        {paymentCards.map((card, index) => (
          <div key={index}>
            <p>Card {index + 1}: {card.number}</p>
          </div>
        ))}
        {paymentCards.length < 4 && (
          <button onClick={() => handleCardUpdate(paymentCards.length)}>Add New Card</button>
        )}
      </div>

      <a href="/" className="back-link">Back to Home</a>
    </div>
  );
}
