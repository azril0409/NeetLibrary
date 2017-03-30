package library.nettoffice.com.restapi;

import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Type;

/**
 * Created by Deo-chainmeans on 2017/3/22.
 */

public class Reference<Response> extends ParameterizedTypeReference<Response> {
    private final Type type;

    public Reference(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }
}
