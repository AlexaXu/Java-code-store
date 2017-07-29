import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Created by Better on 2017/7/13.
 */
public class BeatBox {
    JFrame frame;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;

    String[] instrumentName = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare",
        "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
        "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    int[] instrument = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args){
        new BeatBox().buildGui();
    }

    public void buildGui() {
        frame = new JFrame("My BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo up");
        upTempo.addActionListener(new MyUpListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo down");
        downTempo.addActionListener(new MyDownListener());
        buttonBox.add(downTempo);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());
        buttonBox.add(saveButton);

        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(new RestoreButtonListener());
        buttonBox.add(restoreButton);

        checkBoxList = new ArrayList<JCheckBox>();
        Box nameBox = new Box(BoxLayout.Y_AXIS);

        for(int i = 0; i < 16; i++){
            nameBox.add(new Label(instrumentName[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        frame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(0);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for(int i = 0; i < 256; i++){
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();
        
        frame.setBounds(50, 50, 300 ,300);
        frame.pack();
        frame.setVisible(true);
    }

    public void setUpMidi() {
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            sequence = new Sequence(Sequence.PPQ, 4);

            track = sequence.createTrack();

            sequencer.setTempoInBPM(120);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void buildTrackAndStart() {
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for(int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instrument[i];

            for(int j = 0; j < 16; j++){
                JCheckBox jc = (JCheckBox) checkBoxList.get((16 * i) + j);

                if(jc.isSelected()){
                    trackList[j] = key;
                }else {
                    trackList[j] = 0;
                }
            }

            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTracks(int[] list) {
        for(int i = 0; i < 16; i++){
            int key = list[i];

            if(key != 0){
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    public MidiEvent makeEvent(int cmd, int chan, int one, int two, int tick) {
        MidiEvent event = null;

        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(cmd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }

    class MyStartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    class MyStopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    class MyUpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    class MyDownListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkboxState = new boolean[256];

            for(int i = 0; i < 256; i++) {
                JCheckBox jc = (JCheckBox) checkBoxList.get(i);

                if(jc.isSelected()) {
                    checkboxState[i] = true;
                }
            }

            try{
                FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.cer"));
                ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
                outputStream.writeObject(checkboxState);
                outputStream.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private class RestoreButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkboxState = null;

            try{
                JFileChooser fileRestore = new JFileChooser();
                fileRestore.showOpenDialog(frame);
                File file = fileRestore.getSelectedFile();
                FileInputStream inFile = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inFile);

                checkboxState = (boolean[]) in.readObject();
            }catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            for(int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) checkBoxList.get(i);

                if(checkboxState[i]) {
                    check.setSelected(true);
                }else {
                    check.setSelected(false);
                }
            }

            sequencer.stop();
            buildTrackAndStart();

        }
    }
}
