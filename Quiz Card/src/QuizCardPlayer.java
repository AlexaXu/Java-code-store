import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Created by Better on 2017/7/17.
 */
public class QuizCardPlayer {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea questionArea;
    private JTextArea answerArea;
    private JLabel qLabel;
    private JLabel aLabel;
    private JButton loadButton;
    private JButton nextButton;
    private JButton previousButton;
    private ArrayList<QuizCard> cardList;
    private int curCardId = 0;

    public static void main(String [] args) {
        QuizCardPlayer cardPlayer = new QuizCardPlayer();

        cardPlayer.go();
    }

    public void go() {
        frame = new JFrame("Quiz Card");
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

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load card set");
        loadMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);

        loadButton = new JButton("Load card set");
        loadButton.addActionListener(new OpenMenuListener());
        nextButton = new JButton("Show Answer");
        nextButton.addActionListener(new NextCardListener());
        previousButton = new JButton("Previous Card");
        previousButton.addActionListener(new PreviousCardListener());

        qLabel = new JLabel("Question " + (curCardId + 1));
        aLabel = new JLabel("Answer");

        mainPanel.add(loadButton);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);

    }

    public class OpenMenuListener implements ActionListener {
        public void actionPerformed (ActionEvent e){
            JFileChooser fileLoad = new JFileChooser();
            fileLoad.showOpenDialog(frame);
            loadFile(fileLoad.getSelectedFile());

            questionArea.setText(cardList.get(curCardId).getQuestion());

            mainPanel.removeAll();
            mainPanel.add(qLabel);
            mainPanel.add(questionArea);
            mainPanel.add(aLabel);
            mainPanel.add(answerArea);
            mainPanel.add(nextButton);
        }
    }

    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if(nextButton.getText() == "Show Answer") {
                answerArea.setText(cardList.get(curCardId).getAnswer());


                nextButton.setText("Next Card");
                mainPanel.add(previousButton);
            }
            else {
                if (curCardId < cardList.size()) {
                    curCardId++;

                    qLabel.setText("Question " + (curCardId + 1));
                    questionArea.setText(cardList.get(curCardId).getQuestion());
                    answerArea.setText("");
                    nextButton.setText("Show Answer");
                }
                else {
                    questionArea.setText("No more card");
                    answerArea.setText("");
                }
            }
        }
    }

    public class PreviousCardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (curCardId > 0) {
                curCardId--;

                qLabel.setText("Question " + (curCardId + 1));
                questionArea.setText(cardList.get(curCardId).getQuestion());
                answerArea.setText(cardList.get(curCardId).getAnswer());
            }
            else {
            }
        }
    }

    private void loadFile(File file){
        cardList = new ArrayList<QuizCard>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = null;

            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }

            reader.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void makeCard(String line) {
        String[] splitedLine = line.split("/");

        QuizCard card = new QuizCard(splitedLine[0], splitedLine[1]);
        cardList.add(card);
    }
}

