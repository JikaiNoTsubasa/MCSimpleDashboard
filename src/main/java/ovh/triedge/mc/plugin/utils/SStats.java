package ovh.triedge.mc.plugin.utils;

public class SStats {
    public static double getMemoryUsagePercent(){
        return (getMemoryUsed()*100.0)/getMemoryMax();
    }

    public static long getMemoryUsed(){
        return Runtime.getRuntime().totalMemory() - getMemoryFree();
    }

    public static long getMemoryMax(){
        return Runtime.getRuntime().maxMemory();
    }

    public static long getMemoryFree(){
        return Runtime.getRuntime().freeMemory();
    }

    public static String format(double value){
        return String.format("%.2f", value);
    }

    public static String prettyPrint(){
        return format(getMemoryUsagePercent())+"% "+String.format("%.3f",(getMemoryUsed()/1024.0/1024.0/1024.0))+"GB/"+String.format("%.3f",(getMemoryMax()/1024.0/1024.0/1024.0))+"GB";
    }
}
