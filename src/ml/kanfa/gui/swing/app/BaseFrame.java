package ml.kanfa.gui.swing.app;

import com.sun.awt.AWTUtilities;
import ml.kanfa.model.Rb;
import ml.kanfa.utils.verifier.IdGen;
import ml.kanfa.utils.verifier.VerifierInterface;
import ml.kanfa.utils.verifier.WindowOpenedVerifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Ibrahim Maïga.
 */
public abstract class BaseFrame extends ApplicationComponent implements VerifierInterface{

    private boolean iconified;
    private int posX;
    private int posY;
    private boolean undecorated;

    public BaseFrame(Rb rb, boolean undecorated){
        super(rb);
        this.undecorated = undecorated;
        this.setLayout(new BorderLayout());
        JPanel quitPan = new JPanel(null);
        quitPan.setOpaque(true);
        JLabel quit = new JLabel("x", JLabel.CENTER){
        };
        quit.setFont(new Font("Verdana", Font.BOLD, 12));
        quit.setPreferredSize(new Dimension(quit.getPreferredSize().height, quit.getPreferredSize().height));
        quit.setBackground(Color.RED);
        JLabel hide = new JLabel("-", JLabel.CENTER);
        hide.setFont(new Font("Verdana", Font.BOLD, 12));
        hide.setPreferredSize(new Dimension(hide.getPreferredSize().height, hide.getPreferredSize().height));
        hide.setBackground(Color.BLUE);
        quit.setBounds(this.getDimension().width - (quit.getPreferredSize().width + 1), 0, quit.getPreferredSize().width, quit.getPreferredSize().height);
        hide.setBounds(this.getDimension().width - (quit.getPreferredSize().width + hide.getPreferredSize().width + 1),
                       0, hide.getPreferredSize().width, hide.getPreferredSize().height);
        quitPan.setPreferredSize(new Dimension(this.getDimension().width, quit.getPreferredSize().height));
        hide.setToolTipText(this.rb.get("App.HideBtnText"));
        quit.setToolTipText(this.rb.get("App.QuitBtnText"));
        quitPan.add(quit);
        quitPan.add(hide);
        quit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Color defaultColor = quit.getForeground();

        hide.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                toBack();
                iconified = true;
            }

            @Override public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                hide.setOpaque(true);
                hide.setForeground(Color.WHITE);
            }

            @Override public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                hide.setOpaque(false);
                hide.setForeground(defaultColor);
            }
        });
        quit.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                close();
            }

            @Override public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                quit.setOpaque(true);
                quit.setForeground(Color.WHITE);
            }

            @Override public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                quit.setOpaque(false);
                quit.setForeground(defaultColor);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                addOpened();
            }

            @Override public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                close();
            }
        });

        WindowListener windowListener =  new WindowAdapter() {
            @Override public void windowIconified(WindowEvent e) {
                super.windowIconified(e);
                iconified = true;
            }

            @Override public void windowDeiconified(WindowEvent e) {
                super.windowDeiconified(e);
                iconified = false;
            }

            @Override public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                addOpened();
            }
        };

        MouseListener mouseListener = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                posX = e.getX();
                posY = e.getY();
            }
        };


        MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
            }
        };

        if (this.undecorated){
            this.setUndecorated(this.undecorated);
            this.add(quitPan, BorderLayout.NORTH);
            this.addMouseMotionListener(mouseMotionListener);
            this.addMouseListener(mouseListener);
            this.addWindowListener(windowListener);
            AWTUtilities.setWindowShape(this, shape());
        }
    }

    public BaseFrame(){
        this(Rb.getInstance(), false);
    }

    public BaseFrame(boolean undecorated){
        this(Rb.getInstance(), undecorated);
    }

    protected Dimension getDimension() {
        return new Dimension(10, 10);
    }

    protected Shape shape(){
        return new RoundRectangle2D.Double(0, 0, this.getSize().getWidth(), this.getSize().getHeight(), 20, 20);
    }

    public boolean isIconified(){
        return this.iconified;
    }

    @Override public int id() {
        return IdGen.getId(this);
    }

    @Override public void addOpened() {
        WindowOpenedVerifier.addOpened(this);
    }

    @Override public void close() {
        WindowOpenedVerifier.dispose(this);
    }
}
