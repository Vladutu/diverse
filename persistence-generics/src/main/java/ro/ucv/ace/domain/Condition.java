package ro.ucv.ace.domain;

import org.jinq.orm.stream.JinqStream;

/**
 * Created by Geo on 04.06.2016.
 */
@FunctionalInterface
public interface Condition<U> extends JinqStream.Where<U, Exception> {
}
