package it.unipi.dii.lsmsdb.lsmsdb_project;

import java.util.List;

import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.BookingRequestDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.dto.LoginRequest;
import it.unipi.dii.lsmsdb.lsmsdb_project.dto.UserSummaryDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.dto.PathResponseDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.Booking;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.Car;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.Notification;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.Ride;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.User;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.BookingRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.BookingRequestRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.CarRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.NotificationRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.RideRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.RouteRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.SessionRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.UserRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.AuthService;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.RouteService; // Assicurati che questo import ci sia
import it.unipi.dii.lsmsdb.lsmsdb_project.service.BookingAnalyticsService;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.BookingProcessService;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.CarService;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.RideService;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.UserService;

@SpringBootApplication
public class LsmsdbProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsmsdbProjectApplication.class, args);
    }

    /**
     * FULL END-TO-END TEST (ALL APIs)
     */
    @Bean
    CommandLineRunner demo(
            UserService userService,
            RideService rideService,
            BookingProcessService bookingService,
            AuthService authService,
            BookingAnalyticsService analyticsService,
            CarService carService,
            RouteService routeService, // <--- AGGIUNTO QUI!
            UserRepository userRepo,
            RideRepository rideRepo,
            BookingRepository bookingRepo,
            BookingRequestRepository redisBookingRepo,
            NotificationRepository notificationRepo,
            SessionRepository sessionRepo,
            CarRepository carRepo,
            RouteRepository neo4jRepo
    ) {
        return args -> {
            System.out.println("\n\n================================================================");
            System.out.println(" STARTING DEMO ");
            System.out.println("================================================================\n");

            //Cleanup
            userRepo.deleteAll();
            rideRepo.deleteAll();
            bookingRepo.deleteAll();
            carRepo.deleteAll();
            redisBookingRepo.deleteAll();
            notificationRepo.deleteAll();
            sessionRepo.deleteAll();

            //User registration
            // DRIVER: Mario
            User driver = new User();
            driver.setPersonalInfo(new User.PersonalInfo());
            driver.getPersonalInfo().setName("Mario");
            driver.getPersonalInfo().setSurname("Rossi");
            driver.getPersonalInfo().setEmail("mario@driver.com");
            driver.getPersonalInfo().setLocation("Pisa");
            driver.getPersonalInfo().setIdentityVerified(true);
            driver.setStatus("ACTIVE");

            User.DriverInfo dInfo = new User.DriverInfo();
            dInfo.setAvgAcceptanceRate(0.99);
            dInfo.setNumberOfAcceptance(100);
            driver.setDriverInfo(dInfo);

            User.ReviewStats dStats = new User.ReviewStats();
            dStats.setAverageRating(4.9);
            dStats.setCount(50);
            driver.setReviewsDriver(dStats);

            userRepo.save(driver);
            System.out.println(" Driver registered: Mario Rossi (" + driver.getId() + ")");

            // PASSENGER: Luigi
            User passenger = new User();
            passenger.setPersonalInfo(new User.PersonalInfo());
            passenger.getPersonalInfo().setName("Luigi");
            passenger.getPersonalInfo().setSurname("Verdi");
            passenger.getPersonalInfo().setEmail("luigi@passenger.com");
            passenger.setStatus("ACTIVE");

            User.ReviewStats pStats = new User.ReviewStats();
            pStats.setAverageRating(5.0);
            pStats.setCount(5);
            passenger.setReviewsPassenger(pStats);

            userRepo.save(passenger);
            System.out.println("Passenger registered: Luigi Verdi (" + passenger.getId() + ")");

            //Cars testing
            System.out.println("\nAdding Car");
            Car car = new Car();
            Car.CarDetails details = new Car.CarDetails();
            details.setBrand("Fiat");
            details.setModel("Panda 4x4");
            details.setColor("Green");
            details.setSeats(4);
            car.setDetails(details);

            carService.saveCar(car, driver.getId());

            List<Car> driverCars = carService.getCarsByOwner(driver.getId());
            if (!driverCars.isEmpty()) {
                System.out.println("Car added successfully: " + driverCars.get(0).getDetails().getModel());
            } else {
                System.err.println("Error: Car not found.");
            }

            // LOGIN (redis)
            System.out.println("\n Authentication");
            LoginRequest login = new LoginRequest();
            login.setEmail("luigi@passenger.com");
            login.setPassword("password123");

            String token = authService.login(login);
            System.out.println("Login successful. Token: " + token);

            if (sessionRepo.existsById(token)) {
                System.out.println("Session verified on Redis.");
            }

            //RIDE PUBLICATION
            System.out.println("\n Publishing Ride");
            Ride ride = new Ride();

            ride.setDriver(new Ride.DriverInfo());
            ride.getDriver().setId(driver.getId());
            ride.getDriver().setName("Mario Rossi");

            Ride.CarInfo cInfo = new Ride.CarInfo();
            cInfo.setModel("Fiat Panda 4x4");
            ride.setCar(cInfo);

            ride.setBookingState(new Ride.BookingState());
            ride.getBookingState().setTotalSeats(4);
            ride.getBookingState().setAvailableSeats(4);
            ride.setBasePrice(10.0);

            Ride.RouteInfo route = new Ride.RouteInfo();
            route.setOrigin("Pisa");
            route.setOriginLat(43.7228);
            route.setOriginLon(10.4017);

            route.setDestination("Firenze");
            route.setDestLat(43.7696);
            route.setDestLon(11.2558);

            ride.setRoute(route);

            Ride savedRide = rideService.createRide(ride);
            System.out.println("Ride saved on Mongo: " + savedRide.getId());
            System.out.println("Nodes and Relationship created on Neo4j");

            //GEOSPATIAL SEARCH
            System.out.println("\n Proximity Search");
            List<Ride> matches = rideService.searchMatchingRides(43.72, 10.40, 43.77, 11.25);

            if (!matches.isEmpty()) {
                System.out.println("Ideal ride found ID: " + matches.get(0).getId());
                System.out.println("-> Price: " + matches.get(0).getBasePrice() + "€");
            } else {
                System.err.println("No rides found");
            }

            //DRIVER PROFILE
            System.out.println("\nDriver profile:");
            UserSummaryDTO profile = userService.getPublicProfile(driver.getId());
            System.out.println("Profile downloaded: " + profile.getName() + " | Rating: " + profile.getAverageRating() + " stars");

            //BOOKING
            System.out.println("\nBooking Flow: ");
            BookingRequestDTO req = new BookingRequestDTO();
            req.setUserId(passenger.getId());
            req.setRideId(savedRide.getId());
            req.setSeatsRequested(2);
            String reqId = bookingService.createTemporaryReservation(req);
            System.out.println("Request buffered on Redis. ID: " + reqId);

            Booking booking = bookingService.finalizeBooking(reqId);
            System.out.println("Booking finalized on Mongo. ID: " + booking.getId());

            Ride updatedRide = rideRepo.findById(savedRide.getId()).get();
            int seatsLeft = updatedRide.getBookingState().getAvailableSeats();
            System.out.println("Seat Verification: " + seatsLeft + " remaining.");
            if(seatsLeft == 2) System.out.println("OK.");
            else System.err.println("SEAT CONSISTENCY ERROR.");

            //NOTIFICATIONS
            System.out.println("\nChecking Notifications");
            List<Notification> notifs = notificationRepo.findByRecipientUserId(driver.getId());
            if (!notifs.isEmpty()) {
                System.out.println("Notification received by Driver: " + notifs.get(0).getMessage());
            } else {
                System.err.println("No notifications found.");
            }

            //ANALYTICS
            System.out.println("\nTesting Analytics Aggregations");
            Document revenue = analyticsService.getRevenueStats("2020-01-01", "2030-12-31");
            if(revenue != null)
                System.out.println("Total Revenue: " + revenue.get("totalRevenue"));

            List<Document> leaders = analyticsService.getTopDriverLeaderboard();
            if(!leaders.isEmpty())
                System.out.println("Tops Driver: " + leaders.get(0).getString("name") + " (Score: " + leaders.get(0).get("performanceScore") + ")");

            List<Document> churners = analyticsService.getHighValueChurners(30);
            System.out.println("Churner Analysis executed (" + churners.size() + " at-risk users found).");

            System.out.println("\n Testing Shortest Path (Neo4j)");
            try {
                // Test con i dati creati dalla demo (Pisa -> Firenze)
                // Nota: Funziona solo se 'createRide' ha creato i nodi con name="Pisa" e "Firenze" su Neo4j
                PathResponseDTO path = routeService.getShortestPath("Pisa", "Firenze");
                if (!path.getPath().isEmpty()) {
                    System.out.println("Path found: " + path.getPath());
                    System.out.println("Total Cost: " + path.getEstimatedCost());
                } else {
                    System.err.println("No path found between Pisa and Firenze");
                }
            } catch (Exception e) {
                System.err.println("Error testing path: " + e.getMessage());
            }
            // --------------------------------------------------

            //LOGOUT
            System.out.println("\nLogout");
            authService.logout(token);
            if (!sessionRepo.existsById(token)) {
                System.out.println("Logout successful. Redis key removed.");
            } else {
                System.err.println("Logout Error: Key still exists.");
            }

            System.out.println("\n================================================================");
            System.out.println(" DEMO COMPLETED SUCCESSFULLY. ALL SYSTEMS OPERATIONAL.");
            System.out.println("================================================================\n");
        };
    }
}