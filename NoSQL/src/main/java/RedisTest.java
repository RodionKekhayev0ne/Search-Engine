

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.System.in;
import static java.lang.System.out;

public class RedisTest {

    // Запуск докер-контейнера:
    // docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis

    // Для теста будем считать неактивными пользователей, которые не заходили 2 секунды
    private static final int DELETE_SECONDS_AGO = 2;

    // Допустим пользователи делают 500 запросов к сайту в секунду
    private static final int RPS = 500;

    // И всего на сайт заходило 1000 различных пользователей
    private static final int USERS = 20;

    // Также мы добавим задержку между посещениями
    private static final int SLEEP = 1; // 1 миллисекунда

    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");

    private static void log(int id) {
        String log = String.format("[%s] Пользователь_" + String.valueOf(id) + " online: %d", DF.format(new Date()));
        out.println(log);
    }

    public static void main(String[] args) throws InterruptedException {



        RedisStorage redis = new RedisStorage();
        redis.init();



        for (int user = 0; user <= 19; user++ ){
            redis.logPageVisit(user);
        }

        int usersOnline = redis.calculateUsersNumber();


        for(;;) {

              int user_id = new Random().nextInt(20);

                out.println("Users_" + user_id + " приобрел платную услугу");
                out.println("User_" + user_id + " online");

            
            for (int online = 0; online <= usersOnline; online++) {
                Thread.sleep(500);



                if (user_id == online) {
                    out.println("Users_" + user_id + " приобрел платную услугу");
                } else {
                    out.println("User_" + online + " online");
                    //}
                }

            }
        }
//
//        // Эмулируем 10 секунд работы сайта
//        for(int seconds=0; seconds <= 10; seconds++) {
//            // Выполним 500 запросов
//            for(int request = 0; request <= RPS; request++) {
//                int user_id = new Random().nextInt(USERS);
//                redis.logPageVisit(user_id);
//                Thread.sleep(SLEEP);
//            }
//            // redis.deleteOldEntries(DELETE_SECONDS_AGO);
//           // int usersOnline = redis.calculateUsersNumber();
//            //log(usersOnline);
//        }
            // redis.shutdown();
        }
}
