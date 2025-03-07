### Project Don't Wreck My House

### Planning have these Components and packages

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
	│   │           │       Result.java - should think if need it
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

![class.png](/src/class.png)

```
UI Reservation 

ViewByEmail -> enter host email or groupByHostEmail (required)
                     host last name: city to book
                     findAll: sort by date, total $ for stay, guest full name, email, phone

Make -> enter guest email or groupByGuestEmail (required)
              enter host email or groupByHostEmail (required)
              see host last name: city to book
              findAll: sort by date, total $ for stay, guest full name, email. phone
              Summary: future start-date (required)
                              end-date (required)
                              total $ (rate based standard & weekend (fri-sat night)
The reservation may never overlap existing reservation dates.

Edit -> enter host email or groupByHostEmail (required)
              enter host email or groupByHostEmail (required)
              host last name: city to book
              findAll: sort by date, total $ for stay, guest full name, email, phone
              enter for edit ByGuest_Id
              start-date (show old info)
              end-date (show old info)
              Summary: start-date (required)
                       end-date (required)
                       total $ (rate based standard & weekend)

Cancel -> enter host email or groupByHostEmail (required)
                enter host email or groupByHostEmail (required)
                host last name: city to book
                findAll: sort by date, total $ for stay, guest full name, email, phone (future)
                enter ByGuest_Id to remove (only future)

```

### Work Plan
1. *[x] Make a plan for what should be in the project, 6hrs (Actual time 10 hours)
2. *[x] Start from the backend layers: Models, 3hrs (Actual time 3 hours)
3. *[x] Make Data, 4hrs (Actual 6 hours)
4. *[x] Next make unit tests for Data, including fixing bugs, 4hrs (Actual time 5 hours)
5. *[x] Start work on the domain layer, 4hrs (Actual time 5 hours)
6. *[x] Do unit test for the domain, including fixing bugs, 2hrs (Actual time 3 hours)
7. *[x] Be sure all unit tests pass, be sure added positive & negative cases, 2 hrs (Actual time 4 hours)
    Start to work on the UI layer:
8. *[x] Add Spring dependency injection to the project (use either XML or annotations) to run `App.java` max 1hr (Actual time 1 hour)
9. *[ ] Break UI layer into several steps: make `MainMenu` and `ViewReservation`, fixing some logic on domain layer, 4 hrs
10. *[ ] Do `edit` and `cancel` reservation including helper methods, 8hrs
11. *[ ] Be sure all tests pass and the application works without any issue, 2hrs