/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;



/**
 *
 * @author 蔡承延
 */
public class BtuPanel extends JPanel implements ActionListener{
    protected JTextField textField;
    private JLabel CandyCount;
    private JLabel FilePathText;
    private JPanel CandyBtnList = new JPanel();
    private JPanel SaveBtnList = new JPanel();
    private static String input = "";
    private final static String SmallCandy = "/25";
    private final static String MidCandy = "/100";
    private static String FilePath = "New File";

    
    protected final JButton CandyBtn;
    protected final JButton OpenBtn;
    protected final JButton SaveBtn;
    protected final JButton SaveAsBtn;
        
    public BtuPanel(){
        super(new GridLayout(4,0));
        
        textField = new JTextField();        
        textField.setText("Set Your Nickname!(輸入完畢按Enter)");       
        add(textField);
        
        CandyBtn = new JButton("Give Candy!");
        CandyBtn.addActionListener(this);
        CandyCount = new JLabel("0"+SmallCandy);
        CandyBtnList.add(CandyBtn);
        CandyBtnList.add(CandyCount);
        add(CandyBtnList);
        
        OpenBtn = new JButton("Open Game");
        OpenBtn.addActionListener(this);
        SaveBtn = new JButton("Save");
        SaveBtn.addActionListener(this);
        SaveAsBtn = new JButton("Save As");
        SaveAsBtn.addActionListener(this);
        SaveBtnList.add(OpenBtn);
        SaveBtn.setEnabled(false);
        SaveBtnList.add(SaveBtn);
        SaveBtnList.add(SaveAsBtn);
        add(SaveBtnList);
        
        FilePathText = new JLabel(FilePath);
        add(FilePathText);
        
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public void setCandyText(int count,String status){        
        if(status=="small"){
            CandyCount.setText(count+SmallCandy);
        }else if(status=="mid"){
            CandyCount.setText(count+MidCandy);            
        }else if(status=="large"){
            CandyCount.setText("100/100");
        }        
    }
    
    public void setFilePathText(String Path){        
            FilePathText.setText("File Load From:"+Path);
    }
    
    
    
}
