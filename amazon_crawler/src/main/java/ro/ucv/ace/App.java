package ro.ucv.ace;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.MyConfiguration;
import ro.ucv.ace.parser.ProductParser;
import ro.ucv.ace.service.CategoryService;
import ro.ucv.ace.service.ProductService;

import java.io.IOException;

/**
 * Hello world!
 */
@Component
public class App {

    @Autowired
    private ProductParser productParser;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
        App app = ctx.getBean(App.class);
        app.action();
    }

    public void action() throws IOException {
//        List<String> productLinks = productParser
//                .parseCategoryPage("https://www.amazon.com/b/ref=lp_565098_ln_0?node=13896603011&ie=UTF8&qid=1482847407");
////                .parseCategoryPage("https://www.amazon.com/b/ref=lp_565098_ln_2?node=13896597011");
////                .parseCategoryPage("https://www.amazon.com/b/ref=lp_565108_ln_1?node=13896615011&ie=UTF8&qid=1482939328");
//
//        List<Product> products = productParser.parseProductPages(productLinks);
//
//        categoryService.save(products.get(0).getCategory());
//        products.forEach(productService::save);
        long start = System.currentTimeMillis();
        Document document = Jsoup.connect("https://www.amazon.com/gp/slredirect/picassoRedirect.html/ref=pa_sp_btf_browse_computers_sr_pg400_3?ie=UTF8&adId=A090239431J0FX7PPYTU4&url=https%3A%2F%2Fwww.amazon.com%2FAsus-15-6-Inch-Premium-Celeron-Processor%2Fdp%2FB01DNTYUG4%2Fref%3Dlp_13896615011_1_9603%2F161-6134936-4207867%3Fs%3Dpc%26ie%3DUTF8%26qid%3D1485373151%26sr%3D1-9603-spons%26psc%3D1%26smid%3DA3GQ8W9AF8L0NJ&qualifier=1485373150&id=2910103727771636&widgetName=sp_btf_browse")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                .timeout(0)
                .get();
        System.out.println(document.location());
        long end = System.currentTimeMillis();
    }

}
