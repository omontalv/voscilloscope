package es.upv.oscilloscope;

import es.upv.simulator.Tokenizer;
import java.io.*;
import java.net.MalformedURLException;
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

// Referenced classes of package es.upv.scope:
//            OperatorSignalException, DlgOpAdq, ClienteSenyal, DlgArchivo

public class Signal
{

    public static final int AC = 1;
    public static final int DC = 2;
    public static final int GND = 0;
    public static final int MAX12BITS = 4096;
    private boolean ciclica;
    private boolean invert;
    private boolean visible;
    private int yPos;
    private float vDiv;
    private int muestras[];
    private int nMuestras;
    private float posMuestra;
    private int tipoSenyal;
    private int nivelContinua;
    private float velozMuestreo;
    private float valorMax;
    private float valorMin;

    public synchronized void rebobinar()
    {
        posMuestra = 0.0F;
    }

    public synchronized float getTensionMax()
    {
        return valorMax;
    }

    public synchronized void setTensionMax(float tensionMax)
    {
        valorMax = tensionMax;
    }

    public synchronized float getTensionMin()
    {
        return valorMin;
    }

    public synchronized void setTensionMin(float tensionMin)
    {
        valorMax = tensionMin;
    }

    public synchronized float getTension(int pos)
    {
        if(pos >= nMuestras || pos < 0)
        {
            return 0.0F;
        }
        float valor = ((float)muestras[pos] * (valorMax - valorMin)) / (float)4096 + (valorMax + valorMin) / (float)2;
        if(tipoSenyal == 1)
        {
            valor -= getContinua();
        }
        return valor;
    }

    public synchronized void setTension(float valor, int pos)
    {
        muestras[pos] = (int)(((float)4096 * (valor - valorMin)) / (valorMax - valorMin) - (float)2048);
    }

    public synchronized float sigMuestra(float time)
    {
        float valor;
        if(nMuestras == 0 || tipoSenyal == 0 || posMuestra >= (float)nMuestras)
        {
            valor = 0.0F;
        } else
        {
            int x = (int)posMuestra;
            float ini = getTension(x);
            float fin = getTension(ciclica ? (x + 1) % nMuestras : x + 1);
            valor = (fin - ini) * (posMuestra - (float)x) + ini;
            float Inc = velozMuestreo * (float)10 * time;
            avanzar(Inc);
        }
        return (invert ? valor : -valor) + (float)yPos;
    }

    public Signal()
    {
        yPos = 0;
        ciclica = false;
        invert = visible = false;
        vDiv = 2.0F;
        nMuestras = 0;
        posMuestra = 0.0F;
        tipoSenyal = 2;
        nivelContinua = 0;
        velozMuestreo = 0.0F;
        valorMax = 20F;
    }

    public Signal(InputStream is)
    {
        this();
        setMuestras(is);
    }

    public void setMuestras(InputStream fMuestra)
    {
        nMuestras = 0;
        posMuestra = 0.0F;
        velozMuestreo = 6000F;
        try
        {
            Tokenizer tok = new Tokenizer(fMuestra);
            tok.whitespaceChars(58, 255);
            tok.wordChars(48, 57);
            valorMax = tok.readFloat();
            valorMin = tok.readFloat();
            nMuestras = tok.readInt();
            velozMuestreo = tok.readInt();
            int longitud = nMuestras * 2;
            byte buf[] = new byte[longitud];
            fMuestra.skip(1L);
            int num = fMuestra.read(buf, 0, longitud);
            if(num >= 0)
            {
                nMuestras = num / 2;
            }
            muestras = new int[nMuestras];
            System.out.println("valorMax:" + valorMax);
            System.out.println("valorMin:" + valorMin);
            for(int i = 0; i < nMuestras; i++)
            {
                muestras[i] = (buf[i * 2] + 128 | (buf[i * 2 + 1] + 128 & 0xff) << 8) - 2048;
            }

            fMuestra.close();
        }
        catch(IOException e)
        {
            System.out.println("Error en el flujo de entrada:" + e);
        }
    }

    public void setMuestras(byte bmuestras[])
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(bmuestras);
        setMuestras(((InputStream) (bais)));
    }

    public synchronized int getLength()
    {
        return tipoSenyal != 0 ? nMuestras : 0;
    }

    public synchronized float getVelozMuestreo()
    {
        return velozMuestreo;
    }

    public synchronized void sinContinua()
    {
        tipoSenyal = 1;
    }

    public synchronized void conContinua()
    {
        tipoSenyal = 2;
    }

    public synchronized void senyalGND()
    {
        tipoSenyal = 0;
    }

    public synchronized int getTipoSenyal()
    {
        return tipoSenyal;
    }

    private static int calculaContinua(Signal sig)
    {
        int cont = 0;
        for(int i = 0; i < sig.nMuestras; i++)
        {
            cont += sig.muestras[i];
        }

        if(sig.nMuestras > 0)
        {
            cont /= sig.nMuestras;
        }
        return cont;
    }

    private static float calculaValorMax(float tensiones[])
    {
        float max = (-1.0F / 0.0F);
        for(int i = 0; i < tensiones.length; i++)
        {
            if(max < tensiones[i])
            {
                max = tensiones[i];
            }
        }

        return max;
    }

    private static float calculaValorMin(float tensiones[])
    {
        float min = (1.0F / 0.0F);
        for(int i = 0; i < tensiones.length; i++)
        {
            if(min > tensiones[i])
            {
                min = tensiones[i];
            }
        }

        return min;
    }

    public synchronized void avanzar(float inc)
    {
        posMuestra = ciclica ? (posMuestra + inc) % (float)nMuestras : posMuestra + inc;
    }

    public void conectaSenyal(URL dir, SignalClient cs)
    {
        DlgOpAdq dlgOp = DlgOpAdq.makeDialog();
        int op;
        if(cs.hasSenyal())
        {
            op = dlgOp.showModal(new Signal(new ByteArrayInputStream(cs.getDatos())));
        } else
        {
            op = dlgOp.showModal();
        }
        if(op != DlgOpAdq.CANCELAR)
        {
            if(dlgOp.getOpcion() == DlgOpAdq.ARCHIVO)
            {
                DlgArchivo dlgArchivo = DlgArchivo.makeDialog(dir);
                try
                {
                    if(dlgArchivo.showModal())
                    {
                        setMuestras((new URL(dir + dlgArchivo.getFile())).openStream());
                    }
                }
                catch(MalformedURLException e)
                {
                    System.out.println("Mal formada URL:" + e);
                }
                catch(IOException e)
                {
                    System.out.println("Error de E/S:" + e);
                }
            } else
            {
                synchronized(cs)
                {
                    if(cs.hasSenyal())
                    {
                        setMuestras(new ByteArrayInputStream(cs.getDatos()));
                    } else
                    {
                        System.out.println("No hay se\361al por el puerto 5454");
                    }
                }
            }
        }
        nivelContinua = calculaContinua(this);
    }

    public void conectaSenyal(byte datos[])
    {
        //DlgOpAdq dlgOp = DlgOpAdq.makeDialog();
        setMuestras(new ByteArrayInputStream(datos));
        nivelContinua = calculaContinua(this);
    }

    public synchronized float getPosMuestra()
    {
        return posMuestra;
    }

    public synchronized void setPosMuestra(float pos)
    {
        posMuestra = pos;
    }

    public synchronized boolean isInvert()
    {
        return invert;
    }

    public synchronized void setInvert(boolean inv)
    {
        invert = inv;
    }

    public synchronized int getYPos()
    {
        return yPos;
    }

    public synchronized void setYPos(int pos)
    {
        yPos = pos;
    }

    public synchronized boolean isVisible()
    {
        return visible;
    }

    public synchronized void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public synchronized void setCiclica(boolean ciclica)
    {
        this.ciclica = ciclica;
    }

    public synchronized boolean isCiclica()
    {
        return ciclica;
    }

    public synchronized void setVDiv(float vdiv)
    {
        vDiv = vdiv;
    }

    public synchronized float getVDiv()
    {
        return vDiv;
    }

    public synchronized float getContinua()
    {
        return ((valorMax - valorMin) * (float)nivelContinua) / (float)4096 + (valorMax + valorMin) / (float)2;
    }

    public static Signal suma(Signal a, Signal b)
        throws OperatorSignalException
    {
        Signal resul = new Signal();
        if(a.nMuestras != b.nMuestras && a.velozMuestreo != b.velozMuestreo)
        {
            throw new OperatorSignalException("La se\361ales no tienen el mismo n\372mero de muestras o la misma frecuencia de " +
"muestreo"
);
        }
        resul.nMuestras = a.nMuestras;
        resul.velozMuestreo = a.velozMuestreo;
        resul.muestras = new int[resul.nMuestras];
        float tensiones[] = new float[resul.nMuestras];
        for(int i = 0; i < resul.nMuestras; i++)
        {
            tensiones[i] = a.getTension(i) + b.getTension(i);
        }

        resul.valorMax = calculaValorMax(tensiones);
        resul.valorMin = calculaValorMin(tensiones);
        for(int i = 0; i < resul.nMuestras; i++)
        {
            resul.setTension(tensiones[i], i);
        }

        resul.nivelContinua = calculaContinua(resul);
        return resul;
    }

    public static Signal resta(Signal a, Signal b)
        throws OperatorSignalException
    {
        Signal resul = new Signal();
        if(a.nMuestras != b.nMuestras && a.velozMuestreo != b.velozMuestreo)
        {
            throw new OperatorSignalException("La se\361ales no tienen el mismo n\372mero de muestras o la misma frecuencia de " +
"muestreo"
);
        }
        resul.nMuestras = a.nMuestras;
        resul.velozMuestreo = a.velozMuestreo;
        resul.muestras = new int[resul.nMuestras];
        float tensiones[] = new float[resul.nMuestras];
        for(int i = 0; i < resul.nMuestras; i++)
        {
            tensiones[i] = a.getTension(i) - b.getTension(i);
        }

        resul.valorMax = calculaValorMax(tensiones);
        resul.valorMin = calculaValorMin(tensiones);
        for(int i = 0; i < resul.nMuestras; i++)
        {
            resul.setTension(tensiones[i], i);
        }

        resul.nivelContinua = calculaContinua(resul);
        return resul;
    }

    public static Signal multiplica(Signal a, Signal b)
        throws OperatorSignalException
    {
        Signal resul = new Signal();
        if(a.nMuestras != b.nMuestras && a.velozMuestreo != b.velozMuestreo)
        {
            throw new OperatorSignalException("La se\361ales no tienen el mismo n\372mero de muestras o la misma frecuencia de " +
"muestreo"
);
        }
        resul.nMuestras = a.nMuestras;
        resul.velozMuestreo = a.velozMuestreo;
        resul.muestras = new int[resul.nMuestras];
        float tensiones[] = new float[resul.nMuestras];
        for(int i = 0; i < resul.nMuestras; i++)
        {
            tensiones[i] = a.getTension(i) * b.getTension(i);
        }

        resul.valorMax = calculaValorMax(tensiones);
        resul.valorMin = calculaValorMin(tensiones);
        for(int i = 0; i < resul.nMuestras; i++)
        {
            resul.setTension(tensiones[i], i);
        }

        resul.nivelContinua = calculaContinua(resul);
        return resul;
    }

    public static Signal generaSenoidal(float Vp, float cont, int num, float freq, float periodo, float desfase)
    {
        Signal resul = new Signal();
        resul.nMuestras = num;
        resul.velozMuestreo = (freq * (float)num) / periodo;
        resul.muestras = new int[num];
        resul.valorMax = Vp + cont;
        resul.valorMin = -Vp + cont;
        for(int i = 0; i < num; i++)
        {
            resul.setTension((float)Math.sin(((double)(i * 2) * 3.1415926535897931D * (double)periodo) / (double)num + (double)desfase) * Vp + cont, i);
        }

        resul.nivelContinua = calculaContinua(resul);
        return resul;
    }

    public static Signal generaCuadrada(float nAlto, float nBajo, float nDisp, int num, float freq, float periodo, float desfase)
    {
        Signal resul = new Signal();
        resul.nMuestras = num;
        resul.velozMuestreo = freq;
        resul.muestras = new int[num];
        resul.valorMax = nAlto;
        resul.valorMin = nBajo;
        for(int i = 0; i < num; i++)
        {
            resul.setTension(Math.sin(((float)i * periodo) / (float)num + desfase) * (double)(nAlto - nBajo) + (double)(nAlto + nBajo) < (double)nDisp ? nBajo : nAlto, i);
        }

        resul.nivelContinua = calculaContinua(resul);
        return resul;
    }

    public void sendSignal(OutputStream fMuestras)
        throws IOException
    {
        fMuestras.write((new Float(valorMax)).toString().getBytes());
        fMuestras.write(58);
        fMuestras.write((new Float(valorMin)).toString().getBytes());
        fMuestras.write(58);
        fMuestras.write((new Integer(nMuestras)).toString().getBytes());
        fMuestras.write(58);
        fMuestras.write((new Float(velozMuestreo)).toString().getBytes());
        fMuestras.write(58);
        fMuestras.write(13);
        for(int i = 0; i < nMuestras; i++)
        {
            fMuestras.write(muestras[i] - 128);
            fMuestras.write((muestras[i] + 2048 >> 8) - 128);
        }

    }
}
