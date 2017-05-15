import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
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

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	public static int _numFilesOfBlock = 100;
	private JPanel contentPanel;
	private JTextField _txtfieldInput, _txtfieldOutput, _txtFilesBlock;
	private JButton _btnOpenInput, _btnOpenOutput, _btnEncrypt, _btnDecrypt, _btnExportReport;
	private JTextField _txtKeyEncrypt;
	private JTextField _txtKeyDecrypt;
	public static JTextArea _txtAreaEncrypt, _txtAreaDecrypt;
	private JScrollPane _scrpAreaEncrypt, _scrpAreaDecrype;

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
		
		_txtKeyEncrypt = new JTextField();
		_txtKeyEncrypt.setBounds(37, 228, 102, 20);
		panelEncrypt.add(_txtKeyEncrypt);
		_txtKeyEncrypt.setColumns(10);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(442, 11, 17, 206);
		panelEncrypt.add(scrollBar);
		
	    tabbedPane.addTab("Decrypt", null, panelDecrypt, "click to show panel 2");
	    _txtAreaDecrypt = new JTextArea();
	    _txtAreaDecrypt.setEditable(false);
	    _txtAreaDecrypt.setBounds(10, 11, 449, 209);
	    panelDecrypt.add(_txtAreaDecrypt);
	    
	    _btnDecrypt = new JButton("Decrypt");
	    _btnDecrypt.setBounds(364, 231, 95, 33);
	    panelDecrypt.add(_btnDecrypt);
	    
	    JLabel label = new JLabel("Key");
	    label.setBounds(10, 231, 27, 14);
	    panelDecrypt.add(label);
	    
	    _txtKeyDecrypt = new JTextField();
	    _txtKeyDecrypt.setColumns(10);
	    _txtKeyDecrypt.setBounds(37, 231, 102, 20);
	    panelDecrypt.add(_txtKeyDecrypt);
	    
	    
	    tabbedPane.addTab("Hmac", null, panelHmac, "click to show panel 3");
	    //
	    tabbedPane.addTab("Verify", null, panelVerify, "click to show panel 3");
	    //
	    
	    _txtFilesBlock = new JTextField();
	    _txtFilesBlock.setBounds(434, 92, 50, 20);
	    _txtFilesBlock.setColumns(10);
	    contentPanel.add(_txtFilesBlock);
	    
	    JLabel lblAllFile = new JLabel("Files/Block");
	    lblAllFile.setBounds(361, 95, 71, 14);
	    contentPanel.add(lblAllFile);
	    
	    _btnExportReport = new JButton("Export File");
	    _btnExportReport.setBounds(253, 434, 95, 33);
	    contentPanel.add(_btnExportReport);
	    
	    JButton _btnCancel = new JButton("Cancel");
	    _btnCancel.setBounds(370, 434, 95, 33);
	    contentPanel.add(_btnCancel);
	}
	
	private void catchEvent() {
		
		_txtKeyEncrypt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (_txtKeyEncrypt.getText().length() >= 16) {
					getToolkit().beep();
		            e.consume();
				}
			}
		});
		
		_txtKeyDecrypt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (_txtKeyDecrypt.getText().length() >= 16) {
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
						
						try {
							
							// Nên clear filelist sau mỗi lần thay đổi Input và Output
							if (Source.fileList.size() != 0) {
								Source.fileList.clear();
							}
							Source.mgrFile.getFileList(MngrFiles.folderInput, Source.fileList);
						
							Source.Encrypt("passwordtrongcau");
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
						
						try {
							
							// Nên clear filelist sau mỗi lần thay đổi Input và Output
							if (Source.fileList.size() != 0) {
								Source.fileList.clear();
							}
							Source.mgrFile.getFileList(MngrFiles.folderInput, Source.fileList);
						
							Source.Decrypt("passwordtrongcau");
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
	    		}
	    	}
	    });
		
	    _btnExportReport.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		try {
		    		if(Source.fileList.size() == 0) {
		    			infoBox("File list is empty", "Warning");
		    		}
		    		else if (Source.MAC.size() == 0) {
		    			infoBox("MAC is empty", "Warning");
		    		}
		    		else if (Source.ProbVector.size() == 0) {
		    			infoBox("ProbVector is empty", "Warning");
		    		}
		    		else {
		    			Source.mgrFile.writeResultCsv();
		    		}
	    		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
