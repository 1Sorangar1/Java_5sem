package module30;

// Класс собственного исключения, унаследованный от Exception
public class MyFirstException extends Exception {

    // Конструктор без параметров
    public MyFirstException() {
        super("Произошло исключение MyFirstException");
    }

    // Конструктор с сообщением
    public MyFirstException(String message) {
        super(message);
    }

    // Конструктор с сообщением и причиной
    public MyFirstException(String message, Throwable cause) {
        super(message, cause);
    }
}
