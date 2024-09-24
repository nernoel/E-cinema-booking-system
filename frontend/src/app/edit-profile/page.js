// src/app/edit-profile/page.js

"use client"; 

import React, { useState } from 'react';
import './edit-profile.css'; // import the css

export default function EditProfile() {
  // state for name change
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');

  // state for email change
  const [email, setEmail] = useState('');
  const [currentPasswordEmail, setCurrentPasswordEmail] = useState('');

  // state for password change
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  // state for profile photo
  const [profilePhoto, setProfilePhoto] = useState(null);
  const [previewPhoto, setPreviewPhoto] = useState(null);

  // handle name change
  const handleNameChange = (event) => {
    event.preventDefault();
    console.log('First Name:', firstName);
    console.log('Last Name:', lastName);
  };

  // handle email change
  const handleEmailChange = (event) => {
    event.preventDefault();
    console.log('New Email:', email);
    console.log('Current Password for Email Change:', currentPasswordEmail);
  };

  // handle password change
  const handlePasswordChange = (event) => {
    event.preventDefault();
    console.log('Old Password:', oldPassword);
    console.log('New Password:', newPassword);
    console.log('Confirm Password:', confirmPassword);
  };

  // handle profile photo change
  const handlePhotoChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setProfilePhoto(file);
      setPreviewPhoto(URL.createObjectURL(file)); // Create a preview of the selected image
    }
  };

  const handlePhotoUpload = (event) => {
    event.preventDefault();
    // logic to upload profile photo
    if (profilePhoto) {
      console.log('Profile Photo:', profilePhoto);
      // will be implemented later
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

      {/* Change Email Section */}
      <form onSubmit={handleEmailChange}>
        <h2>Change Email</h2>
        <div className="form-group">
          <label>New Email:</label>
          <input 
            type="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
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
