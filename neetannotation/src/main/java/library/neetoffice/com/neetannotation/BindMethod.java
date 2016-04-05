package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/3/18.
 */
abstract class BindMethod {
    static void bindClick(Activity a, Method c) {
        final Click d = c.getAnnotation(Click.class);
        if (d == null) {
            return;
        }
        final int[] e = d.value();
        for (int f : e) {
            final View g = a.findViewById(f);
            if (g == null) {
                continue;
            }
            g.setOnClickListener(new NeetOnClickListener(a, c));
        }
    }

    static void bindClick(Object a, View b, Method c) {
        final Click d = c.getAnnotation(Click.class);
        if (d == null) {
            return;
        }
        final int[] e = d.value();
        for (int f : e) {
            final View g = b.findViewById(f);
            if (g == null) {
                continue;
            }
            g.setOnClickListener(new NeetOnClickListener(a, c));
        }
    }

    static void bindLongClick(Activity a, Method c) {
        final LongClick d = c.getAnnotation(LongClick.class);
        if (d == null) {
            return;
        }
        final int[] e = d.value();
        for (int f : e) {
            final View g = a.findViewById(f);
            if (g == null) {
                continue;
            }
            g.setOnLongClickListener(new NeetLongClickListener(a, c));
        }
    }

    static void bindLongClick(Object a, View b, Method c) {
        final LongClick d = c.getAnnotation(LongClick.class);
        if (d == null) {
            return;
        }
        final int[] e = d.value();
        for (int f : e) {
            final View g = b.findViewById(f);
            if (g == null) {
                continue;
            }
            g.setOnLongClickListener(new NeetLongClickListener(a, c));
        }
    }

    static void bindTouch(Activity a, Method c) {
        final Touch d = c.getAnnotation(Touch.class);
        if (d == null) {
            return;
        }
        final int[] e = d.value();
        for (int f : e) {
            final View g = a.findViewById(f);
            if (g == null) {
                continue;
            }
            g.setOnTouchListener(new NeetTouchListener(a, c));
        }
    }

    static void bindTouch(Object a, View b, Method c) {
        final Touch d = c.getAnnotation(Touch.class);
        if (d == null) {
            return;
        }
        final int[] f = d.value();
        for (int g : f) {
            final View h = b.findViewById(g);
            if (h == null) {
                continue;
            }
            h.setOnTouchListener(new NeetTouchListener(a, c));
        }
    }

    static void bindItemClick(Activity a, Method c) {
        final ItemClick d = c.getAnnotation(ItemClick.class);
        if (d == null) {
            return;
        }
        final int[] f = d.value();
        for (int g : f) {
            final View h = a.findViewById(g);
            if (h == null) {
                continue;
            }
            if (h instanceof AdapterView) {
                ((AdapterView) h).setOnItemClickListener(new NeetItemClickListener(a, c));
            }
        }
    }

    static void bindItemClick(Object a, View b, Method c) {
        final ItemClick d = c.getAnnotation(ItemClick.class);
        if (d == null) {
            return;
        }
        final int[] f = d.value();
        for (int g : f) {
            final View h = b.findViewById(g);
            if (h == null) {
                continue;
            }
            if (h instanceof AdapterView) {
                ((AdapterView) h).setOnItemClickListener(new NeetItemClickListener(a, c));
            }
        }
    }

    static void bindCheckedChange(Activity a, Method c) {
        final CheckedChange d = c.getAnnotation(CheckedChange.class);
        if (d == null) {
            return;
        }
        final int[] f = d.value();
        for (int g : f) {
            final View h = a.findViewById(g);
            if (h == null) {
                continue;
            }
            if (h instanceof AdapterView) {
                ((CompoundButton) h).setOnCheckedChangeListener(new NeetCheckedChangeListener(a, c));
            }
        }
    }

    static void bindCheckedChange(Object a, View b, Method c) {
        final CheckedChange d = c.getAnnotation(CheckedChange.class);
        if (d == null) {
            return;
        }
        final int[] f = d.value();
        for (int g : f) {
            final View h = b.findViewById(g);
            if (h == null) {
                continue;
            }
            if (h instanceof AdapterView) {
                ((CompoundButton) h).setOnCheckedChangeListener(new NeetCheckedChangeListener(a, c));
            }
        }
    }

    static void onActivityResult(Object a, int b, Intent c) {
        Class<?> d = a.getClass();
        do {
            final NActivity q = d.getAnnotation(NActivity.class);
            final NFragment r = d.getAnnotation(NFragment.class);
            if (q != null || r != null) {
                final Method[] f = d.getDeclaredMethods();
                for (Method g : f) {
                    final OnActivityResult h = g.getAnnotation(OnActivityResult.class);
                    if (h == null) {
                        continue;
                    }
                    if (h.value() == b) {
                        final Class<?>[] i = g.getParameterTypes();
                        if (i.length == 0) {
                            try {
                                g.invoke(a);
                            } catch (IllegalAccessException e) {
                            } catch (InvocationTargetException e) {
                            }
                        } else if (i.length == 1) {
                            if (i[0] == Intent.class) {
                                try {
                                    g.invoke(a, c);
                                } catch (IllegalAccessException e) {
                                } catch (InvocationTargetException e) {
                                }
                            } else if (i[0] == Bundle.class) {
                                try {
                                    if (c != null) {
                                        g.invoke(a, c.getExtras());
                                    } else {
                                        g.invoke(a, null);
                                    }
                                } catch (IllegalAccessException e) {
                                } catch (InvocationTargetException e) {
                                }
                            }
                        }
                    }
                }
            }
            d = d.getSuperclass();
        } while (d != null);
    }
}
