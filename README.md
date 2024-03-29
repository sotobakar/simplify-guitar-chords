# Project Overview

## Technology Used

- **Spring Boot**
- **Spring Data JPA**
- **Docker**: 
- **OpenAI API**
- **PostgreSQL 15.3**
- **Flyway Migration Tool**

## Features

[Postman Collection](https://drive.google.com/file/d/1Pjak-EQoAXAZyuedQIb3302Bf6IHlNXz/view?usp=sharing)

### 1. Simplify Guitar Chord Songs
- **Endpoint**: POST /songs/simplify
- **Criteria for Simplification**:
    - Number of open chords
    - Number of barre chords
    - Number of chord transitions
    - Finger placement difficulty

### 2. Get All Songs with Chords
- **Endpoint**: GET /songs

### 3. Get Song by ID
- **Endpoint**: GET /songs/{id}

## Requirements

- Docker installed on your machine.

## Setup Steps

1. Copy `.env.example` to `.env`.
2. Fill the `OPENAPI_API_KEY` variable in `.env` with your API KEY.
3. Run the following command to start the application:

```bash
docker-compose up
