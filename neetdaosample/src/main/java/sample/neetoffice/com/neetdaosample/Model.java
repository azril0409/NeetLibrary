package sample.neetoffice.com.neetdaosample;

import library.neetoffice.com.neetdao.DatabaseField;
import library.neetoffice.com.neetdao.DatabaseTable;

/**
 * Created by Deo on 2016/3/7.
 */
@DatabaseTable
public class Model {
    public static final String TEXT = "_TEXT";

    @DatabaseField(PrimaryKey = true)
    Long id;

    @DatabaseField(columnName = TEXT)
    String text;

    public Model() {
    }

    public Model(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
