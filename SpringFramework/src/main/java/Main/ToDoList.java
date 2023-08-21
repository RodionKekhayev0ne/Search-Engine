package Main;

import Main.model.Job;

import java.util.*;

public class ToDoList {

    private static Integer currentId = 1;
    private static HashMap<Integer, Job> toDoList = new HashMap<Integer, Job>();


    public static int createTask(String task){
        Job job = new Job(task);

          int id = currentId++;
        toDoList.put(id,job);
        return id;
    }

    public static Job getTask(Integer id){

        if (toDoList.containsKey(id)) {
            return toDoList.get(id);
        }
        return null;
    }

    public static void deleteTask(Integer id){

        if (toDoList.containsKey(id)) {
            toDoList.remove(id);
        }

    }

    public static void deleteAllTasks(){
        toDoList.clear();
    }

    public static String changeTask(Integer id, String newTask){
        if (toDoList.containsKey(id)) {
            toDoList.replace(id, new Job(newTask));
        }

        return newTask;
    }

    public static List<Job> listTasks(){

        List<Job> list = new ArrayList<>();
        list.addAll(toDoList.values());

        return list;
    }




}
