package es.upv.simulator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.util.EventObject;
import es.upv.util.TutorialException;

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

public class Bocadillo extends Panel implements MouseListener
{
    class TutorialException extends Exception
    {

        private String mens;

        public String toString()
        {
            return "Tutorial Exception: " + mens;
        }

        public TutorialException(String s, int line)
        {
            super(s);
            mens = "";
            mens = s + " in line " + line;
        }

        public TutorialException()
        {
            mens = "";
            mens = "";
        }
    }


    private int x;
    private int y;
    private int ancho;
    private int alto;
    private int actual;
    private int total;
    private boolean flecha;
    private String texto[];
    public ButtonBitmap btnSig;
    public ButtonBitmap btnStop;
    private Tokenizer archivo;
    private Evaluation simulador;

    public Bocadillo(URL path, Evaluation simulador)
    {
        String arSig[] = {"botplay.gif", "botplayb.gif"};
        String arStop[] = {"botstop.gif", "botstopb.gif"};
        btnSig = new ButtonBitmap(path, arSig);
        add(btnSig);
        btnSig.addMouseListener(this);
        btnStop = new ButtonBitmap(path, arStop);
        add(btnStop);
        btnStop.addMouseListener(this);
        this.simulador = simulador;
    }

    public void setTutorial(URL arc)
    {
        try
        {
            archivo = new Tokenizer(arc.openStream());
            try
            {
                leerCabeceraTutorial();
                total = numBocadillos(arc.openStream());
                actual = 0;
                siguiente();
                setVisible(true);
            }
            catch(TutorialException e)
            {
                Mensaje.makeDialog(e.toString(), "Error", true);
                setVisible(false);
            }
        }
        catch(IOException e)
        {
            System.out.println("Tutorial inexistente");
        }
    }

    public void addNotify()
    {
        super.addNotify();
        setVisible(false);
        setBackground(getParent().getBackground());
    }

    public void siguiente()
        throws TutorialException
    {
        if(actual < total)
        {
            actual++;
            leerCabecera();
            defineBocadillo();
            leerAtributos();
        }
    }

    public void mousePressed(MouseEvent e)
    {
        int x = getX();
        int y = getY();

        if(e.getSource() == btnStop)
        {
            btnStop.setValor(true);
        }

        if(e.getSource() == btnSig)
        {
            btnSig.setValor(true);
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        btnStop.setValor(false);
        btnSig.setValor(false);
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == btnStop)
        {
            setVisible(false);
        } else
        if(e.getSource() == btnSig)
        {
            try
            {
                siguiente();
                repaint();
            }
            catch(TutorialException te)
            {
                Mensaje.makeDialog(te.toString(), "Error", true);
                setVisible(false);
            }
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void defineBocadillo()
    {
        int i = 0;
        int max = 0;
        Graphics g = getGraphics();
        FontMetrics fm = g.getFontMetrics();

        for(i = 0; i < texto.length; i++)
        {
            if(fm.stringWidth(texto[i]) > max)
            {
                max = fm.stringWidth(texto[i]);
            }
        }

        ancho = max + 20;
        alto = texto.length * fm.getHeight() + 30;
        int x = this.x;
        int y = this.y;
        if(x > 390)
        {
            x -= ancho;
        }

        if(y > 215)
        {
            y -= alto;
        }

        setBounds(x + getParent().getInsets().left, y + getParent().getInsets().top, ancho, alto);
    }

    public void paint(Graphics g)
    {
        FontMetrics fm = g.getFontMetrics();
        dibujaBocadillo(g);
        g.setColor(Color.red);

        if(flecha)
        {
            dibujaFlecha(g);
        }

        g.setColor(Color.black);

        for(int i = 0; i < texto.length; i++)
        {
            g.drawString(texto[i], 10, (i + 1) * fm.getHeight());
        }

        if(actual == total)
        {
            btnSig.setVisible(false);
        } else
        {
            btnSig.setVisible(true);
        }

        btnSig.setLocation(ancho - 50, alto - 20);
        btnStop.setLocation(ancho - 30, alto - 20);
    }

    public void dibujaBocadillo(Graphics g)
    {
        g.setColor(Color.yellow);
        g.fillRect(1, 1, ancho - 1, alto - 1);
        g.setColor(Color.black);
        g.drawRect(0, 0, ancho, alto);
    }

    public void dibujaFlecha(Graphics g)
    {
        int x = 0;
        int y = 0;
        int incx = 10;
        int incy = 10;
        Polygon flecha = new Polygon();

        if(this.x > 390)
        {
            x = ancho;
            incx = -10;
        }

        if(this.y > 215)
        {
            y = alto;
            incy = -10;
        }

        flecha.addPoint(x, y);
        flecha.addPoint(x + incx, y);
        flecha.addPoint(x, y + incy);
        g.fillPolygon(flecha);
    }

    private void leerCabecera() throws TutorialException
    {
        String atr = archivo.readWord();
        if(!"nux".equalsIgnoreCase(atr))
        {
            //esto lo he cambiado yo
           throw new TutorialException(atr + " encontrado en vez de NUX.", archivo.lineno());

        }

        x = archivo.readInt();
        atr = archivo.readWord();

        if(!"nuy".equalsIgnoreCase(atr))
        {
             //esto lo he cambiado yo
            throw new TutorialException(atr + " encontrado en vez de NUY.", archivo.lineno());

        }

        y = archivo.readInt();
        atr = archivo.readWord();

        if(!"flecha".equalsIgnoreCase(atr))
        {
            //esto lo he cambiado yo
            throw new TutorialException(atr + " encontrado en vez de FLECHA.", archivo.lineno());

        }

        flecha = archivo.readBoolean();
        atr = archivo.readWord();

        if(!"TEXTO".equalsIgnoreCase(atr))
        {
            //esto lo he cambiado yo
            throw new TutorialException(atr + " encontrado en vez de TEXTO.", archivo.lineno());

        } else
        {
            leerTexto();
            return;
        }
    }

    private void leerTexto()
        throws TutorialException
    {
        int cont = 0;
        String textoaux[] = new String[50];
        archivo.lowerCaseMode(false);
        do
        {
            textoaux[cont] = archivo.readLine();
            Bocadillo _tmp = this;
            if(((StreamTokenizer) (archivo)).ttype == -1)
            {
                //esto lo he cambiado yo
                throw new TutorialException("falta FINTEXTO", archivo.lineno());

            }
        } while(!textoaux[cont++].equalsIgnoreCase("FINTEXTO"));
        archivo.lowerCaseMode(true);
        cont--;
        texto = new String[cont];

        for(int i = 0; i < cont; i++)
        {
            texto[i] = textoaux[i];
        }

    }

    private void leerCabeceraTutorial()
        throws TutorialException
    {
        archivo.lowerCaseMode(true);
        String atr = archivo.readWord();

        if(!"TITULO".equalsIgnoreCase(atr))
        {
            //esto lo he cambiado yo
            throw new TutorialException(atr + " encontrado en vez de TITULO.", archivo.lineno());

        } else
        {
            archivo.readLine();
            return;
        }
    }

    public int numBocadillos(InputStream is)
    {
        Tokenizer tok = new Tokenizer(is);
        int i = 0;
        do
        {
            if("TEXTO".equalsIgnoreCase(tok.readWord()))
            {
                i++;
            }
        } while(((StreamTokenizer) (tok)).ttype != -1);
        return i;
    }

    private void leerAtributos()
        throws TutorialException
    {
        try
        {
            archivo.nextToken();

            for(String atr = archivo.readWord(); ((StreamTokenizer) (archivo)).ttype != -3 || atr.compareTo("finatributos") != 0;)
            {
                if(!simulador.hasParameter(atr))
                {
                    //esto lo he cambiado yo
                    throw new TutorialException(atr + " comando no reconocido.", archivo.lineno());

                }

                String tipo = simulador.getParameterType(atr);

                if(tipo.equalsIgnoreCase("NONE"))
                {
                    simulador.setValueString(atr, "");
                }

                if(tipo.equalsIgnoreCase("BOOLEAN"))
                {
                    simulador.setValueBoolean(atr, archivo.readBoolean());
                }

                if(tipo.equalsIgnoreCase("INT"))
                {
                    simulador.setValueInt(atr, archivo.readInt());
                }

                if(tipo.equalsIgnoreCase("FLOAT"))
                {
                    simulador.setValueFloat(atr, archivo.readFloat());
                }

                if(tipo.equalsIgnoreCase("STRING"))
                {
                    simulador.setValueString(atr, archivo.readLine());
                }

                do
                {
                    atr = archivo.readWord();
                } while(((StreamTokenizer) (archivo)).ttype != -3);
            }

        }
        catch(IOException e)
        {
            //esto lo he cambiado yo
            throw new TutorialException("problemas con el archivo tutorial.", archivo.lineno());

        }
    }
}
