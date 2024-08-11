import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//4 classes

class Car{
    private String carId;
    private String model;
    private String brand;
    private double basePricePerDay;
    private boolean isAvailable;
    //kept private for encapsulation, will use getters and setters to access these


    //making a contructor

    public Car(String carId, String brand, String model, double basePricePerDay){
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basePricePerDay=basePricePerDay;
        this.isAvailable=true;  //we filled details of a new car so its availability is true

    }

    public String getCarId(){
        return carId;   //using getters to access private members
    }

    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable=false;   // if car is rented it is no longer available, so we keep false
    }
    public void returnCar(){
        isAvailable=true;   // when car is returned , it becomes available , so true
    }
}

class Customer{

    private String customerId;
    private String customerName;

    //making constrictor
    public Customer(String customerId, String customerName){
        this.customerId=customerId;
        this.customerName=customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return customerName;
    }

}

class Rental{

    private Car car;   // creating an object of car class (or type car)
    private Customer customer;
    private int days;

    //contructor

    public Rental(Car car, Customer customer, int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

}


class carRentalSystem{

    //each list contains objects, eg car list contains car objects,
    // each car object has its own model, brand, id etc
    //used list over array, as list do not hv fixed sizes

    private List<Car>cars;
    private List<Customer> customers;
    private List<Rental>rental;


    //making constructor
    public carRentalSystem(){
        cars=new ArrayList<>();   // by creating constructor, we allocated space in memory
        customers=new ArrayList<>();
        rental=new ArrayList<>();

    }

    public void addCar(Car car){
        cars.add(car);  // we hv input car,of class Car, we add car to the list cars
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    //rentCar is when a person is renting a car
    public void rentCar(Car car, Customer customer,int days){
        if(car.isAvailable()){
            //car is available
            car.rent();   // setting availability of car as false
            rental.add(new Rental(car,customer,days));
        }
        else System.out.println("Car is not available for rent");
    }

    //returnCar is when a person is returning a car
    public void returnCar(Car car){
        car.returnCar();  //setting availabilty of car to true
        Rental rentalToRemove=null;
        /* thsi will strore the object of rental list, which needs to be removed
        eg if nano is being returned, we need to remove details of nano from the rental list, so we
        first search the rental list,if we do not finde it, means the car was not rented, if we
        find nano, we remove it from the list, as it is no longer rented now it is available */

        for(Rental rental:rental)
        {
            //we are iterating over the rental array, Rental is the class.
            // pink rental is the list we are iterating

            if(rental.getCar()==car)
            {
                rentalToRemove=rental;
                //in rentalToRemove,we save the info of the object of rental to be removed,
                break;
            }
        }
        if(rentalToRemove!=null)  // we found the car which was rented now returned
        {
            rental.remove(rentalToRemove);  // we remove that car from rental list, as now it is available

        }
        else System.out.println("Car was not rented");  // if we do not find any car from rental list

    }

    public void menu(){
        Scanner sc=new Scanner(System.in);
        while(true) {
            System.out.println("===Car Rental System===");
            System.out.println("1. Rent A Car");
            System.out.println("2.Return A Car");
            System.out.println("3. Exit");
            System.out.println("Enter Your Choice: ");

            int choice = sc.nextInt();  //taking choice from user
            sc.nextLine();

            if (choice == 1) {
                System.out.println("\n==Rent A Car==\n");
                System.out.println("Enter Your Name: ");
                String customerName = sc.nextLine();   //nextLine returns the line that was skipped
                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    //object is car of class Car, we are traversing a list named cars
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());

                    }
                }
                System.out.println("\nEnter the Car Id you want to rent: ");
                String carId = sc.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                //we are giving id to customer , eg 1st customer will have id CUS 1 as
                // customer.size() is 0, 2nd customer i will be CUS 2

                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {  // cars is the list we r traversing

                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==Rental Inforrmation==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.println("Total Price: $" + totalPrice);

                    System.out.println("\nConfirm Rental(Y/N): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car Rented Successfully!");

                    } else System.out.println("\nRental Canceled");
                } else System.out.println("Invalid Car Selection or Car Not Available");  // when selectedcar==null
            } else if (choice == 2) {
                System.out.println("\n==Return A Car==");
                System.out.println("Enter The Car Id You Want To Return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;   // checking if any such car exists in our list
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                        //here !car.isavaialable() implies isavaialable was false, i.e car was rented
                    }
                }

                if (carToReturn != null)  // there is some car that is being returned
                {
                    Customer customer = null;
                    for (Rental rental : rental) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();  // we are retrieving customer name to
                            // display a msg that says car returned by xyz

                            break;
                        }

                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by: " + customer.getName());

                    } else {  //customer ==null
                        System.out.println("Car was not rented or rental information missing");
                    }
                } else System.out.println("Invalid Car ID Or Car Not Rented");
            } else if (choice == 3) {
                break;
            } else System.out.println("Invalid Choice.Please Enter A Valid Option");
        }
        System.out.println("\nThank You For Using The Car Rental System");
    }
}

public class Main {
    public static void main(String[] args) {
        carRentalSystem rentalSystem=new carRentalSystem();  // created an object of carrentalsyste class

        Car car1=new Car("C001","TOYOTA","CAMRY",70);
        Car car2=new Car("C002","HONDA","ACCORD",80);
        Car car3=new Car("C003", "MAHINDRA","THAR",105);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();


        //if u add incorrect car id to rent, it will throw the error after taking ur name, not at that very moment
        //code takes incorrect car id as i/p, at the end it will return wrong id statement










    }
}