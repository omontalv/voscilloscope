package es.upv.oscilloscope;


import java.awt.*;

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
//            PFondo

public class Screen extends Canvas
{

    private boolean enabled;
    private int intens;
    private int foco;
    private int nivel;
    private Graphics g;

    public synchronized void dibujaCol(int x)
    {
        int ancho = getSize().width;
        int alto = getSize().height;
        int div10Width = ancho / 10;
        int div10Height = alto / 10;
        int div50Width = ancho / 50;
        int div50Height = alto / 50;
        if(x >= 0 && x <= ancho)
        {
            g.setColor(Color.black);
            g.drawLine(x, 0, x, alto - 2);
            g.setColor(Color.green);
            g.drawLine(x, alto - 1, x, alto - 1);
            for(int i = 0; i <= alto; i += div10Height)
            {
                g.drawLine(x, i, x, i);
            }

            if(x >= (ancho >> 1) - 3 && x <= (ancho >> 1) + 3)
            {
                for(int i = 1; i < 50; i++)
                {
                    g.drawLine(x, div50Height * i, x, div50Height * i);
                }

            }
            if(x % div10Width == 0)
            {
                g.drawLine(x, 0, x, alto);
            } else
            if(x % div50Width == 0)
            {
                g.drawLine(x, (alto >> 1) - 3, x, (alto >> 1) + 3);
            } else
            if(x == ancho - 1)
            {
                g.drawLine(x, 0, x, alto);
            }
        }
    }

    public synchronized void setNivel(int nivel)
    {
        borraNivel();
        this.nivel = nivel;
        dibujaNivel();
    }

    private synchronized void borraNivel()
    {
        int alto = getSize().height;
        int x = getLocation().x - 10;
        int y = (getLocation().y + getSize().height / 2) - nivel;
        Graphics g = getParent().getGraphics();
        if(y < getLocation().y || y > getLocation().y + alto)
        {
            return;
        } else
        {
            g.setColor(PFondo.relleno);
            g.drawLine(x, y - 2, x, y + 2);
            g.setColor(PFondo.fondo);
            g.drawLine(x + 1, y - 2, x + 1, y + 2);
            g.drawLine(x + 2, y - 2, x + 2, y + 2);
            g.drawLine(x + 3, y - 4, x + 3, y + 5);
            g.drawLine(x + 4, y - 2, x + 4, y + 2);
            g.setColor(Color.black);
            g.drawLine(x + 5, y - 2, x + 5, y + 2);
            return;
        }
    }

    private synchronized void dibujaNivel()
    {
        int alto = getSize().height;
        int x = getLocation().x - 10;
        int y = (getLocation().y + alto / 2) - nivel;
        Graphics g = getParent().getGraphics();
        if(y < getLocation().y || y > getLocation().y + alto)
        {
            return;
        } else
        {
            g.setColor(Color.red);
            g.drawLine(x, y - 2, x, y + 2);
            g.drawLine(x + 1, y - 2, x + 1, y + 2);
            g.drawLine(x + 2, y - 2, x + 2, y + 2);
            g.drawLine(x + 3, y - 4, x + 3, y + 4);
            g.drawLine(x + 4, y - 2, x + 4, y + 2);
            g.drawLine(x + 5, y, x + 5, y);
            return;
        }
    }

    private synchronized void dibujaEjes()
    {
        int ancho = getSize().width;
        int alto = getSize().height;
        g.setColor(Color.green);
        g.drawRect(0, 0, ancho - 1, alto - 1);
        for(int i = 1; i < 10; i++)
        {
            g.drawLine((ancho * i) / 10, 0, (ancho * i) / 10, alto);
            g.drawLine(0, (alto * i) / 10, ancho, (alto * i) / 10);
        }

        for(int i = 1; i < 50; i++)
        {
            g.drawLine((ancho * i) / 50, alto / 2 - 3, (ancho * i) / 50, alto / 2 + 3);
            g.drawLine(ancho / 2 - 3, (alto * i) / 50, ancho / 2 + 3, (alto * i) / 50);
        }

    }

    public synchronized void dibujaFondo()
    {
      g = getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getSize().width, getSize().height);
        dibujaEjes();
    }

    public Screen()
    {
        foco = 0;
        intens = 0;
        nivel = 0;
    }

    public synchronized void addNotify()
    {
        super.addNotify();
        setBackground(Color.black);
        setForeground(Color.black);
        g = getGraphics();
    }

    public synchronized void encender()
    {
        enabled = true;
    }

    public synchronized void apagar()
    {
        enabled = false;
    }

    public synchronized boolean isPower()
    {
        return enabled;
    }

    public synchronized void setIntens(int intensidad)
    {
        intens = intensidad;
    }

    public synchronized int getIntens()
    {
        return intens;
    }

    public synchronized void setFoco(int foco)
    {
        this.foco = foco;
    }

    public synchronized int getFoco()
    {
        return foco;
    }

    public synchronized void putPixel(int x, int y, Color color)
    {
        int alto = getSize().height;
        g.setColor(new Color((color.getRed() * intens) / 255, (color.getGreen() * intens) / 255, (color.getBlue() * intens) / 255));
        if(enabled)
        {
            y = alto / 2 - y;
            switch(foco)
            {
            case 17: // '\021'
                g.drawLine(x - 3, y - 2, x - 3, y - 2);
                // fall through

            case 16: // '\020'
                g.drawLine(x - 3, y + 2, x - 3, y + 2);
                // fall through

            case 15: // '\017'
                g.drawLine(x - 4, y - 1, x - 4, y + 1);
                // fall through

            case 14: // '\016'
                g.drawLine(x, y - 1, x, y - 1);
                // fall through

            case 13: // '\r'
                g.drawLine(x, y + 1, x, y + 1);
                // fall through

            case 12: // '\f'
                g.drawLine(x - 2, y - 2, x - 1, y - 2);
                // fall through

            case 11: // '\013'
                g.drawLine(x - 2, y + 2, x - 1, y + 2);
                // fall through

            case 10: // '\n'
                g.drawLine(x - 3, y - 1, x - 3, y - 1);
                // fall through

            case 9: // '\t'
                g.drawLine(x - 3, y + 1, x - 3, y + 1);
                // fall through

            case 8: // '\b'
                g.drawLine(x - 2, y - 1, x - 2, y - 1);
                // fall through

            case 7: // '\007'
                g.drawLine(x - 2, y + 1, x - 2, y + 1);
                // fall through

            case 6: // '\006'
                g.drawLine(x - 3, y, x - 3, y);
                // fall through

            case 5: // '\005'
                g.drawLine(x - 2, y, x - 2, y);
                // fall through

            case 4: // '\004'
                g.drawLine(x - 1, y - 1, x - 1, y - 1);
                // fall through

            case 3: // '\003'
                g.drawLine(x - 1, y + 1, x - 1, y + 1);
                // fall through

            case 2: // '\002'
                g.drawLine(x - 1, y, x - 1, y);
                // fall through

            case 1: // '\001'
                g.drawLine(x, y, x, y);
                break;
            }
        }
    }

    public synchronized void borra()
    {
        dibujaFondo();
    }

    public synchronized void update(Graphics g)
    {
        paint(g);
    }

    public synchronized void repaint()
    {
        paint(getGraphics());
    }

    public synchronized void paint(Graphics g)
    {
        dibujaFondo();
        dibujaNivel();
    }
}
