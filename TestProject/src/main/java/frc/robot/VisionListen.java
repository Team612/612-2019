package frc.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionListen {

    NetworkTable table;
    NetworkTableEntry angleEntry;
    NetworkTableEntry p1Entry;
    NetworkTableEntry p2Entry;
    public static double vision_array[] = new double[3];

    public static boolean calculate_delta = false;

    VisionListen() {
        //System.out.println("We heresssss");
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        table = inst.getTable("VisionTable");
        angleEntry = table.getEntry("angle");
        p1Entry = table.getEntry("p1_offset");
        p2Entry = table.getEntry("p2_offset");
        inst.startClientTeam(612);
    }

    public void read_vision() {
        
        angleEntry.addListener(event -> {
            vision_array[0] = (double) event.value.getValue();
        }, EntryListenerFlags.kUpdate);

        p1Entry.addListener(event -> {
            vision_array[1] = (double) event.value.getValue();
            calculate_delta = true;
        }, EntryListenerFlags.kUpdate);

        p2Entry.addListener(event -> {
            vision_array[2] = (double) event.value.getValue();
            calculate_delta = true;
        }, EntryListenerFlags.kUpdate);

    }

    /*
    public void execute_raspberry() {
        JSch jsch = new JSch();
        String script = "pbrun su - user; cd /home/scripts;./sample_script.sh";
    }
    */

}