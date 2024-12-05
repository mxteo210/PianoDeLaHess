package com.example.pianodelahess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NB = 14; // Nombre total de touches blanches
    private Paint black, gray, white; // Couleurs pour dessiner les touches
    private ArrayList<Key> whites = new ArrayList<>(); // Liste des touches blanches
    private ArrayList<Key> blacks = new ArrayList<>(); // Liste des touches noires
    private int keyWidth, height; // Dimensions des touches du clavier
    private AudioSoundPlayer soundPlayer; // Gestionnaire des sons

    // Constructeur pour initialiser les objets de dessin et le gestionnaire audio
    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        black = new Paint();
        black.setColor(Color.BLACK);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        gray = new Paint();
        gray.setColor(Color.GRAY);
        gray.setStyle(Paint.Style.FILL);

        soundPlayer = new AudioSoundPlayer(context); // Initialise le gestionnaire de sons
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        keyWidth = w / NB; // Largeur d'une touche blanche
        height = h; // Hauteur du clavier

        int count = 15; // Identifiant initial pour les touches noires

        // Génération des touches blanches
        for (int i = 0; i < NB; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            if (i == NB - 1) {
                right = w; // Ajuste la largeur de la dernière touche blanche pour combler l'espace
            }

            RectF rect = new RectF(left, 0, right, h);
            whites.add(new Key(rect, i + 1)); // Ajoute la touche blanche avec son identifiant

            // Génération des touches noires, sauf aux positions où elles n’existent pas (C, F)
            if (i != 0 && i != 3 && i != 7 && i != 10) {
                rect = new RectF(
                        (float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth,
                        0,
                        (float) i * keyWidth + 0.25f * keyWidth,
                        0.67f * height
                );
                blacks.add(new Key(rect, count)); // Ajoute la touche noire avec son identifiant
                count++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Dessine toutes les touches blanches
        for (Key k : whites) {
            canvas.drawRect(k.rect, k.down ? gray : white); // Change la couleur si la touche est pressée
        }

        // Dessine les lignes séparatrices entre les touches blanches
        for (int i = 1; i < NB; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, height, black);
        }

        // Dessine toutes les touches noires
        for (Key k : blacks) {
            canvas.drawRect(k.rect, k.down ? gray : black); // Change la couleur si la touche est pressée
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // Type d'action (DOWN, MOVE, UP)
        int pointerCount = event.getPointerCount(); // Nombre total de doigts actifs

        // Liste temporaire pour stocker les touches pressées
        ArrayList<Key> currentlyPressedKeys = new ArrayList<>();

        // Gérer les appuis ou mouvements des doigts
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_MOVE) {
            for (int i = 0; i < pointerCount; i++) { // Parcourt tous les doigts actifs
                float x = event.getX(i);
                float y = event.getY(i);

                Key k = keyForCoords(x, y); // Trouve la touche associée aux coordonnées
                if (k != null) {
                    currentlyPressedKeys.add(k); // Ajoute la touche à la liste temporaire des touches pressées

                    if (!k.isPlaying) { // Si la note n'est pas déjà en lecture
                        k.down = true; // Marque la touche comme pressée visuellement
                        k.isPlaying = true; // Empêche de rejouer la note
                        soundPlayer.playNote(k.sound); // Joue la note
                    }
                }
            }

            // Réinitialise toutes les touches qui ne sont pas pressées
            resetNonPressedKeys(currentlyPressedKeys);
        }

        // Gérer les relâchements de doigts
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            // Si c'est un relâchement d'un seul doigt
            if (action == MotionEvent.ACTION_UP) {
                resetKeys(); // Réinitialise toutes les touches si le dernier doigt est levé
            } else { // Si c'est un relâchement de pointeur (ACTION_POINTER_UP)
                int pointerIndex = event.getActionIndex(); // Obtient l'index du pointeur (doigt levé)
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);

                Key k = keyForCoords(x, y);
                if (k != null) {
                    k.down = false; // Réinitialise l'état de la touche
                    k.isPlaying = false; // Arrête la lecture de la note
                }
            }
        }

        invalidate(); // Rafraîchit l'affichage des touches
        return true; // Indique que l'événement a été traité
    }

    private Key keyForCoords(float x, float y) {
        // Cherche d'abord dans les touches noires
        for (Key k : blacks) {
            if (k.rect.contains(x, y)) {
                return k;
            }
        }

        // Ensuite, cherche dans les touches blanches
        for (Key k : whites) {
            if (k.rect.contains(x, y)) {
                return k;
            }
        }

        return null; // Aucune touche trouvée
    }

    private void resetNonPressedKeys(ArrayList<Key> currentlyPressedKeys) {
        ArrayList<Key> allKeys = new ArrayList<>(whites); // Regroupe toutes les touches blanches
        allKeys.addAll(blacks); // Ajoute toutes les touches noires

        for (Key k : allKeys) {
            // Si la touche n'est pas dans la liste des touches pressées actuellement :
            if (!currentlyPressedKeys.contains(k)) {
                k.down = false; // Réinitialise l'état visuel
                k.isPlaying = false; // Arrête la lecture de la note
            }
        }
    }

    private void resetKeys() {
        ArrayList<Key> allKeys = new ArrayList<>(whites); // Regroupe toutes les touches blanches
        allKeys.addAll(blacks); // Ajoute toutes les touches noires

        // Réinitialise chaque touche
        for (Key k : allKeys) {
            k.down = false; // Réinitialise l'état visuel (légèrement grisé ou non pressé)
            k.isPlaying = false; // Arrête la lecture de la note
        }

        invalidate(); // Rafraîchit l'affichage des touches
    }
}
