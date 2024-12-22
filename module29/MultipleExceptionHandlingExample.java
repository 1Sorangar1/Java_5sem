package module29;

public class MultipleExceptionHandlingExample {
    public static void main(String[] args) {
        try {
            generateException(1); // Генерация исключения типа 1
            generateException(2); // Генерация исключения типа 2
            generateException(3); // Генерация исключения типа 3
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            // Обработка ArithmeticException и ArrayIndexOutOfBoundsException
            System.out.println("Handled a common exception type: " + e.getClass().getSimpleName());
        } catch (Ex1 e) {
            // Обработка Ex1, Ex2, Ex3 (Ex1 <|-- Ex2 <|-- Ex3)
            System.out.println("Handled an exception of type Ex1: " + e.getClass().getSimpleName());
        }
    }

    public static void generateException(int type) throws ArithmeticException, ArrayIndexOutOfBoundsException, Ex1, Ex2, Ex3 {
        switch (type) {
            case 1:
                throw new ArithmeticException("Arithmetic operation error");
            case 2:
                throw new ArrayIndexOutOfBoundsException("Array index out of bounds");
            case 3:
                throw new Ex3("Exception of type Ex3");
            default:
                break;
        }
    }
}

class Ex1 extends Exception {
    public Ex1(String message) {
        super(message);
    }
}

class Ex2 extends Ex1 {
    public Ex2(String message) {
        super(message);
    }
}

class Ex3 extends Ex2 {
    public Ex3(String message) {
        super(message);
    }
}


/*
 Метод generateException используется для генерации различных типов исключений в зависимости от переданного значения параметра type. 
 Если значение равно 1, генерируется исключение ArithmeticException, связанное с ошибками в арифметических операциях. 
 Если значение равно 2, выбрасывается ArrayIndexOutOfBoundsException, которое возникает при попытке обращения к индексу массива, 
 выходящему за его границы. Если значение равно 3, создаётся исключение Ex3, 
 являющееся пользовательским типом, которое наследует от Ex2, а тот, в свою очередь, наследует от Ex1

 В блоке try-catch видно, что первый catch обрабатывает исключения ArithmeticException и ArrayIndexOutOfBoundsException идентичным образом. 
 Это реализовано с использованием конструкции catch (Type1 | Type2 e), которая позволяет указать сразу несколько типов исключений, 
 обрабатываемых одинаково. Это приводит к выводу сообщения о типе обработанного исключения через метод getClass().getSimpleName(). 
 Второй catch блок предназначен для обработки всех исключений, относящихся к иерархии Ex1, включая сам Ex1, а также его подклассы Ex2 и Ex3. 
 При этом конкретный тип исключения в пределах этой иерархии выводится с помощью аналогичного вызова.

 Ключевым моментом является то, что исключения обрабатываются в порядке их специфичности. 
 Так, если исключение соответствует более конкретному классу из первого catch, управление передаётся ему. 
 Если нет, оно попадает под общий обработчик исключений иерархии Ex1

 При запуске программы в её текущем виде вывод будет следующим: сначала выбрасывается ArithmeticException, 
 обработанное первым catch, затем ArrayIndexOutOfBoundsException, которое также перехватывается этим блоком, 
 и, наконец, Ex3, обрабатываемое вторым блоком для иерархии Ex1.
*/