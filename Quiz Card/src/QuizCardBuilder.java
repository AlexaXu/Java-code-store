import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Better on 2017/7/17.
 */
public class QuizCardBuilder {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea questionArea;
    private JTextArea answerArea;
    private ArrayList<QuizCard> cardList = new ArrayList<>();

    public static void main(String[] args){
        QuizCardBuilder builder = new QuizCardBuilder();

        builder.go();
    }

    public void go() {
        frame = new JFrame("Building your Quiz Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();

        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        questionArea = new JTextArea(6, 20);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(bigFont);
        JScrollPane qScroller = new JScrollPane(questionArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answerArea = new JTextArea(6, 20);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);
        answerArea.setFont(bigFont);
        JScrollPane aScroller = new JScrollPane(answerArea);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next card");
        nextButton.addActionListener(new NextCardListener());

        JButton saveButton = new JButton("Save all cards");
        saveButton.addActionListener(new SaveButtonListener());

        JLabel qLabel = new JLabel("Question");
        JLabel aLabel = new JLabel("Answer");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);
        mainPanel.add(saveButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("Next Card");
        JMenuItem saveMenuItem = new JMenuItem("Save");

        newMenuItem.addActionListener(new NextCardListener());
        saveMenuItem.addActionListener(new SaveButtonListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }

    private void clearCard() {
        questionArea.setText("");
        answerArea.setText("");
        questionArea.requestFocus();
    }

    private class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QuizCard card = new QuizCard(questionArea.getText(), answerArea.getText());
            cardList.add(card);

            clearCard();
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QuizCard card = new QuizCard(questionArea.getText(), answerArea.getText());
            cardList.add(card);

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    private void saveFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(QuizCard q : cardList) {
                writer.write(q.getQuestion() + "/");
                writer.write(q.getAnswer() + "\n");
            }

            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
