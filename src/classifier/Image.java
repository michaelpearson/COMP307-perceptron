package classifier;

public class Image {
    public boolean data[][];
    public Perceptron.ImageClass actualClass;

    public void print() {
        for(int x = 0;x < data.length;x++) {
            for(int y = 0; y < data[x].length;y++) {
                System.out.print(data[x][y] ? "X" : " ");
            }
            System.out.println();
        }
        System.out.println("----------------------");
    }
}