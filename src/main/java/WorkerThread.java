import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;

public class WorkerThread implements Runnable{
    private String OUTPUT_FOLDER = ".";
    private String INPUT_FOLDER = "./compressed";
    private double COMPRESSION_LEVEL = 0.9;
    private double COMPRESSION_LEVEL_STEP = 0.01;
    private Long EXPECTED_SIZE = 500000L;
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
        System.out.println("File: "+FILE.getName()+"; Size after: "+new File(OUTPUT_FOLDER+"\\"+FILE.getName()).length());

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
        return new File(OUTPUT_FOLDER+"\\"+file.getName()).length();

    }
}
