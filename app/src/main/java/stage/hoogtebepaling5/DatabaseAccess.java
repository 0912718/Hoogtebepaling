package stage.hoogtebepaling5;

/**
 * Created by Edgar on 10/2/2017.
 */

/**Standaard database access functie*/
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * voert een query uit en haalt vervolgens de gegevens op en slaat die in een lijst
     */
    public List<String> getData() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * from router", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;

    }
}


//knutseltje, gewoon negeren



//import android.content.Context;
//import android.database.SQLException;
//import android.database.db.SQLiteDatabase;
//import android.database.db.SQLiteException;
//import android.database.db.SQLiteOpenHelper;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class DBHelper extends SQLiteOpenHelper{
//    public static String DATABASE_NAME = "db.sqlite";
//    private static String DATABASE_PATH = "";
//    private SQLiteDatabase mDatabase;
//    private final Context mContext;
//
//    public DBHelper(Context context){
//        super(context, DATABASE_NAME, null, 1);
//        if (android.os.Build.VERSION.SDK_INT >= 17)
//            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
//        else
//            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
//        this.mContext = context;
//
//        //copyDatabase();
//        //this.getReadableDatabase();
//    }
////
//public void createDataBase() throws IOException{
//
//    boolean dbExist = checkDataBase();
//
//    if(dbExist){
//        //do nothing - database already exist
//    }else{
//
//        //By calling this method and empty database will be created into the default system path
//        //of your application so we are gonna be able to overwrite that database with our database.
//        this.getReadableDatabase();
//
//        try {
//
//            copyDataBase();
//
//        } catch (IOException e) {
//
//            throw new Error("Error copying database");
//
//        }
//    }
//
//}
//
//    /**
//     * Check if the database already exist to avoid re-copying the file each time you open the application.
//     * @return true if it exists, false if it doesn't
//     */
//    private boolean checkDataBase(){
//
//        SQLiteDatabase checkDB = null;
//
//        try{
//            String myPath = DATABASE_PATH + DATABASE_NAME;
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//        }catch(SQLiteException e){
//
//            //database does't exist yet.
//
//        }
//
//        if(checkDB != null){
//
//            checkDB.close();
//
//        }
//
//        return checkDB != null ? true : false;
//    }
//
//    /**
//     * Copies your database from your local assets-folder to the just created empty database in the
//     * system folder, from where it can be accessed and handled.
//     * This is done by transfering bytestream.
//     * */
//    private void copyDataBase() throws IOException{
//
//        //Open your local db as the input stream
//        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
//
//        // Path to the just created empty db
//        String outFileName = DATABASE_PATH + DATABASE_NAME;
//
//        //Open the empty db as the output stream
//        OutputStream myOutput = new FileOutputStream(outFileName);
//
//        //transfer bytes from the inputfile to the outputfile
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = myInput.read(buffer))>0){
//            myOutput.write(buffer, 0, length);
//        }
//
//        //Close the streams
//        myOutput.flush();
//        myOutput.close();
//        myInput.close();
//
//    }
//
//    public void openDataBase() throws SQLException{
//
//        //Open the database
//        String myPath = DATABASE_PATH + DATABASE_NAME;
//        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//    }
//
//    @Override
//    public synchronized void close() {
//
//        if(mDatabase != null)
//            mDatabase.close();
//
//        super.close();
//
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//
//    // Add your public helper methods to access and get content from the database.
//    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
//    // to you to create adapters for your views.
//
//}
////
////    private boolean checkDatabase(){
////            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
////            return dbFile.exists();
////    }
////    private void copyDatabase(){
////        if(!checkDatabase()){
////            this.getReadableDatabase();
////            this.close();
////            try{
////                copyDBFile();
////            }
////            catch(IOException mIOException){
////                throw new Error("Error copying database lol");
////            }
////        }
////    }
////
////    private void copyDBFile() throws IOException{
////        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
////        OutputStream mOutput = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);
////        byte[] mBuffer = new byte[1024];
////        int mLength;
////        while ((mLength=mInput.read(mBuffer))>0)
////            mOutput.write(mBuffer, 0,mLength);
////        mOutput.flush();
////        mOutput.close();
////        mInput.close();
////    }
////    public boolean openDatabase() throws SQLException{
////        mDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME,null,SQLiteDatabase.CREATE_IF_NECESSARY);
////        return mDatabase != null;
////    }
////    @Override
////    public synchronized void close(){
////        if(mDatabase != null)
////            mDatabase.close();
////        super.close();
////    }
////
////
////    @Override
////    public void onCreate(SQLiteDatabase db) {
////
////    }
////
////    @Override
////    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
////
////    }
//
