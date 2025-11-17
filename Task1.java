class JointAccount {
    private int balance = 50000;

    public synchronized void withdraw(String name, int amount) {
        System.out.println(name + " is trying to withdraw " + amount);

        if (balance >= amount) {
            System.out.println(name + " is proceeding with the withdrawal...");
            try { Thread.sleep(1000); } catch (Exception e) {}
            balance -= amount;
            System.out.println(name + " successfully withdrew " + amount);
        } else {
            System.out.println("Insufficient balance for " + name);
        }

        System.out.println("Remaining balance: " + balance + "\n");
    }
}

class UserThread extends Thread {
    JointAccount acct;
    String name;
    int amount;

    UserThread(JointAccount acct, String name, int amount) {
        this.acct = acct;
        this.name = name;
        this.amount = amount;
    }

    public void run() {
        acct.withdraw(name, amount);
    }
}

public class Task1 {
    public static void main(String[] args) {
        JointAccount account = new JointAccount();

        UserThread t1 = new UserThread(account, "Sameer", 45000);
        UserThread t2 = new UserThread(account, "035", 20000);

        t1.start();
        t2.start();
    }
}
