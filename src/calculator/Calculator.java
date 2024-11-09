package calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame{
 
    private JTextField tfDisplay; 
    private JTextArea taHistory;
    private JCheckBox ch;
    private JScrollPane js;
    
    private Font f = new Font("arial", Font.BOLD , 20);
    
    JMenuBar menuBar;
    JMenu fileMenu , editMenu;
    JMenuItem setting , about , exit;
    
    private JPanel panelDisplay = new JPanel(new GridLayout(2 , 1) );
    private JPanel panelOperation = new JPanel(new GridLayout(5 , 1));
    private JPanel panelNumBtns = new JPanel(new GridLayout(5 , 3));
    
    private JButton[] btnNumbers = new JButton[15];
    private String[] numberText ={"C","x","P","7","8","9","4","5","6","1","2","3","0",".","+/-"}; 
    
    private JButton[] btnOperations = new JButton[5];
    private String[] operationText = {"/","*","-","+","="};
    
    double firstNumber, secondNumber, result;
    String operation;
    
    public Calculator(){
        setupMenuBar();
        setupMainDisplay();
        setupNumberBtns();
        setupOperationBtns();
        setupMainFrame();
    }
    
    private void setupOperationBtns(){
        for(int i=0 ;i<btnOperations.length-1 ; i++){
            btnOperations[i] = new JButton(operationText[i]);
            btnOperations[i].setBackground(Color.decode("#f0f0f0"));
            btnOperations[i].setFont(new Font("arial", Font.PLAIN , 25));
            btnOperations[i].setForeground(Color.GRAY);
            btnOperations[i].setBorder(BorderFactory.createLineBorder(Color.decode("#e6e6e6")));
            panelOperation.add(btnOperations[i]);
            
            int index = i;
            
            btnOperations[i].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        operation="";
                        operation=btnOperations[index].getText();
                        firstNumber=Double.parseDouble(tfDisplay.getText());
                        
                        taHistory.append(firstNumber + " " + operation);
                        
                         
                    }
            });
        }
        
        JButton equalBtn = btnNumbers[btnOperations.length-1];
        equalBtn = new JButton("=");
        equalBtn.setBackground(Color.decode("#1c577c"));
        equalBtn.setForeground(Color.WHITE);
        equalBtn.setFont(new Font("arial" ,Font.PLAIN ,25));
        panelOperation.add(equalBtn);
        
        equalBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    secondNumber = Double.parseDouble(tfDisplay.getText());
                    
                    switch(operation){
                    case"+":
                        result = firstNumber + secondNumber;
                        break;
                    case"-":
                        result = firstNumber - secondNumber;
                        break;
                    case"*":
                        result = firstNumber * secondNumber;
                        break;
                    case"/":
                        result = firstNumber / secondNumber;
                        break;   
                    }
                    tfDisplay.setText(String.valueOf(result));
                    taHistory.append(secondNumber + " = " + result + '\n');
                }
        });
        
    }
    private void setupNumberBtns(){
        for(int i=0 ; i<btnNumbers.length ; i++){
            btnNumbers[i] = new JButton(numberText[i]);
            btnNumbers[i].setBackground(Color.decode("#f0f0f0"));
            btnNumbers[i].setBackground(Color.WHITE);
            btnNumbers[i].setBorder(BorderFactory.createLineBorder(Color.decode("#e6e6e6")));
            btnNumbers[i].setFont(f);
            panelNumBtns.add(btnNumbers[i]);
        }
        for(int i=0 ;i<3 ;i++){
            btnNumbers[i].setBackground(Color.decode("#f0f0f0"));
            btnNumbers[i].setFont (new Font("arial" , Font.PLAIN , 25));
            btnNumbers[i].setForeground(Color.GRAY);
        }
        for(int i=3 ; i<btnNumbers.length-1 ; i++){
            int index = i;
            btnNumbers[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    double d = Double.parseDouble(tfDisplay.getText());
                    
                    if(d==0 || d==firstNumber || d==result){
                        tfDisplay.setText("");
                    }
                    
                    tfDisplay.setText(tfDisplay.getText() + btnNumbers[index].getText());
                }
            });
        }
        btnNumbers[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!tfDisplay.equals("0")){
                    tfDisplay.setText("0");
                }
            }
        });
        
        btnNumbers[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = tfDisplay.getText();
                
                if(s.length()== 1){
                    tfDisplay.setText("0");
                }
                 else {
                    s = s.substring(0,s.length()-1);
                    tfDisplay.setText(s);
                }
                
            }
        });
        
        btnNumbers[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double d = Double.parseDouble(tfDisplay.getText());
                tfDisplay.setText(Math.pow(d,2) + "");
            }
        });
        
        btnNumbers[btnNumbers.length-1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double d = Double.parseDouble(tfDisplay.getText());
                d *= -1;
                
                tfDisplay.setText(d +"");
            }
        });
        
    } 
    private void setupMenuBar(){
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        ch = new JCheckBox("Show History");
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        setting = new JMenuItem("Setting");
        about = new JMenuItem("About");
        exit = new JMenuItem("Exit");
        
        editMenu.add(ch);
        fileMenu.add(about);
        fileMenu.add(exit);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
        panelDisplay.setBackground(Color.decode("#e6e6e6"));
        menuBar.setBackground(Color.decode("#e6e6e6"));
        menuBar.setBorder(BorderFactory.createLineBorder(Color.decode("#e6e6e6")));
        
        taHistory = new JTextArea(3 , 3);
        js = new JScrollPane(taHistory);
        panelDisplay.add(js);
        taHistory.setEditable(false);
        taHistory.setBackground(Color.decode("#e6e6e6"));
        js.setBorder(BorderFactory.createLineBorder(Color.decode("#e6e6e6")));
        js.setVisible(false);
        ch.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(ch.isSelected())
                    js.setVisible(true);
                else
                    js.setVisible(false);
            }
        });
        
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
    });
        
        about.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AboutFrame();
            }
        });
    }
    private void setupMainDisplay(){
       tfDisplay = new JTextField("0" , 9);
       tfDisplay.setHorizontalAlignment(JTextField.RIGHT);
       tfDisplay.setBackground(Color.decode("#e6e6e6"));
       tfDisplay.setBorder(BorderFactory.createLineBorder(Color.decode("#e6e6e6")));
       tfDisplay.setEditable(false);
       tfDisplay.setFont (new Font("arial" , Font.BOLD , 30));
       panelDisplay.add(tfDisplay);
    }
    private void setupMainFrame(){
        setLayout(new BorderLayout());
        setTitle("Calculator");
        add(panelDisplay , BorderLayout.NORTH);
        add(panelOperation, BorderLayout.EAST);
        add(panelNumBtns , BorderLayout.CENTER);
        setSize(300 , 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    class AboutFrame extends JFrame{
        
        AboutFrame(){
            
            JPanel panelPic = new JPanel();
            ImageIcon image = new ImageIcon(getClass().getResource("/Calculator/Jawad.jpg"));
            panelPic.add( new JLabel(image));
            JLabel j = new JLabel("Developer: Jawad Hakimi");
            setLayout(new BorderLayout());
            add(j,BorderLayout.NORTH);
            add(panelPic , BorderLayout.CENTER);
      
            setSize(600,800);
            setTitle("About Developer");
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
    
    public static void main(String[] args) {
       new Calculator();
    }
    
}
