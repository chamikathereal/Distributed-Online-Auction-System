# 🚀 Distributed Online Auction System

Welcome to the **Distributed Online Auction System** repository!  
This project is a robust, modular, and scalable platform for conducting real-time online auctions, built with modern Java EE technologies and a distributed architecture.  
It is designed to deliver a seamless, interactive, and transparent auction experience for both administrators and bidders.  

---

## 📝 Overview

The Distributed Online Auction System (DOAS) provides a virtual marketplace where users can register, view auction items, place bids in real time, and compete to win products. The system leverages a distributed, multi-module architecture to ensure scalability, responsiveness, and maintainability.  
Key features include:

- **Real-time bidding** with live updates via WebSockets
- **Admin panel** for managing auction items and monitoring bids
- **Secure user sessions** and authentication
- **Auction timers** and automatic auction closure
- **Modular architecture** for easy extension and maintenance

---

## 📽️ Demo Video
[![JMS ActiveMQ Project Demo](https://github.com/chamikathereal/JMS-ActiveMQ/blob/main/JMS-ActiveMQ.png)](https://youtu.be/pc15pltfXDs)

---

## 🗂️ Project Structure

The project is organized as a Maven multi-module application:

```
Distributed-Online-Auction-System/
├── core/
├── ejb/
├── web/
├── ear/
├── pom.xml
```

| Module | Description |
|--------|-------------|
| `core` | Domain models (AuctionItem, Bid, User) and shared logic |
| `ejb`  | Enterprise JavaBeans (EJB) for business logic, bid processing, session management, and broadcasting |
| `web`  | Web frontend, servlets, JSPs, WebSocket endpoints, and static resources |
| `ear`  | Enterprise Archive for deployment, aggregating all modules |

---

## 🧩 Module Descriptions

### 1. `core` – Domain Model Layer

Defines the core business entities:

- **AuctionItem**: Represents an item available for auction, including name, description, starting price, and expiry time.
- **Bid**: Represents a bid placed by a user, including bidder details, bid amount, and the associated auction item.
- **User**: Represents a registered user (bidder), including name and email.

This module is shared by both EJB and web modules to ensure consistency across the application.[2]

---

### 2. `ejb` – Business Logic Layer

Implements the core business processes using EJBs:

- **AuctionManagerBean**: Manages auction items and bids, provides methods to add items, retrieve items, and fetch bids.
- **BidManagerBean**: Handles bid validation and placement, ensuring that each bid is higher than the current highest and that the auction is active.
- **BroadcasterBean**: Sends bid updates to the messaging system (JMS), enabling real-time notifications.
- **UserSessionManagerBean**: Manages user sessions, mapping HTTP sessions to user objects for authentication and authorization.

**Key Features:**
- Thread-safe, singleton beans for shared state
- JMS integration for scalable, asynchronous bid broadcasting
- Robust exception handling for invalid bids[4]

---

### 3. `web` – Presentation & Real-Time Layer

Provides the user interface and real-time communication:

- **Servlets**:
  - `HomeServlet`: Displays auction items and live bids to users
  - `LoginServlet` / `LogoutServlet`: User authentication and session management
  - `AdminServlet` / `AdminLoginServlet`: Admin authentication and item management
  - `PlaceBidServlet`: Handles bid submissions
- **WebSocket**:
  - `BidWebSocketEndpoint`: Enables real-time bid and item updates to all connected clients
  - `WebSocketBroadcaster`: Broadcasts messages to active WebSocket sessions
- **JSP Pages**:
  - `index.jsp`, `login.jsp`, `auction.jsp`, `admin.jsp`, etc., for user and admin interfaces
- **Static Assets**:
  - CSS, JS (including `auction.js` for client-side WebSocket logic), fonts, and images

**User Experience:**
- Live auction and bid updates without page reloads
- Countdown timers for auction expiry
- Responsive, Bootstrap-based UI[1]

---

### 4. `ear` – Deployment Aggregator

Packages all modules into a single deployable EAR (Enterprise Archive):

- Bundles the `core`, `ejb`, and `web` modules
- Provides deployment descriptors and configuration for application servers (e.g., Payara, GlassFish)
- Ensures seamless integration and deployment of all components[3]

---

## 📂 Detailed Project Structure

```
Distributed-Online-Auction-System/
├── core/
│   └── src/main/java/io/github/chamikathereal/auction/core/model/
│       ├── AuctionItem.java
│       ├── Bid.java
│       └── User.java
├── ejb/
│   └── src/main/java/io/github/chamikathereal/auction/ejb/
│       ├── bean/
│       ├── exception/
│       └── remote/
├── web/
│   └── src/main/java/io/github/chamikathereal/auction/web/
│       ├── listener/
│       ├── servlet/
│       └── websocket/
│   └── src/main/webapp/
│       ├── assets/
│       ├── *.jsp
├── ear/
│   └── src/main/
└── pom.xml
```

---

## 🏛️ System Architecture

- **Domain Layer (`core`)**: Defines entities and shared logic.
- **Business Layer (`ejb`)**: Handles auction logic, bid processing, and session management.
- **Presentation Layer (`web`)**: User/admin interfaces, real-time updates, and static content.
- **Deployment Layer (`ear`)**: Aggregates and deploys all modules as a single application.

---

## 🔍 Module Responsibilities

| Module     | Key Classes / Files                | Responsibilities                                                                                   |
|------------|------------------------------------|----------------------------------------------------------------------------------------------------|
| `core`     | `AuctionItem`, `Bid`, `User`       | Domain models, serialization, and business rules                                                   |
| `ejb`      | `AuctionManagerBean`, `BidManagerBean`, `BroadcasterBean`, `UserSessionManagerBean`, `InvalidBidException` | Business logic, bid validation, messaging, user session management                                 |
| `web`      | `HomeServlet`, `LoginServlet`, `PlaceBidServlet`, `AdminServlet`, `BidWebSocketEndpoint`, `WebSocketBroadcaster`, JSPs, JS/CSS | Web interface, real-time updates, REST endpoints, WebSocket communication, static resources        |
| `ear`      | `application.xml`, `pom.xml`       | Deployment descriptor, module aggregation, application server integration                          |

---

## ⚙️ How It Works

1. **Admin adds auction items** via the admin panel.
2. **Users register/login** and view live auction items.
3. **Users place bids**; each bid is validated and processed by the EJB layer.
4. **Bids are broadcasted** in real time to all connected clients via JMS and WebSockets.
5. **Auction timers** control the start and end of each auction, automatically closing bidding when expired.
6. **All activities** are logged for transparency and debugging.

---

## 🖥️ Technologies Used

- Java 11
- Jakarta EE (EJB, JMS, Servlet, WebSocket)
- JSP, HTML5, CSS3, Bootstrap 5
- JavaScript (for real-time updates)
- Maven (multi-module project)
- Payara/GlassFish (application server)

---

## 📦 Getting Started

1. **Clone the repository**  
   `git clone https://github.com/chamikathereal/Distributed-Online-Auction-System.git`

2. **Build the project**  
   `mvn clean install`

3. **Deploy the EAR file** to your Java EE application server (e.g., Payara, GlassFish).

4. **Access the application**  
   - User: `http://localhost:8080/plexusbid/`
   - Admin: `http://localhost:8080/plexusbid/admin-login.jsp`

---

## 🏷️ Key Features

- Real-time, distributed auctioning
- Modular, maintainable codebase
- Secure session and bid management
- Responsive and modern UI
- Extensible for new auction types or features

---

## 🧑‍💻 Author

**Chamika Gayashan**  
Undergraduate Software Engineer | Sri Lanka  
Linkedin: @chamikathereal  

_Current date: Wednesday, June 11, 2025, 8:45 PM +0530_

---

*Thank you for checking out the Distributed Online Auction System! If you have any questions or suggestions, feel free to open an issue or reach out via LinkedIn.*



