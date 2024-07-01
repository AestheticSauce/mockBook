import React, { useState } from "react";
import { useForm } from "react-hook-form";
import {redirect, useNavigate} from "react-router-dom";
import {Button, Modal} from "react-bootstrap";

function PasswordReset() {
    const { register, handleSubmit, formState: { errors }, setError } = useForm();
    const [showPassword, setShowPassword] = useState(false);
    const [securityQuestion, setSecurityQuestion] = useState('');
    const [securityAnswer, setSecurityAnswer] = useState('');
    const [email, setEmail] = useState('');
    const [step, setStep] = useState(1);
    const navigate = useNavigate();
    const [showResetSuccess, setShowResetSuccess] = useState(false);

    const handleSecurityQuestionSubmit = (data) => {
        fetch(`http://localhost:8085/v1/api/auth/security-question?email=${data.email}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
            if (!response.ok) {
                setError("securityAnswer", { type: "manual", message: "Incorrect answer" });
            } else {
                response.json().then((responseData) => {
                setSecurityQuestion(responseData.securityQuestion);
                setEmail(responseData.email);
                setStep(2); // Proceed to next step
            });
                
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            setError("securityAnswer", { type: "manual", message: error.message });
        });
    };

    const handlePasswordResetSubmit = (data) => {
        fetch("http://localhost:8085/v1/api/auth/password-reset", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: data.email, newPassword: data.newPassword, securityAnswer: data.securityAnswer}),
        })
        .then((response) => {
            if (!response.ok) {
                setError("newPassword", { type: "manual", message: "Password reset failed" });
            } else {
                setShowResetSuccess(true);
                setTimeout(() => {
                    navigate("/Login");
                }, 2000); // redirect after 2 seconds
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            setError("newPassword", { type: "manual", message: error.message });
        });
    };

    return (
        <div>

            {step === 1 && (
                <form onSubmit={handleSubmit(handleSecurityQuestionSubmit)}>
                    <h2>Reset Password</h2>
                    <label htmlFor="email">Email</label>
                    <input id="email" {...register("email", { required: "Required" })} />
                    {errors.email && <p>{errors.email.message}</p>}
                    <button type="submit">Submit</button>
                </form>
            )}
            {step === 2 && (
                <form onSubmit={handleSubmit(handlePasswordResetSubmit)}>
                    <h2>Set New Password</h2>

                    <label htmlFor="securityQuestion">Security Question</label>
                    <input id="securityQuestion" value={securityQuestion} disabled />

                    <label htmlFor="securityAnswer">Security Answer</label>
                    <input id="securityAnswer" {...register("securityAnswer", { required: "Required" })} />
                    {errors.securityAnswer && <p>{errors.securityAnswer.message}</p>}

                    <label htmlFor="newPassword">New Password</label>
                    <input
                        id="newPassword"
                        type={showPassword ? "text" : "password"}
                        {...register("newPassword", {
                            required: "Required, must be 8 characters, 1 number, 1 upper case, 1 lower case and 1 special character",
                            minLength: { value: 8, message: "Password must be at least 8 characters" },
                            pattern: {
                                value: /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).*$/,
                                message: "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character",
                            },
                        })}
                    />
                    <button type="button" onClick={() => setShowPassword(!showPassword)}>
                        {showPassword ? "Hide" : "Show"} Password
                    </button>
                    {errors.newPassword && <p>{errors.newPassword.message}</p>}
                    
                    <button type="submit">Reset Password</button>
                    {errors.submit && <p>{errors.submit.message}</p>}
                </form>
            )}

            <style>{`
                .modal-content {
                    text-align: center;
                }
                .modal-body p {
                    margin: 0;
                }
                .modal-header, .modal-body, .modal-footer {
                    color: #000000;
                }
            `}</style>

            <Modal
                show={showResetSuccess}
                onHide={() => setShowResetSuccess(false)}
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title>Password Reset Successful!</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    You will be redirected to the login page shortly.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowResetSuccess(false)}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default PasswordReset;
