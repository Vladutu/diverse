package ro.ucv.ace.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Geo on 04.06.2016.
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
