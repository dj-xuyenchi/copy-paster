package query.genarate.gettext.config;

import java.io.Serializable;

public interface SerializableFunction<T, R> extends Serializable {
    R apply(T t);
}