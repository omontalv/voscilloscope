package es.upv.simulator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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

// Referenced classes of package es.upv.simulator:
//            StaticBitmapButton

public class RotateBitmap extends StaticBitmapButton
    implements MouseMotionListener
{

    private float Valores[];
    private int PosValor;

    public RotateBitmap(URL path, String Archivos[], float Valores[])
    {
        super(path, Archivos);
        this.Valores = Valores;
        PosValor = 0;
        addMouseMotionListener(this);
    }

    public float getValor()
    {
        return Valores[PosValor];
    }

    public void setValor(float valor)
    {
        boolean esta = false;
        int i = 0;
        do
        {
            if(i >= Valores.length || esta)
            {
                break;
            }
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
            super.posImagen = i % super.imagen.length;
        } else
        {
            System.out.println("No exite ese valor " + valor + " en el componente " + this);
        }
        repaint();
    }

    public int getPosicion()
    {
        return PosValor;
    }

    public void setPosicion(int pos)
    {
        PosValor = pos;
    }

    protected double getAngulo(int x, int y, int centrox, int centroy)
    {
        double c2 = y - centroy;
        double c1 = centrox - x;
        double h = Math.sqrt(c1 * c1 + c2 * c2);
        if(h == (double)0)
        {
            return 0.0D;
        }
        if(y < centroy)
        {
            return 3.1415926535897931D + (3.1415926535897931D - Math.acos(c1 / h));
        } else
        {
            return Math.acos(c1 / h);
        }
    }

    protected int nuevaPosicion(int x, int y, int centrox, int centroy)
    {
        int posAnt = PosValor % 44;
        double angulo = getAngulo(x, y, centrox, centroy);
        int posNuevo = (int)(((double)22 * angulo) / 3.1415926535897931D);
        int inc = posNuevo - posAnt;
        if(inc > 22)
        {
            inc = -44 + inc;
        }
        if(inc < -22)
        {
            inc = 44 + inc;
        }
        return inc;
    }

    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        FontMetrics Fuente = getFontMetrics(getFont());
        if(x >= 0 && x <= getSize().width && y >= 0 && y <= getSize().height)
        {
            int centroy;
            if(super.titulo.length() > 0)
            {
                centroy = 16 + Fuente.getHeight();
            } else
            {
                centroy = 16;
            }
            int centrox = 32;
            PosValor = PosValor + nuevaPosicion(x, y, centrox, centroy);
            if(PosValor < 0)
            {
                PosValor = 0;
            } else
            if(PosValor >= Valores.length)
            {
                PosValor = Valores.length - 1;
            }
            super.posImagen = PosValor % super.imagen.length;
            dibuja(getGraphics());
        }
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
        mouseClicked(e);
    }
}
