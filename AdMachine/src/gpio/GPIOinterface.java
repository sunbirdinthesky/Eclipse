package gpio;

public class GPIOinterface {
	static {
		System.load("/home/pi/libgpio.so");
	}

	public native static int getUserFlag();

	public native static int gpio();

	public native static int stepCtrl();

	public native static int resetFlag();

	public native static int resetOutOfPaper();

	public native static String getCardNumber();
}
