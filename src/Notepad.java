import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Notepad {
	
	JFrame f;
	JTextArea ta;
	int valueFont = 20;
	File currentFile;
	
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	public Notepad() {
		f = new JFrame("Untitled-Notepad");
		ta = new JTextArea(800,600);
		//Font font = new Font("Serif", Font.PLAIN, 20);
		//ta.setFont(font);
		ta.setFont(new Font("Serif", Font.PLAIN, 20));
		addingMenuBar();
		f.add(new JScrollPane(ta));
		//f.add(new JScrollPane(ta), BorderLayout.CENTER);
		f.setLocation(50, 50);
		f.setSize(800, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addingMenuBar(){
		
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu format = new JMenu("Format");
		JMenu help = new JMenu("Help");
		addingSubMenusForFile(file);
		addingSubMenusForFormat(format);
		addingSubMenusForEdit(edit);
		addingSubMenusForHelp(help);
		mb.add(file);  mb.add(edit); mb.add(format); mb.add(help); 
		f.setJMenuBar(mb);
	}
	
	private void addingSubMenusForHelp(JMenu help) {
		// TODO Auto-generated method stub
		JMenuItem helpSub = new JMenuItem("Help");
		help.add(helpSub);
		
		helpSub.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JDialog d = new JDialog(f, "Help", true);
				d.setBounds(200, 200, 500, 500);
				JLabel label = new JLabel("Write your files and save them");
				label.setBounds(150,100,200,100);
				JButton b = new JButton("OK");
				b.setBounds(150, 200, 100 , 50);
				d.add(b);
				d.add(label);
				b.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						d.dispose();
					}
				});
				d.setLayout(null);
				d.setVisible(true);
			}
		});
		
	}

	private void addingSubMenusForFile(JMenu file) {
		// TODO Auto-generated method stub
		JMenuItem new_file = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save as");
		file.add(new_file); file.addSeparator(); file.add(open); file.addSeparator(); file.add(saveAs);
		file.addSeparator(); file.add(save);
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveFile();
			}
		});
		saveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveAsFile();
			}
		});
		new_file.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(ta.getText().equals("")){
					ta.setText("");
					f.setTitle("Untitled-Notepad");
				}else{
					JDialog d = new JDialog(f, "Do you want to save the existing file?", true);
					d.setBounds(100,100,500,200);
					JButton yes = new JButton("Yes");
					JButton no = new JButton("No");
					yes.setBounds(150,103,80,50);
					no.setBounds(250,103,80,50);
					d.add(yes); d.add(no);
					yes.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							saveFile();
							f.setTitle("Untitled-Notepad");
							ta.setText("");
							d.dispose();
						}
					});
					d.setLayout(null);
					d.setVisible(true);
				}
			}
		});
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openFile();
			}
		});
	}

	protected void openFile(){
		JFileChooser fileChooser = new JFileChooser();
		File dir = new File("D:/");
		fileChooser.setCurrentDirectory(dir);
		int actionDialog = fileChooser.showOpenDialog(f);
		if(actionDialog == JFileChooser.APPROVE_OPTION){
			File temp = fileChooser.getSelectedFile();
			setFileLocation(temp);
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(temp)));
				
				StringTokenizer st = new StringTokenizer(temp+"", "\\");
                String name = "";
                while(st.hasMoreTokens()){
                	name = st.nextToken();
                	
                }
                f.setTitle(name);
                
				String str=in.readLine();
				while(str!=null){
					ta.append(str+"\n");
					str = in.readLine();
				}
				in.close();
			}catch(Exception e){
				
			}
		}
	}
	
	protected void saveFile(){
		if(f.getTitle().toString().equals("Untitled-Notepad")){
			saveAsFile();
		}else{
			File loc = getFileLocation();
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(loc));
				out.write(ta.getText());
                out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setFileLocation(File temp) {
		// TODO Auto-generated method stub
		currentFile = temp;
		System.out.println(currentFile+"");
	}
	
	private File getFileLocation(){
		return currentFile;
	}

	protected void saveAsFile() {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
		File dir = new File("D:/");
		fileChooser.setCurrentDirectory(dir);
		int actionDialog = fileChooser.showSaveDialog(f);
		if(actionDialog == JFileChooser.APPROVE_OPTION){
	            File fileName = new File(fileChooser.getSelectedFile( ) + "" );
	            if(fileName.exists())
	            {
	                actionDialog = JOptionPane.showConfirmDialog(f,
	                                   "Replace existing file?");
	                if (actionDialog == JOptionPane.NO_OPTION)
	                    return;
	            }
	            try
	            {
	                BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
	                StringTokenizer st = new StringTokenizer(fileName+"", "\\");
	                String name = "";
	                while(st.hasMoreTokens()){
	                	name = st.nextToken();
	                	
	                }
	                	f.setTitle(name);
	                    out.write(ta.getText());
	                    out.close();
	            }
	            catch(Exception e)
	            {
	                 System.err.println("Error: " + e.getMessage());
	            }
		}
	}

	private void addingSubMenusForEdit(JMenu edit) {
		// TODO Auto-generated method stub
		JMenuItem m1 = new JMenuItem("Cut");
		JMenuItem m2 = new JMenuItem("Copy");
		JMenuItem m3 = new JMenuItem("Paste");
		JMenuItem m4 = new JMenuItem("Select All");
		JMenuItem m5 = new JMenuItem("Date");
		edit.add(m1); edit.addSeparator(); edit.add(m2); 
		edit.addSeparator(); edit.add(m3); edit.addSeparator(); 
		edit.add(m4); edit.addSeparator(); edit.add(m5);
		m1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.cut();
			}
		});
		m2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.copy();
			}
		});
		m3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.paste();
			}
		});
		m4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.selectAll();
			}
		});
		m5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getDateAndTime();
			}
		});
	}

	private void addingSubMenusForFormat(JMenu format) {
		// TODO Auto-generated method stub
		JMenuItem fontSize = new JMenuItem("Set Font Size");
		format.add(fontSize);
				
		fontSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JDialog d = new JDialog(f, "Set Font Size", true);
				SpinnerNumberModel sm = new SpinnerNumberModel(20, 10, 80, 2);
				JSpinner s = new JSpinner(sm);
				s.setBounds(150,100, 100, 50);
				d.setBounds(200, 200, 500, 500);
				JButton b = new JButton("Set");
				b.setBounds(150, 200, 100 , 50);
				d.add(s);
				d.add(b);
				s.addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						valueFont = (int) ((JSpinner)e.getSource()).getValue();
					}
					
				});
				b.addActionListener(new ActionListener() {		
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						d.setVisible(false);
						ta.setFont(new Font("Serif", Font.PLAIN, valueFont));
					}
				});
				//d.setSize(500, 500);
				d.setLayout(null);
				d.setVisible(true);
			}
		});
	}

	public void getDateAndTime(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		String date = day+"/"+month+"/"+year;
		Date get_time = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		String time = formatter.format(get_time);
		ta.append(": "+time+" "+date);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Notepad();
		
	}

}
