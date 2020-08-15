import java.io.File;

public class DefaultSettings {
    public final static String INPUT_FOLDER = ".";
    public final static String OUTPUT_FOLDER = "."+File.separator+"compressed";
    public final static double COMPRESSION_LEVEL = 0.9;
    public final static double COMPRESSION_LEVEL_STEP = 0.05;
    public final static Long EXPECTED_SIZE = 500000L;
}
