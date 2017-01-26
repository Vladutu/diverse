package ro.ucv.ace.downloader;

import org.jsoup.nodes.Document;

import java.util.concurrent.Future;

/**
 * Created by Geo on 25.01.2017.
 */
public interface JsoupDownloader {

    Future<Document> download(String url);
}
