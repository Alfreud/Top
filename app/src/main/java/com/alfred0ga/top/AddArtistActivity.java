package com.alfred0ga.top;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddArtistActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int RC_PHOTO_PICKER = 21;

    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.etApellidos)
    TextInputEditText etApellidos;
    @BindView(R.id.etFechaNacimiento)
    TextInputEditText etFechaNacimiento;
    @BindView(R.id.etEstatura)
    TextInputEditText etEstatura;
    @BindView(R.id.etlugarNacimiento)
    TextInputEditText etlugarNacimiento;
    @BindView(R.id.etNotas)
    TextInputEditText etNotas;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tilNombre)
    TextInputLayout tilNombre;
    @BindView(R.id.tilApellidos)
    TextInputLayout tilApellidos;
    @BindView(R.id.tilEstatura)
    TextInputLayout tilEstatura;

    private Artista mArtista;
    private Calendar mCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist);
        ButterKnife.bind(this);

        configActionBar();
        configArtista(getIntent());
        configCalendar();
    }

    private void configActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configArtista(Intent intent) {
        mArtista = new Artista();
        mArtista.setFechaNacimiento(System.currentTimeMillis());
        mArtista.setOrden(intent.getIntExtra(Artista.ORDEN, 0));
    }

    private void configCalendar() {
        mCalendar = Calendar.getInstance(Locale.ROOT);
        etFechaNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(
                System.currentTimeMillis()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                saveArtist();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveArtist() {
        if (validateFields()) {
            mArtista.setNombre(etNombre.getText().toString().trim());
            mArtista.setApellidos(etApellidos.getText().toString().trim());
            mArtista.setEstatura(Short.valueOf(etEstatura.getText().toString().trim()));
            mArtista.setLugarNacimiento(etlugarNacimiento.getText().toString().trim());
            mArtista.setNotas(etNotas.getText().toString().trim());
            try {
                mArtista.save();
                Log.i("DBFlow", "Inserción correcta de datos");
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*MainActivity.sArtista.setNombre(etNombre.getText().toString().trim());
            MainActivity.sArtista.setApellidos(etApellidos.getText().toString().trim());
            MainActivity.sArtista.setEstatura(Short.valueOf(etEstatura.getText().toString().trim()));
            MainActivity.sArtista.setLugarNacimiento(etlugarNacimiento.getText().toString().trim());
            MainActivity.sArtista.setNotas(etNotas.getText().toString().trim());
            MainActivity.sArtista.setOrden(mArtista.getOrden());
            MainActivity.sArtista.setFotoUrl(mArtista.getFotoUrl());
*/
            //setResult(RESULT_OK);
            finish();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (etEstatura.getText().toString().trim().isEmpty() ||
                Integer.valueOf(etEstatura.getText().toString().trim()) < getResources().getInteger(R.integer.estatura_min)) {
            tilEstatura.setError(getString(R.string.addArtist_error_estaturaMin));
            tilEstatura.requestFocus();
            isValid = false;
        }else{
            tilEstatura.setError(null);
        }

        if (etApellidos.getText().toString().trim().isEmpty()) {
            tilApellidos.setError(getString(R.string.addArtist_error_required));
            tilApellidos.requestFocus();
            isValid = false;
        }else{
            tilApellidos.setError(null);
        }

        if (etNombre.getText().toString().trim().isEmpty()) {
            //etNombre.setError(getString(R.string.addArtist_error_required));
            tilNombre.setError(getString(R.string.addArtist_error_required));
            tilNombre.requestFocus();
            isValid = false;
        }else{
            tilNombre.setError(null);
        }

        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_PHOTO_PICKER:
                    configImageView(data.getDataString());
                    break;
            }
        }
    }

    @OnClick(R.id.etFechaNacimiento)
    public void onSetFecha() {
        DialogSelectorFecha selectorFecha = new DialogSelectorFecha();
        selectorFecha.setListener(AddArtistActivity.this);

        Bundle args = new Bundle();
        args.putLong(DialogSelectorFecha.FECHA, mArtista.getFechaNacimiento());
        selectorFecha.setArguments(args);
        selectorFecha.show(getSupportFragmentManager(), DialogSelectorFecha.SELECTED_DATE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        etFechaNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(
                mCalendar.getTimeInMillis()));
        mArtista.setFechaNacimiento(mCalendar.getTimeInMillis());
    }

    @OnClick({R.id.imgDeleteFoto, R.id.imgFromGallery, R.id.imgFromUrl})
    public void imageEvents(View view) {
        switch (view.getId()) {
            case R.id.imgDeleteFoto:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.detalle_dialogDelete_title)
                        .setMessage(String.format(Locale.ROOT,
                                getString(R.string.detalle_dialogDelete_message),
                                mArtista.getNombreCompleto()))
                        .setPositiveButton(R.string.label_dialog_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                configImageView(null);
                            }
                        })
                        .setNegativeButton(R.string.label_dialog_cancel, null);
                builder.show();
                break;
            case R.id.imgFromGallery:
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i,
                        getString(R.string.detalle_chooser_title)), RC_PHOTO_PICKER);
                break;
            case R.id.imgFromUrl:
                showAddPhotoDialog();
                break;
        }
    }

    private void showAddPhotoDialog() {
        final EditText etFotoUrl = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.addArtist_dialogUrl_title)
                .setPositiveButton(R.string.label_dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        configImageView(etFotoUrl.getText().toString().trim());
                    }
                })
                .setNegativeButton(R.string.label_dialog_cancel, null);
        builder.setView(etFotoUrl);
        builder.show();
    }

    private void configImageView(String fotoUrl) {
        if (fotoUrl != null) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(this)
                    .load(fotoUrl)
                    .apply(options)
                    .into(imgFoto);
        } else {
            imgFoto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_photo_size_select_actual));
        }

        mArtista.setFotoUrl(fotoUrl);
    }
}
