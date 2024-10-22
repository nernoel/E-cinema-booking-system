"use client";

import React, { useState, useEffect } from 'react';
import axios from 'axios';  // Assuming you're using axios for API calls
import './edit-profile.css'; // Import CSS for styling

export default function EditProfile() {
  // State for profile fields
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [currentPasswordEmail, setCurrentPasswordEmail] = useState('');

  // Password change states
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  // Profile photo
  const [profilePhoto, setProfilePhoto] = useState(null);
  const [previewPhoto, setPreviewPhoto] = useState(null);

  // Fetch logged-in user profile (useEffect to load user data when component mounts)
  useEffect(() => {
    axios.get('/api/user/profile')  // Replace with your API endpoint
      .then(response => {
        const { firstName, lastName, email, profilePhotoUrl } = response.data;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);  // Assuming email can't be changed
        setPreviewPhoto(profilePhotoUrl);
      })
      .catch(error => {
        console.error("There was an error fetching the user profile!", error);
      });
  }, []);  // Empty dependency array ensures this runs only once when the component mounts

  // Handle Name change
  const handleNameChange = async (event) => {
    event.preventDefault();
    try {
      await axios.put('/api/user/update-profile', { firstName, lastName });
      alert("Profile updated successfully");
    } catch (error) {
      console.error("There was an error updating the profile!", error);
    }
  };

  // Handle Password change
  const handlePasswordChange = async (event) => {
    event.preventDefault();
    if (newPassword !== confirmPassword) {
      alert("New passwords do not match");
      return;
    }

    try {
      await axios.put('/api/user/change-password', {
        oldPassword,
        newPassword,
      });
      alert("Password updated successfully");
    } catch (error) {
      console.error("There was an error changing the password!", error);
    }
  };

  // Handle Profile photo change
  const handlePhotoChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setProfilePhoto(file);
      setPreviewPhoto(URL.createObjectURL(file));  // Preview image
    }
  };

  const handlePhotoUpload = async (event) => {
    event.preventDefault();
    if (profilePhoto) {
      const formData = new FormData();
      formData.append("file", profilePhoto);
      try {
        await axios.post('/api/user/upload-photo', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        alert("Profile photo updated successfully");
      } catch (error) {
        console.error("There was an error uploading the profile photo!", error);
      }
    }
  };

  return (
    <div className="edit-profile-container">
      <h1>Edit Profile</h1>

      {/* Change Profile Photo Section */}
      <form onSubmit={handlePhotoUpload}>
        <h2>Change Profile Photo</h2>
        <div className="form-group">
          <label>Upload New Profile Photo:</label>
          <input type="file" accept="image/*" onChange={handlePhotoChange} required />
        </div>
        {previewPhoto && (
          <div className="profile-photo-preview">
            <h3>Preview:</h3>
            <img src={previewPhoto} alt="Profile Preview" className="photo-preview" />
          </div>
        )}
        <button type="submit">Upload Photo</button>
      </form>

      {/* Change Name Section */}
      <form onSubmit={handleNameChange}>
        <h2>Change Name</h2>
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
        <button type="submit">Change Name</button>
      </form>

      {/* Change Email Section (read-only) */}
      <form>
        <h2>Change Email</h2>
        <div className="form-group">
          <label>New Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)} // Assuming email cannot be changed
            readOnly
          />
        </div>
        <div className="form-group">
          <label>Current Password:</label>
          <input
            type="password"
            value={currentPasswordEmail}
            onChange={(e) => setCurrentPasswordEmail(e.target.value)}
            required
          />
        </div>
        <button type="submit">Change Email</button>
      </form>

      {/* Change Password Section */}
      <form onSubmit={handlePasswordChange}>
        <h2>Change Password</h2>
        <div className="form-group">
          <label>Old Password:</label>
          <input
            type="password"
            value={oldPassword}
            onChange={(e) => setOldPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>New Password:</label>
          <input
            type="password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Confirm New Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Change Password</button>
      </form>

      <a href="/" className="back-link">Back to Home</a>
    </div>
  );
}
