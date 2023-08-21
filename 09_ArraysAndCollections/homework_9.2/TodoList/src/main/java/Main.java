import java.util.Scanner;

public class Main {

    public static final String ADD_COMMAND = "ADD";
    public static final String EDIT_COMMAND = "EDIT";
    public static final String DELETE_COMMAND = "DELETE";
    public static final String LIST_COMMAND = "LIST";

    private static TodoList todoList = new TodoList();

    public static void main(String[] args) {
        // TODO: написать консольное приложение для работы со списком дел todoList

        Scanner in = new Scanner(System.in);
        String action = in.nextLine();


        if (action.isEmpty() || action.isBlank()) {

            System.out.println("\t" + "ATENTION!!!" + System.lineSeparator() + "Action is not found !!!");

        }

        TodoList todoList = new TodoList();
        String[] listInfo;

        if (action.length() != 0) {

            listInfo = action.split("\\s+");

            if (listInfo[0].equals(ADD_COMMAND)) {


                if (Character.isDigit(listInfo[1].charAt(0))) {
                    int index = Integer.parseInt(listInfo[1]);
                    String actionADD = "";

                    for (int i = 2; i < listInfo.length; i++) {
                        actionADD = actionADD + listInfo[i] + " ";
                        todoList.add(index, actionADD);
                    }

                } else {
                    String actionADD = "";
                    for (int i = 1; i < listInfo.length; i++) {
                        actionADD = actionADD + listInfo[i] + " ";
                        todoList.add(actionADD);
                    }
                }
            }


            if (listInfo[0].equals(EDIT_COMMAND) && Character.isDigit(listInfo[1].charAt(0))) {

                int index = Integer.parseInt(listInfo[1]);
                String actionEDIT = "";
                for (int i = 2; i < listInfo.length; i++) {
                    actionEDIT = actionEDIT + listInfo[i];
                }

                todoList.edit(actionEDIT, index);

            }
            if (listInfo[0].equals(EDIT_COMMAND) && !Character.isDigit(listInfo[1].charAt(0))) {
                System.out.println("\t" + "ATENTION!!!" + System.lineSeparator() + "Action is not right !!!");
            }

            if (listInfo[0].equals(DELETE_COMMAND) && Character.isDigit(listInfo[1].charAt(0))) {
                String actionDELETE = "";
                int index = Integer.parseInt(listInfo[1]);

                if (index > listInfo.length - 2) {
                    System.out.println("\t" + "ATENTION!!!" + System.lineSeparator() + "Action is not right !!!");
                }

                for (int i = 2; i < listInfo.length; i++) {
                    actionDELETE = actionDELETE + listInfo[i];
                }

                todoList.edit(actionDELETE, index);

            }
            if (listInfo[0].equals(DELETE_COMMAND) && Character.isDigit(listInfo[1].charAt(0)) == false) {
                System.out.println("\t" + "ATENTION!!!" + System.lineSeparator() + "Action is not right !!!");
            }

            if (listInfo[0].equals(LIST_COMMAND)) {
                for (int i = 0; i < todoList.getTodos().size(); i++) {
                    System.out.println(i + " - " + todoList.getTodos().get(i));
                }
            }
        }
    }

}





//ADD apple
//ADD milk
