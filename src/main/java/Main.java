import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    protected static AtomicInteger lenght3 = new AtomicInteger(0);
    protected static AtomicInteger lenght4 = new AtomicInteger(0);
    protected static AtomicInteger lenght5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        ThreadGroup mainGroup = new ThreadGroup("mainGroup");

        final Thread thread1 = new Thread(mainGroup, () -> {
            for (String e:
            texts) {
                if (e.equalsIgnoreCase(new StringBuffer(e).reverse().toString())) {
                    incLenght(e.length());
                }
            }
            System.out.println("Конец цикла 1");
        });

        thread1.start();

        final Thread thread2 = new Thread(mainGroup, () -> {
            for (String e:
                    texts) {
                if (e.chars()
                        .filter(c -> c == e.charAt(0))
                        .count() == e.length()) {
                    incLenght(e.length());
                }
            }
            System.out.println("Конец цикла 2");
        });
        thread2.start();

        final Thread thread3 = new Thread(mainGroup, () -> {
            for (String e:
                    texts) {
                int i;
                for (i = 0; i < e.length(); i++) {
                    if (i > 0 && e.charAt(i-1) < e.charAt(i)) {
                        break;
                    }
                if (i == e.length()) {
                    incLenght(e.length());
                }
                }
            }
            System.out.println("Конец цикла 3");
        });
        thread3.start();

        while (mainGroup.activeCount() !=0) {

        }

        System.out.println(lenght3);
        System.out.println(lenght4);
        System.out.println(lenght5);
    }

    public static void incLenght(int e) {
        if (e == 3) {lenght3.incrementAndGet();}
        if (e == 4) {lenght4.incrementAndGet();}
        if (e == 5) {lenght5.incrementAndGet();}
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
