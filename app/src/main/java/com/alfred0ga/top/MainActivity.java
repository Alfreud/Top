package com.alfred0ga.top;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.containerMain)
    CoordinatorLayout containerMain;

    private ArtistaAdapter adapter;

    public static final Artista sArtista = new Artista();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();

        generateArtist();
    }

    private void generateArtist() {
        String[] nombres = {"Rachel", "Amy", "Scarlett", "Miley"};
        String[] apellidos = {"McAdams", "McDonald", "Johansson", "Cyrus"};
        long[] nacimientos = {280108800000L, 280108800000L, 280108800000L, 280108800000L};
        String[] lugares = {"Canada", "UK", "EEUU", "EEUU"};
        short[] estaturas = {163, 163, 160, 165};
        String[] notas = {
                "Rachel Anne McAdams was born on November 17, 1978 in London, Ontario, Canada, to Sandra Kay (Gale), a nurse, and Lance Frederick McAdams, a truck driver and furniture mover. She is of English, Welsh, Irish, and Scottish descent. Rachel became involved with acting as a teenager and by the age of 13 was performing in Shakespearean productions in summer theater camp; she went on to graduate with honors with a BFA degree in Theater from York University. After her debut in an episode of Disney's The Famous Jett Jackson (1998), she co-starred in the Canadian TV series Slings and Arrows (2003), a comedy-drama about the trials and travails of a Shakespearean theater group, and won a Gemini award for her performance in 2003.",
                "Rachel Anne McAdams was born on November 17, 1978 in London, Ontario, Canada, to Sandra Kay (Gale), a nurse, and Lance Frederick McAdams, a truck driver and furniture mover. She is of English, Welsh, Irish, and Scottish descent. Rachel became involved with acting as a teenager and by the age of 13 was performing in Shakespearean productions in summer theater camp; she went on to graduate with honors with a BFA degree in Theater from York University. After her debut in an episode of Disney's The Famous Jett Jackson (1998), she co-starred in the Canadian TV series Slings and Arrows (2003), a comedy-drama about the trials and travails of a Shakespearean theater group, and won a Gemini award for her performance in 2003.",
                "Rachel Anne McAdams was born on November 17, 1978 in London, Ontario, Canada, to Sandra Kay (Gale), a nurse, and Lance Frederick McAdams, a truck driver and furniture mover. She is of English, Welsh, Irish, and Scottish descent. Rachel became involved with acting as a teenager and by the age of 13 was performing in Shakespearean productions in summer theater camp; she went on to graduate with honors with a BFA degree in Theater from York University. After her debut in an episode of Disney's The Famous Jett Jackson (1998), she co-starred in the Canadian TV series Slings and Arrows (2003), a comedy-drama about the trials and travails of a Shakespearean theater group, and won a Gemini award for her performance in 2003.",
                "Rachel Anne McAdams was born on November 17, 1978 in London, Ontario, Canada, to Sandra Kay (Gale), a nurse, and Lance Frederick McAdams, a truck driver and furniture mover. She is of English, Welsh, Irish, and Scottish descent. Rachel became involved with acting as a teenager and by the age of 13 was performing in Shakespearean productions in summer theater camp; she went on to graduate with honors with a BFA degree in Theater from York University. After her debut in an episode of Disney's The Famous Jett Jackson (1998), she co-starred in the Canadian TV series Slings and Arrows (2003), a comedy-drama about the trials and travails of a Shakespearean theater group, and won a Gemini award for her performance in 2003."
        };
        String[] fotos = {
                "https://upload.wikimedia.org/wikipedia/commons/3/3e/Rachel_McAdams%2C_2016_%28cropped%29.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/0/07/Amy_MacDonald-9578.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/3/36/Scarlett_Johansson_in_Kuwait_01.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/3/3d/Miley_Cyrus_at_the_2009_Academy_Awards_04_new.jpg"
        };
        for (int i = 0; i < 4; i++) {
            Artista artista = new Artista(i + 1, nombres[i], apellidos[i], nacimientos[i],
                    lugares[i], estaturas[i], notas[i], i + 1, fotos[i]);
            adapter.add(artista);
        }
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configAdapter() {
        adapter = new ArtistaAdapter(new ArrayList<Artista>(), this);
    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /*
     *   OnItemClickListener Interface
     * */
    @Override
    public void onItemClick(Artista artista) {
        sArtista.setId(artista.getId());
        sArtista.setNombre(artista.getNombre());
        sArtista.setApellidos(artista.getApellidos());
        sArtista.setFechaNacimiento(artista.getFechaNacimiento());
        sArtista.setEstatura(artista.getEstatura());
        sArtista.setLugarNacimiento(artista.getLugarNacimiento());
        sArtista.setOrden(artista.getOrden());
        sArtista.setNotas(artista.getNotas());
        sArtista.setFotoUrl(artista.getFotoUrl());

        Intent i = new Intent(MainActivity.this, DetalleActivity.class);
        startActivity(i);
    }

    @Override
    public void onLongItemClick(Artista artista) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            adapter.add(sArtista);
        }
    }

    @OnClick(R.id.fab)
    public void addArtist() {
        Intent intent = new Intent(MainActivity.this, AddArtistActivity.class);
        intent.putExtra(Artista.ORDEN, adapter.getItemCount() + 1);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }
}
