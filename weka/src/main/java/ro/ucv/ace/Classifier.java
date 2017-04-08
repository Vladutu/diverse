package ro.ucv.ace;


import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;

/**
 * Created by Geo on 21.03.2017.
 */
public class Classifier {

    public void classify() throws Exception {
        String path = getClass().getClassLoader().getResource("train.arff").getPath();
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(path);
        Instances data = dataSource.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.buildClassifier(data);
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(naiveBayes, data, 3, new Random());

        System.out.println("Correct: " + evaluation.correct());
        System.out.println("Incorrect: " + evaluation.incorrect());
        System.out.println("Pct correct: " + evaluation.pctCorrect());
        System.out.println("Pct incorrect: " + evaluation.pctIncorrect());
        System.out.println("Kappa: " + evaluation.kappa());
        System.out.println("Mean absolute error: " + evaluation.meanAbsoluteError());
        System.out.println("Root mean square error: " + evaluation.rootMeanSquaredError());
        System.out.println("Unclassified " + evaluation.unclassified());
        System.out.println("Pct unclassified: " + evaluation.pctUnclassified());
    }
}
