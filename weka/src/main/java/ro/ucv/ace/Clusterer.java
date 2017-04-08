package ro.ucv.ace;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.MinkowskiDistance;
import weka.core.converters.ConverterUtils;

/**
 * Created by Geo on 04.04.2017.
 */
public class Clusterer {

    public void cluster() throws Exception {
        String path = getClass().getClassLoader().getResource("train.arff").getPath();
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(path);
        Instances data = dataSource.getDataSet();

        SimpleKMeans simpleKMeans = new SimpleKMeans();
        simpleKMeans.setSeed(342);
        simpleKMeans.setDistanceFunction(new MinkowskiDistance());
        simpleKMeans.buildClusterer(data);

        ClusterEvaluation evaluation = new ClusterEvaluation();
        evaluation.setClusterer(simpleKMeans);
        evaluation.evaluateClusterer(data);

        System.out.println(evaluation.clusterResultsToString());
    }
}
