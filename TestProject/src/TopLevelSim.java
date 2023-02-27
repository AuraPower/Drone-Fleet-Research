
public class TopLevelSim {
	//added flags so theres no infinite screen making
	public static int dronefleetFlag=0;
	public static int startupFlag=0;
	
	public static void main(String[] args) {
		while(true) {//start App while loop
//			System.out.println("App Loop");
			if(DroneFleet.currentFrame==0&&startupFlag==0) {//if the currentFrame is 0 (and its the first time)
				System.out.println("Switching Frame to Startup");
				startupFlag=1;
				Startup.main(args);
			} 
			if(DroneFleet.currentFrame==1&&dronefleetFlag==0) {//if the currentFrame is 1 (and its the first time)
				System.out.println("Switching Frame to DroneFleet");
				dronefleetFlag=1;
				DroneFleet.main(args);
			}
		}//end App while
		
	}

}
