package ro.ucv.ace.downloader;

import org.jsoup.nodes.Document;

/**
 * Created by Geo on 09.02.2017.
 */
public interface JsoupDownloader {
    Document download(String docUrl);
}
