package library.neetoffice.com.neetannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Deo on 2016/3/8.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface NeetActivity {

    /**
     * The R.layout.* field which refer to the layout.
     *
     * @return the id of the layout
     */
    int value();
}
