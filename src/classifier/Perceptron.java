package classifier;

import java.util.List;

public class Perceptron {
    private List<Feature> features;
    private double learningRate;

    public enum ImageClass {
        X ("Yes", 1),
        O ("other", 0);

        private String stringValue;
        public int value;
        ImageClass(String stringValue, int value) {
            this.stringValue = stringValue;
            this.value = value;
        }
        public static ImageClass fromValue(String value) {
            for(ImageClass ic : ImageClass.values()) {
                if(ic.stringValue.equals(value)) {
                    return ic;
                }
            }
            return null;
        }
        public static ImageClass fromValue(int value) {
            switch(value) {
                default:
                case 0:
                    return O;
                case 1:
                    return X;
            }
        }
    }

    public Perceptron(List<Feature> features, double learningRate) {
        this.features = features;
        this.learningRate = learningRate;
    }

    public void train(List<Image> images, double trainingExpectation) {
        int a;
        for(a = 0;a < 1000;a++) {
            int correct = 0;
            for (Image i : images) {
                double result = 0;
                for (Feature f : features) {
                    result += f.evaluateFeature(i.data) * f.getWeight();
                }
                int actual = i.actualClass.value;
                double error = result - actual;
                if ((result > 0 && actual == 1) || (result <= 0 && actual == 0)) {
                    correct++;
                    continue;
                }
                for (Feature f : features) {
                    f.updateWeight(error * learningRate * f.evaluateFeature(i.data));
                }
            }
            if(correct >= images.size() * trainingExpectation) {
                System.out.printf("Convergence reached\n");
                break;
            }
        }
        System.out.printf("Total of %d training cycle(s)\n", a);
    }
    public void evaluate(List<Image> images) {
        int correct = 0;
        int incorrect = 0;
        for (Image i : images) {
            double actualOutput = 0;
            for(Feature f : features) {
                actualOutput += f.evaluateFeature(i.data) * f.getWeight();
            }
            ImageClass actualOutputClass = ImageClass.fromValue(actualOutput > 0 ? 1 : 0);
            //System.out.printf("This is a \"%s\", output value: %2.2f\n", actualOutputClass.toString(), actualOutput);
            //i.print();

            int actualClass = i.actualClass.value;
            if ((actualOutput > 0 && actualClass == 1) || (actualOutput <= 0 && actualClass == 0)) {
                correct++;
            } else {
                incorrect++;
            }
        }
        System.out.printf("Correct: %2.2f%% (%d from %d)\n", ((double)correct / (incorrect + correct)) * 100, correct, incorrect + correct);
    }
}
