# Mastery Project Don't Wreck My House
Don't Wreck My House is a reservation management system inspired by platforms like Airbnb. The primary users of this application are accommodation administrators, who manage reservations by pairing guests with available host locations. The system ensures no double-booking by marking reserved dates as unavailable.

#### Features
- View Reservations: Administrators can view all existing reservations for a particular host.
- Make a Reservation: Administrators can create a new reservation for a guest if the host location is available.
- Edit Reservations: Existing reservations can be modified to adjust dates or guest information.
- Cancel Reservations: Administrators may cancel upcoming reservations if necessary.

#### Main Menu Options

```
Main Menu
=========
0. Exit
1. View Reservations for Host
2. Make a Reservation
3. Edit a Reservation
4. Cancel a Reservation
Select [0-4]:

```

### Maven project with IntelliJ
Technology Stack: Java
Build Tool: Maven
Framework: Spring Context
Testing: JUnit 5
Encoding: UTF-8

This project demonstrates core concepts of Java application development using Spring for application management and JUnit for comprehensive testing.

### Project has these Components and packages

```
       src
        ├───main
	│   |───java
	│   │   └───learn
	│   │       └───mastery
	│   │           │   App.java
	│   │           │
	│   │           |───data
	│   │           │       DataException.java
	│   │           │       GuestFileRepository.java
	│   │           │       GuestRepository.java
	│   │           │       HostFileRepository.java
	│   │           │       HostRepository.java
	│   │           │       ReservationFileRepository.java
	│   │           │       ReservationRepository.java
	│   │           │
	│   │           |───domain
	│   │           │       GuestService.java
	│   │           │       HostService.java
	│   │           │       ReservationService.java
	│   │           │       Response.java
	│   │           │       Result.java
	│   │           │
	│   │           |───models
	│   │           │       Guest.java
	│   │           │       Host.java
	│   │           │       Reservation.java    
	│   │           │
	│   │           └───ui
	│   │                   ConsoleIO.java
	│   │                   Controller.java
	│   │                   GenerateRequest.java
	│   │                   MainMenuOption.java
	│   │                   View.java
	│   │
	│   └───resources- > dependency-configuration.xml
	└───test
	    └───java
	        └───learn
	            └───mastery
	                ├───data
	                │       GuestFileRepositoryTest.java
	                │       GuestRepositoryDouble.java
	                │       HostFileRepositoryTest.java
	                │       HostRepositoryDouble.java
	                │       ReservationFileRepositoryTest.java
	                │       ReservationRepositoryDouble.java
	                │
	                └───domain
	                        GuestServiceTest.java
	                        HostServiceTest.java
                                ReservationServiceTest.java

```

### Class details & design

<img width="603" alt="Screenshot 2025-03-06 at 10 04 22 PM" src="https://github.com/user-attachments/assets/0c970472-1cb3-439e-af94-25ce82797a40" />

<img width="939" alt="data" src="https://github.com/user-attachments/assets/07bb16ed-befa-407f-94e4-0fe4d4b08df5" />

<img width="543" alt="Screenshot 2025-03-04 at 12 24 51 PM" src="https://github.com/user-attachments/assets/698e53eb-670c-4d0e-b417-ec7095b7570e" />



