package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Input name of week");
        String name= in.nextLine();
        name = name.toLowerCase();
        String day_rus ="";
        switch (name) {
            case ("monday"): day_rus="понедельник";
                break;
            case ("tuesday"): day_rus="вторник";
                break;
            case ("wednesday "): day_rus="среда";
                break;
            case ("thursday "): day_rus="четверг";
                break;
            case ("friday"): day_rus="пятница";
                break;
            case ("saturday "): day_rus="суббота";
                break;
            case ("sunday "): day_rus="воскресенье";
                break;
            default:
                day_rus = "понедельник";
                break;
        }
        System.out.println(String.format("Сегодня %s!", day_rus));

    }
}
