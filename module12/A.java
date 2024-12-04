package module12;

/**
 * Класс демонстрирует различные способы инициализации
 * final переменной.
 */
public class A {
    public final int a; // Константа, которая должна быть инициализирована.

    /**
     * Способ 1: Инициализация в конструкторе.
     */
    public A(int value) {
        this.a = value; // Инициализация через конструктор.
    }

    /**
     * Способ 2: Инициализация в блоке инициализации.
     */
    public A() {
        this.a = 42; // Инициализация константы значением по умолчанию.
    }

    /**
     * Способ 3: Инициализация через статический конструктор (если поле static).
     */
    public static class B {
        public static final int b;

        static {
            b = 99; // Инициализация в статическом блоке.
        }
    }

    public static void main(String[] args) {
        // Инициализация через разные конструкторы
        A obj1 = new A(10);
        System.out.println("obj1.a = " + obj1.a);

        A obj2 = new A();
        System.out.println("obj2.a = " + obj2.a);

        // Доступ к статической константе
        System.out.println("Static constant B.b = " + B.b);
    }
}
