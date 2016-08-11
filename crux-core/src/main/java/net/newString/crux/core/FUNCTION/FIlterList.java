package net.newString.crux.core.FUNCTION;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created on 2016/8/9 16:28.
 *
 * @author lic
 */
public class FilterList<T> implements Predicate<T> {


    private List<Predicate> filterList;


    @Override
    public boolean test(T t) {
        return false;
    }

    @Override
    public Predicate<T> and(Predicate<? super T> other) {
        return null;
    }

    @Override
    public Predicate<T> negate() {
        return null;
    }

    @Override
    public Predicate<T> or(Predicate<? super T> other) {
        return null;
    }
}
