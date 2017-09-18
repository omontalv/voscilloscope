package es.upv.oscilloscope;

import es.upv.simulator.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;

/**
 * <p>Title: Virtual Oscilloscope.</p>
 * <p>Description: A Oscilloscope simulator</p>
 * <p>Copyright (C) 2003 José Manuel Gómez Soriano</p>
 * <h2>License</h2>
 * <p>
 This file is part of Virtual Oscilloscope.

 Virtual Oscilloscope is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 Virtual Oscilloscope is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Virtual Oscilloscope; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 * </p>
 */

public class Oscilloscope extends Frame
    implements MouseListener, AdjustmentListener, ItemListener, MouseMotionListener, WindowListener
{

    public static URL path= null;
    public static URL dirImagenes;
    private SignalClient cs;
    public PFondo pFondo;
    private Choice chTutorial;
    private Choice chIdioma;
    private Bocadillo bocadillo;
    private Choice chArcTut;
    private Choice chExt;
    public GestionSenyales senyales;
    public boolean actSenyales;
    Evaluation evaluation;

    public Oscilloscope(URL path, URL dirImages, Evaluation eval)
    {
        super("Osciloscopio Virtual");
        evaluation = eval;
        this.path = path;
        this.dirImagenes = dirImages;
    }

    public void init()
    {
/*        try {
            path = new URL("file:/D:/Loli/Applet01/build/classes/");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }*/
        setLayout(null);
        setVisible(true);
        setSize(780 + getInsets().left + getInsets().right, 430 + getInsets().top + getInsets().bottom);
        setLocation(0, 0);
        addWindowListener(this);
        Mensaje mens = Mensaje.makeDialog("Iniciando el applet, por favor, espere...", "Información", false);
/*        try
        {
            //AQUI
            dirImagenes = new URL(path+"Imagenes/");
        }
        catch(Exception e)
        {
            Mensaje.makeDialog("Ruta URL mal formada intentando acceder a Imagenes/", "Error", true);
        }*/
        pFondo = new PFondo(dirImagenes);
        add(pFondo);
        pFondo.setLocation(getInsets().left, getInsets().top);
        Component buttons[] = pFondo.pTime.getComponents();
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].addMouseListener(this);
            if(buttons[i] instanceof RotateBitmap)
            {
                ((RotateBitmap)buttons[i]).addMouseMotionListener(this);
            }
        }

        pFondo.pTime.btnPosicion.addAdjustmentListener(this);
        buttons = pFondo.pVolt.getComponents();
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].addMouseListener(this);
            if(buttons[i] instanceof RotateBitmap)
            {
                ((RotateBitmap)buttons[i]).addMouseMotionListener(this);
            }
        }

        buttons = pFondo.pCalidad.getComponents();
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].addMouseListener(this);
            if(buttons[i] instanceof RotateBitmap)
            {
                ((RotateBitmap)buttons[i]).addMouseMotionListener(this);
            }
        }

        mens.setText("Insertando los componentes del tutorial");
        chTutorial = new Choice();
        chIdioma = new Choice();
        chArcTut = new Choice();
        chExt = new Choice();
        bocadillo = new Bocadillo(dirImagenes, evaluation);
        add(bocadillo, 0);
        bocadillo.btnStop.addMouseListener(this);
        bocadillo.btnSig.addMouseListener(this);
        mens.setText("Cargando lista de idiomas");
        listaIdiomas();
        chIdioma.setBounds(63, 402 + getInsets().top, 240, 15);
        chIdioma.setVisible(true);
        chExt.setVisible(false);
        add(chIdioma);
        add(chExt);
        chIdioma.addItemListener(this);
        mens.setText("Cargando lista de tutoriales");
        listaTutoriales();
        chTutorial.setBounds(400, 402 + getInsets().top, 240, 15);
        chTutorial.setVisible(true);
        chArcTut.setVisible(false);
        add(chTutorial);
        add(chArcTut);
        chTutorial.addItemListener(this);

        mens.setText("Lanzo los gestores de se\361ales");
        senyales = new GestionSenyales(pFondo.pantalla);
        senyales.setPriority(1);
        senyales.setDaemon(true);
        senyales.start();

        mens.setText("Lanzo el cliente de se\361al despues del gestor de senyales!!!");
        cs = new SignalClient(senyales);
        cs.setPriority(1);
        cs.start();

        actSenyales = true;
        pFondo.pCalidad.BtnDigital.setValor(true);
        pFondo.pTime.btnXPos.setVisible(false);
        pFondo.pTime.btnHoldOff.setVisible(false);
        pFondo.pTime.btnAtNormal.setVisible(false);
        pFondo.pTime.btnLevel.setVisible(false);
        pFondo.pTime.btnMasMenos.setVisible(false);
        pFondo.pTime.btnPosicion.setVisible(true);
        pFondo.pVolt.BtnCiclica1.setVisible(false);
        pFondo.pVolt.BtnCiclica2.setVisible(false);
        configuraSenyal();
        mens.closeDialog();
    }

    public void start()
    {
        if(senyales.isSuspend())
        {
            senyales.reanudar();
        }
    }

    public void stop()
    {
        if(!senyales.isSuspend())
        {
            senyales.suspender();
        }
    }

    public void destroy()
    {
        if(senyales.isAlive())
        {
            senyales.stop();
        }
        if(cs.isAlive())
        {
            cs.stop();
        }
        setVisible(false);
    }

    private void listaIdiomas()
    {
        chIdioma.removeAll();
        chExt.removeAll();
        try
        {
            Tokenizer idiomas = new Tokenizer((new URL(path + "Idiomas.txt")).openStream());
            do
            {
                String pal = idiomas.readLine();
                if(pal != null)
                {
                    chIdioma.add(pal);
                }
                pal = idiomas.readLine();
                if(pal != null)
                {
                    chExt.add(pal);
                }
            } while(((StreamTokenizer) (idiomas)).ttype != -1);
        }
        catch(IOException e)
        {
            Mensaje.makeDialog("No puedo abrir el \355ndice de idiomas", "Error", true);
        }
    }

    private void listaTutoriales()
    {
        chTutorial.removeAll();
        chArcTut.removeAll();
        chTutorial.addItem("Ninguno");
        chArcTut.addItem("Ninguno");
        try
        {
            Tokenizer dir = new Tokenizer((new URL(path + "Directorio.txt")).openStream());
            do
            {
                if(dir.buscarArchivo("." + chExt.getItem(chIdioma.getSelectedIndex())) != null)
                {
                    chArcTut.add(((StreamTokenizer) (dir)).sval);
                    String tit = leerTitulo(((StreamTokenizer) (dir)).sval);
                    if(tit != null)
                    {
                        chTutorial.add(tit);
                    }
                }
            } while(((StreamTokenizer) (dir)).ttype != -1);
        }
        catch(IOException e)
        {
            Mensaje.makeDialog("No puedo abrir el archivo de directorio", "Error", true);
        }
    }

    private String leerTitulo(String arch)
    {
        try
        {
            Tokenizer Tut = new Tokenizer((new URL(path + arch)).openStream());
            Tut.nextToken();
            if(((StreamTokenizer) (Tut)).sval.equalsIgnoreCase("Titulo"))
            {
                String s = Tut.readLine();
                return s;
            }
        }
        catch(IOException e)
        {
            Mensaje.makeDialog("No puedo leer el t\355tulo del tutorial:" + arch, "Error", true);
        }
        return null;
    }

    public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource() == chTutorial)
        {
            if((String)e.getItem() == "Ninguno")
            {
                bocadillo.setVisible(false);
            } else
            {
                try
                {
                    bocadillo.setTutorial(new URL(path + chArcTut.getItem(chTutorial.getSelectedIndex())));
                    bocadillo.setVisible(true);
                    if(actSenyales)
                    {
                        configuraSenyal();
                    }
                }
                catch(IOException ioe)
                {
                    Mensaje.makeDialog("No puedo encontar el archivo del tutorial:" + ioe, "Error", true);
                }
            }
        }
        if(e.getSource() == chIdioma)
        {
            listaTutoriales();
            bocadillo.setVisible(false);
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        int y = e.getY();
        if(e.getSource() == pFondo.pVolt.BtnCiclica1 || e.getSource() == pFondo.pVolt.BtnCiclica2 || e.getSource() == pFondo.pCalidad.BtnDigital)
        {
            senyales.rebobinar();
        }
        if(e.getSource() == pFondo.pVolt.BtnCanal1)
        {
            if(!senyales.isSuspend())
            {
                senyales.suspender();
            }
            senyales.canal1.conectaSenyal(path, cs);
            senyales.rebobinar();
            if(senyales.isSuspend())
            {
                senyales.reanudar();
            }
        }
        if(e.getSource() == pFondo.pVolt.BtnCanal2)
        {
            if(!senyales.isSuspend())
            {
                senyales.suspender();
            }
            senyales.canal2.conectaSenyal(path, cs);
            senyales.rebobinar();
            if(senyales.isSuspend())
            {
                senyales.reanudar();
            }
        }
        if(e.getSource() == bocadillo.btnStop)
        {
            chTutorial.select("Ninguno");
        }
        if(e.getSource() == pFondo.pCalidad.BtnCal)
        {
            try
            {
                if(y < 355)
                {
                    senyales.canal1.setMuestras((new URL(path, "Cuad02.sig")).openStream());
                } else
                {
                    senyales.canal1.setMuestras((new URL(path, "Cuad02.sig")).openStream());
                }
            }
            catch(MalformedURLException mue)
            {
                System.out.println("Mal formada URL:" + mue);
            }
            catch(IOException ioe)
            {
                System.out.println("Error de E/S:" + ioe);
            }
        }
        if(actSenyales)
        {
            configuraSenyal();
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        if(senyales.isDigital())
        {
            int valor = pFondo.pTime.btnPosicion.getValue();
            senyales.setPosMuestra(valor);
            if(actSenyales)
            {
                configuraSenyal();
            }
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        if(actSenyales)
        {
            configuraSenyal();
        }
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    void configuraSenyal()
    {
        int max = senyales.canal1.getLength();
        if(max < senyales.canal2.getLength())
        {
            max = senyales.canal2.getLength();
        }
        pFondo.pTime.btnPosicion.setMaximum(max + 30);
        senyales.setXPos((int)pFondo.pTime.btnXPos.getValor());
        senyales.setTime(pFondo.pTime.btnTime.getValor() + (pFondo.pTime.btnTime.getValor() * pFondo.pTime.btnTime.getValorFino()) / (float)100);
        senyales.setXY(pFondo.pTime.btnXY.getValor());
        senyales.setAtNormal(!pFondo.pTime.btnAtNormal.getValor());
        senyales.setNivelDisparo((int)pFondo.pTime.btnLevel.getValor());
        senyales.setDisPosNeg(!pFondo.pTime.btnMasMenos.getValor());
        senyales.setHoldOff((int)pFondo.pTime.btnHoldOff.getValor());
        senyales.setXMag(pFondo.pCalidad.BtnX10.getValor());
        senyales.setAdd(pFondo.pVolt.BtnAdd.getValor());
        senyales.setCalidad((int)pFondo.pCalidad.BtnCalidad.getValor());
        senyales.setDigital(pFondo.pCalidad.BtnDigital.getValor());
        senyales.setCH12(pFondo.pVolt.BtnCH12.getValor());
        senyales.canal1.setCiclica(pFondo.pCalidad.BtnDigital.getValor() ? false : pFondo.pVolt.BtnCiclica1.getValor());
        senyales.canal1.setInvert(!pFondo.pVolt.BtnInv1.getValor());
        senyales.canal1.setVisible(pFondo.pVolt.BtnCH12.getValor() ? pFondo.pVolt.BtnDual.getValor() : true);
        senyales.canal1.setYPos((int)pFondo.pVolt.BtnYPos1.getValor());
        senyales.canal1.setVDiv(pFondo.pVolt.BtnVolt1.getValor() + (pFondo.pVolt.BtnVolt1.getValor() * pFondo.pVolt.BtnVolt1.getValorFino()) / (float)100);
        if(pFondo.pVolt.BtnAcDc1.getValor() == 1)
        {
            senyales.canal1.sinContinua();
        }
        if(pFondo.pVolt.BtnAcDc1.getValor() == 2)
        {
            senyales.canal1.conContinua();
        }
        if(pFondo.pVolt.BtnAcDc1.getValor() == 0)
        {
            senyales.canal1.senyalGND();
        }
        senyales.canal2.setCiclica(pFondo.pCalidad.BtnDigital.getValor() ? false : pFondo.pVolt.BtnCiclica2.getValor());
        senyales.canal2.setInvert(!pFondo.pVolt.BtnInv2.getValor());
        senyales.canal2.setVisible(pFondo.pVolt.BtnCH12.getValor() ? true : pFondo.pVolt.BtnDual.getValor());
        senyales.canal2.setYPos((int)pFondo.pVolt.BtnYPos2.getValor());
        senyales.canal2.setVDiv(pFondo.pVolt.BtnVolt2.getValor() + (pFondo.pVolt.BtnVolt2.getValor() * pFondo.pVolt.BtnVolt2.getValorFino()) / (float)100);
        if(pFondo.pVolt.BtnAcDc2.getValor() == 2)
        {
            senyales.canal2.conContinua();
        }
        if(pFondo.pVolt.BtnAcDc2.getValor() == 1)
        {
            senyales.canal2.sinContinua();
        }
        if(pFondo.pVolt.BtnAcDc2.getValor() == 0)
        {
            senyales.canal2.senyalGND();
        }
        pFondo.pantalla.setFoco((int)pFondo.pTime.btnFoco.getValor());
        pFondo.pantalla.setIntens((int)pFondo.pTime.btnIntens.getValor());
        pFondo.pantalla.setNivel(pFondo.pTime.btnAtNormal.getValor() ? ((int)pFondo.pTime.btnLevel.getValor() * pFondo.pantalla.getSize().height) / 200 + (int)(pFondo.pVolt.BtnCH12.getValor() ? pFondo.pVolt.BtnYPos2.getValor() : pFondo.pVolt.BtnYPos1.getValor()) : 0);
        if(pFondo.pTime.btnPower.getValor())
        {
            pFondo.pantalla.encender();
        } else
        {
            pFondo.pantalla.apagar();
        }
        pFondo.pTime.btnLed.setValor(pFondo.pTime.btnPower.getValor());
    }

    public void repaint()
    {
        update(getGraphics());
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        g.drawString("Idioma:", 20, 414 + getInsets().top);
        g.drawString("Tutorial activo:", 320, 414 + getInsets().top);
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent e)
    {
        destroy();
        dispose();
        try
        {
            finalize();
        }
        catch(Throwable th)
        {
            System.out.println("No he podido finalizar:" + th);
        }
    }

    public void windowClosing(WindowEvent e)
    {
        setVisible(false);
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent e)
    {
        start();
    }

    public void windowIconified(WindowEvent e)
    {
        stop();
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public static void main(String [] args){
        //Osciloscopio osc = new Osciloscopio();
    }
}

