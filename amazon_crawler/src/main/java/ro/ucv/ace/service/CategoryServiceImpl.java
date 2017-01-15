package ro.ucv.ace.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Category;
import ro.ucv.ace.repository.CategoryRepository;

import java.util.List;

/**
 * Created by Geo on 26.12.2016.
 */
@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Session session;

    @Override
    public Category save(Category category) {
        Category find = categoryRepository.findByName(category.getName());
        if (find != null) {
            return find;
        }

        List<Category> all = categoryRepository.findAllAsList();
        Category inMem = category.getAncestor();
        Category toBeSaved = null;

        while (true) {
            Category child = inMem.getChildren().get(0);
            if (!all.contains(child)) {
                for (Category c : all) {
                    if (c.equals(inMem)) {
                        c.addChildren(child);
                        toBeSaved = c;
                        break;
                    }
                }
                break;
            } else {
                inMem = child;
            }
        }

        Category save = categoryRepository.save(toBeSaved, -1);
        session.clear();

        return save;
    }
}
