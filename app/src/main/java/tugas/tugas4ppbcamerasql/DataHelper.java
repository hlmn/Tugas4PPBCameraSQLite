package tugas.tugas4ppbcamerasql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tugas4camera";


    private static final String TABLE_IMAGE = "imagestugas";


    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_IMAGES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        // Create tables again
        onCreate(db);
    }



    public// Adding new image
    void addImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, image._name); // Image Name
        values.put(KEY_IMAGE, image._image); // Image Phone

        // Inserting Row
        db.insert(TABLE_IMAGE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Image getImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMAGE, new String[] { KEY_ID,
                        KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Image image = new Image(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getBlob(2));

        // return image
        return image;

    }


    public List<Image> getAllImages() {
        List<Image> imageList = new ArrayList<Image>();
        // Select All Query
        String selectQuery = "SELECT  * FROM imagestugas ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Image image = new Image();
                image.setID(Integer.parseInt(cursor.getString(0)));
                image.setName(cursor.getString(1));
                image.setImage(cursor.getBlob(2));
                // Adding image to list
                imageList.add(image);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();

        return imageList;

    }


    public int updateImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, image.getName());
        values.put(KEY_IMAGE, image.getImage());

        // updating row
        return db.update(TABLE_IMAGE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(image.getID()) });

    }


    public void deleteImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, KEY_ID + " = ?",
                new String[] { String.valueOf(image.getID()) });
        db.close();
    }


    public int getImagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_IMAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}