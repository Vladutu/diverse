package ro.ucv.ace;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.Random;

/**
 * Created by Geo on 28.03.2017.
 */
public class DecisionTree {

    public void decisionTree() throws Exception {
        String path = getClass().getClassLoader().getResource("train.arff").getPath();
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(path);
        Instances data = dataSource.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);

        J48 j48 = new J48();
        j48.buildClassifier(data);

        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(j48, data, 3, new Random());

        System.out.println("Correct: " + evaluation.correct());
        System.out.println("Incorrect: " + evaluation.incorrect());
        System.out.println("Pct correct: " + evaluation.pctCorrect());
        System.out.println("Pct incorrect: " + evaluation.pctIncorrect());
        System.out.println("Kappa: " + evaluation.kappa());
        System.out.println("Mean absolute error: " + evaluation.meanAbsoluteError());
        System.out.println("Root mean square error: " + evaluation.rootMeanSquaredError());
        System.out.println("Unclassified " + evaluation.unclassified());
        System.out.println("Pct unclassified: " + evaluation.pctUnclassified());
        System.out.println("Graph:\n" + j48.graph());
        // display classifier
        final javax.swing.JFrame jf = new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
        jf.setSize(1920, 1080);
        jf.getContentPane().setLayout(new BorderLayout());
        TreeVisualizer tv = new TreeVisualizer(null, j48.graph(), new PlaceNode2());
        jf.getContentPane().add(tv, BorderLayout.CENTER);
        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                jf.dispose();
            }
        });

        jf.setVisible(true);
        tv.fitToScreen();
    }
}
