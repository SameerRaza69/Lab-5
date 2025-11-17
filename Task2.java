public class Task2 {
    static int pagesInTray = 10;
    static int pagesToPrint = 15;
    static boolean paperAdded = false;
    static final Object lock = new Object();

    public static void main(String[] args) {

        Thread printThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("PrintThread: Requested to print 15 pages");
                System.out.println("PrintThread: Not enough pages. Available = 10");
                System.out.println("PrintThread: Waiting for more pages...");

                while (!paperAdded) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {}
                }

                System.out.println("PrintThread: Notification received.");
                System.out.println("PrintThread: Enough pages available. Printing 15 pages...");
                pagesInTray -= 15;
                System.out.println("PrintThread: Printing completed.");
                System.out.println("PrintThread: Remaining pages in tray = " + pagesInTray);
            }
        }, "PrintThread");

        Thread trayThread = new Thread(() -> {
            synchronized (lock) {
                try { Thread.sleep(1500); } catch (InterruptedException e) {}

                System.out.println("TrayThread: Checking tray pages...");
                System.out.println("TrayThread: Added 10 pages to tray");
                pagesInTray += 10;
                System.out.println("TrayThread: Total pages now = 20");
                paperAdded = true;
                System.out.println("TrayThread: Notifying PrintThread...");
                lock.notify();
            }
        }, "TrayThread");

        printThread.start();
        trayThread.start();
    }
}
