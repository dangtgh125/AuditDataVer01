import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Label;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	public static int _numFilesOfBlock;
	private double confidenVal = 0.1;
	private String[] confidentValArr = {"80%", "90%", "95%", "98%", "99%"};
	private JPanel contentPanel;
	private JTextField _txtfieldInput, _txtfieldOutput, _txtFilesBlock;
	private JButton _btnOpenInput, _btnOpenOutput, _btnEncrypt, _btnDecrypt, _btnCancel;
	private JButton _btnRun, _btn_Browse_Key_ProbVector, _btnComputeConfidentInterval, _btnVerify, _btn_Browse_Mac;
	private JPasswordField _pwKeyEncrypt;
	private JPasswordField _pwKeyDecrypt;
	public static JTextArea _txtAreaEncrypt, _txtAreaDecrypt, _txtMACArea;
	private JScrollPane _scrpAreaEncrypt, _scrpAreaDecrype, _scrpAreaMac;
	private JTextField _txtField_ProbVector_Location;
	private JTextField _txtFiled_BlockID;
	private JTextField _txtField_BlockSize;
	private JTextArea _txtStartPoint, _txtEndPoint;
	private JComboBox<String> _combobox_confidentValue;
	private JTextField _txtField_Max_Location;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
		catchEvent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Audit Tool");
		setBounds(100, 100, 500, 522);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel _lbInput = new JLabel("Folder Input");
		_lbInput.setBounds(20, 24, 71, 14);
		contentPanel.add(_lbInput);
		
		JLabel _lbOutput = new JLabel("Folder Output");
		_lbOutput.setBounds(20, 55, 80, 14);
		contentPanel.add(_lbOutput);
		
		_txtfieldInput = new JTextField();
		_txtfieldInput.setBounds(100, 21, 296, 20);
		_txtfieldInput.setColumns(10);
		_txtfieldInput.setEditable(false);
		contentPanel.add(_txtfieldInput);
		
		_txtfieldOutput = new JTextField();
		_txtfieldOutput.setBounds(100, 52, 296, 20);
		_txtfieldOutput.setColumns(10);
		_txtfieldOutput.setEditable(false);
		contentPanel.add(_txtfieldOutput);
		
		_btnOpenInput = new JButton("Browse");
		_btnOpenInput.setBounds(406, 20, 26, 23);
		contentPanel.add(_btnOpenInput);
		
		_btnOpenOutput = new JButton("Browse");
		_btnOpenOutput.setBounds(406, 51, 26, 23);
		contentPanel.add(_btnOpenOutput);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 103, 474, 308);
		contentPanel.add(tabbedPane);
		JPanel panelEncrypt = new JPanel();
		JPanel panelDecrypt = new JPanel();
		JPanel panelHmac = new JPanel();
		JPanel panelVerify = new JPanel();
		panelEncrypt.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEncrypt.setLayout(null);
		panelDecrypt.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDecrypt.setLayout(null);
		panelHmac.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelHmac.setLayout(null);
		panelVerify.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelVerify.setLayout(null);
		
		tabbedPane.addTab("Encrypt", null, panelEncrypt, "click to show panel 1");
		_txtAreaEncrypt = new JTextArea();
		//_txtAreaEncrypt.setEditable(false);
		_scrpAreaEncrypt = new JScrollPane(_txtAreaEncrypt);
		_scrpAreaEncrypt.setBounds(10, 11, 449, 206);
		panelEncrypt.add(_scrpAreaEncrypt);
		
		_btnEncrypt = new JButton("Encrypt");
		_btnEncrypt.setBounds(364, 228, 95, 33);
		panelEncrypt.add(_btnEncrypt);
		
		JLabel lblKey = new JLabel("Key");
		lblKey.setBounds(10, 228, 27, 14);
		panelEncrypt.add(lblKey);
		
		_pwKeyEncrypt = new JPasswordField();
		_pwKeyEncrypt.setBounds(37, 228, 102, 20);
		panelEncrypt.add(_pwKeyEncrypt);
		_pwKeyEncrypt.setColumns(10);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(442, 11, 17, 206);
		panelEncrypt.add(scrollBar);
		
		tabbedPane.addTab("Decrypt", null, panelDecrypt, "click to show panel 2");
	    _txtAreaDecrypt = new JTextArea();
	    _txtAreaDecrypt.setEditable(false);
	    _scrpAreaDecrype = new JScrollPane(_txtAreaDecrypt);
	    _scrpAreaDecrype.setBounds(10, 11, 449, 209);
	    panelDecrypt.add(_scrpAreaDecrype);
	    
	    _btnDecrypt = new JButton("Decrypt");
	    _btnDecrypt.setBounds(364, 231, 95, 33);
	    panelDecrypt.add(_btnDecrypt);
	    
	    JLabel label = new JLabel("Key");
	    label.setBounds(10, 231, 27, 14);
	    panelDecrypt.add(label);
	    
	    _pwKeyDecrypt = new JPasswordField();
	    _pwKeyDecrypt.setColumns(10);
	    _pwKeyDecrypt.setBounds(37, 231, 102, 20);
	    panelDecrypt.add(_pwKeyDecrypt);
	    
	    
	    tabbedPane.addTab("Hmac", null, panelHmac, "click to show panel 3");
	    
	    Label lblMACList = new Label("MAC list");
	    lblMACList.setBounds(50, 47, 56, 16);
	    panelHmac.add(lblMACList);
	    
	    _txtMACArea = new JTextArea();
	    _txtMACArea.setEditable(false);
	    _scrpAreaMac = new JScrollPane(_txtMACArea);
	    _scrpAreaMac.setBounds(112, 47, 345, 178);
	    panelHmac.add(_scrpAreaMac);
	    
	    _btnRun = new JButton("Run");
	    _btnRun.setBounds(360, 238, 97, 25);
	    panelHmac.add(_btnRun);
	    //
	    tabbedPane.addTab("Verify", null, panelVerify, "click to show panel 3");
	    
	    _btn_Browse_Key_ProbVector = new JButton("Browse");
	    _btn_Browse_Key_ProbVector.setBounds(429, 13, 28, 25);
	    panelVerify.add(_btn_Browse_Key_ProbVector);
	    
	    _btnComputeConfidentInterval = new JButton("Compute Confident Interval");
	    _btnComputeConfidentInterval.setBounds(268, 240, 189, 25);
	    panelVerify.add(_btnComputeConfidentInterval);
	    
	    _txtField_ProbVector_Location = new JTextField();
	    _txtField_ProbVector_Location.setEditable(false);
	    _txtField_ProbVector_Location.setBounds(135, 14, 282, 22);
	    panelVerify.add(_txtField_ProbVector_Location);
	    _txtField_ProbVector_Location.setColumns(10);
	    
	    JLabel lblProbvectorLocation = new JLabel("ProbVector Location");
	    lblProbvectorLocation.setBounds(10, 17, 122, 16);
	    panelVerify.add(lblProbvectorLocation);
	    
	    JLabel lblEncPoint = new JLabel("End Point");
	    lblEncPoint.setBounds(63, 205, 60, 16);
	    panelVerify.add(lblEncPoint);
	    
	    JLabel lblStartPoint = new JLabel("Start Point");
	    lblStartPoint.setBounds(63, 167, 60, 16);
	    panelVerify.add(lblStartPoint);
	    
	    _txtStartPoint = new JTextArea();
	    _txtStartPoint.setBounds(135, 164, 116, 22);
	    panelVerify.add(_txtStartPoint);
	    
	    _txtEndPoint = new JTextArea();
	    _txtEndPoint.setBounds(135, 202, 116, 25);
	    panelVerify.add(_txtEndPoint);
	    
	    JLabel _lblBlockID = new JLabel("BlockID");
	    _lblBlockID.setBounds(135, 102, 60, 16);
	    panelVerify.add(_lblBlockID);
	    
	    JLabel lblBlocksize = new JLabel("BlockSize");
	    lblBlocksize.setBounds(299, 102, 60, 16);
	    panelVerify.add(lblBlocksize);
	    
	    _txtFiled_BlockID = new JTextField();
	    _txtFiled_BlockID.setBounds(135, 131, 116, 22);
	    panelVerify.add(_txtFiled_BlockID);
	    _txtFiled_BlockID.setColumns(10);
	    
	    _txtField_BlockSize = new JTextField();
	    _txtField_BlockSize.setColumns(10);
	    _txtField_BlockSize.setBounds(301, 131, 116, 22);
	    panelVerify.add(_txtField_BlockSize);
	    
	    _combobox_confidentValue = new JComboBox(confidentValArr);
	    _combobox_confidentValue.setBounds(135, 241, 116, 22);
	    panelVerify.add(_combobox_confidentValue);

	    
	    JLabel lblConfidentValue = new JLabel("Confident Value");
	    lblConfidentValue.setBounds(23, 244, 100, 16);
	    panelVerify.add(lblConfidentValue);
	    
	    JLabel lblMacLocation = new JLabel("Mac Location");
	    lblMacLocation.setBounds(10, 53, 122, 16);
	    panelVerify.add(lblMacLocation);
	    
	    _txtField_Max_Location = new JTextField();
	    _txtField_Max_Location.setEditable(false);
	    _txtField_Max_Location.setColumns(10);
	    _txtField_Max_Location.setBounds(135, 50, 282, 22);
	    panelVerify.add(_txtField_Max_Location);
	    
	    _btn_Browse_Mac = new JButton("Browse");
	    _btn_Browse_Mac.setBounds(429, 49, 28, 25);
	    panelVerify.add(_btn_Browse_Mac);
	    
	    _btnVerify = new JButton("Verify");
	    _btnVerify.setBounds(368, 189, 89, 40);
	    panelVerify.add(_btnVerify);
	    
	    _txtFilesBlock = new JTextField();
	    _txtFilesBlock.setBounds(434, 92, 50, 20);
	    _txtFilesBlock.setColumns(10);
	    contentPanel.add(_txtFilesBlock);
	    
	    JLabel lblAllFile = new JLabel("Files/Block");
	    lblAllFile.setBounds(361, 95, 71, 14);
	    contentPanel.add(lblAllFile);
	    
	    _btnCancel = new JButton("Cancel");
	    _btnCancel.setBounds(370, 434, 95, 33);
	    contentPanel.add(_btnCancel);
	}
	
	private void catchEvent() {
		
		_pwKeyEncrypt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (_pwKeyEncrypt.getPassword().length >= 16) {
					getToolkit().beep();
		            e.consume();
				}
			}
		});
		
		_pwKeyDecrypt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (_pwKeyDecrypt.getPassword().length >= 16) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		_btnOpenInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == _btnOpenInput) {
			        int returnVal = fc.showOpenDialog(MainGUI.this);
			        
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            MngrFiles.folderInput = fc.getSelectedFile().toString() + "\\";
			            _txtfieldInput.setText(MngrFiles.folderInput);
			        }
			   }
			}
		});
		
		_btnOpenOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == _btnOpenOutput) {
			        int returnVal = fc.showOpenDialog(MainGUI.this);
			        
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            MngrFiles.folderOutput = fc.getSelectedFile().toString() + "\\";
			            _txtfieldOutput.setText(MngrFiles.folderOutput);
			        }
			   }
			}
		});
		
		_btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == _btnEncrypt) {
					if (!_txtFilesBlock.getText().equals("")) {
						_numFilesOfBlock = Integer.parseInt(_txtFilesBlock.getText());
						String key = new String(_pwKeyEncrypt.getPassword());
						_txtAreaEncrypt.setText("");;
						if (!key.equals("")) {
							try {
								
								int size = 0;
								int countFile = 1, blockID = 1;
								boolean success = false;
								String filename = "";
								// Nên clear filelist sau mỗi lần thay đổi Input và Output
								if (Source.fileList.size() != 0) {
									Source.fileList.clear();
								}
								Source.mgrFile.getFileList(MngrFiles.folderInput, Source.fileList);
								size = Source.fileList.size();
								for (int i = 0; i < size; i++) {
									filename = Source.fileList.get(i);
									_txtAreaEncrypt.append("Encrypting file " + filename + "...");
									success = Source.Encrypt(filename, key, blockID, countFile);
									if (success) {
										_txtAreaEncrypt.append("Success\n");
									}
									else {
										_txtAreaEncrypt.append("Fail\n");
									}
									countFile++;
									if (countFile > _numFilesOfBlock) {
										countFile = 1;
										blockID++;
									}
								}
								_txtAreaEncrypt.append("Done!!!");
								Source.fileList.clear();
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		_btnDecrypt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (e.getSource() == _btnDecrypt) {
	    			if (!_txtFilesBlock.getText().equals("")) {
						_numFilesOfBlock = Integer.parseInt(_txtFilesBlock.getText());
						String key = new String(_pwKeyDecrypt.getPassword());
						_txtAreaDecrypt.setText("");
						if (!key.equals("")) {
							try {
								
								int size = 0;
								boolean success = false;
								String filename = "";
								// Nên clear filelist sau mỗi lần thay đổi Input và Output
								if (Source.fileList.size() != 0) {
									Source.fileList.clear();
								}
								Source.mgrFile.getFileList(MngrFiles.folderInput, Source.fileList);
								size = Source.fileList.size();
								for (int i = 0; i < size; i++) {
									filename = Source.fileList.get(i);
									_txtAreaDecrypt.append("Decrypting file " + filename + "...");
									success = Source.Decrypt(filename, key);
									if (success) {
										_txtAreaDecrypt.append("Success\n");
									}
									else {
										_txtAreaDecrypt.append("Fail\n");
									}
								}
								_txtAreaDecrypt.append("Done!!!");
							Source.fileList.clear();
							
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
	    		}
	    	}
	    });
	    
	    _btnRun.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (e.getSource() == _btnRun) {
	    			if (!_txtFilesBlock.getText().equals("")) {
	    				_numFilesOfBlock = Integer.parseInt(_txtFilesBlock.getText());
			    		MngrFiles._numBlock = 0;
			    		_txtMACArea.setText("");
			    		Source.mgrFile.getFileList(MngrFiles.folderInput, Source.fileList);
			    		System.out.println("count block:" + MngrFiles._numBlock);
			    		System.out.println(MngrFiles.folderInput);
			    		System.out.println(MngrFiles.folderOutput);
			    		
			    		try {
			    			// numBlock is countBlock, blockSize is numFilesOfBlock
							Source.computeHMACfileList(MngrFiles._numBlock, _numFilesOfBlock, Source.fileList);
							Source.fileList.clear();
						} catch (GeneralSecurityException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		_txtMACArea.append("Done!!!");
	    			}
	    		}
	    	}
	    });
	   
	    final JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
	    chooser.setFileFilter(filter);
	    _btn_Browse_Key_ProbVector.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//choose file from filechooser and assign to
	    		String keyFile = "";
	    		//Vector<Double> ProbVector = new Vector<Double>();
	    		if (e.getSource() == _btn_Browse_Key_ProbVector) {
			        int returnVal = chooser.showOpenDialog(MainGUI.this);
			        
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	keyFile = chooser.getSelectedFile().toString();
			            _txtField_ProbVector_Location.setText(keyFile);
			        }
			   }
	    		
	    	}
	    });
	    
	    _btn_Browse_Mac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keyFile = "";
				if (e.getSource() == _btn_Browse_Mac) {
					int returnVal = chooser.showOpenDialog(MainGUI.this);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						keyFile = chooser.getSelectedFile().toString();
						_txtField_Max_Location.setText(keyFile);
					}
				}
			}
		});
	    
	    _combobox_confidentValue.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String select = (String) _combobox_confidentValue.getSelectedItem();
	    		if(select == "80%"){
	    			confidenVal = 0.1;
	    		}
	    		else if(select == "90%"){
	    			confidenVal = 0.05;
	    		}
	    		else if(select == "95%"){
	    			confidenVal = 0.025;
	    		}
	    		else if(select == "98%"){
	    			confidenVal = 0.01;
	    		}
	    		else if (select == "99%"){
	    			confidenVal = 0.005;
	    		}
	    	}
	    });
	    
	    _btnComputeConfidentInterval.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {

	    		_txtStartPoint.setText("");
	    		_txtEndPoint.setText("");
	    		String keyFile = _txtField_ProbVector_Location.getText();
	    		int tmp1 = 0, tmp2 = 0;
	    		//show chỉ số blockID và số lương xs trong file 
		        try {
					MngrFiles.readKeyFile(keyFile, Source.ProbVector);
					tmp1 = Source.ProbVector.get(0).intValue();
					tmp2 = Source.ProbVector.get(1).intValue();
					_txtFiled_BlockID.setText(Integer.toString(tmp1));
					_txtField_BlockSize.setText(Integer.toString(tmp2));
					Source.ProbVector.removeElementAt(0);
					Source.ProbVector.removeElementAt(0);
					
					Prob_Handle prh = new Prob_Handle(Source.ProbVector);
					double mean = prh.getMean();
					double sd = prh.getSD();
					double a = mean - sd*prh.getZ(confidenVal);
					double b = mean + sd*prh.getZ(confidenVal);
					
					System.out.println("mean = " + mean);
					System.out.println("sd = " + sd);
					System.out.println("a = " + a);
					System.out.println("b = " + b);
					
					_txtStartPoint.setText(Integer.toString((int)a));
					_txtEndPoint.setText(Integer.toString((int)b+1));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "File Not Found!");
					e1.printStackTrace();
				}
		        
		        for (Double i : Source.ProbVector){
		        	System.out.println(i);
		        }
		        Source.ProbVector.clear();
		        
	    	}
	    });
	    
	    _btnVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String macFile = _txtField_Max_Location.getText();
				int startPoint = 0, endPoint = 0;
				//show chỉ số blockID và số lương xs trong file 
				try {
					
					MngrFiles.readMacFile(macFile, Source.MAC);
					_txtFiled_BlockID.setText(Source.MAC.get(0).toString());
					_txtField_BlockSize.setText(Source.MAC.get(1).toString());
					endPoint = Integer.parseInt(_txtField_BlockSize.getText());
					Source.MAC.removeElementAt(0);
					Source.MAC.removeElementAt(0);
					
					if (!_txtStartPoint.getText().equals("") && !_txtEndPoint.getText().equals("")) {
						startPoint = Integer.parseInt(_txtStartPoint.getText());
						endPoint = Integer.parseInt(_txtEndPoint.getText());
					}
					
					if (!Source.Verify(_txtField_ProbVector_Location.getText(), _txtfieldInput.getText(), startPoint, endPoint)) {
						JOptionPane.showMessageDialog(null, "Verify Fail!");
					}
					else
						JOptionPane.showMessageDialog(null, "Verify Success");
					
					Source.MAC.clear();
					
				} catch (FileNotFoundException e1) {
					
					JOptionPane.showMessageDialog(null, "File Not Found!");
					e1.printStackTrace();
					
				} catch (GeneralSecurityException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	    
	    _btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == _btnCancel) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					System.exit(0);
				}
				
			}
		});
	}
	
	public static void infoBox(String infoMessage, String titleBar) throws Exception
    {
		JTextPane jtp = new JTextPane();
		Document doc = jtp.getDocument();
		doc.insertString(doc.getLength(), infoMessage, new SimpleAttributeSet());
	    jtp.setSize(new Dimension(480, 10));
	    jtp.setPreferredSize(new Dimension(480, jtp.getPreferredSize().height));
        JOptionPane.showMessageDialog(null, jtp, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
