 Package ChatSwingApp; import javax.swing.; import java.awt.; import
 java.awt.event.ActionEvent; import java.awt.event.ActionListener; import
 java.util.LinkedList; import java.util.Queue;
 public class ChatSwingApp extends JFrame implements
 ActionListener,Runnable{ private JTextAreachatArea;
 privateJTextFieldperson1InputField,person2InputField;
 privateJButtonperson1SendButton,person2SendButton; private Chat person1Chat,
 person2Chat; public ChatSwingApp(){
 super("Chat Application "); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setSize(400, 300); person1Chat = new Chat(); person2Chat = new Chat();
 chatArea=newJTextArea(); chatArea.setEditable(false);
 chatArea.setBackground(newColor(240,240,240));//Lightgraybackground
 chatArea.setForeground(Color.BLACK); // Black text colorperson1InputField
 = new JTextField(20); person1InputField.addActionListener(this); person2InputField
 = new JTextField(20); person2InputField.addActionListener(this);
 person1SendButton=newJButton("Person1Send");
 person1SendButton.addActionListener(this);
 person2SendButton=newJButton("Person2Send");
 person2SendButton.addActionListener(this);
 JPanelinputPanel=new JPanel(newGridLayout(2,3));
 inputPanel.setBackground(newColor(200,200,200));//Lightgraybackground
 inputPanel.add(new JLabel("Person 1:")); inputPanel.add(person1InputField);
 inputPanel.add(person1SendButton); inputPanel.add(new JLabel("Person 2:"));
 inputPanel.add(person2InputField); inputPanel.add(person2SendButton);
 getContentPane().setLayout(newBorderLayout());
 getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);
 getContentPane().add(inputPanel, BorderLayout.SOUTH);
 getContentPane().setBackground(new Color(220, 220, 220)); // Light gray
 background for the entire frame newThread(this).start();
 }
 @Override
 publicvoidactionPerformed(ActionEvente){ String message;
 6
if(e.getSource()==person1InputField |e.getSource()==person1SendButton){ message
 = person1InputField.getText().trim(); if (!message.isEmpty()) {
 person1Chat.putMessage("Person1:"+message); person1InputField.setText("");
 }
 }elseif(e.getSource()==person2InputField |e.getSource()==person2SendButton){ mes
 sage
 = person2InputField.getText().trim(); if (!message.isEmpty()) {
 person2Chat.putMessage("Person2:"+message); person2InputField.setText("");
 }
 }
 }
 @Override publicvoidrun(){ while
 (true) {
 Stringmessage1=person1Chat.getMessage(); appendToChatArea(message1);
 Stringmessage2=person2Chat.getMessage(); appendToChatArea(message2);
 }
 }
 private void appendToChatArea(String message) {
 SwingUtilities.invokeLater(()->chatArea.append(message+"\n"));
 }
 publicstatic voidmain(String[]args){
 SwingUtilities.invokeLater(()->newChatSwingApp().setVisible(true));
 } } classChat {
 privateQueue<String>messages=newLinkedList<>();
 publicsynchronizedvoidputMessage(Stringmessage){ messages.offer(message);
 notify();// Notify theUIthread thatamessageis available
 }
 publicsynchronizedStringgetMessage(){ while
 (messages.isEmpty()) { try {
 wait();//Waituntilamessageisavailable } catch (InterruptedException e) {
 Thread.currentThread().interrupt();
 } }
 returnmessages.poll();
 }
 