package es.upv.simulator;

import java.io.*;

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

public class Tokenizer extends StreamTokenizer
{

    public Tokenizer(InputStream archivo)
    {
        super(archivo);
    }

    public char readChar()
    {
        return readWord().charAt(0);
    }

    public String readWord()
    {
        try
        {
            nextToken();
            if(super.ttype != -3 || super.sval == null)
            {
                String s = null;
                return s;
            } else
            {
                String s1 = super.sval;
                return s1;
            }
        }
        catch(IOException e)
        {
            String s2 = null;
            return s2;
        }
    }

    public String readLine()
    {
        wordChars(32, 122);
        String linea = readWord();
        whitespaceChars(0, 32);
        return linea;
    }

    public boolean readBoolean()
    {
        return "true".equalsIgnoreCase(readWord());
    }

    public int readInt()
    {
        return (int)readDouble();
    }

    public float readFloat()
    {
        return (float)readDouble();
    }

    public double readDouble()
    {
        try
        {
            nextToken();
            if(super.ttype != -2)
            {
                double d = 0.0D;
                return d;
            } else
            {
                double num = super.nval;
                double d1 = num;
                return d1;
            }
        }
        catch(IOException e)
        {
            double d2 = 0.0D;
            return d2;
        }
    }

    public String buscarArchivo(String ext)
    {
        try
        {
            while(super.ttype != -1)
            {
                nextToken();
                if(super.ttype == -3 && super.sval != null && super.sval.toUpperCase().endsWith(ext.toUpperCase()))
                {
                    String s = super.sval;
                    return s;
                }
            }
        }
        catch(IOException ioexception) { }
        return null;
    }
}
