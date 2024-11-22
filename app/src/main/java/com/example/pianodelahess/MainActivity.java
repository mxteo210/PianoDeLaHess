package com.example.pianodelahess;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PianoView pianoView;
    private AudioSoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pianoView = findViewById(R.id.piano_view);
        soundPlayer = new AudioSoundPlayer(this);

        // Initialisation du menu déroulant
        Spinner instrumentSpinner = findViewById(R.id.instrument_spinner);

        // Liste des instruments disponibles
        String[] instruments = {"Piano", "Guitare", "Violoncelle", "Flûte"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instruments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instrumentSpinner.setAdapter(adapter);

        // Gestion des sélections dans le Spinner
        instrumentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedInstrument = instruments[position];
                updateInstrument(selectedInstrument);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aucun instrument sélectionné
            }
        });
    }

    private void updateInstrument(String instrument) {
        // Changez la logique pour associer les sons des notes en fonction de l'instrument
        soundPlayer.setInstrument(instrument);
    }
}