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
│   ├── controller
│   ├── model
│   ├── repository
│   ├── security
│   ├── service
│   └── AlgospectraBackendApplication.java
└── resources
    ├── application.properties
```

---

## 🔗 API Endpoints

### 🧪 Health Check

- **GET** `/algohealth`  
  ⮕ Check if the API is alive and responsive.


### 🔐 Auth

#### `POST /api/auth/register`
Registers a new user.

**Request Body:**
```json
{
  "name": "Sachin Kumar",
  "email": "sachin@example.com",
  "password": "securePassword123"
}
```

#### `POST /api/auth/login`
Logs in a user.

**Request Body:**
```json
{
  "email": "sachin@example.com",
  "password": "securePassword123",
  "rememberMe": true
}
```

#### `POST /api/auth/guest-login`
Logs in a temporary guest user. Generates a unique guest ID.

#### `POST /api/auth/logout`
Logs out the current user. Clears session or token.

---

### 👤 Profile

#### `GET /api/profile/{emailId}`
Returns profile details of the logged-in user (guest or registered).

**Response:**
```json
{
  "id": "user-or-guest-id",
  "name": "Sachin Kumar",
  "email": "sachin@example.com",
  "role": "USER" | "GUEST"
}
```

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

