package utility;
class Booking {
    private Passenger passenger; // Has-a relationship
    private Flight flight; // Has-a relationship

    public Booking(Passenger passenger, Flight flight) {
        this.passenger = passenger;
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "passenger=" + passenger +
                ", flight=" + flight.getFlightNumber() +
                '}';
    }
}