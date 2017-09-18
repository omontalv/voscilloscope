package es.upv.simulator;

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

public interface Evaluation
{

    public abstract String getParameterType(String s);

    public abstract String[] getAllParameters();

    public abstract boolean hasParameter(String s);

    public abstract void setValue(String s, Object obj);

    public abstract void setValueBoolean(String s, boolean flag);

    public abstract void setValueInt(String s, int i);

    public abstract void setValueFloat(String s, float f);

    public abstract void setValueString(String s, String s1);

    public abstract void setValueVoid(String s);

    public abstract boolean getValueBoolean(String s);

    public abstract int getValueInt(String s);

    public abstract float getValueFloat(String s);

    public abstract String getValueString(String s);

    public abstract void setRandomValue(String s);

    public abstract void setRandomValueAll();

    public static final String NONE = "NONE";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String INT = "INT";
    public static final String FLOAT = "FLOAT";
    public static final String STRING = "STRING";
    public static final String EXECUTE = "EXECUTE";
    public static final String PLAY = "PLAY";
    public static final String RESET = "RESET";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String STEPBACK = "STEPBACK";
    public static final String STEPFORWARD = "STEPFORWARD";
    public static final String VISIBLE = "VISIBLE";
}
