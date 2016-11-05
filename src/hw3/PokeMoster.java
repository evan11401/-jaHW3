/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author 蔡承延
 */
public class PokeMoster extends JFrame {

    private BufferedImage mosterImg;
    private JPanel ImgPanel = new JPanel();
    private JLabel picLabel;
    private BtuPanel ButtonList = new BtuPanel() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == this.CandyBtn) {//增加糖果的按鍵
                if (NowStatus.getCandy() < 100) {
                    NowStatus.setCandy(NowStatus.getCandy() + 1);
                }
                CheckEvolved(NowStatus);
                CheckImg(NowStatus);
            }
            if (e.getSource() == this.SaveBtn) {//儲存按鍵
                try {
                    FileOutputStream fos = new FileOutputStream(save.getSelectedFile().getAbsolutePath());
                    try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(NowStatus);
                        oos.flush();
                    }
                    JOptionPane.showMessageDialog(null, "Save successed!", "Noticed", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
            if (e.getSource() == this.OpenBtn) {//開啟舊檔
                save.setFileSelectionMode(JFileChooser.FILES_ONLY);//用JFileChooser來選擇欲存取的位置
                int result = save.showDialog(SaveAsBtn, "確認");
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        PokeSerializable openFile;
                        FileInputStream fis = new FileInputStream(save.getSelectedFile().getAbsolutePath());
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        openFile = (PokeSerializable) ois.readObject();
                        ois.close();
                        NowStatus.setCandy(openFile.getCandy());
                        if ("small".equals(openFile.getMonster())) {
                            NowStatus.setMonster("small");
                        } else if ("mid".equals(openFile.getMonster())) {
                            NowStatus.setMonster("mid");
                        } else if ("large".equals(openFile.getMonster())) {
                            NowStatus.setMonster("large");
                        }
                        NowStatus.setNickname(openFile.getNickname());
                        CheckImg(NowStatus);
                        setCandyText(NowStatus.getCandy(), NowStatus.getMonster());
                        SaveBtn.setEnabled(true);//將儲存按鈕啟用 預設為關閉
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }                    
                    setFilePathText(save.getSelectedFile().getAbsolutePath());
                    if (!"".equals(NowStatus.getNickname())) {//有小名則顯示 沒有則印出初始字樣
                        ButtonList.textField.setText(NowStatus.getNickname());
                    } else {
                        ButtonList.textField.setText("Set Your Nickname!(輸入完畢按Enter)");
                    }

                }

            }
            if (e.getSource() == this.SaveAsBtn) {//儲存按鈕
                save.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = save.showDialog(SaveAsBtn, "確認");
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fos = new FileOutputStream(save.getSelectedFile().getAbsolutePath());
                        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                            oos.writeObject(NowStatus);
                            oos.flush();
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    setFilePathText(save.getSelectedFile().getAbsolutePath());
                }
            }
        }
    };
    private String[] ImgPath = new String[3];
    private int[] CandyCount = new int[3];
    private int j = 0;
    private PokeSerializable NowStatus = new PokeSerializable("", "small", 0);

    private JFileChooser save = new JFileChooser();

    public PokeMoster() {
        this.setTitle("Pokemon");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(true);

        ButtonList.textField.addActionListener(new TextListener());
        ImgPanel.setBackground(Color.WHITE);
        add(ImgPanel, BorderLayout.CENTER);
        add(ButtonList, BorderLayout.SOUTH);

        InputStream i = getClass().getResourceAsStream("pokemon.txt");//讀取圖鑑檔pokemon.txt
        try (InputStreamReader fr = new InputStreamReader(i) {//以inputStream來讀取一行一行的資料
        }) {
            BufferedReader reader = new BufferedReader(fr);
            while (reader.ready()) {
                String[] line = reader.readLine().split(" ");//以中間的空格切割 並存入陣列中
                ImgPath[j] = line[0];
                if (!"full".equals(line[1])) {
                    CandyCount[j] = Integer.parseInt(line[1]);
                    j++;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        try {
            mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[0]));//以剛剛讀取到的字串來讀取圖片
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        picLabel = new JLabel(new ImageIcon(mosterImg));
        ImgPanel.add(picLabel);
    }

    public void CheckImg(PokeSerializable p) {//用monster的狀態small,mid,large來決定要用的圖片檔
        if (p.getMonster() == "small") {
            ImgPanel.remove(picLabel);
            try {
                mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[0]));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            picLabel = new JLabel(new ImageIcon(mosterImg));
            ImgPanel.add(picLabel);
        } else if (p.getMonster() == "mid") {
            ImgPanel.remove(picLabel);
            try {
                mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[1]));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            picLabel = new JLabel(new ImageIcon(mosterImg));
            ImgPanel.add(picLabel);
        } else if (p.getMonster() == "large") {
            ImgPanel.remove(picLabel);
            try {
                mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[2]));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            picLabel = new JLabel(new ImageIcon(mosterImg));
            ImgPanel.add(picLabel);
        }

    }

    public void CheckEvolved(PokeSerializable p) {//在特殊狀況下 會進化 
        if (p.getCandy() == 25 && "small".equals(p.getMonster())) {
            JOptionPane.showMessageDialog(null, "Your monster is evolved!", "Noticed", JOptionPane.PLAIN_MESSAGE);
            p.setCandy(0);
            p.setMonster("mid");

        } else if (p.getCandy() == 100 && "mid".equals(p.getMonster())) {
            JOptionPane.showMessageDialog(null, "Your monster is evolved!", "Noticed", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(null, "Congratuation!! Your monster has final evolved", "Noticed", JOptionPane.PLAIN_MESSAGE);
            p.setMonster("large");
        }
        ButtonList.setCandyText(p.getCandy(), p.getMonster());
    }

    public class TextListener implements ActionListener {//設定小名 須按enter輸入

        @Override
        public void actionPerformed(ActionEvent e) {
            NowStatus.setNickname(ButtonList.textField.getText());
            JOptionPane.showMessageDialog(null, "Set nickname successed!", "Noticed", JOptionPane.PLAIN_MESSAGE);
        }
    }

}
