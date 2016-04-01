import classifier.Feature;
import classifier.Image;
import classifier.Perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] argv) throws FileNotFoundException {
        List<Image> images = loadImages(argv[0]);
        List<Feature> features = new ArrayList<>();
        for(int a = 0;a < 100;a++) {
            features.add(new Feature(10, 10, 4));
        }

        Perceptron p = new Perceptron(features, 0.5);
        p.train(images);
    }


    private static List<Image> loadImages(String fileName) throws FileNotFoundException {
        Scanner f = new Scanner(new File(fileName));
        List<Image> build = new ArrayList<>();
        Pattern bit = java.util.regex.Pattern.compile("[01]");

        while(f.hasNextLine()) {
            Image i = new Image();
            if (!f.next().equals("P1")) System.out.println("Not a P1 PBM file");
            i.actualClass = Perceptron.ImageClass.fromValue(f.next().substring(1));
            int rows = f.nextInt();
            int cols = f.nextInt();
            i.data = new boolean[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    i.data[r][c] = (f.findWithinHorizon(bit, 0).equals("1"));
                }
            }
            build.add(i);
        }
        f.close();
        return build;
    }
}
