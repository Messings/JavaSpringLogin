A Java Spring template for authorizing users based on their roles. 
Uses Spring security with bcrypt hashing and DaoAuthenticationProvider

Uses Postgres db:
PostgreSQL Table Setup:


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

-- Join table for the many-to-many relationship
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);


-- Give users roles
INSERT INTO user_roles (user_id, role_id) VALUES

(1, 2),  -- Give user with id=1 → ROLE_ADMIN

(5, 1);  -- Give user with id=5 → ROLE_USER


