public class Main {
	public volatile static int produtos = 0;
	private static volatile Object lock = new Object();
	public static void main(String[] args) {
		Produtor p1 = new Produtor(1, lock);
		Consumidor c1 = new Consumidor(2, lock);
		p1.start();
		c1.start();
	}
}

class Produtor extends Thread {
	int nProdutor = 0;
	private static volatile Object lock = new Object();
	Produtor(int num, Object lock){
		this.nProdutor = num;
	}
	public void run() {
		for(int i = 0; i <= 15; i++) {
		try {
			synchronized( lock ) {
				while (Main.produtos >= 10) {
					System.out.println("Capacidade máxima!");
					lock.wait();
				}
				Thread.sleep(500);
				Main.produtos = Main.produtos +1;
				/*if (Main.produtos < 100)
					Main.produtos = Main.produtos + 1;
					lock.wait();
					lock.notify();*/
				lock.notify();
				System.out.println("Produtor " + nProdutor + "\tProduziu\t" + Main.produtos);
				}
			} catch (Exception e) {}
		}
	}
}

class Consumidor extends Thread {
	int nConsumidor = 0;
	private static volatile Object lock = new Object();
	Consumidor(int num, Object lock){
		this.nConsumidor = num;
	}
	public void run() {
		for(int i = 0; i <= 5; i++) {
			try {
				synchronized( lock ) {
					/*if ( Main.produtos > 0)
					Main.produtos = Main.produtos - 1;
					}*/
					while(Main.produtos <= 0) {
						System.out.println("Não há o que ser consumido!");
						lock.wait();
					}
					Main.produtos = Main.produtos -1;
					System.out.println("Consumidor " + nConsumidor + "\tConsumiu\t" + Main.produtos);
					Thread.sleep(2000);
					lock.notify();
				}
			}catch (Exception e) {}
		}
	}
}