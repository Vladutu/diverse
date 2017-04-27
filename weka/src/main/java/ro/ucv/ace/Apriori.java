package ro.ucv.ace;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by Geo on 26.04.2017.
 */
public class Apriori {

    public void apriori() throws Exception {
        String path = getClass().getClassLoader().getResource("studenti.arff").getPath();
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(path);
        Instances data = dataSource.getDataSet();

        weka.associations.Apriori apriori = new weka.associations.Apriori();

        apriori.buildAssociations(data);

        System.out.println(apriori);
    }

}
