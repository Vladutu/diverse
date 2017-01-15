package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.MyConfiguration;
import ro.ucv.ace.entity.Product;
import ro.ucv.ace.parser.ProductParser;
import ro.ucv.ace.service.CategoryService;
import ro.ucv.ace.service.ProductService;

import java.util.List;

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

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
        App app = ctx.getBean(App.class);
        app.action();
    }

    public void action() {
        List<String> productLinks = productParser
                .parseCategoryPage("https://www.amazon.com/b/ref=lp_565098_ln_0?node=13896603011&ie=UTF8&qid=1482847407");
//                .parseCategoryPage("https://www.amazon.com/b/ref=lp_565098_ln_2?node=13896597011");
//                .parseCategoryPage("https://www.amazon.com/b/ref=lp_565108_ln_1?node=13896615011&ie=UTF8&qid=1482939328");

        List<Product> products = productParser.parseProductPages(productLinks);

        categoryService.save(products.get(0).getCategory());
        products.forEach(productService::save);
//
//        productLinks = productParser.parseCategoryPage("https://www.amazon.com/b/ref=lp_565098_ln_2?node=13896597011");
//        products = productParser.parseProductPages(productLinks);
//
//        categoryService.save(products.get(0).getCategory());
//        products.forEach(productService::save);
//
//        productLinks = productParser.parseCategoryPage("https://www.amazon.com/b/ref=lp_565108_ln_1?node=13896615011&ie=UTF8&qid=1482939328");
//        products = productParser.parseProductPages(productLinks);
//
//        categoryService.save(products.get(0).getCategory());
//        products.forEach(productService::save);
    }

}
