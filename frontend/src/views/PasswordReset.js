import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { redirect } from "react-router-dom";

function PasswordReset() {
    const { register, handleSubmit, formState: { errors }, setError } = useForm();
    const [showPassword, setShowPassword] = useState(false);
    const [securityQuestion, setSecurityQuestion] = useState('');
    const [securityAnswer, setSecurityAnswer] = useState('');
    const [email, setEmail] = useState('');
    const [step, setStep] = useState(1);

    const handleSecurityQuestionSubmit = (data) => {
        // Mock API call to validate security question answer
        fetch("http://localhost:8085/v1/api/validate-security-question", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: data.email, securityAnswer: data.securityAnswer }),
        })
        .then((response) => {
            if (!response.ok) {
                setError("securityAnswer", { type: "manual", message: "Incorrect answer" });
            } else {
                setEmail(data.email);
                setStep(2); // Proceed to next step
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            setError("securityAnswer", { type: "manual", message: error.message });
        });
    };

    const handlePasswordResetSubmit = (data) => {
        fetch("http://localhost:8085/v1/api/reset-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, newPassword: data.newPassword }),
        })
        .then((response) => {
            if (!response.ok) {
                setError("newPassword", { type: "manual", message: "Password reset failed" });
            } else {
                redirect("/login");
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
                    
                    <label htmlFor="securityAnswer">Security Answer</label>
                    <input id="securityAnswer" {...register("securityAnswer", { required: "Required" })} />
                    {errors.securityAnswer && <p>{errors.securityAnswer.message}</p>}
                    
                    <button type="submit">Submit</button>
                </form>
            )}
            {step === 2 && (
                <form onSubmit={handleSubmit(handlePasswordResetSubmit)}>
                    <h2>Set New Password</h2>
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
                </form>
            )}
        </div>
    );
}

export default PasswordReset;
