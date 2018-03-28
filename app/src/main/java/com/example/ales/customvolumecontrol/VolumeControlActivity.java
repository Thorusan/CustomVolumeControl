package com.example.ales.customvolumecontrol;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ales.customvolumecontrol.customviews.VolumeControlView;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeControlActivity extends AppCompatActivity {

    // region Members
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.edit_volume_label)
    EditText editVolume;
    @BindView(R.id.btn_setVolume)
    Button btnSetVolume;
    @BindView(R.id.edit_scale_label)
    EditText editScale;
    @BindView(R.id.btn_setScale)
    Button btnSetScale;
    @BindView(R.id.btn_setColor)
    Button btnSetColor;
    @BindView(R.id.volume_control_view)
    VolumeControlView volumeControlView;

    @BindView(R.id.volume_label)
    TextView volumeLabel;

    private ColorRGB colorRGB;

    //endregion

    // region Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        colorRGB = new ColorRGB(0, 0, 0);

        toControls();

        registerListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_volume_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Save UI state changes to the savedInstanceState.
     * This bundle will be passed to onCreate if the process is killed and restarted.
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("controlVolume", volumeControlView.getControlVolume());
        savedInstanceState.putInt("controlScale", volumeControlView.getControlScale());
        savedInstanceState.putInt("controlColor", volumeControlView.getControlColor());
    }

    /**
     * Restore UI state from the savedInstanceState.
     * This bundle has also been passed to onCreate.
     *
     * @param savedInstanceState
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int controlVolume = savedInstanceState.getInt("controlVolume");
        int controlScale = savedInstanceState.getInt("controlScale");
        int controlColor = savedInstanceState.getInt("controlColor");

        restoreCustomViewValues(controlVolume, controlScale, controlColor);
    }

    //endregion

    //region private Methods

    /**
     * Set inital values of Control volume and scale to input fields
     */
    private void toControls() {
        editVolume.setText(String.valueOf(volumeControlView.getControlVolume()));
        editScale.setText(String.valueOf(volumeControlView.getControlScale()));
    }

    /**
     * Register listeners for setting volume, scale and color of custom view
     */
    private void registerListeners() {
        btnSetVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editVolume.getText() != null && !editVolume.getText().toString().isEmpty()) {
                    volumeControlView.setVolume(Integer.parseInt(editVolume.getText().toString()));
                }

            }
        });

        btnSetScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editScale.getText() != null && !editScale.getText().toString().isEmpty()) {
                    volumeControlView.setScale(Integer.parseInt(editScale.getText().toString()));
                }
            }
        });

        btnSetColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker cp = new ColorPicker(VolumeControlActivity.this, colorRGB.getRedColor(), colorRGB.getGreenColor(), colorRGB.getBlueColor());
                  /* Show color picker dialog */
                cp.show();
                /* Set a new Listener called when user click "select" */
                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(@ColorInt int color) {
                        // Do whatever you want
                        // Examples
                        volumeControlView.setColor(color);
                        colorRGB.setRedColor(cp.getRed());
                        colorRGB.setGreenColor(cp.getGreen());
                        colorRGB.setBlueColor(cp.getBlue());

                        cp.hide(); // hide color picker dialog
                    }
                });
            }
        });

    }

    /**
     * Restore values after orientation change
     * @param controlVolume volume
     * @param controlScale scale
     * @param controlColor color
     */
    private void restoreCustomViewValues(int controlVolume, int controlScale, int controlColor) {
        volumeControlView.setVolume(controlVolume);
        volumeControlView.setScale(controlScale);
        volumeControlView.setColor(controlColor);
    }

    public void setTextVolume(String volumeString) {
        volumeLabel.setText(volumeString);
    }

    //endregion
}
