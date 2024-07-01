import React, { useEffect, useState } from "react";
import axios from "axios";
import * as jwtUtils from "../jwt.js";
import { useForm } from "react-hook-form";
import "../HomePageStyles.css";
import Logout from "../components/Logout";

const HomePage = () => {
  const [user, setUser] = useState(null);
  const [friendships, setFriendships] = useState([]);
  const [friendsPosts, setFriendsPosts] = useState([]);
  const [nonFriends, setNonFriends] = useState([]);
  const [error, setError] = useState(null);
  const { register, handleSubmit, reset } = useForm();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userProfile = await jwtUtils.getUserProfile();
        console.log(userProfile);
        setUser(userProfile);
      } catch (error) {
        console.error("Unauthorized access", error);
        setError("Unauthorized access");
      }
    };

    fetchUser();
  }, []);

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8085/v1/api/friendships",
          {
            headers: {
              Authorization: `Bearer ${jwtUtils.getToken()}`,
            },
          }
        );
        setFriendships(response.data);
      } catch (error) {
        console.error("Error fetching friends:", error);
        setError("Error fetching data");
      }
    };

    fetchFriends();
  }, []);

  useEffect(() => {
    const fetchNonFriends = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8085/v1/api/friendships/recommended",
          {
            headers: {
              Authorization: `Bearer ${jwtUtils.getToken()}`,
            },
          }
        );
        setNonFriends(response.data);
      } catch (error) {
        console.error("Error fetching non-friends:", error);
        setError("Error fetching data");
      }
    };

    fetchNonFriends();
  }, []);

  useEffect(() => {
    const fetchFriendsPosts = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8085/v1/api/posts/friends-posts",
          {
            headers: {
              Authorization: `Bearer ${jwtUtils.getToken()}`,
            },
          }
        );
        setFriendsPosts(response.data);
      } catch (error) {
        console.error("Error fetching friends' post:", error);
        setError("Error fetching data");
      }
    };

    fetchFriendsPosts();
  }, []);

  const createPost = (data) => {
    try {
      axios
        .post(
          "http://localhost:8085/v1/api/posts",
          {
            title: data.title,
            content: data.content,
          },
          {
            headers: {
              Authorization: `Bearer ${jwtUtils.getToken()}`,
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          if (response.status !== 200) {
            console.error("Error creating post:", response);
            setError("Error creating post");
          }
        });
      //reset(); // Reset form fields after submission
    } catch (error) {
      console.error("Error creating post:", error);
      setError("Error creating post");
    }
  };

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div className="container">
      <div className="header">
        <h1>Welcome, {user ? user.email : "Loading..."}</h1>
        <button
          type="button"
          onClick={() => (window.location.href = `/userprofile/${user.id}`)}
        >
          My Profile
        </button>
                <Logout/>
      </div>

      <div className="main-content">
        <div className="posts-panel">
          <div className="post-form">
            <h2>Create a Post</h2>
            <form onSubmit={handleSubmit(createPost)}>
              <label htmlFor="title">Title:</label>
              <br />
              <input
                type="text"
                id="title"
                name="title"
                {...register("title", { required: "Required" })}
              />
              <br />
              <label htmlFor="content">Content:</label>
              <br />
              <textarea
                id="content"
                name="content"
                {...register("content", { required: "content" })}
              />
              <br />
              <button type="submit">Create Post</button>
            </form>
          </div>
          <h2>My Friends' Posts</h2>
          <ul>
            {friendsPosts.map((friendsPost, index) => (
              <li key={index}>
                <h3>{friendsPost.title}</h3>
                <p>{friendsPost.content}</p>
                <small>
                  Author:{" "}
                  <a href={`/userprofile/${friendsPost.author.id}`}>
                    {friendsPost.author.username}
                  </a>
                </small>
                <br />
                <small>
                  Created at:{" "}
                  {new Date(friendsPost.created_at).toLocaleString()}
                </small>
              </li>
            ))}
          </ul>
        </div>

        <div className="friends-list">
          <h2>My Friends</h2>
          <ul>
            {friendships.map((friendship, index) => {
              if (friendship.sourceUser.id !== user.id) {
                var friend = friendship.sourceUser;
              } else {
                var friend = friendship.targetUser;
              }
              <li key={index}>
                <h3>
                  <a href={`/userprofile/${friend.id}`}>
                    {friend.username}
                  </a>
                </h3>
                <small>Status: {friend.status}</small>
              </li>;
            })}
          </ul>

          <h2>Recommended</h2>
          <ul>
            {nonFriends.map((nonFriend, index) => (
              <li key={index}>
                <h3>{nonFriend.username}</h3>
                <button
                    type="button"
                    onClick={() => axios({
                      method: 'POST',
                      url: `http://localhost:8085/v1/api/friendships`,
                      data:{
                        "targetId": nonFriend.id
                      },
                      headers: {
                          "Authorization": `Bearer ${jwtUtils.getToken()}`,
                          "Content-Type": "application/json",
                      }
                  }).then(response=>{console.log(response)})}
                >
                  add friend
                </button>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
