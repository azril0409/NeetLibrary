package library.nettoffice.com.restapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Deo-chainmeans on 2017/3/22.
 */

public abstract class ResponseCallBack<Response>  {

    private final Type type;

    protected ResponseCallBack() {
        Class<?> parameterizedTypeReferenceSubclass = findParameterizedTypeReferenceSubclass(getClass());
        Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
        Assert.isInstanceOf(ParameterizedType.class, type);
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Assert.isTrue(parameterizedType.getActualTypeArguments().length == 1);
        this.type = parameterizedType.getActualTypeArguments()[0];
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj || (obj instanceof ParameterizedTypeReference &&
                this.type.equals(((ResponseCallBack) obj).type)));
    }

    @Override
    public int hashCode() {
        return this.type.hashCode();
    }

    @Override
    public String toString() {
        return "ResponseCallBack<" + this.type + ">";
    }


    private static Class<?> findParameterizedTypeReferenceSubclass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        if (Object.class.equals(parent)) {
            throw new IllegalStateException("Expected ResponseCallBack superclass");
        } else if (ResponseCallBack.class.equals(parent)) {
            return child;
        } else {
            return findParameterizedTypeReferenceSubclass(parent);
        }
    }

    public abstract void onResponse(Response response);
}
