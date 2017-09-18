package es.upv.simulator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.net.URL;

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


public abstract class DoubleRotateBitmap extends RotateBitmap
{

    public DoubleRotateBitmap(URL path, String ArchivosGrueso[], float ValoresGrueso[], String ArchivosFino[], float ValoresFino[])
    {
        super(path, ArchivosGrueso, ValoresGrueso);
        int i = 0;
        tracker = new MediaTracker(this);
        Imagen2 = new Image[ArchivosFino.length];
        try
        {
            for(i = 0; i < ArchivosFino.length; i++)
            {
                Imagen2[i] = getToolkit().getImage(new URL(path + ArchivosFino[i]));
                tracker.addImage(Imagen2[i], 0);
                tracker.waitForAll();
            }

        }
        catch(Exception e)
        {
            System.out.println("Error " + e.getMessage() + " cargando imagen:" + path + ArchivosFino[i]);
        }
        PosValor = 0;
        PosImagen2 = 0;
        Valores = ValoresFino;
    }

    protected int nuevaPosicion(int x, int y, int centrox, int centroy)
    {
        int posAnt = PosValor % 44;
        double angulo = getAngulo(x, y, centrox, centroy);
        int posNuevo = (int)(((double)22 * angulo) / 3.1415926535897931D);
        int inc = posNuevo - posAnt;
        if(inc > 22)
            inc = -44 + inc;
        if(inc < -22)
            inc = 44 + inc;
        return inc;
    }

    public abstract void btnMove(int i, int j);

    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(x > 55 && x < 55 + Imagen2[0].getWidth(this) && y > 28 && y < 28 + Imagen2[0].getHeight(this))
        {
            PosValor = PosValor + nuevaPosicion(x, y, 86, 43);
            if(PosValor < 0)
                PosValor = 0;
            else
            if(PosValor >= Valores.length)
                PosValor = Valores.length - 1;
            PosImagen2 = PosValor % Imagen2.length;
            dibujaFino(getGraphics());
        } else
        if(x >= 0 && x <= getSize().width && y >= 0 && y <= getSize().height)
        {
            btnMove(x, y);
            dibuja(getGraphics());
        }
    }

    public void mouseDragger(MouseEvent e)
    {
        mouseClicked(e);
    }

    public Dimension getMinimumSize()
    {
        int ancho = super.imagen[0].getWidth(this) + 20;
        int alto = super.imagen[0].getHeight(this) + 10;
        FontMetrics Fuente = getFontMetrics(getFont());
        if(ancho < Fuente.stringWidth(super.titulo))
            ancho = Fuente.stringWidth(super.titulo);
        if(super.titulo.length() > 0)
            alto += Fuente.getHeight();
        return new Dimension(ancho, alto);
    }

    public void paint(Graphics g)
    {
        g.setColor(getForeground());
        g.fillRoundRect(0, 0, getSize().width, getSize().height - 5, 20, 20);
        dibuja(g);
    }

    public void dibuja(Graphics g)
    {
        int ancho = getSize().width;
        int alto = getSize().height;
        FontMetrics Fuente = getFontMetrics(getFont());
        int y;
        if(super.titulo.length() > 0)
            y = Fuente.getHeight();
        else
            y = 0;
        g.setColor(getBackground());
        g.fillOval((ancho / 2 - super.imagen[0].getWidth(this) / 2) + 4, 14, super.imagen[0].getWidth(this) - 8, super.imagen[0].getHeight(this) - 8);
        g.setColor(Color.orange);
        g.drawString(super.titulo, ancho / 2 - Fuente.stringWidth(super.titulo) / 2, alto);
        g.drawImage(super.imagen[super.posImagen], ancho / 2 - super.imagen[0].getWidth(this) / 2, 10, this);
        dibujaFino(g);
        dibujaValores(g);
    }

    public abstract void dibujaValores(Graphics g);

    public void dibujaFino(Graphics g)
    {
        g.drawImage(Imagen2[PosImagen2], 55, 28, this);
    }

    public float getValorFino()
    {
        return Valores[PosValor];
    }

    public void setValorFino(float valor)
    {
        boolean esta = false;
        int i = 0;
        do
        {
            if(i >= Valores.length || esta)
                break;
            if(valor == Valores[i])
            {
                esta = true;
                break;
            }
            i++;
        } while(true);
        if(esta)
        {
            PosValor = i;
            PosImagen2 = i % Imagen2.length;
        } else
        {
            System.out.println("No exite ese valor " + valor + " en el componente fino de " + this);
        }
        repaint();
    }

    private MediaTracker tracker;
    protected Image Imagen2[];
    protected int PosImagen2;
    protected float Valores[];
    protected int PosValor;
}
