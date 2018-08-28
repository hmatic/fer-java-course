package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.components.JStatusBar;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectSaver;

/**
 * GUI application for drawing geometrical objects.
 * Currently supported objects are: line, circle and filled circle.
 * List of drawn object is in the right panel.
 * To delete object, select it on the list and press DEL.
 * You can reorder objects using "+" and "-" keys.
 * Select foreground and background color for objects using color chooser
 * located in toolbar. Change drawing tools by pressing buttons on toolbar.
 * With this application you can open JVDraw files(*.jvd).
 * Files created by this application are saved under .jvd format.
 * They can also be exported in following extensions: png, jpg, gif.
 * @author Hrvoje Matic
 *
 */
public class JVDraw extends JFrame {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default foreground color.
	 */
	private static final Color DEFAULT_FOREGROUND = Color.BLACK;
	/**
	 * Default background color.
	 */
	private static final Color DEFAULT_BACKGROUND = Color.WHITE;
	/**
	 * Default extension for exporting.
	 */
	protected static final String DEFAULT_EXPORT_EXTENSION = "png";
	/**
	 * Default tool upon program starting.
	 */
	protected static final String DEFAULT_STARTING_TOOL = "lineTool";
	/**
	 * Foreground color chooser component.
	 */
	private JColorArea foreground;
	/**
	 * Background color chooser component.
	 */
	private JColorArea background;
	
	/**
	 * Status bar.
	 */
	private JStatusBar statusBar;
	
	/**
	 * Drawing model.
	 */
	private DrawingModel model;
	/**
	 * Drawing canvas.
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * Currently selected tool.
	 */
	private Tool currentTool;
	/**
	 * Map of available tools.
	 */
	private Map<String, Tool> tools = new HashMap<>();
	
	/**
	 * Sidebar list component.
	 */
	private JList<GeometricalObject> sidebarList;

	/**
	 * File path of current drawing
	 */
	private Path documentPath;
	/**
	 * Modification flag. True if there are no unsaved changes.
	 */
	private boolean saved = true;

	/**
	 * Default constructor for JVDraw app.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0,0);
		setSize(900,600);
		setTitle("JVDraw");

		initGUI();
	}
	
	/**
	 * Program entry point.
	 * @param args no arguments needed
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
	
	/**
	 * Initialize GUI.
	 */
	private void initGUI() {
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		foreground = new JColorArea(DEFAULT_FOREGROUND);
		background = new JColorArea(DEFAULT_BACKGROUND);
		
		statusBar = new JStatusBar(foreground, background);
		pane.add(statusBar, BorderLayout.PAGE_END);
		
		model = new DrawingModelImpl();
		canvas = new JDrawingCanvas(model, this);
		pane.add(canvas, BorderLayout.CENTER);
		
		tools.put("lineTool", new LineTool(foreground, model, canvas));
		tools.put("circleTool", new CircleTool(foreground, model, canvas));
		tools.put("filledCircleTool", new FilledCircleTool(foreground, background, model, canvas));
		currentTool = tools.get(DEFAULT_STARTING_TOOL);
		
		sidebarList = new JList<>(new DrawingObjectListModel(model));
		JScrollPane scrollPane = new JScrollPane(sidebarList);
		
		pane.add(scrollPane, BorderLayout.LINE_END);
		
		initListeners();
		initActions();
		initMenu();
		initToolbar();
	}

	/**
	 * Initialize action values.
	 */
	private void initActions() {
		setLineToolAction.putValue(Action.NAME, "Line");
		setCircleToolAction.putValue(Action.NAME, "Circle");
		setFilledCircleToolAction.putValue(Action.NAME, "Filled circle");
		
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		
		saveAction.setEnabled(false);
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		
		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
	}

	/**
	 * Initialize all listeners on JVDraw components.
	 */
	private void initListeners() {
		// CLOSING WINDOW LISTENER
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplication();	
			}
		});
		
		// CANVAS MOUSE MOTION LISTENER
		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				currentTool.mouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				currentTool.mouseMoved(e);
			}
		});
		
		// CANVAS MOUSE LISTENER
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTool.mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				currentTool.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				currentTool.mouseReleased(e);
			}
		});
		
		// CANVAS RESIZE LISTENER
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				canvas.repaint();
			}
		});
		
		// LIST KEY LISTENER (HANDLES DEL, +, - KEYS)
		sidebarList.addKeyListener(new KeyboardListener());
		
		// LIST MOUSE LISTENER (HANDLES DOUBLE CLICK ON LIST)
		sidebarList.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("rawtypes")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = ((JList)e.getSource()).locationToIndex(e.getPoint());
					GeometricalObject selectedObject = model.getObject(index);
					GeometricalObjectEditor editor = selectedObject.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit coordinates and colors",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch(IllegalArgumentException ex) {
							JOptionPane.showMessageDialog(JVDraw.this, 
									"Entered data is not valid.",
									"Invalid input",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				} 
			}
		});
		
		// MODIFICATION LISTENER
		model.addDrawingModelListener(new ModificationListener());
		
	}
	
	/**
	 * Listener triggered when drawing model changes.
	 * Changes modification flag and enables save action.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class ModificationListener implements DrawingModelListener {
		@Override
		public void objectsAdded(DrawingModel source, int index0, int index1) {
			saveAction.setEnabled(true);
			saved = false;
		}
		
		@Override
		public void objectsRemoved(DrawingModel source, int index0, int index1) {
			saveAction.setEnabled(true);
			saved = false;
		}

		@Override
		public void objectsChanged(DrawingModel source, int index0, int index1) {
			saveAction.setEnabled(true);
			saved = false;
		}
	}
	
	/**
	 * Keyboard listener for JList.
	 * DEL key: delete selected object
	 * "-" key: move selected object down
	 * "+" key: move selected object up
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class KeyboardListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int index = sidebarList.getSelectedIndex();
			if(index>=0) {
				GeometricalObject object = sidebarList.getModel().getElementAt(index);
				if(e.getKeyCode()==KeyEvent.VK_DELETE) {
					model.remove(object);
					sidebarList.setSelectedIndex(index==0 ? 0 : index-1);
				} else if(e.getKeyCode()==KeyEvent.VK_PLUS || e.getKeyCode()==KeyEvent.VK_ADD) {
					model.changeOrder(object, -1);
					sidebarList.setSelectedIndex(index-1);
				} else if(e.getKeyCode()==KeyEvent.VK_MINUS || e.getKeyCode()==KeyEvent.VK_SUBTRACT) {
					model.changeOrder(object, 1);
					sidebarList.setSelectedIndex(index+1);
				}
			}
		}
	}

	/**
	 * Initialize toolbar.
	 */
	private void initToolbar() {
		JToolBar toolBar = new JToolBar("Toolbar");
		toolBar.setFloatable(true);
		
		toolBar.add((JComponent)foreground);
		toolBar.add((JComponent)background);
		
		JToggleButton lineTool = new JToggleButton(setLineToolAction);
		lineTool.setSelected(true);
		JToggleButton circleTool = new JToggleButton(setCircleToolAction);
		JToggleButton filledCircleTool = new JToggleButton(setFilledCircleToolAction);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(lineTool);
		bg.add(circleTool);
		bg.add(filledCircleTool);

		toolBar.add(lineTool);
		toolBar.add(circleTool);
		toolBar.add(filledCircleTool);
	
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Initialize menu.
	 */
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exportAction));
		
		menuBar.add(fileMenu);
		
		this.setJMenuBar(menuBar);
	}

	/**
	 * Action which sets Line Tool as current tool.
	 */
	private final Action setLineToolAction = new AbstractAction() {
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentTool = tools.get("lineTool");
		}
	};
	
	/**
	 * Action which sets Circle Tool as current tool.
	 */
	private final Action setCircleToolAction = new AbstractAction() {
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentTool = tools.get("circleTool");
		}
	};
	
	/**
	 * Action which sets Filled Circle Tool as current tool.
	 */
	private final Action setFilledCircleToolAction = new AbstractAction() {
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentTool = tools.get("filledCircleTool");
		}
	};
	
	/**
	 * Action which saves document to his current document path or 
	 * forwards request to saveAs() method if document has no current path.
	 */
	private final Action saveAction = new AbstractAction() {
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(documentPath!=null) {
				save(documentPath);
			} else {
				if(!saveAs()) return;
			}
			
			this.setEnabled(false);
		}
	};
	
	/**
	 * Action which saves file to new file path using saveAs() method.
	 */
	private final Action saveAsAction = new AbstractAction() {	
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveAs();
			
			saveAction.setEnabled(false);
		}
	};
	
	/**
	 * Action which exports drawing to jpg, png or gif.
	 * Uses bounding box mechanism so there is no blanks space around objects.
	 */
	private final Action exportAction = new AbstractAction() {
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {	
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Export");
			fc.setApproveButtonText("Export");
			fc.setFileFilter(new FileNameExtensionFilter("JPG(*.jpg)", "jpg", "image"));
			fc.setFileFilter(new FileNameExtensionFilter("GIF(*.gif)", "gif", "image"));
			fc.setFileFilter(new FileNameExtensionFilter("PNG(*.png)", "png", "image"));
			if(fc.showOpenDialog(JVDraw.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
		
			String extension = DEFAULT_EXPORT_EXTENSION;
			File file = fc.getSelectedFile();
			if(!file.toString().contains(".")) {
				file = new File(file.toString() + ".png");
			} else {
				String fileName = file.toString();
				extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				if(!(extension.equals("png") || extension.equals("jpg") || extension.equals("gif"))) {
					JOptionPane.showMessageDialog(JVDraw.this, 
							"Only supported extensions are png, jpg and gif.",
							"Unsupported extension",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for(int i=0;i<model.getSize();i++) {
				model.getObject(i).accept(bbcalc);
			}
			Rectangle box = bbcalc.getBoundingBox();
			
			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
			
			Graphics2D g = image.createGraphics();
			g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, box.width, box.height);
			
			AffineTransform t = new AffineTransform();
			t.translate(-box.x, -box.y);
			g.setTransform(t);
			
			GeometricalObjectPainter painter = new GeometricalObjectPainter();
			painter.setGraphics(g);
			for(int i=0, modelSize=model.getSize(); i<modelSize; i++) {
				model.getObject(i).accept(painter);
			}
			g.dispose();
			
			try {
				ImageIO.write(image, extension, file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(JVDraw.this, 
						"Error while exporting.",
						"Export failed",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(JVDraw.this, 
					"File exported to " + file.toString() + ".",
					"Successful export",
				    JOptionPane.PLAIN_MESSAGE);
		}
	};
	
	/**
	 * Action used to open .jvd files.
	 * Reads from file, creates geometrical objects and adds them to model.
	 * 
	 */
	private final Action openAction = new AbstractAction() {
		/** Serialization ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			fc.setApproveButtonText("Open");
			fc.setFileFilter(new FileNameExtensionFilter("JVDraw Files(*.jvd)", "jvd", "text"));
			if(fc.showOpenDialog(JVDraw.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File file = fc.getSelectedFile();
			String fileName = file.toString();
			if(!fileName.contains(".")) {
				JOptionPane.showMessageDialog(JVDraw.this, 
						"Only supported extension is \".jvd\".",
						"Unsupported extension",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(!extension.equals("jvd")) {
				JOptionPane.showMessageDialog(JVDraw.this, 
						"Only supported extension is \".jvd\".",
						"Unsupported extension",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			Path filePath = file.toPath();
			if(!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, 
						"File does not exist.",
						"Can't open file",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			List<String> objects;
			try {
				objects = Files.readAllLines(filePath);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(JVDraw.this, 
						"Error occured during reading file.",
						"Can't open file",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			List<GeometricalObject> newModel = new ArrayList<>();
			for(String object : objects) {
				try {
					String[] objectProperties = object.split(" ");
					switch(objectProperties[0]) {
						case "LINE":
							newModel.add(createLineObject(objectProperties));
							break;
						case "CIRCLE":
							newModel.add(createCircleObject(objectProperties));
							break;
						case "FCIRCLE":
							newModel.add(createFilledCircleObject(objectProperties));
							break;
						default:
							JOptionPane.showMessageDialog(JVDraw.this, 
									"Invalid JVD file format.",
									"Invalid JVD file",
								    JOptionPane.ERROR_MESSAGE);
							return;
					}
				} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(JVDraw.this, 
							"Invalid jvd file format.",
							"Invalid jvd",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			clearModel();
			for(GeometricalObject object : newModel) {
				model.add(object);
			}
			canvas.repaint();
			
			setDocumentPath(filePath);
			saveAction.setEnabled(false);
			saved = true;
		}
	};
	
	/**
	 * Sets document path and updates window title.
	 * @param newPath new document path
	 */
	private void setDocumentPath(Path newPath) {
		this.documentPath = newPath;
		setTitle("JVDraw - " + newPath.normalize().toAbsolutePath().toString());
	}
	
	/**
	 * Getter for current tool.
	 * @return current tool
	 */
	public Tool getCurrentTool() {
		return currentTool;
	}
	
	
	
	// ********************
	//   HELPER METHODS
	// ********************
	
	/**
	 * Removes all objects from model.
	 */
	private void clearModel() {
		for(int i=model.getSize()-1; i>=0; i--) {
			model.remove(model.getObject(i));
		}
	}
	
	/**
	 * Creates new line object based on properties given in argument.
	 * @param objectProperties object properties
	 * @return new line object
	 */
	private GeometricalObject createLineObject(String[] objectProperties) {
		int x1 = Integer.parseInt(objectProperties[1]);
		int y1 = Integer.parseInt(objectProperties[2]);
		int x2 = Integer.parseInt(objectProperties[3]);
		int y2 = Integer.parseInt(objectProperties[4]);
		int r = Integer.parseInt(objectProperties[5]);
		int g = Integer.parseInt(objectProperties[6]);
		int b = Integer.parseInt(objectProperties[7]);
		return new Line(x1, y1, x2, y2, new Color(r,g,b));
	}
	
	/**
	 * Creates new circle object based on properties given in argument.
	 * @param objectProperties object properties
	 * @return new circle object
	 */
	private GeometricalObject createCircleObject(String[] objectProperties) {
		int x = Integer.parseInt(objectProperties[1]);
		int y = Integer.parseInt(objectProperties[2]);
		int radius = Integer.parseInt(objectProperties[3]);
		int r = Integer.parseInt(objectProperties[4]);
		int g = Integer.parseInt(objectProperties[5]);
		int b = Integer.parseInt(objectProperties[6]);
		return new Circle(x, y, radius, new Color(r,g,b));
	}
	
	/**
	 * Creates new filled circle object based on properties given in argument.
	 * @param objectProperties object properties
	 * @return new filled circle object
	 */
	private GeometricalObject createFilledCircleObject(String[] objectProperties) {
		int x = Integer.parseInt(objectProperties[1]);
		int y = Integer.parseInt(objectProperties[2]);
		int radius = Integer.parseInt(objectProperties[3]);
		int fgR = Integer.parseInt(objectProperties[4]);
		int fgG = Integer.parseInt(objectProperties[5]);
		int fgB = Integer.parseInt(objectProperties[6]);
		int bgR = Integer.parseInt(objectProperties[7]);
		int bgG = Integer.parseInt(objectProperties[8]);
		int bgB = Integer.parseInt(objectProperties[9]);
		return new FilledCircle(x, y, radius, new Color(fgR,fgG,fgB), new Color(bgR, bgG, bgB));
	}
	
	/**
	 * Saves current model to path given in argument using GeometricalObjectSaver.
	 * @param path saving file path
	 */
	private void save(Path path) {
		GeometricalObjectSaver saver = new GeometricalObjectSaver(path);
		for(int i=0;i<model.getSize();i++) {
			model.getObject(i).accept(saver);
		}
		saver.close();
		saved = true;
	}
	
	/**
	 * Prompts user with file chooser and calls save() method on chosen path.
	 * @return true if file is saved, false otherwise
	 */
	private boolean saveAs() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save as");
		fc.setApproveButtonText("Save");
		fc.setFileFilter(new FileNameExtensionFilter("JVDraw files(*.jvd)", "jvd", "text"));
		if(fc.showOpenDialog(JVDraw.this)!=JFileChooser.APPROVE_OPTION) {
			return false;
		}
		
		File fileName = fc.getSelectedFile();
		if(!fileName.toString().contains(".")) {
			fileName = new File(fileName.toString() + ".jvd");
		}
		
		setDocumentPath(fileName.toPath());
		
		save(documentPath);
		return true;
	}
	
	/**
	 * Exits application.
	 * If there are unsaved changes, prompts user with dialog.
	 * Dialog offers user to save file, exit without saving or cancel exiting.
	 */
	protected void exitApplication() {
		if(!saved) {
			String[] options = new String[] {
					"Save file", "Don't save", "Cancel"};
			int result = JOptionPane.showOptionDialog(
					JVDraw.this,
					"File is not saved. Would you like to save it?",
					"File not saved",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, options, options[0]);
			switch(result) {
			case JOptionPane.YES_OPTION:
				saveAction.actionPerformed(null);
				break;
			case JOptionPane.NO_OPTION:
				break;
			case JOptionPane.CANCEL_OPTION:
				return;
			default:
				return;
			}
		}
		
		dispose();	
	}
	
}
