package es.upv.oscilloscope;

import es.upv.simulator.StaticBitmapButton;
import java.awt.*;
import java.awt.event.MouseEvent;
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

public class CommutatorACDC extends StaticBitmapButton
{

    public static final int GND = 0;
    public static final int AC = 1;
    public static final int DC = 2;
    public static final boolean IZQ = true;
    public static final boolean DER = false;
    private boolean posLetras;
    private int estado;

    public CommutatorACDC(URL path, String Archivos[])
    {
        super(path, Archivos);
        estado = 2;
        posLetras = true;
        super.posImagen = estado;
    }

    public void setJustificacion(boolean pos)
    {
        posLetras = pos;
    }

    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(y < getSize().height / 3)
        {
            estado = 2;
        } else
        if(y < (getSize().height * 2) / 3)
        {
            estado = 1;
        } else
        {
            estado = 0;
        }
        super.posImagen = estado;
        dibuja(getGraphics());
    }

    public Dimension getMinimumSize()
    {
        int ancho = super.imagen[0].getWidth(this);
        int alto = super.imagen[0].getHeight(this);
        FontMetrics Fuente = getFontMetrics(getFont());
        ancho += Fuente.stringWidth("DC");
        return new Dimension(ancho, alto);
    }

    public int getValor()
    {
        return estado;
    }

    public void setValor(int e)
    {
        estado = e;
        super.posImagen = e;
        repaint();
    }

    public void dibuja(Graphics g)
    {
        int ancho = getSize().width;
        int alto = getSize().height;
        FontMetrics Fuente = getFontMetrics(getFont());
        int x = Fuente.stringWidth("DC");
        if(posLetras)
        {
            g.drawImage(super.imagen[super.posImagen], x, 0, this);
            x = 0;
        } else
        {
            g.drawImage(super.imagen[super.posImagen], 0, 0, this);
            x = super.imagen[super.posImagen].getWidth(this);
        }
        if(estado == 2)
        {
            g.setColor(Color.red);
        } else
        {
            g.setColor(Color.orange);
        }
        g.drawString("DC", x, getSize().height / 3);
        if(estado == 1)
        {
            g.setColor(Color.red);
        } else
        {
            g.setColor(Color.orange);
        }
        g.drawString("AC", x, (getSize().height * 2) / 3);
        if(estado == 0)
        {
            g.setColor(Color.red);
        } else
        {
            g.setColor(Color.orange);
        }
        g.drawString("GD", x, getSize().height);
    }
}
