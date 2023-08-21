public class Account {

    private long money;
    private String accNumber;
    private volatile boolean block = false;

    public  long getMoney() {
        return money;
    }

    public  void setMoney(long money) {
        if (block) {
            System.out.println("account blocked");
            return;
        }
        this.money = money;
    }

    public  String getAccNumber() {
        return accNumber;
    }

    public  void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public  void block() {
        block = true;
    }

    public void activate() {
        block = false;

    }

    public boolean isBlock() {
        return block;

    }
}




