package ro.ucv.ace;

import weka.clusterers.ClusterEvaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by Geo on 11.04.2017.
 */
public class HierarchicalClusterer {

    public void cluster() throws Exception {
        String path = getClass().getClassLoader().getResource("train.arff").getPath();
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(path);
        Instances data = dataSource.getDataSet();

        weka.clusterers.HierarchicalClusterer clusterer = new weka.clusterers.HierarchicalClusterer();
        clusterer.buildClusterer(data);

        ClusterEvaluation evaluation = new ClusterEvaluation();
        evaluation.setClusterer(clusterer);
        evaluation.evaluateClusterer(data);

        System.out.println(evaluation.clusterResultsToString());
    }
}
