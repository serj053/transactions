import java.util.Map;

public class TestBank {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        Account acc1 = new Account(bank, "acc1", 170_000);
        Account acc2 = new Account(bank, "acc2", 290_000);
        Account acc3 = new Account(bank, "acc3", 73_000);
        Account acc4 = new Account(bank, "acc4", 450_000);

        bank.setAccount(acc1.getAccNumber(), acc1);
        bank.setAccount(acc2.getAccNumber(), acc2);
        bank.setAccount(acc3.getAccNumber(), acc3);
        bank.setAccount(acc4.getAccNumber(), acc4);

        getDataFromAccounts(bank);
        System.out.println(bank.getSumAllAccounts());

        acc1.getBalance();
        acc3.getBalance();
        acc4.getBalance();

        acc1.transferPush(acc2.getAccNumber(), 23000);
        acc2.transferPush(acc3.getAccNumber(), 55000);
        acc3.transferPush(acc4.getAccNumber(), 130000);

        acc1.leaveBank();
        acc2.leaveBank();
        acc3.leaveBank();
        acc4.leaveBank();

        getDataFromAccounts(bank);
        System.out.println(bank.getSumAllAccounts());
    }

    static public void getDataFromAccounts(Bank bank) {
        for (Map.Entry<String, Account> set : bank.getAccounts().entrySet()) {
            System.out.println(set.getKey() + "  " + set.getValue().getMoney());
        }
    }
}
