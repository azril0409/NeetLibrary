package library.neetoffice.com.neetdao;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Deo-chainmeans on 2016/11/2.
 */

class DatabaseManager {
    private final SQLiteHelper sqLiteHelper;
    private final String password;
    private SQLiteDatabase sqLiteDatabase;
    private AtomicInteger mOpenCounter = new AtomicInteger();

    DatabaseManager(SQLiteHelper sqLiteHelper, String password) {
        this.sqLiteHelper = sqLiteHelper;
        this.password = password;
    }

    synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            sqLiteDatabase = sqLiteHelper.getWritableDatabase(password);
        }
        return sqLiteDatabase;
    }

    synchronized void close() {
        if (mOpenCounter.decrementAndGet() == 0) {
            sqLiteDatabase.close();
        }
    }
}
