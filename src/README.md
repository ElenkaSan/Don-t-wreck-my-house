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



### Work Plan

1. *[x] Make a plan for what should be in the project, 6hrs (Actual time 10 hours)
2. *[x] Start from the backend layers: Models, 3hrs (Actual time 3 hours)
3. *[x] Make Data, 4hrs (Actual 6 hours)
4. *[x] Next make unit tests for Data, including fixing bugs, 4hrs (Actual time 5 hours)
5. *[x] Start work on the domain layer, 4hrs (Actual time 5 hours)
6. *[x] Do unit test for the domain, including fixing bugs, 2hrs (Actual time 3 hours)
7. *[ ] Be sure all unit tests pass, be sure added positive & negative cases, 2 hour
    Start to work on the UI layer:
8. *[ ] Break UI layer into several steps, 8 hours
9. *[ ] Add Spring dependency injection to the project (use either XML or annotations) to run `App.java` max 1 hour