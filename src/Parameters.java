


public class Parameters {


	private static String PathToErr = "\\\\\\\\dataserver/Common/Programy/ModbusCommunication/err.txt";
	private static String PathToOut = "\\\\\\\\dataserver/Common/Programy/ModbusCommunication/out.txt";

	public static String getPathToErr() {
		return PathToErr;
	}
	public static void setPathToErr(String pathToErr) {
		PathToErr = pathToErr;
	}
	public static String getPathToOut() {
		return PathToOut;
	}
	public static void setPathToOut(String pathToOut) {
		PathToOut = pathToOut;
	}
	
}
