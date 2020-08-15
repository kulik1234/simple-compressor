import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;

public class WorkerThread implements Runnable{
    private String OUTPUT_FOLDER = DefaultSettings.OUTPUT_FOLDER;
    private String INPUT_FOLDER = DefaultSettings.INPUT_FOLDER;
    private double COMPRESSION_LEVEL = DefaultSettings.COMPRESSION_LEVEL;
    private double COMPRESSION_LEVEL_STEP = DefaultSettings.COMPRESSION_LEVEL_STEP;
    private Long EXPECTED_SIZE = DefaultSettings.EXPECTED_SIZE;
    private File FILE = null;
    WorkerThread(File file,String inputFolder,String outputFolder,double compressionLevel,double compressionLevelStep,Long expectedSize){
        FILE = file;
        OUTPUT_FOLDER = outputFolder;
        INPUT_FOLDER = inputFolder;
        COMPRESSION_LEVEL = compressionLevel;
        COMPRESSION_LEVEL_STEP = compressionLevelStep;
        EXPECTED_SIZE = expectedSize;
    }
    @Override
    public void run() {
        System.out.println("File: "+FILE.getName()+"; size before: "+FILE.length());
        Long size = compress(FILE,COMPRESSION_LEVEL);
        int i = 1;
        while(size>EXPECTED_SIZE){
            size = compress(FILE,COMPRESSION_LEVEL-COMPRESSION_LEVEL_STEP*i++);
        }
        System.out.println("File: "+FILE.getName()+"; Size after: "+new File(OUTPUT_FOLDER+File.separator+FILE.getName()).length());

    }
    private Long compress(File file, double compressionLevel){
        try {
            File output = new File(this.OUTPUT_FOLDER);
            if(!output.exists()){
                output.mkdir();
            }
            Thumbnails
                    .of(file)
                    .scale(1)
                    .outputQuality(compressionLevel)
                    .toFiles(output, Rename.NO_CHANGE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new File(OUTPUT_FOLDER+File.separator+file.getName()).length();

    }
}
