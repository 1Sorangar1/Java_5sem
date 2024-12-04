package module13;

class Wrapper<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

public class Main {
    public static void main(String[] args) {
        Wrapper<Integer> intWrapper = new Wrapper<>();
        Wrapper<String> stringWrapper = new Wrapper<>();

        // Использование instanceof с параметризованными типами
        if (intWrapper instanceof Wrapper<?>) {
            System.out.println("intWrapper is an instance of Wrapper");
        }

        if (stringWrapper instanceof Wrapper<?>) {
            System.out.println("stringWrapper is an instance of Wrapper");
        }

        // Проверка с конкретными типами
        if (intWrapper instanceof Wrapper<Integer>) {
            System.out.println("intWrapper is specifically an instance of Wrapper<Integer>");
        }

        if (stringWrapper instanceof Wrapper<String>) {
            System.out.println("stringWrapper is specifically an instance of Wrapper<String>");
        }
    }
}

