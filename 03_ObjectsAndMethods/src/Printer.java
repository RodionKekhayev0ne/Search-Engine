public class Printer {
    private String queue = "";
    private int totalPages = 0;
    private int docCount = 0;

    // Я создал четвертый метод который используется для подсчета количества документов
    public void append(String text) {
        append(text, "", 1, 1);
    }

    public void append(String text, String name) {
        append(text, "", 1, 1);
    }

    public void append(String text, String name, int pages) {
        append(text, "", 1, 1);
    }

    public void append(String text, String name, int pages, int docCount) {
        queue = queue + text + "\n" + " " + "\n";
        totalPages = totalPages + pages;
        this.docCount = this.docCount + docCount;

    }

    public void print() {
        System.out.println("********************ALL INFO***********************");
        System.out.println(queue);
        getPagesCount();
        getDocumentsCount();
        System.out.println("****************************************************");
        System.out.println("");
        clear();
    }

    public void clear() {
        queue = "На очереди нет докуметов" + "\n";
        totalPages = 0;
        docCount = 0;
    }

    public void getPagesCount() {
        System.out.println("Total pages: " + totalPages + " стр");
    }

    public void getDocumentsCount() {
        System.out.println("Total documents count: " + docCount + " шт");
    }

}

