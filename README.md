# AlgoSpectra Backend

<table>
  <tr>
    <td style="vertical-align: middle; width: 100px;">
      <img src="https://github.com/user-attachments/assets/a162ef20-38a5-4341-98af-4f53ffd7ee91" alt="algospectra-logo" width="100" height="100">
    </td>
    <td style="vertical-align: middle; padding-left: 15px;">
      <strong>The AlgoSpectra Backend</strong> is a Spring Boot application built with Java 17 and PostgreSQL.<br>
      It powers the authentication, guest login, user profile management, and secure access for the AlgoSpectra platform.<br><br>
      Hosted on <strong>Render</strong>, the backend is connected to a <strong>Supabase PostgreSQL</strong> database.
    </td>
  </tr>
</table>



## 🚀 Features

- ✅ Guest login with auto-generated unique guest ID
- 🔐 Secure user registration and login with email, password, and name
- ❌ Global error handling
- 🔐 Variable Rate limiting per per endpoints call
- 🔁 Persistent login with remember-me functionality using cookies or refresh tokens
- 🔓 Logout support
- 👤 View logged-in user's profile
- 📦 PostgreSQL integration (Supabase)



## ⚙️ Tech Stack

- **Spring Boot** 3.4.4
- **Java 17**
- **PostgreSQL** (hosted on Supabase)
- **Spring Security**
- **JPA / Hibernate**
- **HikariCP**



## 📁 Project Structure

```
src/
├── main/java/org/company/algospectra_backend
│   ├── config
│   ├── controller
│   ├── model
│   ├── repository
│   ├── exception
│   ├── ratelimiter
│   ├── service
│   └── AlgospectraBackendApplication.java
└── resources
    ├── application.properties
    └── application-local.properties
```

---

## 🔗 API Endpoints

### 🧪 Health Check

- **GET** `/algohealth`  
  ⮕ Check if the API is alive and responsive.


### 🔐 Auth

### 1. **User Registration**

- **Endpoint:** `POST /api/auth/register`
- **Description:** Registers a new user by providing their name, email, and password.
- **Request Body:**
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  - **Status:** 200 OK (Success)
  - **Response Body:**
  ```json
  {
    "status": "success",
    "message": "Registration successful",
    "user": {
      "id": "string",
      "name": "string",
      "email": "string",
      "createdAt": "string"
    }
  }
  ```

### 2. **User Login**

- **Endpoint:** `POST /api/auth/login`
- **Description:** Authenticates a user based on their email and password.
- **Request Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  - **Status:** 200 OK (Success)
    ```json
    {
      "status": "success",
      "message": "Login successful",
      "user": {
        "id": "string",
        "name": "string",
        "email": "string",
        "createdAt": "string"
      }
    }
    ```
  - **Status:** 404 Not Found (Invalid credentials or user not found)
    ```json
    {
      "status": "error",
      "message": "User not found or invalid credentials"
    }
    ```

### 3. **Get All User Profiles**

- **Endpoint:** `GET /api/auth/profiles`
- **Description:** Retrieves a paginated list of all user profiles.
- **Query Parameters:**
  - `page`: Page number (optional, default is `0`)
  - `size`: Number of records per page (optional, default is `10`)
- **Response:**
  - **Status:** 200 OK
    ```json
    {
      "status": "success",
      "message": "User profiles retrieved",
      "totalUsers": "total_count",
      "currentPage": "current_page",
      "totalPages": "total_pages",
      "users": [
        {
          "id": "string",
          "name": "string",
          "email": "string",
          "userSince": "string"
        },
        ...
      ]
    }
    ```

### 4. **Delete User Account**

- **Endpoint:** `DELETE /api/auth/delete/{email}`
- **Description:** Deletes a user account by their email.
- **Path Variable:**
  - `email`: The email of the user to be deleted.
- **Response:**
  - **Status:** 200 OK (Success)
    ```json
    {
      "status": "success",
      "message": "Account deleted successfully"
    }
    ```
  - **Status:** 404 Not Found (User not found or error during deletion)
    ```json
    {
      "status": "error",
      "message": "User not found or could not delete"
    }
    ```

## Error Handling

The API will return appropriate error messages with relevant HTTP status codes (e.g., `400 Bad Request`, `404 Not Found`, `500 Internal Server Error`) for unsuccessful requests.

---

## 🌐 Environment Variables (Render)

Make sure these variables are set in **Render > Environment > Environment Variables**:

| Key | Value |
|-----|-------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://<your-host>:5432/<database>` |
| `SPRING_DATASOURCE_USERNAME` | Supabase username |
| `SPRING_DATASOURCE_PASSWORD` | Supabase password |

---

## 📦 Run Locally

```bash
git clone https://github.com/your-repo/algospectra-backend.git
cd algospectra-backend
./mvnw spring-boot:run
```

---

## 🛠️ Future Improvements

- Email verification
- Password reset
- JWT refresh token mechanism
- Admin dashboard endpoints

---

## 📄 License

MIT License. Built with ❤️ by AlgoSpectra.

