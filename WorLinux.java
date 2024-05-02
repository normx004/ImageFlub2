package imageflubber;

public class WorLinux {
    private boolean isWindows = false;
    public  boolean izzit() {
    	
        String osName = System.getProperty("os.name").toLowerCase();
        
        if (osName.contains("win")) {
            System.out.println("You are running on Windows");
	        isWindows = true;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            System.out.println("You are running on Linux/Unix");
	        isWindows = false;
        } else {
            System.out.println("Could not determine the operating system");
        }
        return isWindows;
    }
    public static void main(String[] args) {
        WorLinux w = new WorLinux();
    }
}
