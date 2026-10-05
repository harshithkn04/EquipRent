# EquipRent - Peer-to-Peer Equipment Rental Platform

EquipRent is a peer-to-peer equipment rental web application that allows users to discover, view, and rent equipment available from other users.

The platform is designed to make equipment sharing easier by connecting equipment owners with people who need equipment temporarily.

---

## Features

### User Features

- User registration and login
- Browse available equipment
- Search equipment by name
- Filter equipment by category
- Search equipment by owner
- View detailed equipment information
- Rent available equipment
- Track rental information
- View equipment availability

### Owner Features

- Add and manage equipment
- View owned equipment
- Track equipment availability
- Manage rental-related information

### Equipment Categories

The application supports categories such as:

- Cameras
- Tools
- Camping Gear
- Party Supplies
- Outdoor Gear
- Electronics

---

## Technology Stack

### Frontend

- React
- JavaScript
- HTML5
- CSS3
- Vite
- React Router

### Backend

- Java
- JDBC
- MySQL
- HTTP Server / Handlers

### Database

- MySQL

### Development Tools

- Visual Studio Code
- Git
- GitHub
- npm

---

## Project Structure

```text
PPER TO PEER rental webappl/
│
├── backend/
│   ├── lib/
│   │   └── mysql-connector-j.jar
│   │
│   └── src/
│       ├── DBConnection.java
│       ├── Equipment.java
│       ├── EquipmentDAO.java
│       ├── EquipmentHandler.java
│       ├── LoginHandler.java
│       ├── Main.java
│       ├── RegisterHandler.java
│       ├── RentalDAO.java
│       ├── RentalHandler.java
│       ├── RentalTrackingDAO.java
│       ├── RentalTrackingHandler.java
│       ├── ReviewDAO.java
│       ├── ReviewHandler.java
│       ├── User.java
│       └── UserDAO.java
│
├── frontend/
│   ├── css/
│   ├── js/
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── owner.html
│   ├── equipment-details.html
│   └── rental-tracking.html
│
├── frontend-react/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   │   ├── EquipmentCard.jsx
│   │   │   ├── FilterBar.jsx
│   │   │   └── Navbar.jsx
│   │   │
│   │   ├── pages/
│   │   │   ├── Home.jsx
│   │   │   ├── Login.jsx
│   │   │   ├── Register.jsx
│   │   │   ├── EquipmentDetails.jsx
│   │   │   ├── OwnerDashboard.jsx
│   │   │   └── RentalTracking.jsx
│   │   │
│   │   ├── App.jsx
│   │   ├── App.css
│   │   ├── index.css
│   │   └── main.jsx
│   │
│   ├── package.json
│   └── vite.config.js
│
└── README.md

Getting Started
Prerequisites
Make sure the following are installed:
- Java JDK
- MySQL
- Node.js
- npm
- Git
- Visual Studio Code
Installation
1. Clone the Repository
git clone <your-github-repository-url>

Navigate to the project:
cd "PPER TO PEER rental webappl"

2. Database Setup
The application uses MySQL as its database.
Create the database:
CREATE DATABASE equiprent;

Configure the database connection inside:
backend/src/DBConnection.java

Update the database URL, username, and password according to your local MySQL configuration.
Do not commit database passwords or other sensitive credentials to GitHub.
3. React Frontend Setup
Navigate to the React frontend:
cd frontend-react

Install dependencies:
npm install

Start the development server:
npm run dev

The application will normally run at:
http://localhost:5173

4. Backend Setup
The backend is developed using Java and JDBC.
Backend source files are located in:
backend/src/

The backend uses the MySQL JDBC connector to communicate with the MySQL database.
Before running the backend:
1. Start MySQL.
2. Create the equiprent database.
3. Configure the database connection.
4. Make sure the MySQL JDBC connector is available.
5. Compile and run the Java backend.

Application Architecture
                    EquipRent
                       │
                       ▼
              React Frontend
                       │
                       │ HTTP Requests
                       ▼
                 Java Backend
                       │
                       │ JDBC
                       ▼
                MySQL Database

The React frontend provides the user interface.
The Java backend handles application requests and communicates with the MySQL database using JDBC.
Frontend Architecture
The React application is organized into reusable components and pages.
Components
- Navbar
- FilterBar
- EquipmentCard
Pages
- Home
- Login
- Register
- Equipment Details
- Owner Dashboard
- Rental Tracking
React Router is used for navigation between application pages.
Backend Architecture
The backend follows a simple layered structure.
Model Classes
The application contains Java classes representing entities such as:
- User
- Equipment
DAO Layer
The Data Access Object classes handle database operations.
Examples:
- UserDAO
- EquipmentDAO
- RentalDAO
- RentalTrackingDAO
- ReviewDAO
Handler Layer
The handler classes process different application requests.
Examples:
- LoginHandler
- RegisterHandler
- EquipmentHandler
- RentalHandler
- RentalTrackingHandler
- ReviewHandler
Database Connection
Database connectivity is handled through:
DBConnection.java

Main Functionalities
Equipment Discovery
Users can browse equipment available for rent and view:
- Equipment name
- Description
- Category
- Owner
- Availability
Search
Users can search for equipment by name.
Category Filtering
Users can filter equipment based on categories such as:
- Cameras
- Tools
- Camping Gear
- Party Supplies
- Outdoor Gear
- Electronics
Owner Search
Users can search for equipment listed by a particular owner.
Equipment Details
Users can open an equipment item and view detailed information before renting.
Rental
Users can rent equipment when the required quantity is available.
Rental Tracking
Users can track rental-related information through the rental tracking section.
Owner Dashboard
Equipment owners can manage their equipment and monitor availability and rental information.
Authentication
The application provides registration and login functionality for users.
Development Commands
Install Dependencies
cd frontend-react
npm install

Start Development Server
npm run dev

Build React Application
npm run build

Security
Sensitive information should not be committed to the repository.
The following should remain private:
- MySQL passwords
- API keys
- Authentication secrets
- Environment variables
- Other private credentials
Use environment variables or local configuration files for sensitive information.
Future Enhancements
- Online payment integration
- Location-based equipment discovery
- Equipment image upload
- Email notifications
- Rental approval and rejection workflow
- Equipment ratings and reviews
- Owner verification
- User profile management
- Improved authentication and authorization
- Responsive mobile interface
- Admin dashboard
- Cloud deployment
- Real-time rental status updates
Screenshots
Screenshots of the application can be added here.
Home Page
Add a screenshot of the EquipRent home page here.
Equipment Listing
Add a screenshot of the equipment listing page here.
Equipment Details
Add a screenshot of the equipment details page here.
Owner Dashboard
Add a screenshot of the owner dashboard here.
Project Status
The project currently includes:
- React-based frontend
- Java backend
- MySQL database integration
- User registration
- User login
- Equipment browsing
- Equipment search
- Category filtering
- Owner filtering
- Equipment details
- Rental functionality
- Rental tracking
- Owner dashboard
Contributing
Contributions and suggestions are welcome.
To contribute:
1. Fork the repository.
2. Create a new branch.
git checkout -b feature/your-feature

3. Make your changes.
4. Add the changes.
git add .

5. Commit the changes.
git commit -m "Add new feature"

6. Push the branch.
git push origin feature/your-feature

7. Open a Pull Request.