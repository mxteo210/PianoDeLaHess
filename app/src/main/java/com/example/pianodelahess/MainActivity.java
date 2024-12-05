package com.example.pianodelahess;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PianoView pianoView; // Instance de la vue piano, responsable de l'affichage et des interactions.
    private AudioSoundPlayer soundPlayer; // Gestionnaire des sons pour jouer les notes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Charge la disposition principale de l'application.

        pianoView = findViewById(R.id.piano_view); // Initialise la vue piano.
        soundPlayer = new AudioSoundPlayer(this); // Initialise le gestionnaire audio avec le contexte de l'application.

        // Configuration du menu déroulant (Spinner) pour sélectionner un instrument.
        Spinner instrumentSpinner = findViewById(R.id.instrument_spinner);

        // Liste des instruments disponibles.
        String[] instruments = {"Piano", "Guitare", "Violons", "Bass"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instruments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instrumentSpinner.setAdapter(adapter); // Associe la liste des instruments au Spinner.

        // Gestion des événements de sélection dans le Spinner.
        instrumentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedInstrument = instruments[position]; // Récupère l'instrument sélectionné.
                updateInstrument(selectedInstrument); // Met à jour les sons associés à l'instrument.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aucun instrument sélectionné, aucune action nécessaire.
            }
        });
    }

    // Met à jour les sons en fonction de l'instrument sélectionné.
    private void updateInstrument(String instrument) {
        soundPlayer.setInstrument(instrument); // Appelle la méthode setInstrument pour charger les nouveaux sons.
    }
}
