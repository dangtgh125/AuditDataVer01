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
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	public static int _numFilesOfBlock;
	private double confidenVal = 0.1;
	private JPanel contentPanel;
	private JTextField _txtfieldInput, _txtfieldOutput, _txtFilesBlock;
	private JButton _btnOpenInput, _btnOpenOutput, _btnEncrypt, _btnDecrypt, _btnCancel;
	private JButton _btnRun, _btn_Browse_Key_ProbVector, _btnCompute, _btnVerify, _btn_Browse_Mac;
	private JRadioButton _rdbtnNumberOfFiles, _rdbtnConfidentValue;
	private JPasswordField _pwKeyEncrypt;
	private JPasswordField _pwKeyDecrypt;
	public static JTextArea _txtAreaEncrypt, _txtAreaDecrypt, _txtMACArea, _txtAreaLogFileVerify;
	private JScrollPane _scrpAreaEncrypt, _scrpAreaDecrype, _scrpAreaMac;
	private JTextField _txtField_ProbVector_Location;
	private JTextField _txtFiled_BlockID;
	private JTextField _txtField_BlockSize;
	private JTextField _txtField_Max_Location;
	private JTextField _txtNumOfFileVerify;
	private JTextField _txtConfidentValue;

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
	private void initialize() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Audit Tool");
		setBounds(100, 100, 499, 598);
		
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
		tabbedPane.setBounds(10, 103, 474, 412);
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
		_scrpAreaEncrypt.setBounds(10, 11, 449, 292);
		panelEncrypt.add(_scrpAreaEncrypt);
		
		_btnEncrypt = new JButton("Encrypt");
		_btnEncrypt.setBounds(364, 328, 95, 33);
		panelEncrypt.add(_btnEncrypt);
		
		JLabel lblKey = new JLabel("Key");
		lblKey.setBounds(10, 328, 27, 14);
		panelEncrypt.add(lblKey);
		
		_pwKeyEncrypt = new JPasswordField();
		_pwKeyEncrypt.setBounds(37, 328, 102, 20);
		panelEncrypt.add(_pwKeyEncrypt);
		_pwKeyEncrypt.setColumns(10);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(442, 11, 17, 206);
		panelEncrypt.add(scrollBar);
		
		tabbedPane.addTab("Decrypt", null, panelDecrypt, "click to show panel 2");
	    _txtAreaDecrypt = new JTextArea();
	    _txtAreaDecrypt.setEditable(false);
	    _scrpAreaDecrype = new JScrollPane(_txtAreaDecrypt);
	    _scrpAreaDecrype.setBounds(10, 11, 449, 294);
	    panelDecrypt.add(_scrpAreaDecrype);
	    
	    _btnDecrypt = new JButton("Decrypt");
	    _btnDecrypt.setBounds(364, 328, 95, 33);
	    panelDecrypt.add(_btnDecrypt);
	    
	    JLabel label = new JLabel("Key");
	    label.setBounds(10, 328, 27, 14);
	    panelDecrypt.add(label);
	    
	    _pwKeyDecrypt = new JPasswordField();
	    _pwKeyDecrypt.setColumns(10);
	    _pwKeyDecrypt.setBounds(37, 328, 102, 20);
	    panelDecrypt.add(_pwKeyDecrypt);
	    
	    
	    tabbedPane.addTab("Hmac", null, panelHmac, "click to show panel 3");
	    
	    Label lblMACList = new Label("MAC list");
	    lblMACList.setBounds(50, 47, 56, 16);
	    panelHmac.add(lblMACList);
	    
	    _txtMACArea = new JTextArea();
	    _txtMACArea.setEditable(false);
	    _scrpAreaMac = new JScrollPane(_txtMACArea);
	    _scrpAreaMac.setBounds(112, 47, 345, 266);
	    panelHmac.add(_scrpAreaMac);
	    
	    _btnRun = new JButton("Run");
	    _btnRun.setBounds(362, 337, 97, 25);
	    panelHmac.add(_btnRun);
	    //
	    tabbedPane.addTab("Verify", null, panelVerify, "click to show panel 3");
	    
	    _btn_Browse_Key_ProbVector = new JButton("Browse");
	    _btn_Browse_Key_ProbVector.setBounds(429, 13, 28, 25);
	    panelVerify.add(_btn_Browse_Key_ProbVector);
	    
	    _btnCompute = new JButton("Compute");
	    _btnCompute.setBounds(365, 166, 92, 48);
	    panelVerify.add(_btnCompute);
	    
	    _txtField_ProbVector_Location = new JTextField();
	    _txtField_ProbVector_Location.setEditable(false);
	    _txtField_ProbVector_Location.setBounds(135, 14, 282, 22);
	    panelVerify.add(_txtField_ProbVector_Location);
	    _txtField_ProbVector_Location.setColumns(10);
	    
	    JLabel lblProbvectorLocation = new JLabel("ProbVector Location");
	    lblProbvectorLocation.setBounds(10, 17, 122, 16);
	    panelVerify.add(lblProbvectorLocation);
	    
	    JLabel lblNumOfFileVerify = new JLabel("Number Of Files");
	    lblNumOfFileVerify.setBounds(10, 166, 100, 16);
	    panelVerify.add(lblNumOfFileVerify);
	    
	    JLabel _lblBlockID = new JLabel("BlockID");
	    _lblBlockID.setBounds(135, 84, 60, 16);
	    panelVerify.add(_lblBlockID);
	    
	    JLabel lblBlocksize = new JLabel("BlockSize");
	    lblBlocksize.setBounds(299, 84, 60, 16);
	    panelVerify.add(lblBlocksize);
	    
	    _txtFiled_BlockID = new JTextField();
	    _txtFiled_BlockID.setBounds(135, 113, 116, 22);
	    panelVerify.add(_txtFiled_BlockID);
	    _txtFiled_BlockID.setColumns(10);
	    
	    _txtField_BlockSize = new JTextField();
	    _txtField_BlockSize.setColumns(10);
	    _txtField_BlockSize.setBounds(301, 113, 116, 22);
	    panelVerify.add(_txtField_BlockSize);

	    
	    JLabel lblConfidentValue = new JLabel("Confident Value");
	    lblConfidentValue.setBounds(10, 193, 100, 16);
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
	    _btnVerify.setBounds(10, 227, 89, 40);
	    panelVerify.add(_btnVerify);
	    
	    _txtNumOfFileVerify = new JTextField();
	    _txtNumOfFileVerify.setBounds(135, 166, 77, 20);
	    panelVerify.add(_txtNumOfFileVerify);
	    _txtNumOfFileVerify.setColumns(10);
	    
	    _txtConfidentValue = new JTextField();
	    _txtConfidentValue.setColumns(10);
	    _txtConfidentValue.setBounds(135, 193, 77, 20);
	    panelVerify.add(_txtConfidentValue);
	    
	    JScrollPane _scrpLogFileVerify = new JScrollPane();
	    _scrpLogFileVerify.setBounds(118, 225, 339, 148);
	    panelVerify.add(_scrpLogFileVerify);
	    
	    _txtAreaLogFileVerify = new JTextArea();
	    _txtAreaLogFileVerify.setEditable(false);
	    _scrpLogFileVerify.setViewportView(_txtAreaLogFileVerify);
	    
	    _rdbtnNumberOfFiles = new JRadioButton("Number Of Files");
	    _rdbtnNumberOfFiles.setSelected(true);
	    _rdbtnNumberOfFiles.setBounds(243, 166, 116, 23);
	    panelVerify.add(_rdbtnNumberOfFiles);
	    
	    _rdbtnConfidentValue = new JRadioButton("Confident Value");
	    _rdbtnConfidentValue.setBounds(243, 190, 116, 23);
	    panelVerify.add(_rdbtnConfidentValue);
	    
	    _txtFilesBlock = new JTextField();
	    _txtFilesBlock.setBounds(434, 92, 50, 20);
	    _txtFilesBlock.setColumns(10);
	    contentPanel.add(_txtFilesBlock);
	    
	    JLabel lblAllFile = new JLabel("Files/Block");
	    lblAllFile.setBounds(361, 95, 71, 14);
	    contentPanel.add(lblAllFile);
	    
	    _btnCancel = new JButton("Cancel");
	    _btnCancel.setBounds(376, 526, 95, 33);
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
	    
	    _btnCompute.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {

	    		String keyFile = _txtField_ProbVector_Location.getText();
	    		int tmp1 = 0, tmp2 = 0;
		        try {
		    		//show chỉ số blockID và số lương xs trong file 
		    		MngrFiles.readKeyFile(keyFile, Source.ProbVector);
					tmp1 = Source.ProbVector.get(0).intValue();
					tmp2 = Source.ProbVector.get(1).intValue(); //số lượng file
					_txtFiled_BlockID.setText(Integer.toString(tmp1));
					_txtField_BlockSize.setText(Integer.toString(tmp2));
					Source.ProbVector.removeElementAt(0);
					Source.ProbVector.removeElementAt(0);
					
					Prob_Handle prh = new Prob_Handle(Source.ProbVector);
					double mean = prh.getMean();
					double sd = prh.getSD();
					
		    		// Compute number of files to verify, if else compute confident value
		    		if (_rdbtnNumberOfFiles.isSelected()) {
			    		_txtNumOfFileVerify.setText("");
				        	
			        	if (!_txtConfidentValue.getText().equals("")) {
			        		confidenVal = Double.parseDouble(_txtConfidentValue.getText()) / 100; // 95 => 0.95
			        		if (confidenVal < 100 && confidenVal > 0) {
								
								double a = mean - (sd*ProbCompute.getZ(confidenVal))/Math.sqrt(tmp2);
								double b = mean + (sd*ProbCompute.getZ(confidenVal))/Math.sqrt(tmp2);
								double    temp = b + 1 - a;
								
								System.out.println("mean = " + mean);
								System.out.println("sd = " + sd);
								System.out.println("a = " + a);
								System.out.println("b = " + b);
			        			
			        			/*double tmp3 = (double)tmp2*0.05;
			        			double a = (Math.pow(ProbCompute.getZ(confidenVal), 2)*Math.pow(sd, 2)) / (Math.pow(tmp3, 2));
			        			double b = Math.pow(ProbCompute.getZ(confidenVal), 2);
			        			double c = Math.pow(sd, 2);
			        			double d = Math.pow(tmp3, 2);
								double    temp = a;
								
								System.out.println("mean = " + mean);
								System.out.println("sd = " + sd);
								System.out.println("a = " + a);
								System.out.println("b = " + b);
								System.out.println("c = " + c);
								System.out.println("d = " + d);*/
								
								_txtNumOfFileVerify.setText(Integer.toString((int)temp));
			        		}
			        		else {
			        			infoBox("Confident Value doesn't in range 0% to 100%!", "Error");
			        		}
			        	}
				        
				        for (Double i : Source.ProbVector){
				        	System.out.println(i);
				        }
		    		}
		    		else if (_rdbtnConfidentValue.isSelected()) { //Compute confident value with n files verify
		    			_txtConfidentValue.setText("");
		    			if (!_txtNumOfFileVerify.equals("")) {
		    				int numOfFileVerify = Integer.parseInt(_txtNumOfFileVerify.getText());
		    				double z = (((double)numOfFileVerify / 2) * Math.sqrt(tmp2)) / sd;
		    				if (z > 3.489) {
		    					z = 3.49;
		    				}
		    				System.out.println(z);
		    				confidenVal = ProbCompute.getP(z);
		    				_txtConfidentValue.setText(Double.toString(Source.round(confidenVal * 100, 5)));
		    			}
		    		}
			        
			        Source.ProbVector.clear();
		    		
		        } catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "File Not Found!");
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    	
	    });
	    
	    _btnVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String macFile = _txtField_Max_Location.getText();
				int numOfFiletoVerify = 0;
				//show chỉ số blockID và số lương xs trong file 
				try {
					
					int flag = 0;
					
					MngrFiles.readMacFile(macFile, Source.MAC);
					_txtFiled_BlockID.setText(Source.MAC.get(0).toString());
					_txtField_BlockSize.setText(Source.MAC.get(1).toString());
					Source.MAC.removeElementAt(0);
					Source.MAC.removeElementAt(0);
					
					if (!_txtNumOfFileVerify.getText().equals("") && !_txtConfidentValue.getText().equals("")) {
						numOfFiletoVerify = Integer.parseInt(_txtNumOfFileVerify.getText());
					}
					
					flag = Source.Verify(_txtField_ProbVector_Location.getText(), _txtfieldInput.getText(), numOfFiletoVerify);
					if (flag == 0) {
						JOptionPane.showMessageDialog(null, "Verify Success!");
					}
					else
						JOptionPane.showMessageDialog(null, "File " + flag + " is changed!");
					
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
	    
	    _rdbtnNumberOfFiles.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (_rdbtnNumberOfFiles.isSelected()) {
					_rdbtnConfidentValue.setSelected(false);
				}
				
			}
		});
	    
	    _rdbtnConfidentValue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (_rdbtnConfidentValue.isSelected()) {
					_rdbtnNumberOfFiles.setSelected(false);
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
