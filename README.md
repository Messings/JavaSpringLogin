# 🔐 Java Spring Role-Based Authorization Template by messings

A **Spring Boot** template for authorizing user API calls based on their **roles** using **Spring Security**, **BCrypt password hashing**, and **PostgreSQL**.

---

## 🧩 Features

- ✅ Secure authentication using **Spring Security (sessions)**
- 🔑 Passwords hashed with **BCrypt**
- 🧍‍♂️ Role-based access control (`ROLE_USER`, `ROLE_ADMIN`)
- 🗄️ **PostgreSQL** database integration
- ⚙️ Authentication handled via **DaoAuthenticationProvider**
- 🧰 Ready to test using **Postman**

---

## 1.
## 🏗️ Database Setup (PostgreSQL)

```sql
-- Roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_type VARCHAR(50) NOT NULL UNIQUE
);

-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Join table for many-to-many relationship
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Example: assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 2),  -- user id=1 → ROLE_ADMIN
(5, 1);  -- user id=5 → ROLE_USER

```

## 2.📨 Postman
POST /register
Content-Type: application/json
- register via json. ( /register and /login urls are whitelisted from authorizeHttpRequests)
```json
{
  "username": "john",
  "password": "mypassword"
}
```


## 3. Assign role in the db query:
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

## 4. Test the endpoints
/admin → Only ROLE_ADMIN can access

/api/product → Both ROLE_USER and ROLE_ADMIN has access

/login & /register → Public endpoints



