public class Hospital {

    public  static int PATIENTS;
    public  static int PATIENTS_MAX_COUNT = 100;
    private final static double  MAX_TEMP = 40.0;
    private final static double  MIN_TEMP = 32.0;
    private final static float  MAX_TEMP_HEALTHY = 36.9f;
    private final static float  MIN_TEMP_HEALTHY = 36.2f;

    public static float[] generatePatientsTemperatures(int patientsCount) {

        PATIENTS = patientsCount;
 if (patientsCount > PATIENTS_MAX_COUNT){
     System.out.println("over limit");
 }
        //TODO: напишите метод генерации массива температур пациентов
        float[] temperature = new float[patientsCount];



        for (int i = 0; i < PATIENTS; i++) {
            temperature[i] = (float) (Math.random() * ((MAX_TEMP - MIN_TEMP))) + (float) MIN_TEMP;
        }


        return temperature;
    }

    public static String getReport(float[] temperatureData) {
        /*
        TODO: Напишите код, который выводит среднюю температуру по больнице,количество здоровых пациентов,
            а также температуры всех пациентов.
        */

        int healthyCount = 0;
        String patientsTemperatures = "";
        float midleTemperature = 0;

        for (int i = 0; i < PATIENTS; i++) {
            if (temperatureData[i] <= MAX_TEMP_HEALTHY && temperatureData[i] >= MIN_TEMP_HEALTHY) {
                healthyCount++;
            }
            midleTemperature = (midleTemperature + temperatureData[i] / PATIENTS);
            patientsTemperatures = patientsTemperatures + " " + String.format("%.1f", temperatureData[i]);
        }


        String report =
                "Температуры пациентов: " + patientsTemperatures.trim() +
                        "\nСредняя температура: " + String.format("%.2f", midleTemperature) +
                        "\nКоличество здоровых: " + healthyCount;

        return report;
    }


}
