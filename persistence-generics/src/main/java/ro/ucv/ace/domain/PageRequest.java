package ro.ucv.ace.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * This class implements the Page interface.
 */
@Getter
@Setter
public class PageRequest implements Page {

    private Integer page;

    private Integer limit;

    public PageRequest(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    @Override
    public Integer getSkip() {
        return (page - 1) * limit;
    }
}
