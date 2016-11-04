package vars;

public class Device implements Cloneable {
	public String imei;
	public String location;
	public int nItem = 0;
	public String time;
	public int setUp;
	public String passwd;
	public int bluetoothState;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Device tmp = (Device)super.clone();
		tmp.imei = imei;
		tmp.location = location;
		tmp.nItem = nItem;
		tmp.time = time;
		tmp.setUp = setUp;
		tmp.passwd = passwd;
		tmp.bluetoothState = bluetoothState;
		return tmp;
	}
}
