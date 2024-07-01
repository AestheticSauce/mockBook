import { useForm } from "react-hook-form";
import React, { useState } from  "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm();

  // State to manage password masking
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);

  const onSubmit = (data) => {
    fetch("http://localhost:8085/v1/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (!response.ok) {
          setError("root.serverError", {type: response.status, message: response.statusText})
        }
        navigate("/Login");
      })
      .catch((error) => {
        console.error("Error:", error);
        setError("submit", {type: "custom", message: error.message})
      });
  };

  return (
    <div>
      <h1>Register</h1>
      <form onSubmit={handleSubmit(onSubmit)}>
        <label htmlFor="email">Email</label>
        <input
          id="email"
          {...register("email", {
            required: "Required, must be dal.ca email",
            pattern: {
              value: /^[A-Z0-9._%+-]+@dal.ca$/i,
              message: "Invalid email address, must be a @dal.ca email address",
            },
          })}
        />
        {errors.email && <p>{errors.email.message}</p>}

        <label htmlFor="username">Username</label>
        <input
          id="username"
          {...register("username", { required: "Required" })}
        />
        {errors.username && <p>{errors.username.message}</p>}

        <label htmlFor="password">Password</label>
        <input
          id="password"
          type={showPassword ? "text" : "password"} // toggle masking
          {...register("password", {
            required:
              "Required, must be 8 characters, 1 number, 1 upper case, 1 lower case and 1 special character",
            minLength: {
              value: 8,
              message: "Password must be at least 8 characters",
            },
            pattern: {
              value: /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).*$/,
              message:
                "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character",
            },
          })}
        />
        <button type="button" onClick={() => setShowPassword(!showPassword)}>
          {showPassword ? "Hide" : "Show"} Password
        </button>
        {errors.password && <p>{errors.password.message}</p>}

        <label htmlFor="securityQuestion">Security Question</label>
        <input
          id="securityQuestion"
          {...register("securityQuestion", { required: "Required" })}
        />
        {errors.securityQuestion && <p>{errors.securityQuestion.message}</p>}

        <label htmlFor="securityAnswer">Security Answer</label>
        <input
          id="securityAnswer"
          {...register("securityAnswer", { required: "Required" })}
        />
        {errors.securityAnswer && <p>{errors.securityAnswer.message}</p>}

        <button type="submit">Submit</button>
        {errors.submit && <p>{errors.submit.message}</p>}

        <p></p>
        <small>Have an account?</small>
        <button type="button" onClick={() => window.location.href = "/Login"}>
          Login
        </button>
      </form>
    </div>
  );
}

export default Register;
