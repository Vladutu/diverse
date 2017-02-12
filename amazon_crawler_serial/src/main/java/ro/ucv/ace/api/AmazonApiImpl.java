package ro.ucv.ace.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geo on 10.02.2017.
 */
@Component
public class AmazonApiImpl implements AmazonApi {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${amazon.access_key_id}")
    private String AWS_ACCESS_KEY_ID;

    @Value("${amazon.secret_key}")
    private String AWS_SECRET_KEY;

    @Value("${amazon.endpoint}")
    private String ENDPOINT;

    @Value("${amazon.associate_tag}")
    private String AWS_ASSOCIATE_TAG;


    @Override
    public String productInfo(String asin) {
        String url = getSignedUrl(asin);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        UriComponents components = builder.build(true);
        URI uri = components.toUri();

        return restTemplate.getForObject(uri, String.class);
    }

    private String getSignedUrl(String asin) {
        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String requestUrl = null;

        Map<String, String> params = new HashMap<>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemLookup");
        params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);
        params.put("ItemId", asin);
        params.put("IdType", "ASIN");
        params.put("ResponseGroup", "ItemAttributes,SalesRank");

        requestUrl = helper.sign(params);

        return requestUrl;
    }
}
