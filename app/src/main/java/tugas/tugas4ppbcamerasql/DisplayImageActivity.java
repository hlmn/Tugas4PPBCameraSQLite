package tugas.tugas4ppbcamerasql;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class DisplayImageActivity extends Activity {
    Button btnDelete;
    ImageView imageDetail;
    int imageId;
    Bitmap theImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        imageDetail = (ImageView) findViewById(R.id.imageView1);

        Intent intnt = getIntent();
        theImage = (Bitmap) intnt.getParcelableExtra("imagename");
        imageId = intnt.getIntExtra("imageid", 20);
        Log.d("Image ID:****", String.valueOf(imageId));
        imageDetail.setImageBitmap(theImage);
        btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                DataHelper db = new DataHelper(
                        DisplayImageActivity.this);

                Log.d("Delete Image: ", "Deleting.....");
                db.deleteImage(new Image(imageId));
                // /after deleting data go to main page
                Intent i = new Intent(DisplayImageActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}