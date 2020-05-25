import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private String OUTPUT_FOLDER = ".";
    private String INPUT_FOLDER = "./compressed";
    private double COMPRESSION_LEVEL = 0.9;
    private double COMPRESSION_LEVEL_STEP = 0.01;
    private Long EXPECTED_SIZE = 500000L;


        public static void main(String[] args) {
        new Main(args);
    }
    Main(String[] args){
        for(int i = 0; i < args.length; i ++){
            if(args[i].equals("-i")) {
                if(new File(args[i+1]).exists()){
                    INPUT_FOLDER = args[i+1];
                    continue;
                }
            }
            if(args[i].equals("-o")) {
                if(i+1<args.length){
                    OUTPUT_FOLDER = args[i+1];
                    continue;
                }

            }
            if(args[i].equals("-s")) {
                try {
                    Long.parseLong(args[i+1]);
                    EXPECTED_SIZE = Long.parseLong(args[i+1]);
                    continue;
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                }


            }
            if(args[i].equals("-S")) {
                try {
                    Long.parseLong(args[i+1]);
                    COMPRESSION_LEVEL_STEP = Long.parseLong(args[i+1]);
                    continue;
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                }


            }
        }
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-1);
        File input = new File(INPUT_FOLDER);
        File[] filesToCompress = input.listFiles();
        try{
        for(File f : filesToCompress){
            if(!f.isDirectory()){
                List<String> extensions = new ArrayList<String>();
                extensions.add("jpg");
                extensions.add("jpeg");
                extensions.add("png");
                if(extensions.contains(FilenameUtils.getExtension(f.getName()))){
                Runnable worker = new WorkerThread(f,INPUT_FOLDER,OUTPUT_FOLDER,COMPRESSION_LEVEL,COMPRESSION_LEVEL_STEP,EXPECTED_SIZE);
                executor.execute(worker);}
            }

        }} catch(NullPointerException e){
            System.out.println("W folderze nie ma plików");
        }
        executor.shutdown();
        while (!executor.isTerminated()) {   }

        System.out.println("Skompresowano pliki");
    }


}
