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
        setErrorMessage("");
        setSuccessMessage("");

        const userData = { firstname: firstName, lastname: lastName, email, password };

        try {
            const response = await axios.post("http://localhost:8080/api/users/register", userData);

            if (response.status === 201) {
                setSuccessMessage("Registration successful! A verification code has been sent to your email.");
                setUserId(response.data.id);
                setIsCodeSent(true);

                await handleSendEmailConfirmation();

                if (promoOptIn) {
                    await handlePromoRegistration(response.data.id);
                }
            }
        } catch (error) {
            setErrorMessage(error.response?.data?.message || "Registration failed. Please try again.");
        }
    };

    // Send email confirmation
    const handleSendEmailConfirmation = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/send-confirmation-email", { email });
            if (response.status === 200) {
                console.log("Verification Code:", response.data.verificationCode); // Debugging only
                setSuccessMessage("Verification email sent successfully. Please check your inbox.");
            }
        } catch (error) {
            setErrorMessage("Failed to send verification email. Please try again.");
        }
    };

    // Handle promo registration
    const handlePromoRegistration = async (userId) => {
        try {
            await axios.put(`http://localhost:8080/api/users/${userId}/promo-status`, { promoStatus: "SUBSCRIBED" });
        } catch (error) {
            setErrorMessage("Failed to register for promotions.");
        }
    };

    // Handle verification code
    const handleVerifyCode = async (e) => {
        e.preventDefault();
        setErrorMessage("");
        setSuccessMessage("");

        if (!verificationCode.trim()) {
            setErrorMessage("Please enter the verification code.");
            return;
        }

        try {
            // Fetch the stored code (for comparison)
            const fetchCodeResponse = await axios.get("http://localhost:8080/api/get-verification-code", {
                params: { email },
            });
            const serverCode = fetchCodeResponse.data.verificationCode;

            // Verify if codes match
            if (serverCode === verificationCode) {
                const verifyResponse = await axios.post("http://localhost:8080/api/verify-code", { email });
                if (verifyResponse.status === 200) {
                    setSuccessMessage("Verification successful! Redirecting...");
                    setTimeout(() => router.push("/login"), 2000);
                }
            } else {
                setErrorMessage("Invalid verification code. Please try again.");
            }
        } catch (error) {
            setErrorMessage("Failed to verify the code. Please try again.");
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
                    <button type="submit" disabled={!verificationCode.trim()}>
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
