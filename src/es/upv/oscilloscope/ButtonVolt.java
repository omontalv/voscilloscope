package es.upv.oscilloscope;

import es.upv.simulator.*;
import java.awt.*;
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

public class ButtonVolt extends DoubleRotateBitmap
{

    public ButtonVolt(URL path, String ArchivosGrueso[], float ValoresGrueso[], String ArchivosFino[], float ValoresFino[])
    {
        super(path, ArchivosGrueso, ValoresGrueso, ArchivosFino, ValoresFino);
    }

    public void dibujaValores(Graphics g)
    {
        String SValores[] = {
            "20", "10", "5", "2", "1", ".5", "200", "100", "50", "20",
            "10", "5"
        };
        int x[] = {
            34, 14, 6, 10, 28, 55, 79, 103, 118, 122,
            107, 88
        };
        int y[] = {
            122, 107, 80, 51, 27, 13, 14, 27, 51, 80,
            109, 123
        };
        int n = SValores.length - 1;
        g.setColor(getBackground());
        g.drawLine(70, 14, 70, 0);
        g.drawLine(71, 14, 71, 0);
        g.drawLine(72, 14, 72, 0);
        g.setColor(Color.orange);
        g.drawString("V", 5, 12);
        g.drawString("mV", getSize().width - 22, 12);
        for(int i = 0; i <= n; i++)
        {
            if(i == getPosicion())
            {
                g.setColor(Color.red);
            } else
            {
                g.setColor(Color.orange);
            }
            g.drawString(SValores[n - i], x[n - i], y[n - i]);
        }

    }

    public void btnMove(int x, int y)
    {
        double angulo = getAngulo(x, y, 73, 60);
        if(angulo < 1.2156D && angulo > 0.85050000000000003D)
        {
            setPosicion(11);
        }
        if(angulo < 0.85050000000000003D && angulo > 0.42920000000000003D)
        {
            setPosicion(10);
        }
        if(angulo < 0.42920000000000003D)
        {
            setPosicion(9);
        }
        if(angulo > 5.7744999999999997D)
        {
            setPosicion(8);
        }
        if(angulo < 5.7744999999999997D && angulo > 5.2748999999999997D)
        {
            setPosicion(7);
        }
        if(angulo < 5.2748999999999997D && angulo > 4.7328000000000001D)
        {
            setPosicion(6);
        }
        if(angulo < 4.7328000000000001D && angulo > 4.1193999999999997D)
        {
            setPosicion(5);
        }
        if(angulo < 4.1193999999999997D && angulo > 3.6131000000000002D)
        {
            setPosicion(4);
        }
        if(angulo < 3.6131000000000002D && angulo > 3.1415999999999999D)
        {
            setPosicion(3);
        }
        if(angulo < 3.1415999999999999D && angulo > 2.6377999999999999D)
        {
            setPosicion(2);
        }
        if(angulo < 2.6377999999999999D && angulo > 2.0964D)
        {
            setPosicion(1);
        }
        if(angulo < 2.0964D && angulo > 1.7101999999999999D)
        {
            setPosicion(0);
        }
        super.posImagen = getPosicion();
    }
}
