package com.company;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class RecursiveWebLinkParser extends RecursiveTask<Set<String>> {

    private Set<String> urlsMap;

    public RecursiveWebLinkParser(Set<String> urlsMap) {
        this.urlsMap = urlsMap;
    }

    @Override
    protected Set<String> compute() {
        List<RecursiveWebLinkParser> taskList = new ArrayList<>();
        Set<String> urls = new TreeSet<>();
        try {
            for (String recursiveLink : urlsMap) {
                //на каждой итерации работаем с той же общей мапой
                RecursiveWebLinkParser task = new RecursiveWebLinkParser(LinkParser.parsePage(recursiveLink));
                //запускаем ответвление задачи , которая будет работать параллельно
                task.fork();
                taskList.add(task);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /** так как мы используем общее хранилище, нам не обязательно дожидаться окончания рекурсивной задачи, чтобы получить результат.
         но такой подход не идеальный. Гораздо лучше пробовать возвращать результаты сканирования каждой страницы и добавлять их в общий сет
         сет используем, т.к. в нем гарантированно не будет повторений. Однако в моем случае такой подход будет работать дольше. Если у вас будет желание и время,
         можете попробовать сделать реализацию с join
         **/

//        for (RecursiveWebLinkParser task : taskList) {
//            urls.addAll(task.join());
//        }

        return urls;
    }
}
