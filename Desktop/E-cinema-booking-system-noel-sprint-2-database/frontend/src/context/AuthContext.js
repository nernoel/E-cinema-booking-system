"use client"; // Mark this component as a Client Component

import React, { createContext, useContext, useState, useEffect } from 'react';
import { useRouter } from 'next/navigation'; // For use in client components

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null); // Manages logged-in state
  const router = useRouter();

  const login = (email, password) => {
    // Simulate a login with mock data (for now)
    const mockUser = { name: 'John Doe', email };
    setUser(mockUser); // Set logged-in user
    localStorage.setItem('user', JSON.stringify(mockUser)); // Save user in localStorage

    // You can remove this line or point to a different route if no login page exists yet
    router.push('/'); // Redirect to homepage after login
  };

  const logout = () => {
    setUser(null); // Clear user
    localStorage.removeItem('user'); // Clear user data from localStorage

    // You can remove this or redirect to an existing page
    router.push('/'); // Redirect to homepage after logout
  };

  useEffect(() => {
    // Restore session from localStorage, if available
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser)); // Set user from stored data
    }
  }, []);

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// Hook for consuming the AuthContext
export const useAuth = () => useContext(AuthContext);
