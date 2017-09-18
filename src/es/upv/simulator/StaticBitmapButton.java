package es.upv.simulator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
//            Mensaje

public class StaticBitmapButton extends Canvas
    implements MouseListener
{

    private MediaTracker tracker;
    protected Image imagen[];
    protected int posImagen;
    protected String titulo;

    public StaticBitmapButton(URL path, String Archivos[])
    {
        int i = 0;
        tracker = new MediaTracker(this);
        imagen = new Image[Archivos.length];
        titulo = "";
        try
        {
            for(i = 0; i < Archivos.length; i++)
            {
                imagen[i] = getToolkit().getImage(new URL(path + Archivos[i]));
                tracker.addImage(imagen[i], 0);
            }

            tracker.waitForAll();
        }
        catch(Exception e)
        {
            Mensaje.makeDialog("Error " + e.getMessage() + " cargando imagen:" + path + Archivos[i], "Error", true).setVisible(true);
        }
        posImagen = 0;
        setCursor(new Cursor(12));
        setBackground(new Color(90, 90, 90));
        setForeground(new Color(80, 80, 100));
        addMouseListener(this);
    }

    public void putTitulo(String tit)
    {
        titulo = tit;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
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

    public Dimension getMinimumSize()
    {
        int ancho = imagen[0].getWidth(this);
        int alto = imagen[0].getHeight(this);
        FontMetrics Fuente = getFontMetrics(getFont());
        if(ancho < Fuente.stringWidth(titulo))
        {
            ancho = Fuente.stringWidth(titulo);
        }
        if(titulo.length() > 0)
        {
            alto += Fuente.getHeight();
        }
        return new Dimension(ancho, alto);
    }

    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);
        setSize(getMinimumSize());
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        dibuja(g);
    }

    public void dibuja(Graphics g)
    {
        int ancho = getSize().width;
        int alto = getSize().height;
        FontMetrics Fuente = getFontMetrics(getFont());
        int y;
        if(titulo.length() > 0)
        {
            y = Fuente.getHeight();
        } else
        {
            y = 0;
        }
        g.setColor(Color.orange);
        g.drawString(titulo, ancho / 2 - Fuente.stringWidth(titulo) / 2, y - 3);
        g.drawImage(imagen[posImagen], ancho / 2 - imagen[0].getWidth(this) / 2, y, this);
    }
}
