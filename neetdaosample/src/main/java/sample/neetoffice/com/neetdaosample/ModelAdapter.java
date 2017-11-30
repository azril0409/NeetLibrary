package sample.neetoffice.com.neetdaosample;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import library.neetoffice.com.genericadapter.base.GenericAdapter;

/**
 * Created by Deo on 2016/3/7.
 */
public class ModelAdapter extends GenericAdapter<Model, ModelTag> {
    public ModelAdapter(Context context) {
        super(context, new ArrayList<Model>(), android.R.layout.simple_list_item_2);
    }

    @Override
    public ModelTag onCreateTag(View convertView) {
        final ModelTag tag = new ModelTag();
        tag.textView = (TextView) convertView.findViewById(android.R.id.text1);
        return tag;
    }

    @Override
    public void onBind(ModelTag tag, Model model) {
        tag.textView.setText(model.getTitle());
    }
}
