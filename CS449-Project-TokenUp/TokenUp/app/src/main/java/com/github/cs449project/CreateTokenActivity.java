package com.github.cs449project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateTokenActivity extends AppCompatActivity {

    private static int power = 0;
    private static int toughness = 0;
    private Context context;
    private static final int selectImg = 100;
    private Bitmap img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_token);
        context = this.context;


    }

    public void selectImg(View view) {
        Intent selectImgIntent = new Intent(Intent.ACTION_PICK);
        selectImgIntent.setType("image/*");
        startActivityForResult(selectImgIntent, selectImg);
    }

    public Bitmap resizeImg(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case selectImg:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap selectedImg = BitmapFactory.decodeStream(imageStream);

                    selectedImg = resizeImg(selectedImg, 1000);
                    img = selectedImg;

                    ImageView imgview = (ImageView) findViewById(R.id.image_token);
                    imgview.setImageBitmap(selectedImg);

                    imgview.getLayoutParams().height = 240;
                    imgview.getLayoutParams().width = 160;
                }
        }
    }

    public void incPower(View view) {
        TextView powertxt = (TextView) findViewById(R.id.text_power);
        String currentpower = powertxt.getText().toString();
        switch (currentpower) {
            case "-1": power++; powertxt.setText("N/A"); break;
            case "N/A": powertxt.setText("*"); break;
            case "*": powertxt.setText("0"); break;
            default: power++; powertxt.setText(Integer.toString(power)); break;
        }
    }

    public void decPower(View view) {
        TextView powertxt = (TextView) findViewById(R.id.text_power);
        String currentpower = powertxt.getText().toString();
        switch (currentpower) {
            case "0": powertxt.setText("*"); break;
            case "*": powertxt.setText("N/A"); break;
            default: power--; powertxt.setText(Integer.toString(power)); break;
        }
    }

    public void incToughness(View view) {
        TextView toughnesstxt = (TextView) findViewById(R.id.text_toughness);
        String currenttoughness = toughnesstxt.getText().toString();
        switch (currenttoughness) {
            case "-1": toughness++; toughnesstxt.setText("N/A"); break;
            case "N/A": toughnesstxt.setText("*"); break;
            case "*": toughnesstxt.setText("0"); break;
            default: toughness++; toughnesstxt.setText(Integer.toString(toughness)); break;
        }
    }

    public void decToughness(View view) {
        TextView toughnesstxt = (TextView) findViewById(R.id.text_toughness);
        String currenttoughness = toughnesstxt.getText().toString();
        switch (currenttoughness) {
            case "0": toughnesstxt.setText("*"); break;
            case "*": toughnesstxt.setText("N/A"); break;
            default: toughness--; toughnesstxt.setText(Integer.toString(toughness)); break;
        }
    }



    public void create(View view) throws InvalidInputException {
        String id, name, type, subtype, mtgset, power, toughness, artist;
        id = name = type = subtype = mtgset = power = toughness = artist = "";
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativelayout);

        Bundle bundle = new Bundle();
        ArrayList<String> colors = new ArrayList<>();
        String abilities[] = new String[0];
        String tags[] = new String[0];

        for (int i = 0; i < rlayout.getChildCount(); i++) {
            View child = rlayout.getChildAt(i);
            if (child instanceof EditText) {
                switch (getResources().getResourceEntryName(child.getId())) {
                    case "input_id": DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                        databaseAccess.open();
                        try {
                            if (databaseAccess.getIds().contains(((EditText) child).getText().toString())) {
                                throw new InvalidInputException("ID already in database");
                            }
                            else if(((EditText) child).getText().toString().isEmpty()) {
                                throw new InvalidInputException("ID is empty");
                            }
                        }
                        catch (InvalidInputException exception) {
                            exception.alert(CreateTokenActivity.this);
                            return;
                        }
                        databaseAccess.close();
                        id = ((EditText) child).getText().toString().trim(); break;
                    case "input_abilities":
                        abilities = ((EditText) child).getText().toString().split(";");
                        for (int j = 0; j < abilities.length; j++) {
                            abilities[j] = abilities[j].trim();
                        } break;
                    case "input_tags":
                        tags = ((EditText) child).getText().toString().split(";");
                        for (int j = 0; j < tags.length; j++) {
                            tags[j] = tags[j].trim();
                        } break;
                    case "input_name":
                        name = ((EditText) child).getText().toString();
                        break;
                    case "input_type":
                        type = ((EditText) child).getText().toString();
                        break;
                    case "input_subtype":
                        subtype = ((EditText) child).getText().toString();
                        break;
                    case "input_set":
                        mtgset = ((EditText) child).getText().toString();
                        break;
                    case "input_artist":
                        artist = ((EditText) child).getText().toString();
                        break;
                }
            }
            else if (child instanceof TableLayout) {
                TableLayout tlayout = (TableLayout) findViewById(R.id.colorslayout);

                for (int j = 0; j < tlayout.getChildCount(); j++) {
                    View row = tlayout.getChildAt(j);
                    if (row instanceof TableRow) {
                        for (int k = 0; k < ((TableRow) row).getChildCount(); k++) {

                            View rowchild = ((TableRow) row).getChildAt(k);

                            if (rowchild instanceof CheckBox) {
                                if (((CheckBox) rowchild).isChecked()) {
                                    if (((CheckBox) rowchild).getText().toString().equals("Blue")) {
                                        colors.add("U");
                                    }
                                    else {
                                        colors.add(String.valueOf(((CheckBox) rowchild).getText().charAt(0)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if (child instanceof LinearLayout) {
                LinearLayout llayout = ((LinearLayout) child);
                for (int j = 0; j < llayout.getChildCount(); j++) {
                    View lchild = llayout.getChildAt(j);

                    if (lchild instanceof TextView && (getResources().getResourceEntryName(lchild.getId()).equals("text_power")
                            || getResources().getResourceEntryName(lchild.getId()).equals("text_toughness")) ) {
                        if (!((TextView) lchild).getText().toString().equals("Any")) {
                            if (getResources().getResourceEntryName(lchild.getId()).equals("text_power")) {
                                power = ((TextView) lchild).getText().toString();
                            }
                            else {
                                toughness = ((TextView) lchild).getText().toString();
                            }
                        }
                    }
                }
            }
        }

        try {
            if (img == null) {
                throw new InvalidInputException("Image not set");
            }
        }
        catch (InvalidInputException exception) {
            exception.alert(CreateTokenActivity.this);
            return;
        }

        ByteArrayOutputStream imgblob = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 50, imgblob);
        byte[] byteArr = imgblob.toByteArray();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseAccess.createToken(id.trim(), name.trim(), byteArr, type.trim(), subtype.trim(), mtgset.trim(), colors, power.trim(), toughness.trim(), abilities, artist.trim(), tags);

        finish();
    }

}
