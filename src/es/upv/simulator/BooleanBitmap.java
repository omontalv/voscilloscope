package es.upv.simulator;

import java.awt.Component;
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

public class BooleanBitmap extends StaticBitmapButton
{

    public BooleanBitmap(URL path, String Archivos[])
    {
        super(path, Archivos);
        estado = false;
    }

    public boolean getValor()
    {
        return estado;
    }

    public void setValor(boolean e)
    {
        estado = e;
        super.posImagen = estado ? 1 : 0;
        repaint();
    }

    public static final boolean ON = true;
    public static final boolean OFF = false;
    protected boolean estado;
}
