
public class App {
	//added flags so theres no infinite screen making (this bricks the computer)
	public static int startupFlag=0;
	public static int app_mode = 0; // 0 is single run mode, 1 is multi-run mode
	
	public static void main(String[] args) {
		while(true) {//start App while loop
//			System.out.println("App Loop");
			if(DroneFleet.currentFrame==0&&startupFlag==0&&app_mode == 0) {//if the currentFrame is 0 (and its the first time)
//				System.out.println("Switching Frame to Startup");
				startupFlag=1;
				Startup.main(args);
			} //no code to run DroneFleet as that happens on "start" button press in Startup screen
			
			if(DroneFleet.currentFrame == 0 && startupFlag == 0 && app_mode == 1) {
				startupFlag=1;
				Startup_Multi.main(args);
			}

		}//end App while
	}

}
