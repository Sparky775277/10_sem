
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FirstLab {
    public static void main(String[] args) {

        /**
         * Создание подстановки
         */

        List<Integer> podstanovka = new ArrayList<>(64);

        for (int i = 0; i < 64; i++)
            podstanovka.add(i);

        System.out.println("Инициализация подстановки: " + podstanovka);
        Collections.shuffle(podstanovka);
        System.out.println("Исходная подстановка: " + podstanovka);
        System.out.println();


        Integer[][] array = new Integer[6][64];
        for (int i = 0; i < 64; i++) {
            int number = Integer.parseInt(Integer.toBinaryString(podstanovka.get(i)));
            for (int j = 0; j < 6; j++) {
                array[j][i] = number % 10;
                number = number / 10;
            }
        }
        /*for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 1; j++) {
                array[j][i] = podstanovka.get(i);
            }
        }*/

        System.out.println("Получившаяся подстановка в бинарном представлении(координатыне функции):");
        for (Integer[] x : array)
            System.out.println(Arrays.toString(x));

        System.out.println();
        System.out.println("Вес функций:");
        for (Integer[] x : array) {
            System.out.println("Вес функции = " + weight(x));
        }

        for (Integer[] x : array) {
            System.out.println();
            System.out.println("Преобразование Фурье-Жегалкина: ");
            System.out.println(Arrays.toString(Fft(x)));
            System.out.println("Многочлен Жегалкина: ");
            System.out.println(Zhegalkin(x));

        }


        /*for (Integer[] x : array) {
            System.out.println("Многочлен Жегалкина: " );
            System.out.println(Zhegalkin(x));
        }*/
        /*Integer[] proba = new Integer[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1};

        System.out.println(Arrays.toString(Fft(proba)));

        System.out.println(Zhegalkin(proba));*/


    }

    /**
     * Нужно для того, чтобы из полученного преобразования Фурье получить коэффициенты многочлена Жегалкина
     */
    private static final String[] functions = {"X_1", "X_2", "X_3", "X_4", "X_5", "X_6"};


    /**
     * Здесь высчитывается многочлен Жегалкина
     *
     * @param array
     * @return String output
     */
    static StringBuilder fictional;

    static String Zhegalkin(Integer[] array) {
        StringBuilder output = new StringBuilder();
        fictional = new StringBuilder();
        fictional.append("Фиктивные переменные: ");

        if (array[0] == 1) {
            output.append("1 + ");
        }

        for (int i = 1; i < array.length; i++) {
            if (array[i].equals(1)) {
                int number = Integer.parseInt(Integer.toBinaryString(i));
                for (int j = 0; j < 6; j++) {
                    if (1 == number % 10) {
                        output.append(functions[j]);
                        output.append("*");
                    }
                    number = number / 10;
                }
                output.append(" + ");
            }

        }

        for (int i = 0; i < 6; i++) {
            if (!output.toString().contains(functions[i]))
                fictional.append(functions[i]).append(" ");

        }
        if (fictional.toString().length() == 22) {
            fictional.append("Фиктивных переменных нет!");
        }
        output.append("\n").append(fictional.toString());
        return output.toString();
    }

    /**
     * Вот здесь происходит преобразование Фурье-Жегалкина. Меняет исходный массив
     *
     * @param array
     * @return array
     */
    static Integer[] Fft(Integer[] array) {
        Integer[] leftPart = Arrays.copyOfRange(array, 0, array.length / 2);
        Integer[] rightPart = Arrays.copyOfRange(array, array.length / 2, array.length);
        Integer[] copy = array.clone();

        for (int i = 0; i < array.length / 2; i++) {
            leftPart[i] = copy[i];
            rightPart[i] = (copy[i] + copy[i + copy.length / 2]) % 2;
        }

        if (array.length == 2) {
            array[0] = leftPart[0];
            array[1] = rightPart[0];
            return array;
        }
        Integer[] leftRec = Fft(leftPart);
        Integer[] rightRec = Fft(rightPart);

        for (int i = 0; i < array.length / 2; i++) {
            array[i] = leftRec[i];
            array[i + array.length / 2] = rightRec[i];
        }
        return array;
    }

    /**
     * Считает вес координатных функций
     *
     * @param array
     * @return weight
     */
    static int weight(Integer[] array) {
        int weight = 0;
        for (Integer x : array) {
            if (x == 1) {
                weight++;
            }
        }
        return weight;
    }

}
