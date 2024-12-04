package module20;

/**
 * Пример простого класса с переопределением метода equals().
 */
public class Car {
    private String model;
    private String vin; // Уникальный идентификатор автомобиля

    public Car(String model, String vin) {
        this.model = model;
        this.vin = vin;
    }

    /**
     * Переопределяем метод equals() для сравнения объектов Car.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка ссылок
        if (obj == null || getClass() != obj.getClass()) return false; // Проверка класса
        Car car = (Car) obj;
        return vin.equals(car.vin); // Сравнение уникального VIN
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "VIN123");
        Car car2 = new Car("Toyota", "VIN123");
        Car car3 = new Car("Honda", "VIN456");

        System.out.println("car1 equals car2: " + car1.equals(car2)); // true
        System.out.println("car1 equals car3: " + car1.equals(car3)); // false
    }
}
