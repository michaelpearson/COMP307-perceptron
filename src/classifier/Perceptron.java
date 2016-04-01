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
    }

    public Perceptron(List<Feature> features, double learningRate) {
        this.features = features;
        this.learningRate = learningRate;
    }

    public void train(List<Image> images) {
        for(int a = 0;a < 1000;a++) {
            for (Image i : images) {
                double result = 0;
                for (Feature f : features) {
                    result += f.evaluateFeature(i.data) * f.getWeight();
                }
                int actual = i.actualClass.value;
                if ((result > 0 && actual == 1) || (result <= 0 && actual == 0)) {
                    //Prediction was correct
                    continue;
                }

                double error = result - actual;
                for (Feature f : features) {
                    f.updateWeight(error * learningRate * f.evaluateFeature(i.data));
                }
            }
        }

        int correct = 0;
        int incorrect = 0;
        for (Image i : images) {
            double actualOutput = 0;
            for(Feature f : features) {
                actualOutput += f.evaluateFeature(i.data) * f.getWeight();
            }
            int actualClass = i.actualClass.value;
            if ((actualOutput > 0 && actualClass == 1) || (actualOutput <= 0 && actualClass == 0)) {
                correct++;
            } else {
                incorrect++;
            }
        }
        System.out.printf("Correct: %2.2f%%\n", ((double)correct / (incorrect + correct)) * 100);
    }
}
