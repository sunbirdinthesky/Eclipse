package gpio;

public class Main {

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
					player.pause();
					Thread.sleep(2000);
					player.resume();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
		try {
			player.play("/home/pi/v");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
