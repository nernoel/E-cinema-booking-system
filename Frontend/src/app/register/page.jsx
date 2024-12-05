"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import axios from "axios";
import "../styles/register.css";
import Link from "next/link";

export default function Register() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [promoOptIn, setPromoOptIn] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [isCodeSent, setIsCodeSent] = useState(false);
    const [verificationCode, setVerificationCode] = useState("");
    const [userId, setUserId] = useState("");
    const router = useRouter();

    // Handle user registration
    const handleRegister = async (e) => {
        e.preventDefault();

        // Reset messages
        setErrorMessage("");
        setSuccessMessage("");

        const userData = {
            firstname: firstName,
            lastname: lastName,
            email: email,
            password: password,
        };

        try {
            // Register the user
            const response = await axios.post("http://localhost:8080/api/users/register", userData);

            if (response.status === 201) {
                setSuccessMessage("Registration successful! A verification code has been sent to your email.");
                setUserId(response.data.id);
                setIsCodeSent(true); // Show the verification form

                // Send the confirmation email
                await handleSendEmailConfirmation();

                // Handle promo registration if opted in
                if (promoOptIn) {
                    await handlePromoRegistration(response.data.id);
                }
            }
        } catch (error) {
            console.error("Error during registration:", error);
            setErrorMessage(error.response?.data?.message || "Something went wrong. Please try again.");
        }
    };

    // Handle email confirmation
    const handleSendEmailConfirmation = async () => {
        setErrorMessage("");
        setSuccessMessage("");

        try {
            const emailResponse = await axios.post("http://localhost:8080/api/send-confirmation-email", {
                email,
            });

            if (emailResponse.status === 200) {
                setSuccessMessage("Verification email sent successfully. Please check your inbox.");
            }
        } catch (error) {
            console.error("Error during email confirmation:", error);
            setErrorMessage("Failed to send email confirmation. Please try again later.");
        }
    };

    // Handle promo registration
    const handlePromoRegistration = async (userId) => {
        try {
            await axios.put(`http://localhost:8080/api/users/${userId}/promo-status`, {
                promoStatus: "SUBSCRIBED",
            });
        } catch (error) {
            console.error("Error during promo registration:", error);
            setErrorMessage("Failed to register for promotion. Please try again later.");
        }
    };

    // Handle verify code
    const handleVerifyCode = async (e) => {
        e.preventDefault(); // Prevent form submission default behavior
        setErrorMessage(""); // Clear previous error messages

        if (!verificationCode.trim()) {
            setErrorMessage("Please enter a verification code.");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/verify-code", {
                email,
                verificationCode,
            });

            if (response.status === 200) {
                setSuccessMessage("Code verified successfully!");
                router.push("/login"); // Redirect to login or another page upon success
            }
        } catch (error) {
            console.error("Error during code verification:", error);
            setErrorMessage("Failed to verify code. Please try again later.");
        }
    };

    return (
        <div className="register-container">
            <h1>Create account</h1>
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}

            {!isCodeSent ? (
                <form onSubmit={handleRegister}>
                    <div>
                        <input
                            type="text"
                            placeholder="First Name"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <input
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="promotion-checkbox">
                        <input
                            type="checkbox"
                            id="promoOptIn"
                            checked={promoOptIn}
                            onChange={() => setPromoOptIn(!promoOptIn)}
                        />
                        <label htmlFor="promoOptIn">Register for promotions</label>
                    </div>
                    <button type="submit">Register</button>
                </form>
            ) : (
                <form onSubmit={handleVerifyCode}>
                    <p>Enter the verification code sent to your email:</p>
                    <div>
                        <input
                            type="text"
                            placeholder="Verification Code"
                            value={verificationCode}
                            onChange={(e) => setVerificationCode(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" disabled={!verificationCode.trim() || !isCodeSent}>
                        Verify
                    </button>
                </form>
            )}
            <p>
                Already have an account? <Link href="/login">Login here</Link>
            </p>
            <Link href="/">Back to Home</Link>
        </div>
    );
}
