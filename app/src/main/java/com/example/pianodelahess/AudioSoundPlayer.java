package com.example.pianodelahess;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.SparseArray;

public class AudioSoundPlayer {

    private SparseArray<MediaPlayer> playerMap = new SparseArray<>();
    private Context context;

    // Map des noms de fichiers associés aux notes
    private static final SparseArray<String> SOUND_MAP = new SparseArray<>();
    static {
        SOUND_MAP.put(1, "note_do.mp3");
        SOUND_MAP.put(2, "note_re.mp3");
        SOUND_MAP.put(3, "note_mi.mp3");
        SOUND_MAP.put(4, "note_fa.mp3");
        SOUND_MAP.put(5, "note_sol.mp3");
        SOUND_MAP.put(6, "note_la.mp3");
        SOUND_MAP.put(7, "note_si.mp3");
        SOUND_MAP.put(8, "second_do.mp3");
        SOUND_MAP.put(9, "second_re.mp3");
        SOUND_MAP.put(10, "second_mi.mp3");
        SOUND_MAP.put(11, "second_fa.mp3");
        SOUND_MAP.put(12, "second_sol.mp3");
        SOUND_MAP.put(13, "second_la.mp3");
        SOUND_MAP.put(14, "second_si.mp3");
        SOUND_MAP.put(15, "do_dies.mp3");
        SOUND_MAP.put(16, "re_dies.mp3");
        SOUND_MAP.put(17, "fa_dies.mp3");
        SOUND_MAP.put(18, "sol_dies.mp3");
        SOUND_MAP.put(19, "la_dies.mp3");
        SOUND_MAP.put(20, "second_dies_do.mp3");
        SOUND_MAP.put(21, "second_dies_re.mp3");
        SOUND_MAP.put(22, "second_dies_fa.mp3");
        SOUND_MAP.put(23, "second_dies_sol.mp3");
        SOUND_MAP.put(24, "second_dies_la.mp3");
    }

    public AudioSoundPlayer(Context context) {
        this.context = context;
    }

    // Joue une note
    public void playNote(int note) {
        // Vérifie si une instance MediaPlayer pour cette note existe déjà
        if (playerMap.get(note) == null) {
            String fileName = SOUND_MAP.get(note);
            if (fileName != null) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    // Prépare le MediaPlayer avec le fichier dans le dossier assets
                    mediaPlayer.setDataSource(context.getAssets().openFd(fileName).getFileDescriptor(),
                            context.getAssets().openFd(fileName).getStartOffset(),
                            context.getAssets().openFd(fileName).getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    // Définir un Listener pour libérer la ressource après lecture
                    mediaPlayer.setOnCompletionListener(mp -> {
                        mp.release();
                        playerMap.remove(note); // Supprime le MediaPlayer de la map après usage
                    });

                    // Sauvegarde l'instance pour référence
                    playerMap.put(note, mediaPlayer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Arrête une note si elle est en cours
    public void stopNote(int note) {
        MediaPlayer mediaPlayer = playerMap.get(note);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            playerMap.remove(note);
        }
    }

    // Vérifie si une note est en cours de lecture
    public boolean isNotePlaying(int note) {
        MediaPlayer mediaPlayer = playerMap.get(note);
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}