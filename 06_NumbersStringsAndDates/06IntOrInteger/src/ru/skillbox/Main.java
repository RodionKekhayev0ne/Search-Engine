package ru.skillbox;


public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.



        // Это цикл который выводит весь русский алфавит и номер каждого символа

        for (int i = 1025; i <= 1105; i++) {
            if (i > 1024 && i < 1026 || i > 1039 && i < 1106 && i != 1104) {
                System.out.println(i + " - " + (char) i);
            }
        }


    }

}
