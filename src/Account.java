public class Account implements Runnable {
    private final Bank bank;
    private long money;
    private String accNumber;
    private boolean isResume = true;
    private String command;
    private Thread thread;
    private String payee;
    private long moneyToPayee;

    public Account(Bank bank, String accNumber, long money) {
        this.bank = bank;
        this.money = money;
        this.accNumber = accNumber;
        thread = new Thread(this, accNumber);
        thread.start();
    }

    public void run() {
        System.out.println("Создан аккаунт " + Thread.currentThread().getName());
        try {
            synchronized (this) {
                while (isResume) {
                    if (command != null) {
                        if (command.equals("transferPush")) {
                            System.out.print(Thread.currentThread().getName() + " переводит "
                                    + payee +  " сумму  " +  moneyToPayee+" ");
                            bank.transfer(this.accNumber, payee, moneyToPayee);

                        }
                        if (command.equals("getBalance")) {
                            System.out.println("Баланс " + this.accNumber +"  равен "
                                    + bank.getBalance(this.accNumber));
                        }
                    }
                    notify();
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() +" покинул банк.");

    }

    public long getMoney() {
        return money;
    }

    public long getMoney(long getMoney) {
        if (money < getMoney) {
            System.out.println("Недостаточно средств. Отказано в переводе.");
            return 0;
        }
        money = money - getMoney;
        return getMoney;
    }

    public void setMoney(long money) {
        this.money = this.money + money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Bank getBank() {
        return bank;
    }

    public synchronized void transferPush(String to, long money) throws InterruptedException {
        this.payee = to;
        this.moneyToPayee = money;
        this.command = "transferPush";
        notify();
        wait();
    }

    public synchronized void getBalance() throws InterruptedException {
        this.command = "getBalance";
        notify();
        wait();
    }

    public synchronized void leaveBank() {
        this.isResume = false;
        notify();
    }

}
