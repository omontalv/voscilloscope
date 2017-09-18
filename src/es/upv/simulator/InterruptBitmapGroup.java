package es.upv.simulator;

import java.awt.Container;
import java.awt.Panel;
import java.util.Vector;

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
//            InterruptBitmap, BooleanBitmap

public class InterruptBitmapGroup extends Panel
{

    private Vector values;

    public InterruptBitmapGroup()
    {
    }

    public InterruptBitmap getInterruptBitmap()
    {
        for(int i = 0; i < countComponents(); i++)
        {
            if(!(getComponent(i) instanceof InterruptBitmap))
            {
                continue;
            }
            InterruptBitmap bb = (InterruptBitmap)getComponent(i);
            if(bb.getValor())
            {
                return bb;
            }
        }

        return null;
    }

    public void setInterruptBitmap(InterruptBitmap bb)
    {
        for(int i = 0; i < countComponents(); i++)
        {
            if(!(getComponent(i) instanceof InterruptBitmap))
            {
                continue;
            }
            InterruptBitmap button = (InterruptBitmap)getComponent(i);
            if(button == bb)
            {
                button.setValor(true);
            } else
            {
                button.setValor(false);
            }
        }

    }
}

