package es.upv.oscilloscope;

import java.applet.Applet;
import es.upv.util.Parameter;
import es.upv.simulator.Evaluation;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

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

public class VOApplet extends Applet
    implements Evaluation
{
    public Oscilloscope osc;
    private static final int INVALIDRANDOMPARAM = 10;
    Parameter params[] = {
        new Parameter("ACTSENYALES", "BOOLEAN"), new Parameter("ENT1", "STRING"), new Parameter("ENT2", "STRING"), new Parameter("SENYAL", "STRING"), new Parameter("EXECUTE", "NONE"), new Parameter("PLAY", "NONE"), new Parameter("RESET", "NONE"), new Parameter("PAUSE", "NONE"), new Parameter("STOP", "NONE"), new Parameter("VISIBLE", "BOOLEAN"),
        new Parameter("+-", "BOOLEAN"), new Parameter("ACDC1", "INT"), new Parameter("ACDC2", "INT"), new Parameter("ADD", "BOOLEAN"), new Parameter("ATNORMAL", "BOOLEAN"), new Parameter("BTN+-", "BOOLEAN"), new Parameter("BTNACDC1", "INT"), new Parameter("BTNACDC2", "INT"), new Parameter("BTNADD", "BOOLEAN"), new Parameter("BTNATNORMAL", "BOOLEAN"),
        new Parameter("BTNCALIDAD", "INT"), new Parameter("BTNCICLICA1", "BOOLEAN"), new Parameter("BTNCICLICA2", "BOOLEAN"), new Parameter("BTNCH12", "BOOLEAN"), new Parameter("BTNDIGITAL", "BOOLEAN"), new Parameter("BTNDUAL", "BOOLEAN"), new Parameter("BTNFOCO", "INT"), new Parameter("BTNHOLDOFF", "INT"), new Parameter("BTNINTENS", "INT"), new Parameter("BTNINV1", "BOOLEAN"),
        new Parameter("BTNINV2", "BOOLEAN"), new Parameter("BTNLED", "BOOLEAN"), new Parameter("BTNLEVEL", "INT"), new Parameter("BTNPOWER", "BOOLEAN"), new Parameter("BTNTIME", "FLOAT"), new Parameter("BTNTIMEFINO", "FLOAT"), new Parameter("BTNVOLT1", "FLOAT"), new Parameter("BTNVOLT2", "FLOAT"), new Parameter("BTNVOLTFINO1", "FLOAT"), new Parameter("BTNVOLTFINO2", "FLOAT"),
        new Parameter("BTNXMAG", "BOOLEAN"), new Parameter("BTNXPOS", "INT"), new Parameter("BTNXY", "BOOLEAN"), new Parameter("BTNYPOS1", "INT"), new Parameter("BTNYPOS2", "INT"), new Parameter("CALIDAD", "INT"), new Parameter("CICLICA1", "BOOLEAN"), new Parameter("CICLICA2", "BOOLEAN"), new Parameter("CH12", "BOOLEAN"), new Parameter("DIGITAL", "BOOLEAN"),
        new Parameter("DUAL", "BOOLEAN"), new Parameter("FOCO", "INT"), new Parameter("HOLDOFF", "INT"), new Parameter("INTENS", "INT"), new Parameter("INV1", "BOOLEAN"), new Parameter("INV2", "BOOLEAN"), new Parameter("POWER", "BOOLEAN"), new Parameter("TIME", "FLOAT"), new Parameter("VOLT1", "FLOAT"), new Parameter("VOLT2", "FLOAT"),
        new Parameter("XMAG", "BOOLEAN"), new Parameter("XPOS", "INT"), new Parameter("XY", "BOOLEAN"), new Parameter("YPOS1", "INT"), new Parameter("YPOS2", "INT")
    };

    public VOApplet()
    {
    }

    public void init()
    {
      try
      {
        String urlImages = this.getParameter("urlImages");
        System.out.println(urlImages);
        osc = new Oscilloscope(getCodeBase(), new URL(urlImages), this);
        osc.init();
      }
      catch(MalformedURLException mue)
      { mue.printStackTrace(); }
    }

    public void start()
    {
        osc.start();
    }

    public void stop()
    {
    }

    public void destroy()
    {
        osc.destroy();
    }

    public String getParameterType(String param)
    {
        for(int i = 0; i < params.length; i++)
        {
            if(params[i].getName().equalsIgnoreCase(param))
            {
                return params[i].getType();
            }
        }

        return "NONE";
    }

    public String[] getAllParameters()
    {
        String parametros[] = new String[params.length];
        for(int i = 0; i < params.length; i++)
        {
            parametros[i] = params[i].getName();
        }

        return parametros;
    }

    public boolean hasParameter(String param)
    {
        String params[] = getAllParameters();
        for(int i = 0; i < params.length; i++)
        {
            if(params[i].equalsIgnoreCase(param))
            {
                return true;
            }
        }

        return false;
    }

    public void setValue(String param, Object valor)
    {
        if("SENYAL".equalsIgnoreCase(param))
        {
            osc.senyales.canal1 = (Signal)valor;
            osc.senyales.rebobinar();
            if(osc.actSenyales)
            {
                osc.configuraSenyal();
            }
        }
    }

    public void setValueBoolean(String param, boolean valor)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
            return;
        }
        if("BTNPOWER".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnPower.setValor(valor);
        }
        if("BTNLED".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnLed.setValor(valor);
        }
        if("BTNXY".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnXY.setValor(valor);
        }
        if("BTNATNORMAL".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnAtNormal.setValor(valor);
        }
        if("BTN+-".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnMasMenos.setValor(valor);
        }
        if("BTNXMAG".equalsIgnoreCase(param))
        {
            pFondo.pCalidad.BtnX10.setValor(valor);
        }
        if("BTNINV1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnInv1.setValor(valor);
        }
        if("BTNINV2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnInv2.setValor(valor);
        }
        if("BTNCH12".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnCH12.setValor(valor);
        }
        if("BTNDUAL".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnDual.setValor(valor);
        }
        if("BTNADD".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnAdd.setValor(valor);
        }
        if("BTNDIGITAL".equalsIgnoreCase(param))
        {
            pFondo.pCalidad.BtnDigital.setValor(valor);
        }
        if("BTNCICLICA1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnCiclica1.setValor(valor);
        }
        if("BTNCICLICA2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnCiclica2.setValor(valor);
        }
        if("POWER".equalsIgnoreCase(param))
        {
            if(valor)
            {
                pFondo.pantalla.encender();
            } else
            {
                pFondo.pantalla.apagar();
            }
        }
        if("XY".equalsIgnoreCase(param))
        {
            senyales.setXY(valor);
        }
        if("ATNORMAL".equalsIgnoreCase(param))
        {
            senyales.setAtNormal(valor);
        }
        if("+-".equalsIgnoreCase(param))
        {
            senyales.setDisPosNeg(valor);
        }
        if("XMAG".equalsIgnoreCase(param))
        {
            senyales.setXMag(valor);
        }
        if("INV1".equalsIgnoreCase(param))
        {
            senyales.canal1.setInvert(valor);
        }
        if("INV2".equalsIgnoreCase(param))
        {
            senyales.canal2.setInvert(valor);
        }
        if("CH12".equalsIgnoreCase(param))
        {
            senyales.setCH12(valor);
        }
        if("DUAL".equalsIgnoreCase(param))
        {
            senyales.canal1.setVisible(valor);
            senyales.canal2.setVisible(valor);
        }
        if("ADD".equalsIgnoreCase(param))
        {
            senyales.setAdd(valor);
        }
        if("DIGITAL".equalsIgnoreCase(param))
        {
            senyales.setDigital(valor);
        }
        if("CICLICA1".equalsIgnoreCase(param))
        {
            senyales.canal1.setCiclica(valor);
        }
        if("CICLICA2".equalsIgnoreCase(param))
        {
            senyales.canal2.setCiclica(valor);
        }
        if("ACTSENYALES".equalsIgnoreCase(param))
        {
            osc.actSenyales = valor;
        }
        if("VISIBLE".equalsIgnoreCase(param))
        {
            osc.setVisible(valor);
        }
    }

    public void setValueInt(String param, int valor)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
            return;
        }
        if("BTNACDC1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnAcDc1.setValor(valor);
        }
        if("BTNACDC2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnAcDc2.setValor(valor);
        }
        if("BTNYPOS1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnYPos1.setValor(valor);
        }
        if("BTNYPOS2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnYPos2.setValor(valor);
        }
        if("BTNXPOS".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnXPos.setValor(valor);
        }
        if("BTNINTENS".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnIntens.setValor(valor);
        }
        if("BTNFOCO".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnFoco.setValor(valor);
        }
        if("BTNHOLDOFF".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnHoldOff.setValor(valor);
        }
        if("BTNLEVEL".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnLevel.setValor(valor);
        }
        if("BTNCALIDAD".equalsIgnoreCase(param))
        {
            pFondo.pCalidad.BtnCalidad.setValor(valor);
        }
        if("ACDC1".equalsIgnoreCase(param))
        {
            if(valor == 1)
            {
                senyales.canal1.sinContinua();
            } else
            if(valor == 2)
            {
                senyales.canal1.conContinua();
            } else
            {
                senyales.canal1.senyalGND();
            }
        }
        if("ACDC2".equalsIgnoreCase(param))
        {
            if(valor == 1)
            {
                senyales.canal2.sinContinua();
            } else
            if(valor == 2)
            {
                senyales.canal2.conContinua();
            } else
            {
                senyales.canal2.senyalGND();
            }
        }
        if("YPOS1".equalsIgnoreCase(param))
        {
            senyales.canal1.setYPos(valor);
        }
        if("YPOS2".equalsIgnoreCase(param))
        {
            senyales.canal2.setYPos(valor);
        }
        if("XPOS".equalsIgnoreCase(param))
        {
            senyales.setXPos(valor);
        }
        if("INTENS".equalsIgnoreCase(param))
        {
            pFondo.pantalla.setIntens(valor);
        }
        if("FOCO".equalsIgnoreCase(param))
        {
            pFondo.pantalla.setFoco(valor);
        }
        if("HOLDOFF".equalsIgnoreCase(param))
        {
            senyales.setHoldOff(valor);
        }
        if("LEVEL".equalsIgnoreCase(param))
        {
            senyales.setNivelDisparo(valor);
        }
        if("CALIDAD".equalsIgnoreCase(param))
        {
            senyales.setCalidad(valor);
        }
    }

    public void setValueFloat(String param, float valor)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
            return;
        }
        if("BTNTIME".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnTime.setValor(valor);
        }
        if("BTNTIMEFINO".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnTime.setValorFino(valor);
        }
        if("BTNVOLT1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnVolt1.setValor(valor);
        }
        if("BTNVOLT2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnVolt2.setValor(valor);
        }
        if("BTNVOLTFINO1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnVolt1.setValorFino(valor);
        }
        if("BTNVOLTFINO2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnVolt2.setValorFino(valor);
        }
        if("TIME".equalsIgnoreCase(param))
        {
            senyales.setTime(valor);
        }
        if("VOLT1".equalsIgnoreCase(param))
        {
            senyales.canal1.setVDiv(valor);
        }
        if("VOLT2".equalsIgnoreCase(param))
        {
            senyales.canal2.setVDiv(valor);
        }
    }

    public void setValueString(String param, String valor)
    {
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
            return;
        }
        try
        {
            if("ENT1".equalsIgnoreCase(param))
            {
                senyales.canal1.setMuestras((new URL(getCodeBase(), valor)).openStream());
            }
            if("ENT2".equalsIgnoreCase(param))
            {
                senyales.canal2.setMuestras((new URL(getCodeBase(), valor)).openStream());
            }
        }
        catch(MalformedURLException mue)
        {
            System.out.println("Direcci\363n URL mal formada:" + mue);
        }
        catch(IOException ioe)
        {
            System.out.println("Error I/O:" + ioe);
        }
    }

    public void setValueVoid(String param)
    {
        if("EXECUTE".equalsIgnoreCase(param))
        {
            executeSimulator();
        }
        if("PLAY".equalsIgnoreCase(param))
        {
            playSimulator();
        }
        if("RESET".equalsIgnoreCase(param))
        {
            resetSimulator();
        }
        if("PAUSE".equalsIgnoreCase(param))
        {
            pauseSimulator();
        }
        if("STOP".equalsIgnoreCase(param))
        {
            stopSimulator();
        }
    }

    public boolean getValueBoolean(String param)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
        }
        if("BTNPOWER".equalsIgnoreCase(param))
        {
            return pFondo.pTime.btnPower.getValor();
        }
        if("BTNXY".equalsIgnoreCase(param))
        {
            return pFondo.pTime.btnXY.getValor();
        }
        if("BTNATNORMAL".equalsIgnoreCase(param))
        {
            return pFondo.pTime.btnAtNormal.getValor();
        }
        if("BTN+-".equalsIgnoreCase(param))
        {
            return pFondo.pTime.btnMasMenos.getValor();
        }
        if("BTNXMAG".equalsIgnoreCase(param))
        {
            return pFondo.pCalidad.BtnX10.getValor();
        }
        if("BTNINV1".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnInv1.getValor();
        }
        if("BTNINV2".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnInv2.getValor();
        }
        if("BTNCH12".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnCH12.getValor();
        }
        if("BTNDUAL".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnDual.getValor();
        }
        if("BTNADD".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnAdd.getValor();
        }
        if("BTNDIGITAL".equalsIgnoreCase(param))
        {
            return pFondo.pCalidad.BtnDigital.getValor();
        }
        if("BTNCICLICA1".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnCiclica1.getValor();
        }
        if("BTNCICLICA2".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnCiclica2.getValor();
        }
        if("POWER".equalsIgnoreCase(param))
        {
            return pFondo.pantalla.isPower();
        }
        if("XY".equalsIgnoreCase(param))
        {
            return senyales.isXY();
        }
        if("ATNORMAL".equalsIgnoreCase(param))
        {
            return senyales.getAtNormal();
        }
        if("+-".equalsIgnoreCase(param))
        {
            return senyales.getDisPosNeg();
        }
        if("XMAG".equalsIgnoreCase(param))
        {
            return senyales.isXMag();
        }
        if("INV1".equalsIgnoreCase(param))
        {
            return senyales.canal1.isInvert();
        }
        if("INV2".equalsIgnoreCase(param))
        {
            return senyales.canal2.isInvert();
        }
        if("CH12".equalsIgnoreCase(param))
        {
            return senyales.getCH12();
        }
        if("DUAL".equalsIgnoreCase(param))
        {
            return senyales.isDual();
        }
        if("ADD".equalsIgnoreCase(param))
        {
            return senyales.isAdd();
        }
        if("DIGITAL".equalsIgnoreCase(param))
        {
            return senyales.isDigital();
        }
        if("CICLICA1".equalsIgnoreCase(param))
        {
            return senyales.canal1.isCiclica();
        }
        if("CICLICA2".equalsIgnoreCase(param))
        {
            return senyales.canal2.isCiclica();
        }
        if("ACTSENYALES".equalsIgnoreCase(param))
        {
            return osc.actSenyales;
        }
        if("VISIBLE".equalsIgnoreCase(param))
        {
            return osc.isVisible();
        } else
        {
            return false;
        }
    }

    public int getValueInt(String param)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
        }
        if("BTNACDC1".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnAcDc1.getValor();
        }
        if("BTNACDC2".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnAcDc2.getValor();
        }
        if("BTNYPOS1".equalsIgnoreCase(param))
        {
            return (int)pFondo.pVolt.BtnYPos1.getValor();
        }
        if("BTNYPOS2".equalsIgnoreCase(param))
        {
            return (int)pFondo.pVolt.BtnYPos2.getValor();
        }
        if("BTNXPOS".equalsIgnoreCase(param))
        {
            return (int)pFondo.pTime.btnXPos.getValor();
        }
        if("BTNINTENS".equalsIgnoreCase(param))
        {
            return (int)pFondo.pTime.btnIntens.getValor();
        }
        if("BTNFOCO".equalsIgnoreCase(param))
        {
            return (int)pFondo.pTime.btnFoco.getValor();
        }
        if("BTNHOLDOFF".equalsIgnoreCase(param))
        {
            return (int)pFondo.pTime.btnHoldOff.getValor();
        }
        if("BTNLEVEL".equalsIgnoreCase(param))
        {
            return (int)pFondo.pTime.btnLevel.getValor();
        }
        if("BTNCALIDAD".equalsIgnoreCase(param))
        {
            return (int)pFondo.pCalidad.BtnCalidad.getValor();
        }
        if("ACDC1".equalsIgnoreCase(param))
        {
            return senyales.canal1.getTipoSenyal();
        }
        if("ACDC2".equalsIgnoreCase(param))
        {
            return senyales.canal2.getTipoSenyal();
        }
        if("YPOS1".equalsIgnoreCase(param))
        {
            return senyales.canal1.getYPos();
        }
        if("YPOS2".equalsIgnoreCase(param))
        {
            return senyales.canal2.getYPos();
        }
        if("XPOS".equalsIgnoreCase(param))
        {
            return senyales.getXPos();
        }
        if("INTENS".equalsIgnoreCase(param))
        {
            return pFondo.pantalla.getIntens();
        }
        if("FOCO".equalsIgnoreCase(param))
        {
            return pFondo.pantalla.getFoco();
        }
        if("HOLDOFF".equalsIgnoreCase(param))
        {
            return senyales.getHoldOff();
        }
        if("LEVEL".equalsIgnoreCase(param))
        {
            return senyales.getNivelDisparo();
        }
        if("CALIDAD".equalsIgnoreCase(param))
        {
            return senyales.getCalidad();
        } else
        {
            return 0;
        }
    }

    public float getValueFloat(String param)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
        }
        if("BTNTIME".equalsIgnoreCase(param))
        {
            return pFondo.pTime.btnTime.getValor();
        }
        if("BTNTIMEFINO".equalsIgnoreCase(param))
        {
            return pFondo.pTime.btnTime.getValorFino();
        }
        if("BTNVOLT1".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnVolt1.getValor();
        }
        if("BTNVOLT2".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnVolt2.getValor();
        }
        if("BTNVOLTFINO1".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnVolt1.getValorFino();
        }
        if("BTNVOLTFINO2".equalsIgnoreCase(param))
        {
            return pFondo.pVolt.BtnVolt2.getValorFino();
        }
        if("TIME".equalsIgnoreCase(param))
        {
            return senyales.getTime();
        }
        if("VOLT1".equalsIgnoreCase(param))
        {
            return senyales.canal1.getVDiv();
        }
        if("VOLT2".equalsIgnoreCase(param))
        {
            return senyales.canal2.getVDiv();
        } else
        {
            return 0.0F;
        }
    }

    public String getValueString(String param)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
        }
        return "";
    }

    private void setRandomValueInt(String param)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        if(!"BTNACDC1".equalsIgnoreCase(param));
        setValueInt(param, (int)(Math.random() * (double)3));
        if("BTNACDC2".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)3));
        }
        if("BTNYPOS1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnYPos1.setPosicion((int)(Math.random() * (double)291));
        }
        if("BTNYPOS2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnYPos2.setPosicion((int)(Math.random() * (double)291));
        }
        if("BTNXPOS".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnXPos.setPosicion((int)(Math.random() * (double)40));
        }
        if("BTNINTENS".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnIntens.setPosicion((int)(Math.random() * (double)25));
        }
        if("BTNFOCO".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnFoco.setPosicion((int)(Math.random() * (double)14));
        }
        if("BTNHOLDOFF".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)46));
        }
        if("BTNLEVEL".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnLevel.setPosicion((int)(Math.random() * (double)93));
        }
        if("BTNCALIDAD".equalsIgnoreCase(param))
        {
            pFondo.pCalidad.BtnCalidad.setPosicion((int)(Math.random() * (double)9));
        }
        if("ACDC1".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)3));
        }
        if("ACDC2".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)3));
        }
        if("YPOS1".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)291) - 145);
        }
        if("YPOS2".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)291) - 145);
        }
        if("XPOS".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)40) - 20);
        }
        if("INTENS".equalsIgnoreCase(param))
        {
            setValueInt(param, ((int)(Math.random() * (double)25) * 255) / 24);
        }
        if("FOCO".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)14));
        }
        if("HOLDOFF".equalsIgnoreCase(param))
        {
            setValueInt(param, ((int)(Math.random() * (double)46) * 100) / 45);
        }
        if("LEVEL".equalsIgnoreCase(param))
        {
            setValueInt(param, ((int)(Math.random() * (double)93) * 100) / 46 - 100);
        }
        if("CALIDAD".equalsIgnoreCase(param))
        {
            setValueInt(param, (int)(Math.random() * (double)10) + 1);
        }
    }

    private void setRandomValueFloat(String param)
    {
        PFondo pFondo = osc.pFondo;
        GestionSenyales senyales = osc.senyales;
        double voltValues[] = {
            20D, 10D, 5D, 2D, 1.0D, 0.5D, 0.20000000000000001D, 0.10000000000000001D, 0.050000000000000003D, 0.02D,
            0.01D, 0.0050000000000000001D, 0.002D, 0.001D, 0.00050000000000000001D
        };
        double timeValues[] = {
            0.20000000000000001D, 0.10000000000000001D, 0.050000000000000003D, 0.02D, 0.01D, 0.0050000000000000001D, 0.002D, 0.001D, 0.00050000000000000001D, 0.00020000000000000001D,
            0.0001D, 5.0000000000000002E-005D, 2.0000000000000002E-005D, 1.0000000000000001E-005D, 5.0000000000000004E-006D, 1.9999999999999999E-006D, 9.9999999999999995E-007D, 4.9999999999999998E-007D
        };
        if("BTNTIME".equalsIgnoreCase(param))
        {
            pFondo.pTime.btnTime.setPosicion((int)(Math.random() * (double)12));
        }
        if("BTNTIMEFINO".equalsIgnoreCase(param))
        {
            setValueFloat(param, (float)(Math.random() * (double)45 * 0.5D));
        }
        if("BTNVOLT1".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnVolt1.setPosicion((int)(Math.random() * (double)14));
        }
        if("BTNVOLT2".equalsIgnoreCase(param))
        {
            pFondo.pVolt.BtnVolt2.setPosicion((int)(Math.random() * (double)14));
        }
        if("BTNVOLTFINO1".equalsIgnoreCase(param))
        {
            setValueFloat(param, (float)(Math.random() * (double)45 * 0.5D));
        }
        if("BTNVOLTFINO2".equalsIgnoreCase(param))
        {
            setValueFloat(param, (float)(Math.random() * (double)45 * 0.5D));
        }
        if("TIME".equalsIgnoreCase(param))
        {
            setValueFloat(param, (float)timeValues[(int)(Math.random() * (double)18)]);
        }
        if("VOLT1".equalsIgnoreCase(param))
        {
            setValueFloat(param, (float)voltValues[(int)(Math.random() * (double)12)]);
        }
        if("VOLT2".equalsIgnoreCase(param))
        {
            setValueFloat(param, (float)voltValues[(int)(Math.random() * (double)12)]);
        }
    }

    public void setRandomValue(String param)
    {
        if(!hasParameter(param))
        {
            System.out.println("No existe el par\341metro:" + param + ".");
            return;
        } else
        {
            setRandomValueInt(param);
            setValueBoolean(param, Math.random() >= 0.5D);
            setRandomValueFloat(param);
            return;
        }
    }

    public void setRandomValueAll()
    {
        String params[] = getAllParameters();
        for(int i = 10; i < params.length; i++)
        {
            setRandomValue(params[i]);
        }

    }

    public void executeSimulator()
    {
        osc.configuraSenyal();
    }

    public boolean playSimulator()
    {
        GestionSenyales senyales = osc.senyales;
        boolean hasActive = senyales.isSuspend();
        if(!hasActive)
        {
            senyales.reanudar();
        }
        return hasActive;
    }

    public void resetSimulator()
    {
        stop();
        init();
    }

    public void pauseSimulator()
    {
        GestionSenyales senyales = osc.senyales;
        if(!senyales.isSuspend())
        {
            senyales.suspender();
        }
    }

    public void stopSimulator()
    {
        resetSimulator();
    }
}
