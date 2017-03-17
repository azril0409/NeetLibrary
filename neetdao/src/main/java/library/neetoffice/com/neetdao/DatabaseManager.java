package library.neetoffice.com.neetdao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Deo-chainmeans on 2016/11/2.
 */

class DatabaseManager {
    private static final String TAG = DatabaseManager.class.getSimpleName();
    private final SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private AtomicInteger mOpenCounter = new AtomicInteger();

    DatabaseManager(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
    }

    synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    synchronized void close() {
        if (mOpenCounter.decrementAndGet() == 0) {
            sqLiteDatabase.close();
        }
    }
}
