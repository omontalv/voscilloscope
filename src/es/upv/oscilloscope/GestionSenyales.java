package es.upv.oscilloscope;

import es.upv.simulator.SuspendThread;
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
//            Senyal, Pantalla

public class GestionSenyales extends SuspendThread
{

    public final double PI = 3.1415959999999998D;
    public final boolean AUTOMATIC = true;
    public final boolean NORMAL = false;
    public final boolean POS = false;
    public final boolean NEG = true;
    public Signal canal1;
    public Signal canal2;
    private int xPos;
    private float time;
    private boolean xy;
    private boolean atNormal;
    private int nivelDisparo;
    private boolean disPosNeg;
    private int posMuestra;
    private int holdOff;
    private boolean xMag;
    private boolean add;
    private boolean ch12;
    private int calidad;
    private boolean digital;
    private Screen pantalla;

    public synchronized void rebobinar()
    {
        canal1.rebobinar();
        canal2.rebobinar();
    }

    public GestionSenyales(Screen pan)
    {
        canal1 = new Signal();
        canal2 = new Signal();
        pantalla = pan;
    }

    public void run()
    {
        do
        {
            dibujaSenyales();
        } while(true);
    }

    public void dibujaSenyales()
    {
        float pos1 = 0.0F;
        float pos2 = 0.0F;
        int ancho = pantalla.getSize().width;
        boolean disp = true;
        if(digital)
        {
            pos1 = posMuestra;
            pos2 = posMuestra;
        } else
        {
            avanzaHoldOff();
            disp = buscaPrimerValor();
        }
        if(xy)
        {
            pantalla.borra();
        }
        for(int x = xPos - 19; x < ancho; x++)
        {
            if(disp)
            {
                dibujaSenyal(x);
            } else
            {
                pantalla.dibujaCol(x);
            }
            Thread.yield();
        }

        if(digital)
        {
            canal1.setPosMuestra(pos1);
            canal2.setPosMuestra(pos2);
        }
    }

    public synchronized void dibujaSenyal(int x)
    {
        int ancho = pantalla.getSize().width;
        int alto = pantalla.getSize().height;
        for(int i = 0; i < calidad; i++)
        {
            float valor1 = canal1.sigMuestra(time / (float)((xMag ? 10 * ancho : ancho) * calidad));
            float valor2 = canal2.sigMuestra(time / (float)((xMag ? 10 * ancho : ancho) * calidad));
            int y1;
            int y2;
            if(xy)
            {
                y1 = (int)((valor1 * (float)ancho) / ((float)10 * canal1.getVDiv()));
                y2 = (int)((valor2 * (float)alto) / ((float)10 * canal2.getVDiv()));
                pantalla.putPixel(y1 + ancho / 2, y2, Color.yellow);
                continue;
            }
            y1 = (int)((valor1 * (float)alto) / ((float)10 * canal1.getVDiv()));
            y2 = (int)((valor2 * (float)alto) / ((float)10 * canal2.getVDiv()));
            pantalla.dibujaCol(x);
            if(add)
            {
                pantalla.putPixel(x, y1 + y2, Color.yellow);
                continue;
            }
            if(canal1.isVisible())
            {
                pantalla.putPixel(x, y1, Color.yellow);
            }
            if(canal2.isVisible())
            {
                pantalla.putPixel(x, y2, Color.cyan);
            }
        }

    }

    private synchronized void avanzaHoldOff()
    {
        int ancho = pantalla.getSize().width;
        canal1.avanzar(((float)holdOff * time * (float)ancho * canal1.getVelozMuestreo()) / (float)10);
        canal2.avanzar(((float)holdOff * time * (float)ancho * canal2.getVelozMuestreo()) / (float)10);
    }

    private synchronized boolean buscaPrimerValor()
    {
        int alto = pantalla.getSize().height;
        Signal canal;
        if(ch12)
        {
            canal = canal2;
        } else
        {
            canal = canal1;
        }
        float nivel;
        if(!atNormal)
        {
            nivel = (canal.getVDiv() * (float)nivelDisparo) / (float)20;
        } else
        if(add)
        {
            nivel = canal1.getContinua() + canal2.getContinua();
        } else
        {
            nivel = canal.getContinua();
        }
        int tamanyo;
        if(add)
        {
            tamanyo = canal1.getLength();
            if(canal2.getLength() > tamanyo)
            {
                tamanyo = canal2.getLength();
            }
            if(tamanyo == 0)
            {
                return true;
            }
            for(int i = tamanyo; i > 0; i--)
            {
                float valor1ant = canal1.getTension((int)canal1.getPosMuestra());
                float valor2ant = canal2.getTension((int)canal2.getPosMuestra());
                canal1.avanzar(1.0F);
                canal2.avanzar(1.0F);
                float valor1 = canal1.getTension((int)canal1.getPosMuestra());
                float valor2 = canal2.getTension((int)canal2.getPosMuestra());
                if(!disPosNeg && valor1 + valor2 <= valor1ant + valor2ant && valor1ant + valor2ant >= nivel && valor1 + valor2 <= nivel)
                {
                    return true;
                }
                if(disPosNeg && valor1 + valor2 >= valor1ant + valor2ant && valor1ant + valor2ant <= nivel && valor1 + valor2 >= nivel)
                {
                    return true;
                }
            }

            return false;
        }
        tamanyo = canal.getLength();
        if(tamanyo == 0)
        {
            return true;
        }
        for(int i = tamanyo; i > 0; i--)
        {
            float valor1ant = canal.getTension((int)canal.getPosMuestra());
            canal1.avanzar(1.0F);
            canal2.avanzar(1.0F);
            float valor1 = canal.getTension((int)canal.getPosMuestra());
            if(!disPosNeg && valor1 <= valor1ant && valor1ant >= nivel && valor1 <= nivel)
            {
                return true;
            }
            if(disPosNeg && valor1 >= valor1ant && valor1ant <= nivel && valor1 >= nivel)
            {
                return true;
            }
        }

        return false;
    }

    public synchronized boolean isDigital()
    {
        return digital;
    }

    public synchronized void setXPos(int pos)
    {
        xPos = pos;
    }

    public synchronized int getXPos()
    {
        return xPos;
    }

    public synchronized void setTime(float time)
    {
        this.time = time;
    }

    public synchronized float getTime()
    {
        return time;
    }

    public synchronized void setXY(boolean xy)
    {
        if(this.xy != xy)
        {
            rebobinar();
            this.xy = xy;
        }
    }

    public synchronized boolean isXY()
    {
        return xy;
    }

    public synchronized void setAtNormal(boolean atnormal)
    {
        atNormal = atnormal;
    }

    public synchronized boolean getAtNormal()
    {
        return atNormal;
    }

    public synchronized void setNivelDisparo(int nivel)
    {
        nivelDisparo = nivel;
    }

    public synchronized int getNivelDisparo()
    {
        return nivelDisparo;
    }

    public synchronized void setDisPosNeg(boolean disparo)
    {
        disPosNeg = disparo;
    }

    public synchronized boolean getDisPosNeg()
    {
        return disPosNeg;
    }

    public synchronized void setHoldOff(int hold)
    {
        holdOff = hold;
    }

    public synchronized int getHoldOff()
    {
        return holdOff;
    }

    public synchronized void setXMag(boolean xmag)
    {
        xMag = xmag;
    }

    public synchronized boolean isXMag()
    {
        return xMag;
    }

    public synchronized void setAdd(boolean add)
    {
        if(this.add != add)
        {
            this.add = add;
            rebobinar();
        }
    }

    public synchronized boolean isAdd()
    {
        return add;
    }

    public synchronized boolean isDual()
    {
        return canal1.isVisible() && canal2.isVisible();
    }

    public synchronized void setCalidad(int calidad)
    {
        this.calidad = calidad;
    }

    public synchronized int getCalidad()
    {
        return calidad;
    }

    public synchronized void setCH12(boolean ch12)
    {
        this.ch12 = ch12;
    }

    public synchronized boolean getCH12()
    {
        return ch12;
    }

    public synchronized void setDigital(boolean dig)
    {
        digital = dig;
    }

    public synchronized void setPosMuestra(int pos)
    {
        posMuestra = pos;
    }
}
