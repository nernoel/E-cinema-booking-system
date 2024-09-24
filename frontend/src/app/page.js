// src/app/page.js
"use client";

import React from 'react';
import { useAuth } from '../context/AuthContext'; 
import HomeLoggedIn from './home-loggedin/page';
import HomeLoggedOut from './home-loggedout/page';

export default function Home() {
  const { user } = useAuth();  // get user info from AuthContext

  // home page depending on login status 
  return (
    <div>
      {user ? <HomeLoggedIn /> : <HomeLoggedOut />}
    </div>
  );
}
