package es.upv.oscilloscope;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

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

// Referenced classes of package es.upv.scope:
//            Senyal, Pantalla

public class Preview extends Panel
    implements Runnable
{
    class PreviewMouseInputAdapter extends MouseAdapter
        implements MouseMotionListener
    {

        private int x;
        private int y;
        private int posant;
        private int yant;

        public void mouseDragged(MouseEvent e)
        {
            int newpos = (posant + x) - e.getX();
            int newy = yant - (y - e.getY()) / 10;
            if(newpos < 0)
            {
                posicion = 0;
            } else
            if(newpos > senyal.getLength())
            {
                posicion = senyal.getLength();
            } else
            {
                posicion = newpos;
            }
            if(newy < -20)
            {
                senyal.setYPos(-20);
            } else
            if(newy > 20)
            {
                senyal.setYPos(20);
            } else
            {
                senyal.setYPos(newy);
            }
        }

        public void mouseMoved(MouseEvent mouseevent)
        {
        }

        public void mousePressed(MouseEvent e)
        {
            y = e.getY();
            yant = senyal.getYPos();
            x = e.getX();
            posant = posicion;
        }

        PreviewMouseInputAdapter()
        {
        }
    }


    private Screen pantalla;
    private Scrollbar sbCalidad;
    private Scrollbar sbAmplitud;
    private Scrollbar sbTiempos;
    private Label LPrev;
    private Label LCal;
    private Label LMin;
    private Label LMax;
    private Signal senyal;
    private Thread hilo;
    private int posicion;

    public Preview()
    {
        senyal = new Signal();
        init();
    }

    public void start()
    {
        hilo.setPriority(1);
        hilo.start();
    }

    public void stop()
    {
        hilo.stop();
    }

    public Preview(Signal senyal)
    {
        this.senyal = senyal;
        init();
    }

    public void setSenyal(Signal senyal)
    {
        this.senyal = senyal;
    }

    private void init()
    {
        posicion = 0;
        pantalla = new Screen();
        pantalla.setFoco(2);
        pantalla.setIntens(255);
        pantalla.encender();
        pantalla.setCursor(new Cursor(13));
        sbCalidad = new Scrollbar(0, 1, 1, 1, 11);
        sbAmplitud = new Scrollbar(1, 0, 1, -10, 10);
        sbTiempos = new Scrollbar(0, 0, 1, -10, 10);
        LPrev = new Label("Previsualizaci\363n");
        LCal = new Label("Calidad");
        LMin = new Label("MIN");
        LMax = new Label("MAX");
        setSize(174, 220);
        setLayout(null);
        hilo = new Thread(this);
        PreviewMouseInputAdapter pmia = new PreviewMouseInputAdapter();
        pantalla.addMouseListener(pmia);
        pantalla.addMouseMotionListener(pmia);
    }

    public void addNotify()
    {
        super.addNotify();
        add(pantalla);
        add(sbCalidad);
        add(sbAmplitud);
        add(sbTiempos);
        add(LPrev);
        add(LCal);
        add(LMin);
        add(LMax);
    }

    public void setSize(int width, int height)
    {
        super.setSize(width, height);
        pantalla.setBounds(4, 19, width - 24, height - 70);
        pantalla.setBackground(Color.black);
        sbCalidad.setBounds(4, height - 34, width - 24, 13);
        sbAmplitud.setBounds(width - 18, 19, 13, height - 70);
        sbTiempos.setBounds(4, height - 49, width - 24, 13);
        LPrev.setBounds(width / 2 - 50, 3, 100, 13);
        LCal.setBounds(width / 2 - 25, height - 20, 50, 13);
        LMin.setBounds(4, height - 20, 40, 13);
        LMax.setBounds(width - 46, height - 20, 43, 13);
    }

    public void dibujaSenyal()
    {
        float cal = sbCalidad.getValue();
        int width = pantalla.getSize().width;
        int height = pantalla.getSize().height;
        float escalax = (float)Math.pow(2D, sbTiempos.getValue());
        float time = ((float)senyal.getLength() * escalax) / (senyal.getVelozMuestreo() * (float)10 * cal * (float)width);
        float escalay = ((float)Math.pow(2D, -sbAmplitud.getValue()) * (float)height) / (senyal.getTensionMax() * (float)2);
        for(float x = 0.0F; x < (float)width * cal; x++)
        {
            float v = senyal.sigMuestra(time);
            float y = (int)(v * escalay);
            pantalla.dibujaCol((int)(x / cal));
            pantalla.putPixel((int)(x / cal), (int)(-y), Color.white);
        }

    }

    public void run()
    {
        do
        {
            senyal.setPosMuestra(posicion);
            dibujaSenyal();
            try
            {
                Thread.sleep(200L);
            }
            catch(InterruptedException e)
            {
                System.out.println(e);
            }
        } while(true);
    }
}

